package net.java.efurture.reader.biz.timer.task.claimer.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.java.efurture.reader.biz.timer.task.util.JSONUtils;
import net.java.efurture.reader.mybatis.domain.TaskDO;
import net.java.efurture.reader.mybatis.domain.enums.TaskPriorityEnum;
import net.java.efurture.reader.mybatis.domain.enums.TaskStatusEnum;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseTaskClaimer {
	
	/**
	 * 执行失败后推迟多长时间执行,单位分钟。默认一个默认90分钟更新一次
	 * */
	public static final  int DLAY_MINUTES = 61; 
	
	protected Logger  logger = LoggerFactory.getLogger(this.getClass());
	
	
	/**
	 * 此任务结束，不再执行
	 * */
	protected TaskDO wrapFullCompleteTask(TaskDO source, String message){
		TaskDO  target = new TaskDO();
		target.setId(source.getId());
		target.setLastExecuteTime(new Date());
	    target.setStatus(TaskStatusEnum.SYN_END.getValue());
		target.setGmtModified(new Date());
		Map<String,String> tips = new HashMap<String,String>();
		tips.put("message", message);
		target.setExecuteInfo(JSONUtils.toJSONString(tips));
		return target;
	}
	
	
	/**
	 * 执行失败后，优先级调低，推迟一个小时执行
	 * */
	protected TaskDO wrapFailedTask(TaskDO source, String message){
		TaskDO  target = new TaskDO();
		Date now = new Date();
		target.setId(source.getId());
		target.setLastExecuteTime(now);
	    target.setScheduleTime(DateUtils.addMinutes(now,  DLAY_MINUTES));
	    target.setPriority(TaskPriorityEnum.LOW_PRIORITY.getValue());
		target.setGmtModified(new Date());
		Map<String,String> tips = new HashMap<String,String>();
		tips.put("message", message);
		target.setExecuteInfo(JSONUtils.toJSONString(tips));
		return target;
	}
	
	/**
	 * 执行成功后,调到下次执行
	 * */
	protected TaskDO wrapCircleSuccessTask(TaskDO source){
		TaskDO  target = new TaskDO();
		target.setId(source.getId());
		target.setLastExecuteTime(new Date());
		target.setScheduleTime(DateUtils.addMinutes(new Date(), DLAY_MINUTES));
		target.setGmtModified(new Date());
		return target;
	}
	
	
}
