package net.java.efurture.reader.biz.spider;

import java.util.List;
import java.util.Map;

import net.java.efurture.reader.mybatis.domain.FeedDO;

import com.google.code.efurture.common.result.Result;
import com.sun.syndication.feed.synd.SyndEntry;

public interface JuduSpider {
	
	
	
	/**抓取Feed信息，保存并保存到数据库中, 返回响应头信息*/
	public Result<Map<Object, Object>> grapAndSaveArticleOnSite(FeedDO feed, Map<Object,Object> info);
	
	
	/**
	 * 保存feed信息到数据库
	 * */
	public Result<Boolean> saveFeedEntryList(FeedDO feed, List<SyndEntry> entries);

}
