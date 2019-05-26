package net.java.efurture.reader.biz.ao;

import java.util.List;

import net.java.efurture.reader.mybatis.domain.TaskDO;
import net.java.efurture.reader.mybatis.query.TaskQuery;



import com.google.code.efurture.common.result.Result;

public interface TaskAO {
	
	

	/**
	 * 添加任务，如果任务已经存在待同步的，则不添加，且返回成功，如果没有则添加到数据库中
	 * */
	public Result<Boolean> addTaskIfAbsent(TaskDO task);
	
	
	/**
	 * 更想任务状态
	 * */
	public Result<Boolean> updateTask(TaskDO task);
	
	
	/**
	 * 获取任务列表
	 * */
	public Result<List<TaskDO>> getTaskListByQuery(TaskQuery query);

}
