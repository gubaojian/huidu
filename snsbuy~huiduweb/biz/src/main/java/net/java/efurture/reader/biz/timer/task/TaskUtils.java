package net.java.efurture.reader.biz.timer.task;

import java.util.Date;

import net.java.efurture.reader.mybatis.domain.TaskDO;
import net.java.efurture.reader.mybatis.domain.enums.TaskPriorityEnum;
import net.java.efurture.reader.mybatis.domain.enums.TaskStatusEnum;
import net.java.efurture.reader.mybatis.domain.enums.TaskTypeEnum;

import org.apache.commons.lang.time.DateUtils;

public class TaskUtils {

	
	public static final TaskDO initNewTask(long relateId, TaskTypeEnum type){
		TaskDO taskDO = new TaskDO();
	     taskDO.setRelateId(relateId);
	     taskDO.setType(type.getValue());
	     taskDO.setStatus(TaskStatusEnum.SYN_CIRCLE.getValue());
	     Date now = new Date();
	     taskDO.setScheduleTime(DateUtils.addMinutes(now, 5));
	     taskDO.setPriority(TaskPriorityEnum.HIGH_PRIORITY.getValue());
	     taskDO.setGmtModified(now);
	     taskDO.setGmtCreate(now);
	     return taskDO;
	}
	
	
}
