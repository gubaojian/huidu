package net.java.efurture.reader.judu;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.java.efurture.reader.biz.spider.JuduSpider;
import net.java.efurture.reader.mybatis.domain.FeedDO;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.code.efurture.common.result.Result;
import com.sun.syndication.feed.synd.SyndEntry;

import junit.framework.TestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-biz-admin-dev-context-config.xml"})  
public class JuduSpliderTest  extends TestCase{
	
	@Resource
	JuduSpider juduSpider;
	
	@Test
	public void testJuduSpider(){
		FeedDO feed = new FeedDO();
		feed.setId(8L);
		feed.setSite("http://coolshell.cn");
		feed.setUrl("http://coolshell.cn/feed");
		
		
		Map<Object,Object> info = new HashMap<Object,Object>(); 
		juduSpider.grapAndSaveArticleOnSite(feed, info);
	}
	
	
	@Test
	public void testJuduSpiderFrom163(){
		FeedDO feed = new FeedDO();
		feed.setId(8L);
		feed.setSite("http://gubaojian.blog.163.com");
		feed.setUrl("http://gubaojian.blog.163.com/rss");
		
		
		Map<Object,Object> info = new HashMap<Object,Object>(); 
		juduSpider.grapAndSaveArticleOnSite(feed, info);
	}
	
	
	@Test
	public void testJuduSpiderFromRobim(){
		FeedDO feed = new FeedDO();
		feed.setId(18L);
		feed.setSite("http://gubaojian.blog.163.com");
		feed.setUrl("http://robbinfan.com/rss");
		
		
		Map<Object,Object> info = new HashMap<Object,Object>(); 
		juduSpider.grapAndSaveArticleOnSite(feed, info);
		
	}
	
	
	@Test
	public void testUicss(){
		FeedDO feed = new FeedDO();
		feed.setId(18L);
		feed.setSite("http://gubaojian.blog.163.com");
		feed.setUrl("http://uicss.cn/feed/");
		
		
		Map<Object,Object> info = new HashMap<Object,Object>(); 
		juduSpider.grapAndSaveArticleOnSite(feed, info);
		
	}
	
	@Test
	public void testJDON(){ 
		FeedDO feed = new FeedDO();
		feed.setId(18L);
		feed.setSite("http://www.jdon.com");
		feed.setUrl("http://www.jdon.com/rss");
		
		
		Map<Object,Object> info = new HashMap<Object,Object>(); 
		juduSpider.grapAndSaveArticleOnSite(feed, info);
		
	
		
	}
	
	
	@Test
	public void testAtom(){
		FeedDO feed = new FeedDO();
		feed.setId(18L);
		feed.setSite("http://www.jdon.com");
		feed.setUrl("http://blog.codingnow.com/index.xml");
		
		
		Map<Object,Object> info = new HashMap<Object,Object>(); 
		juduSpider.grapAndSaveArticleOnSite(feed, info);
		
	}
	
	
	@Test
	public void testCsdn(){
		FeedDO feed = new FeedDO();
		feed.setId(24L);
		feed.setSite("http://blog.csdn.net/cenwenchu79/rss/list");
		feed.setUrl("http://blog.csdn.net/cenwenchu79/rss/list");
		Map<Object,Object> info = new HashMap<Object,Object>(); 
		juduSpider.grapAndSaveArticleOnSite(feed, info);
	}
	
	
	//http://blog.chedushi.com
	
	//FIXME  http://blog.codingnow.com/atom.xml 获取不到作者信息 
	
	

}
