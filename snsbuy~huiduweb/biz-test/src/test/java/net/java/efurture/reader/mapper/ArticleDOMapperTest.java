package net.java.efurture.reader.mapper;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.java.efurture.reader.mybatis.domain.ArticleDO;
import net.java.efurture.reader.mybatis.domain.enums.ArticleStatusEnum;
import net.java.efurture.reader.mybatis.mapper.ArticleDOMapper;
import net.java.efurture.reader.mybatis.query.ArticleQuery;

import org.junit.Assert;
import org.junit.Test;

public class ArticleDOMapperTest extends BaseMapperTest {
	
	@Resource
	ArticleDOMapper articleDOMapper;
	
	@Test
	public void testOk(){
		Assert.assertNotNull(articleDOMapper);
	}
	
	
	
	@Test
	public void testInsert(){
		articleDOMapper.deleteByPrimaryKey(24L);
		ArticleDO article = new ArticleDO();
		article.setId(24L);
		article.setAuthor("谷宝剑");
		article.setContent("谷宝剑的博客文章");
        article.setFeedId(24L);
        article.setGmtCreate(new Date());
        article.setGmtModified(new Date());
        article.setTitle("文章标题");
        article.setUrl("http://www.baidu.com");
        article.setStatus(ArticleStatusEnum.NORMAL.getValue());
        
		int effectCount = articleDOMapper.insert(article);
		Assert.assertTrue(effectCount > 0);
		
		
		
	}
	
	@Test
	public void testUpdate(){
		ArticleDO article = new ArticleDO();
		article.setId(24L);
		article.setAuthor("谷宝剑跟新");
		int effectCount = articleDOMapper.updateByPrimaryKey(article);
		Assert.assertTrue(effectCount > 0);
	}
	
	
	
	@Test
	public void testSelectByQuery(){
		ArticleQuery query = new ArticleQuery();
		query.setFeedId(30L);
		query.setStatus(ArticleStatusEnum.NORMAL.getValue());
		query.setIncludeContent(Boolean.TRUE);
		List<ArticleDO> articleList = articleDOMapper.selectByQuery(query);
		
		System.out.println(articleList);
		
		articleDOMapper.countByQuery(query);
	}

	
	@Test
	public void selectById(){
		
		ArticleDO article = articleDOMapper.selectByPrimaryKey(24L);
		System.out.println(article.getContent());
		articleDOMapper.deleteByPrimaryKey(24L);
	}
	
	
}
