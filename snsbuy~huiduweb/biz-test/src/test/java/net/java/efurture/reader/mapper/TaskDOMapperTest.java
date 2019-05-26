package net.java.efurture.reader.mapper;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import junit.framework.Assert;

import net.java.efurture.reader.mybatis.domain.TaskDO;
import net.java.efurture.reader.mybatis.domain.enums.TaskPriorityEnum;
import net.java.efurture.reader.mybatis.domain.enums.TaskStatusEnum;
import net.java.efurture.reader.mybatis.domain.enums.TaskTypeEnum;
import net.java.efurture.reader.mybatis.mapper.TaskDOMapper;
import net.java.efurture.reader.mybatis.query.TaskQuery;

import org.junit.Test;

public class TaskDOMapperTest extends BaseMapperTest {
	
	@Resource
	private TaskDOMapper taskDOMapper;
	
	private static  Long taskId = null;
	
	@Test
	public void testInsert(){
		TaskDO task = new TaskDO();
		task.setRelateId(relateId);
		task.setScheduleTime(new Date());
		task.setGmtCreate(new Date());
		task.setLastExecuteTime(new Date());
		task.setPriority(TaskPriorityEnum.DEFAULT_PRIORITY.getValue());
		task.setStatus(TaskStatusEnum.SYN_CIRCLE.getValue());
		task.setType(TaskTypeEnum.SYN_HOT.getValue());
		taskDOMapper.insert(task);
		Assert.assertTrue(task.getId() > 0);
		taskId  = task.getId();
	}
	
	
	@Test
	public void testUpdate(){
		TaskDO target = new TaskDO();
		target.setId(taskId);
		target.setStatus(TaskStatusEnum.SYN_CIRCLE.getValue());
		target.setGmtModified(new Date());
		taskDOMapper.updateByPrimaryKey(target);
	}
	
	
	
	@Test
	public void testSelectByQuery(){
		TaskQuery query = new TaskQuery();
		query.setRelateId(relateId);
		List<TaskDO> list = taskDOMapper.selectByQuery(query);
		Assert.assertTrue(list.size() > 0);
		int count = taskDOMapper.countByQuery(query);
		Assert.assertTrue(count > 0);
	}
	
	
	@Test 
	public void testDataPrepare(){
      String sql = "insert into task(relate_id, type, status, priority, schedule_time, execute_info, gmt_create) values ";
		
		sql += "(1,  1,  1,  0,  now(), '任务执行情况', now())";
		sql += ",(1,  1,  1,  0,  now(), '任务执行情况', now())";
		sql += ",(1,  1,   1,  0,  now(), '任务执行情况', now())";
		sql += ",(1,  1,   1,  0,  now(), '任务执行情况', now())";
		sql += ",(1,  1,   1,  0,  now(), '任务执行情况', now())";
		sql += ",(1,  1,   1,  0,  now(), '任务执行情况', now())";
		sql += ",(1,  1,   1,  0,  now(), '任务执行情况', now())";
		sql += ",(1,  1,   1,  0,  now(), '任务执行情况', now())";
		sql += ",(1,  1,   1,  0,  now(), '任务执行情况', now())";
		sql += ",(1,  1,   1,  0,  now(), '任务执行情况', now())";
		sql += ",(1,  1,   1,  0,  now(), '任务执行情况', now())";
		sql += ",(1,  1,   1,  0,  now(), '任务执行情况', now())";
	
		
		jdbcTemplate.execute(sql);
	}
	
	

}
