package net.java.efurture.reader.biz.ao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.java.efurture.reader.biz.ao.TaskAO;
import net.java.efurture.reader.biz.resultcode.AOResultCode;
import net.java.efurture.reader.mybatis.domain.TaskDO;
import net.java.efurture.reader.mybatis.domain.enums.TaskPriorityEnum;
import net.java.efurture.reader.mybatis.domain.enums.TaskStatusEnum;
import net.java.efurture.reader.mybatis.mapper.TaskDOMapper;
import net.java.efurture.reader.mybatis.query.TaskQuery;


import org.springframework.util.CollectionUtils;

import com.google.code.efurture.common.ao.BaseAO;
import com.google.code.efurture.common.result.Result;
import com.google.code.efurture.common.resultcode.BaseResultCode;

public class TaskAOImpl extends BaseAO  implements TaskAO{
	
	@Resource
	private TaskDOMapper taskDOMapper;
	
	
	public Result<Boolean> addTaskIfAbsent(TaskDO task) {
		Result<Boolean> result = this.createResult();
		try{
			if(task.getRelateId() == null || task.getType() == null){
				result.setResult(false);
				result.setResultCode(AOResultCode.TASK_ARGS_MUST_HAVE);
				return result;
			}
			
			
			TaskQuery query = new TaskQuery();
			query.setRelateId(task.getRelateId());
			query.setType(task.getType());
			query.setStatus(task.getStatus());
			
			List<TaskDO> taskList = taskDOMapper.selectByQuery(query);
			
			//已经有相同的任务，无需添加, 更想信息
			if(!CollectionUtils.isEmpty(taskList)){
				task.setId(taskList.get(0).getId());
				this.updateTask(task);
				result.setResult(true);
				result.setSuccess(true);
				return result;
			}
			
			
			if(task.getPriority() == null){
				task.setPriority(TaskPriorityEnum.DEFAULT_PRIORITY.getValue());
			}
			
			if(task.getScheduleTime() == null){
				task.setScheduleTime(new Date());
			}
			
			task.setStatus(TaskStatusEnum.SYN_CIRCLE.getValue());
			task.setGmtCreate(new Date());
			task.setGmtModified(new Date());
			
			
			int id = taskDOMapper.insert(task);
			
			result.setSuccess(true);
			result.setResult(id > 0);
			return result;
		}catch(Exception e){
			logger.error("addTask Exception", e);
			result.setResult(false);
			result.setSuccess(false);
			result.setResultCode(BaseResultCode.SYSTEM_EXCEPTION);
			result.setThrowable(e);
			return result;
		}
	}


	@Override
	public Result<List<TaskDO>> getTaskListByQuery(TaskQuery query) {
		Result<List<TaskDO>> result = this.createResult();
		try{
			if(query == null){
				return result;
			}
			int count = taskDOMapper.countByQuery(query);
			query.setTotalCount(count);
			if(count <= 0){
				result.setResult(new ArrayList<TaskDO>());
				result.setSuccess(true);
				return result;
			}
			
			List<TaskDO> taskList = taskDOMapper.selectByQuery(query);
			result.setResult(taskList);
			result.setSuccess(true);
			return result;
		}catch(Exception e){
			logger.error("getTaskListByQuery Exception", e);
			result.setResultCode(BaseResultCode.SYSTEM_EXCEPTION);
			result.setThrowable(e);	
			result.setSuccess(false);
			return result;
		}
	}


	@Override
	public Result<Boolean> updateTask(TaskDO task) {
		Result<Boolean> result = this.createResult();
		try{
			if(task == null || task.getId() == null){
				result.setSuccess(false);
				result.setResult(false);
				result.setResultCode(AOResultCode.TASK_MUST_BE_VALID);
				return result;
			}
			
			
			task.setGmtModified(new Date());
			int effectCount = taskDOMapper.updateByPrimaryKey(task);
			result.setResult(effectCount > 0);
			result.setSuccess(true);
			return result;
		}catch(Exception e){
			logger.error("TaobaoSellerAO updateTask Exception", e);
			result.setSuccess(false);
			result.setResultCode(BaseResultCode.SYSTEM_EXCEPTION);
			result.setThrowable(e);
			return result;
		}
	}





}
