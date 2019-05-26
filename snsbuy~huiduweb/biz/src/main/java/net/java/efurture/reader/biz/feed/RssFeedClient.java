package net.java.efurture.reader.biz.feed;

import java.util.List;
import java.util.Map;

import net.java.efurture.reader.mybatis.domain.FeedDO;

import com.google.code.efurture.common.result.Result;
import com.sun.syndication.feed.synd.SyndEntry;

public interface RssFeedClient {
	
	/**
	 * 抓取Feed信息, header信息放到 models 中
	 * */
	public Result<List<SyndEntry>> grapFeedFromSite(FeedDO feed, Map<Object,Object> info);

	
	public static final String HEADER_KEY = "headers";

}
