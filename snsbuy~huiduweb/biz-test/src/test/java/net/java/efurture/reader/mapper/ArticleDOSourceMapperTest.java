package net.java.efurture.reader.mapper;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.java.efurture.reader.mybatis.domain.ArticleDO;
import net.java.efurture.reader.mybatis.domain.enums.ArticleStatusEnum;
import net.java.efurture.reader.mybatis.mapper.ArticleSourceMapper;
import net.java.efurture.reader.mybatis.query.ArticleQuery;

import org.junit.Assert;
import org.junit.Test;

public class ArticleDOSourceMapperTest extends BaseMapperTest {
	
	@Resource
	ArticleSourceMapper articleSourceMapper;
	
	@Test
	public void testOk(){
		Assert.assertNotNull(articleSourceMapper);
	}
	
	
	
	@Test
	public void testInsert(){
		articleSourceMapper.deleteByPrimaryKey(24L);
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
        
		int effectCount = articleSourceMapper.insert(article);
		Assert.assertTrue(effectCount > 0);
		
		
		
	}
	

	@Test
	public void testUpdate(){
		ArticleDO article = new ArticleDO();
		article.setId(24L);
		article.setAuthor("谷宝剑跟新");
		int effectCount = articleSourceMapper.updateByPrimaryKey(article);
		Assert.assertTrue(effectCount > 0);
	}
	
	
	
	@Test
	public void testSelectByQuery(){
		ArticleQuery query = new ArticleQuery();
		query.setFeedId(30L);
		query.setStatus(ArticleStatusEnum.NORMAL.getValue());
		query.setIncludeContent(Boolean.TRUE);
		List<ArticleDO> articleList = articleSourceMapper.selectByQuery(query);
		
		System.out.println(articleList);
		
		articleSourceMapper.countByQuery(query);
	}

	
	@Test
	public void selectById(){
		
		ArticleDO article = articleSourceMapper.selectByPrimaryKey(24L);
		System.out.println(article.getContent());
		articleSourceMapper.deleteByPrimaryKey(24L);
	}
	
	
}
