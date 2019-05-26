package net.java.efurture.reader.biz.timer.task.impl;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import net.java.efurture.reader.biz.ao.TaskAO;
import net.java.efurture.reader.biz.timer.task.Task;
import net.java.efurture.reader.biz.timer.task.claimer.TaskClaimer;
import net.java.efurture.reader.biz.timer.task.claimer.impl.BaseTaskClaimer;
import net.java.efurture.reader.mybatis.domain.TaskDO;
import net.java.efurture.reader.mybatis.domain.enums.TaskPriorityEnum;
import net.java.efurture.reader.mybatis.domain.enums.TaskStatusEnum;
import net.java.efurture.reader.mybatis.domain.enums.TaskTypeEnum;
import net.java.efurture.reader.mybatis.query.TaskQuery;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.google.code.efurture.common.result.Result;

public class DefaultClaimerTask implements Task, InitializingBean {

	private static final Logger log = LoggerFactory.getLogger(DefaultClaimerTask.class);
	
	@Resource
	TaskAO taskAO;
	
	/**
	 * 任务类型
	 * */
	private Byte type;
	
	/**
	 * 任务认领者
	 * */
	TaskClaimer  taskClaimer;
	
	/**
	 * 超时时间执行10分钟后停止执行， 默认一个小时执行一次
	 * */
	private long timeOut = 1000*60*50;


	@Override
	public void execute() {
		   final UncaughtExceptionHandler handler = Thread.getDefaultUncaughtExceptionHandler();
		    Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler(){
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				 log.error("TaskTimer End Exception ", e);
				 if(handler != null){
					 try{
					   handler.uncaughtException(t, e);
					 }catch( Throwable ie){
						 log.error("TaskTimer Handler End Exception ", ie);
					 }
				 }
			}
	    });
	  try{
	    int page = 1;
	    int pageSize = 60;
	    long startTime = System.currentTimeMillis();
	    log.warn("TaskTimer Start, type " + type);
	   
	    int count = 0;
	    int executeCount = 0;
		while(true){
		    TaskQuery query = new TaskQuery();
			query.setCurrentPageNum(1);  //一直首页执行，因为执行后task就被到后面了
			query.setPageSize(pageSize);
			query.setType(type);
			query.setStatus(TaskStatusEnum.SYN_CIRCLE.getValue());
			Date now = new Date();
			now = DateUtils.truncate(now, Calendar.HOUR); //下一个小时的11分前
			now = DateUtils.addHours(now, 1);
			now = DateUtils.addMinutes(now, 10);
			query.setScheduleTimeEnd(now);
			Result<List<TaskDO>> taskResult = taskAO.getTaskListByQuery(query);
			if(!taskResult.isSuccess()){
				log.warn("TaskTimer Break because of query task error, task type : " + type);
				break;
			}
			
			List<TaskDO> taskList = taskResult.getResult();
			if(page  == 1){
				count = query.getTotalCount();
			}
			if(taskList == null || taskList.size() <= 0){
				log.warn("TaskTimer Break because of task Empty: "  + type   + " page " + page + " pageSize " + pageSize   + " total count " + query.getTotalCount());
				break;
			}
			boolean shouldBreak = false;
			
			
			int poolSize = 12; //线程池大小
			ThreadPoolExecutor exectorService = new ThreadPoolExecutor(4, poolSize, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
			for(TaskDO task : taskList){
				 exectorService.submit(new TaskRunnable(task));
				 executeCount++;
			}
			exectorService.shutdown();
			while (!exectorService.awaitTermination(10, TimeUnit.SECONDS)) {  
				log.warn("TaskTimer PoolExecuting executing, complete" + exectorService.getCompletedTaskCount());
		    }   
			exectorService.shutdownNow();
			if((System.currentTimeMillis() - startTime) > timeOut){
				log.warn("TaskTimer End type " + type + "Execute timeout TaskTimeOut");
				shouldBreak = true;
				break;
			}
			log.warn("TaskTimer executing, total " + count  + " pageNum "  + page + " pageSize " + query.getPageSize() 
					+ " complete "  + exectorService.getCompletedTaskCount());
			//执行超时，退出登陆
			if(shouldBreak){
				log.warn("TaskTimer End type " + type + "Execute timeout TaskTimeOut");
				break;
			}
			page++;
	   }
	    //等待所有任务执行结束
	    log.warn("TaskTimer End type "+ type +",used: " + (System.currentTimeMillis() - startTime) + "ms total count " + count + " execute " + executeCount );
	  }catch(Throwable te){
		  log.error("TaskTimer End Exception ", te);
	  }
	}
	
	
	
	@Override
	public void afterPropertiesSet() throws Exception {
	    if(type == null || taskClaimer == null || taskAO == null || TaskTypeEnum.valueOf(type) == null
	    		|| !taskClaimer.claimerTaskType().equals(TaskTypeEnum.valueOf(type))){
	    	throw new IllegalArgumentException("Task type taskClaimer or taskAO must not be null. and task type must equal claimer type");
	    }
	}
	
	

	public void setType(Byte type) {
		this.type = type;
	}

	public void setTimeOut(long timeOut) {
		this.timeOut = timeOut;
	}



	public void setTaskClaimer(TaskClaimer taskClaimer) {
		this.taskClaimer = taskClaimer;
	}
	
	class TaskRunnable implements Runnable{
		private TaskDO task;
		
		public TaskRunnable(TaskDO task) {
			super();
			this.task = task;
		}

		@Override
		public void run() {
			try{
				TaskDO saveResult = taskClaimer.executeTask(task);
				if(saveResult != null){
					if(saveResult.getId() == null){
						saveResult.setId(task.getId());
					}
					taskAO.updateTask(saveResult);
				}
			}catch(Exception e){
				log.error("TimerTask Error " + type, e);
				TaskDO target = new TaskDO();
				target.setId(task.getId());
				target.setScheduleTime(DateUtils.addMinutes(task.getScheduleTime(), BaseTaskClaimer.DLAY_MINUTES));
				target.setLastExecuteTime(new Date());
				target.setExecuteInfo("执行异常:" + e.getClass().getSimpleName() + "，尝试" + BaseTaskClaimer.DLAY_MINUTES + "分钟后再次执行。");
				target.setPriority(TaskPriorityEnum.LOW_PRIORITY.getValue());
				taskAO.updateTask(target);
			}
		}

		public TaskDO getTask() {
			return task;
		}

		public void setTask(TaskDO task) {
			this.task = task;
		}
		
		
		
	};

	
	
	
}
