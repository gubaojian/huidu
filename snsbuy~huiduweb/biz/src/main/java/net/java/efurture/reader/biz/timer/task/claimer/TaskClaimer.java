package net.java.efurture.reader.biz.timer.task.claimer;

import net.java.efurture.reader.mybatis.domain.TaskDO;
import net.java.efurture.reader.mybatis.domain.enums.TaskTypeEnum;



public interface TaskClaimer {
	
	/**
	 * 执行任务，返回需要存储的任务状态
	 * */
	public TaskDO executeTask(TaskDO source);
	
	
	/**
	 * 返回任务类型
	 * */
	public TaskTypeEnum claimerTaskType();

}
