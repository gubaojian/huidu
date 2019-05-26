package net.java.efurture.reader.biz.timer.task.claimer.impl;

import net.java.efurture.reader.mybatis.domain.enums.TaskTypeEnum;

public class NormalFeedAritlceSynTaskClaimer extends AbstractFeedArticleSynTaskClaimer{
	
	@Override
	public TaskTypeEnum claimerTaskType() {
		return TaskTypeEnum.SYN_NORMAL;
	}


}
