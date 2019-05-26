package net.java.efurture.reader.biz.timer.task.claimer.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.java.efurture.reader.biz.spider.JuduSpider;
import net.java.efurture.reader.biz.timer.task.claimer.TaskClaimer;
import net.java.efurture.reader.biz.timer.task.util.JSONUtils;
import net.java.efurture.reader.mybatis.domain.FeedDO;
import net.java.efurture.reader.mybatis.domain.TaskDO;
import net.java.efurture.reader.mybatis.domain.enums.FeedStatusEnum;
import net.java.efurture.reader.mybatis.mapper.FeedDOMapper;

import org.apache.commons.lang.exception.ExceptionUtils;

import com.google.code.efurture.common.result.Result;

public abstract class AbstractFeedArticleSynTaskClaimer extends BaseTaskClaimer implements TaskClaimer {

	@Resource
	FeedDOMapper feedDOMapper;
	
	@Resource
	JuduSpider juduSpider;
	
	
	@Override
	public final TaskDO executeTask(TaskDO task) {
	  Long feedId =   task.getRelateId();
		
	  FeedDO feed = feedDOMapper.selectByPrimaryKey(feedId);	
	  if(feed == null || feed.getStatus() == null || feed.getStatus() != FeedStatusEnum.NORMAL.getValue() || feed.getUrl() == null){
		 if(feed == null){
		     return this.wrapFullCompleteTask(task, "Feed " + feedId + " 数据异常 " );
		 }else{
			 return this.wrapFullCompleteTask(task, "Feed " + feedId + " 数据异常 "  + feed.getSite() );
		 }
	  }
	

	  @SuppressWarnings("unchecked")
	  Map<Object,Object> info = (Map<Object, Object>) JSONUtils.fromJson(task.getExecuteInfo(), Map.class);
	  if(info == null){
		  info = new HashMap<Object,Object>();
	  }
	
	  Result<Map<Object,Object>> grapSaveResult = juduSpider.grapAndSaveArticleOnSite(feed, info);
	  if(!grapSaveResult.isSuccess()){
		  return this.wrapFailedTask(task, ExceptionUtils.getRootCauseMessage(grapSaveResult.getThrowable()) + feed.getSite());  
	  }
	   TaskDO target = this.wrapCircleSuccessTask(task);	  
	   target.setExecuteInfo(JSONUtils.toJSONString(grapSaveResult.getResult()));
	   return target;
	}


}
