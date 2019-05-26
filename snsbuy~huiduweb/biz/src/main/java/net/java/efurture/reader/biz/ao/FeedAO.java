package net.java.efurture.reader.biz.ao;

import java.util.List;

import net.java.efurture.reader.mybatis.domain.FeedDO;
import net.java.efurture.reader.mybatis.query.FeedQuery;

import com.google.code.efurture.common.result.Result;

public interface FeedAO {
	
	/**
	 * 添加feed
	 * */
	public Result<Boolean> addFeed(FeedDO feed);
	
	
	/**
	 * feed
	 * */
	public Result<Boolean> updateFeed(FeedDO feed);
	
	/**
	 * 获取Feed列表, 并统计总数，总数放到query对象中
	 * */
	public  Result<List<FeedDO>> findFeedListByQuery(FeedQuery query);
	
	
	/**
	 * 删除feed
	 * */
	public Result<Boolean> deleteFeed(long feedId);
	
	
	public Result<FeedDO> getFeedById(long feedId);

}
