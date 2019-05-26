package net.java.efurture.reader.biz.feed.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.java.efurture.reader.biz.feed.FeedWrapperUtils;
import net.java.efurture.reader.biz.feed.RssFeedClient;
import net.java.efurture.reader.biz.spider.client.FeedClient;
import net.java.efurture.reader.biz.spider.client.ResponseUtils;
import net.java.efurture.reader.clean.synd.SyndCleaner;
import net.java.efurture.reader.mybatis.domain.FeedDO;

import org.apache.commons.lang.exception.ExceptionUtils;

import com.google.code.efurture.common.ao.BaseAO;
import com.google.code.efurture.common.result.Result;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;

/**
 * http://www.iteye.com/topic/112048
 * 
 * http://www.jarvana.com/jarvana/view/net/java/dev/rome/rome/1.0.0/rome-1.0.0-javadoc.jar!/index.html
 * 
 * */
public class DefaultRssFeedClient extends BaseAO implements RssFeedClient{

	@SuppressWarnings("unchecked")
	@Override
	public Result<List<SyndEntry>> grapFeedFromSite(FeedDO feed, Map<Object, Object> lastHeaders) {
		Result<List<SyndEntry>> result = this.createResult();
   	    try{
   		     Result<SyndFeed> syndFeedResult = FeedClient.grapFeedUrl(feed.getUrl(), lastHeaders);
   		     result.getModels().put(HEADER_KEY, syndFeedResult.getModels().get(ResponseUtils.CONTEXT_KEY));
			 if(!syndFeedResult.isSuccess() || syndFeedResult.getResult() == null){ //有可能被阻止读取，导致读取的数据不是有效的feed数据
				 List<SyndEntry> entries = new LinkedList<SyndEntry>();
				 result.setResult(entries);
				 result.setSuccess(true);
		         return result;
			 }
			
             SyndFeed syndFeed = syndFeedResult.getResult();
			 List<SyndEntry> entries =   syndFeed.getEntries();
			 List<SyndEntry> filterEntries = FeedWrapperUtils.filterSyndFeed(entries, lastHeaders);
			 
			 FeedWrapperUtils.adapterForStandardSynedFeed(filterEntries, syndFeed, feed);
			 
			 //清理Feed
			 SyndCleaner.clean(filterEntries);
			 if(result.getModels().get(HEADER_KEY) instanceof Map<?, ?>){
				 ((Map<Object, Object>)result.getModels().get(HEADER_KEY)).put("synfeedCount", filterEntries.size());
			 }
			 result.setResult(filterEntries);
			 result.setSuccess(true);
	         return result;
		}catch(Exception e){
			 logger.error("grapFeedFromSite error" + feed.getUrl() +  "-->" + feed.getId() +  ExceptionUtils.getRootCauseMessage(e));
			 result.setThrowable(e);
			 result.setSuccess(false);
			 return result;
		}
	}







	
	
	
	
	
	
	

}
