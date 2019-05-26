package net.java.efurture.reader.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import net.java.efurture.reader.mybatis.domain.ArticleCategoryMapperDO;
import net.java.efurture.reader.mybatis.mapper.ArticleCategoryMapperDOMapper;

public class ArticleCategoryMapperDOMapperTest extends BaseMapperTest{

	@Resource
	ArticleCategoryMapperDOMapper articleCategoryMapperDOMapper;
	
	
	@Test
	public void testBatchInsert(){
	
		List<ArticleCategoryMapperDO>  articleCategoryMapperList = new ArrayList<ArticleCategoryMapperDO>();
		
		 for(int i=0; i<6; i++){
			 ArticleCategoryMapperDO articleCategoryMapper = new ArticleCategoryMapperDO();
			 
			 articleCategoryMapper.setArticleId(30L + i);
			 articleCategoryMapper.setCategoryId(34);
			 articleCategoryMapper.setGmtCreate(new Date());
			 articleCategoryMapperList.add( articleCategoryMapper);
		 }
		
		
		
		articleCategoryMapperDOMapper.batchInsert(articleCategoryMapperList);
		
		
		articleCategoryMapperDOMapper.deleteByArticleId(32L);
	}
	
	
	@Test
	public void testCategoryArticleCount(){
		
		List<Integer> categoryIds = new ArrayList<Integer>();
		categoryIds.add(1788);
		categoryIds.add(1793);
		categoryIds.add(1800);
		List<Map<String,Object>>  countList = articleCategoryMapperDOMapper.categoryArticleCount(categoryIds);
		System.out.println(countList);
	}
	
}
