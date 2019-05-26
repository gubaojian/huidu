package net.java.efurture.reader.mapper;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;


import net.java.efurture.reader.mybatis.domain.FeedDO;
import net.java.efurture.reader.mybatis.domain.enums.FeedStatusEnum;
import net.java.efurture.reader.mybatis.domain.enums.FeedTypeEnum;
import net.java.efurture.reader.mybatis.mapper.FeedDOMapper;
import net.java.efurture.reader.mybatis.query.FeedQuery;

public class FeedDOMapperTest extends BaseMapperTest {
	
	@Resource
	FeedDOMapper feedDOMapper;
  
	private static Long id;
	
	@Test
	public void testInsert(){
		FeedDO feedDO = new FeedDO();
		feedDO.setType(FeedTypeEnum.NOTMAL.getValue());
		feedDO.setStatus(FeedStatusEnum.NORMAL.getValue());
		feedDO.setSite("http://www.baidu.com");
		feedDO.setShortDesc("Feed添加单元测试");
		feedDO.setUrl("http://www.xinhuanet.com/politics/news_politics.xml");
		int effectCount = feedDOMapper.insert(feedDO);
		Assert.assertTrue(effectCount > 0);
		id = feedDO.getId();
	}
	
	@Test
	public void testSelect(){
		FeedDO feed = feedDOMapper.selectByPrimaryKey(id);
		Assert.assertNotNull(feed);
	}
	
	@Test
	public void testSelectByQuery(){
		FeedQuery query = new FeedQuery();
		query.setUrl("http://www.xinhuanet.com/politics/news_politics.xml");
		List<FeedDO> feeds = feedDOMapper.selectByQuery(query);
		Assert.assertTrue(feeds.size() > 0);
		
		int count = feedDOMapper.countByQuery(query);
		Assert.assertTrue(count > 0);
	}
	
	@Test
	public void testUpdateByPrimary(){
		FeedDO  target = new FeedDO();
		target.setId(id);
		target.setSite("http://www.163.com");
		int effectCount = feedDOMapper.updateByPrimaryKey(target);
		Assert.assertTrue(effectCount > 0);
		
		FeedDO feed = feedDOMapper.selectByPrimaryKey(id);
		Assert.assertEquals(feed.getSite(), target.getSite());
		
	}
	
	
	
}
