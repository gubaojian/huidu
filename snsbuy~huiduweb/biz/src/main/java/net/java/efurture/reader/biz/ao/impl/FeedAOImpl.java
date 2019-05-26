package net.java.efurture.reader.biz.ao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.java.efurture.reader.biz.ao.FeedAO;
import net.java.efurture.reader.biz.resultcode.AOResultCode;
import net.java.efurture.reader.mybatis.domain.FeedDO;
import net.java.efurture.reader.mybatis.domain.enums.FeedStatusEnum;
import net.java.efurture.reader.mybatis.mapper.FeedDOMapper;
import net.java.efurture.reader.mybatis.query.FeedQuery;

import com.google.code.efurture.common.ao.BaseAO;
import com.google.code.efurture.common.result.Result;
import com.google.code.efurture.common.resultcode.BaseResultCode;

public class FeedAOImpl extends BaseAO implements FeedAO{

	@Resource
	FeedDOMapper feedDOMapper;
	
	@Override
	public Result<Boolean> addFeed(FeedDO feed) {
		Result<Boolean> result = this.createResult();
		try{
			if(feed.getSite() == null || feed.getUrl() == null || feed.getType() == null){
				result.setResult(false);
				result.setResultCode(AOResultCode.FEED_ARGS_MUST_NOT_NULL);
				return result;
			}
			
			
			FeedQuery query = new FeedQuery();
			query.setStatus(FeedStatusEnum.NORMAL.getValue());
			query.setSite(feed.getSite());
			
			
			int count = feedDOMapper.countByQuery(query);
			if(count > 0){
				result.setResult(false);
				result.setResultCode(AOResultCode.FEED_ALREADY_EXISTL);
				return result;
			}
			
			feed.setStatus(FeedStatusEnum.NORMAL.getValue());
			feed.setGmtCreate(new Date());
			feed.setGmtModified(new Date());
			int effectCount = feedDOMapper.insert(feed);
			result.setSuccess(true);
			result.setResult(effectCount > 0);
			return result;
		}catch(Exception e){
			logger.error("addFeed Exception", e);
			result.setResult(false);
			result.setSuccess(false);
			result.setResultCode(BaseResultCode.SYSTEM_EXCEPTION);
			result.setThrowable(e);
			return result;
		}
	}

	@Override
	public Result<Boolean> updateFeed(FeedDO feed) {
		Result<Boolean> result = this.createResult();
		try{
			Long id = feed.getId();
			if(id == null || id < 0){
				result.setResult(false);
				result.setResultCode(AOResultCode.FEED_ID_ILLEGAL);
				return result;
			}
			
			FeedDO  dbFeed = feedDOMapper.selectByPrimaryKey(id);
			if(dbFeed == null){
				result.setResult(false);
				result.setResultCode(AOResultCode.FEED_NOT_EXIST);
				return result;
			}
			
			if(dbFeed.getStatus() != FeedStatusEnum.NORMAL.getValue()){
				result.setResult(false);
				result.setResultCode(AOResultCode.FEED_STATUS_NOT_NORMAL);
				return result;
			}
			
			feed.setGmtModified(new Date());
			int effectCount = feedDOMapper.updateByPrimaryKey(feed);
			result.setSuccess(true);
			result.setResult(effectCount > 0);
			return result;
		}catch(Exception e){
			logger.error("updateFeed Exception", e);
			result.setResult(false);
			result.setSuccess(false);
			result.setResultCode(BaseResultCode.SYSTEM_EXCEPTION);
			result.setThrowable(e);
			return result;
		}
	}

	@Override
	public Result<Boolean> deleteFeed(long feedId) {
		Result<Boolean> result = this.createResult();
		try{
			Long id = feedId;
			if(id == null || id < 0){
				result.setResult(false);
				result.setResultCode(AOResultCode.FEED_ID_ILLEGAL);
				return result;
			}
			
			FeedDO  dbFeed = feedDOMapper.selectByPrimaryKey(id);
			if(dbFeed == null){
				result.setResult(false);
				result.setResultCode(AOResultCode.FEED_NOT_EXIST);
				return result;
			}
			
			if(dbFeed.getStatus() != FeedStatusEnum.NORMAL.getValue()){
				result.setResult(false);
				result.setResultCode(AOResultCode.FEED_STATUS_NOT_NORMAL);
				return result;
			}
			FeedDO feed = new FeedDO();
			feed.setId(id);
			feed.setStatus(FeedStatusEnum.DELETE.getValue());
			feed.setGmtModified(new Date());
			int effectCount = feedDOMapper.updateByPrimaryKey(feed);
			result.setSuccess(true);
			result.setResult(effectCount > 0);
			return result;
		}catch(Exception e){
			logger.error("deleteFeed Exception", e);
			result.setResult(false);
			result.setSuccess(false);
			result.setResultCode(BaseResultCode.SYSTEM_EXCEPTION);
			result.setThrowable(e);
			return result;
		}
	}

	@Override
	public Result<FeedDO> getFeedById(long feedId) {
		Result<FeedDO> result = this.createResult();
		try{
			FeedDO feed = feedDOMapper.selectByPrimaryKey(feedId);
			result.setSuccess(true);
			result.setResult(feed );
			return result;
		}catch(Exception e){
			logger.error("getFeedById Exception", e);
			result.setSuccess(false);
			result.setResultCode(BaseResultCode.SYSTEM_EXCEPTION);
			result.setThrowable(e);
			return result;
		}
	}

	@Override
	public Result<List<FeedDO>> findFeedListByQuery(FeedQuery query) {
		Result<List<FeedDO>> result = this.createResult();
		try{
			int count = feedDOMapper.countByQuery(query);
			query.setTotalCount(count);
			if(count <= 0){
				result.setResult(new ArrayList<FeedDO>());
				result.setSuccess(true);
				return result;
			}
			
			List<FeedDO> taskList = feedDOMapper.selectByQuery(query);
			result.setResult(taskList);
			result.setSuccess(true);
			return result;
		}catch(Exception e){
			logger.error("findFeedListByQuery Exception", e);
			result.setResultCode(BaseResultCode.SYSTEM_EXCEPTION);
			result.setThrowable(e);	
			result.setSuccess(false);
			return result;
		}
	}

}
