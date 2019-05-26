package net.java.efurture.reader.feed;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import net.java.efurture.reader.biz.feed.RssFeedClient;
import net.java.efurture.reader.biz.feed.impl.DefaultRssFeedClient;
import net.java.efurture.reader.mybatis.domain.FeedDO;

import org.apache.http.HttpHeaders;
import org.junit.Assert;
import org.junit.Test;

import com.google.code.efurture.common.result.Result;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEntry;

public class RssFeedClientTest extends TestCase{
	
	private RssFeedClient rssFeedClient = new DefaultRssFeedClient();
	
	

		
	
		
		
		@Test
		public void testRssFeedClientDate(){
			FeedDO feed = new FeedDO();
			feed.setSite("http://blog.codingnow.com/");
			feed.setUrl("http://blog.silverna.org/index/rss");
			
			
			Map<Object,Object> info = new HashMap<Object,Object>(); 
			
			Result<List<SyndEntry>> rssResult = rssFeedClient.grapFeedFromSite(feed, info);
			System.out.println(rssResult.getThrowable());
			Assert.assertTrue(rssResult.isSuccess());
			
			for(SyndEntry entry : rssResult.getResult()){
				SyndContent content = (SyndContent) entry.getContents().get(0);
				System.out.println(content.getValue());
				break;
			}
		}	
		
		@Test
		public void testRssFeedClientEncoding(){
			FeedDO feed = new FeedDO();
			feed.setSite("http://blog.codingnow.com/");
			feed.setUrl("http://blog.codingnow.com/atom.xml");
			
			
			Map<Object,Object> info = new HashMap<Object,Object>(); 
			
			Result<List<SyndEntry>> rssResult = rssFeedClient.grapFeedFromSite(feed, info);
			System.out.println(rssResult.getThrowable());
			Assert.assertTrue(rssResult.isSuccess());
			
			for(SyndEntry entry : rssResult.getResult()){
				SyndContent content = (SyndContent) entry.getContents().get(0);
				System.out.println(content.getValue());
				break;
			}
		}	
		@Test
		public void testRssFeedClientRedirect(){
			FeedDO feed = new FeedDO();
			feed.setSite("http://ditsing.com/");
			feed.setUrl("http://ditsing.com/atom.xml");
			
			
			Map<Object,Object> info = new HashMap<Object,Object>(); 
			
			Result<List<SyndEntry>> rssResult = rssFeedClient.grapFeedFromSite(feed, info);
			System.out.println(rssResult.getThrowable());
			Assert.assertTrue(rssResult.isSuccess());
			
			for(SyndEntry entry : rssResult.getResult()){
				SyndContent content = (SyndContent) entry.getContents().get(0);
				System.out.println(content.getValue());
				break;
			}
		}	
		
	@Test
	public void testRssFeedClient(){
		FeedDO feed = new FeedDO();
		feed.setSite("http://coolshell.cn");
		feed.setUrl("http://coolshell.cn/feed");
		
		
		Map<Object,Object> info = new HashMap<Object,Object>(); 
		
		Result<List<SyndEntry>> rssResult = rssFeedClient.grapFeedFromSite(feed, info);
		System.out.println(rssResult.getThrowable());
		Assert.assertTrue(rssResult.isSuccess());
		
		for(SyndEntry entry : rssResult.getResult()){
			SyndContent content = (SyndContent) entry.getContents().get(0);
			System.out.println(content.getValue());
			break;
		}
	}
	
	
	@Test
	public void testLastModifiedHeaderRssFeedClient(){
		FeedDO feed = new FeedDO();
		feed.setSite("http://gubaojian.blog.163.com");
		feed.setUrl("http://blog.sina.com.cn/rss/1952722131.xml");
		
		
		Map<Object,Object> info = new HashMap<Object,Object>(); 
		info.put(HttpHeaders.LAST_MODIFIED, "Mon, 15 Jul 2013 03:17:12 GMT+8");
		Result<List<SyndEntry>> rssResult = rssFeedClient.grapFeedFromSite(feed, info);
		Assert.assertTrue(rssResult.isSuccess());
		Assert.assertEquals(rssResult.getResult().size(), 0);
		
		for(SyndEntry entry : rssResult.getResult()){
			SyndContent content = (SyndContent) entry.getContents().get(0);
			System.out.println(content.getValue());
		}
	}
	
	
	@Test 
	public void testNoAuthor(){
		FeedDO feed = new FeedDO();
		feed.setSite("http://robbinfan.com");
		feed.setUrl("http://robbinfan.com/rss");
		
		
		Map<Object,Object> info = new HashMap<Object,Object>(); 
		
		Result<List<SyndEntry>> rssResult = rssFeedClient.grapFeedFromSite(feed, info);
		System.out.println(rssResult.getThrowable());
		Assert.assertTrue(rssResult.isSuccess());
		
		for(SyndEntry entry : rssResult.getResult()){
			System.out.println(entry.getAuthor()   + "  " + entry.getCategories());
		}
	}
	
	
	
	
	
	
	
	@Test
	public void testNoAuthorTwo(){
		
		FeedDO feed = new FeedDO();
		feed.setSite("http://robbinfan.com");
		feed.setUrl("http://blog.codingnow.com/atom.xml");
		
		
		Map<Object,Object> info = new HashMap<Object,Object>(); 
		
		Result<List<SyndEntry>> rssResult = rssFeedClient.grapFeedFromSite(feed, info);
		System.out.println(rssResult.getThrowable());
		Assert.assertTrue(rssResult.isSuccess());
		
		for(SyndEntry entry : rssResult.getResult()){
			System.out.println(entry.getAuthor()   + "  " + entry.getCategories());
		}
		
		
	}
	
	
	
	@Test
	public void testDBAClient(){
		
		FeedDO feed = new FeedDO();
		feed.setSite("http://dbanotes.net/feed");
		feed.setUrl("http://dbanotes.net/feed");
		
		
		Map<Object,Object> info = new HashMap<Object,Object>(); 
		
		Result<List<SyndEntry>> rssResult = rssFeedClient.grapFeedFromSite(feed, info);
		System.out.println(rssResult.getThrowable());
		Assert.assertTrue(rssResult.isSuccess());
		
		for(SyndEntry entry : rssResult.getResult()){
			System.out.println(entry.getAuthor()   + "  " + entry.getCategories());
		}
	}
	
	
	public void testChuBa(){
		
		FeedDO feed = new FeedDO();
		feed.setSite("http://blog.yufeng.info/feed");
		feed.setUrl("http://blog.yufeng.info/feed");
		
		
		Map<Object,Object> info = new HashMap<Object,Object>(); 
		
		Result<List<SyndEntry>> rssResult = rssFeedClient.grapFeedFromSite(feed, info);
		System.out.println(rssResult.getThrowable());
		Assert.assertTrue(rssResult.isSuccess());
		
		for(SyndEntry entry : rssResult.getResult()){
			System.out.println(entry.getAuthor()   + "  " + entry.getCategories());
		}
		
	}
	
	public void testPublishDateLost(){
		FeedDO feed = new FeedDO();
		feed.setSite("http://www.laruence.com/feed");
		feed.setUrl("http://www.laruence.com/feed");
		
		
		Map<Object,Object> info = new HashMap<Object,Object>(); 
		
		Result<List<SyndEntry>> rssResult = rssFeedClient.grapFeedFromSite(feed, info);
		System.out.println(rssResult.getThrowable());
		Assert.assertTrue(rssResult.isSuccess());
		
		for(SyndEntry entry : rssResult.getResult()){
			System.out.println(entry  + "  " + entry.getCategories());
		}
		
	}
	
	
	public void testAuthorLost(){
		FeedDO feed = new FeedDO();
		feed.setSite("http://feedpress.me/lucifr");
		feed.setUrl("http://feedpress.me/lucifr");
		
		
		Map<Object,Object> info = new HashMap<Object,Object>(); 
		
		Result<List<SyndEntry>> rssResult = rssFeedClient.grapFeedFromSite(feed, info);
		System.out.println(rssResult.getThrowable());
		Assert.assertTrue(rssResult.isSuccess());
		
		for(SyndEntry entry : rssResult.getResult()){
			System.out.println(entry  + "  " + entry.getCategories());
		}

		
	}
	
	
	public void testPublushDateLost(){
		FeedDO feed = new FeedDO();
		feed.setSite("http://blog.csdn.net/perfect_db/rss/list");
		feed.setUrl("http://blog.csdn.net/perfect_db/rss/list");
		
		
		Map<Object,Object> info = new HashMap<Object,Object>(); 
		
		Result<List<SyndEntry>> rssResult = rssFeedClient.grapFeedFromSite(feed, info);
		System.out.println(rssResult.getThrowable());
		Assert.assertTrue(rssResult.isSuccess());
		
		for(SyndEntry entry : rssResult.getResult()){
			System.out.println(entry  + "  " + entry.getCategories());
		}
		
	}
	
	

	public void testPublushDateLostTwo(){
		FeedDO feed = new FeedDO();
		feed.setSite("http://reads.wangjunyu.net/rss");
		feed.setUrl("http://reads.wangjunyu.net/rss");
		
		
		Map<Object,Object> info = new HashMap<Object,Object>(); 
		
		Result<List<SyndEntry>> rssResult = rssFeedClient.grapFeedFromSite(feed, info);
		System.out.println(rssResult.getThrowable());
		Assert.assertTrue(rssResult.isSuccess());
		
		for(SyndEntry entry : rssResult.getResult()){
			System.out.println(entry  + "  " + entry.getCategories());
		}
		
	}
	
	
	
	

	
	
	public void testCategoryLost(){
		
		FeedDO feed = new FeedDO();
		feed.setSite("http://feedpress.me/lucifr");
		feed.setUrl("http://feedpress.me/lucifr");
		
		
		Map<Object,Object> info = new HashMap<Object,Object>(); 
		
		Result<List<SyndEntry>> rssResult = rssFeedClient.grapFeedFromSite(feed, info);
		System.out.println(rssResult.getThrowable());
		Assert.assertTrue(rssResult.isSuccess());
		
		for(SyndEntry entry : rssResult.getResult()){
			System.out.println(entry  + "  " + entry.getCategories());
		}
	}


	
	// http://zhulch.itpub.net/rss/rss10/17395
	
}
