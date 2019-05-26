package net.java.efurture.reader.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;

import com.google.code.efurture.common.pinyin.PinyinUtils;

import net.java.efurture.reader.mybatis.domain.CategoryDO;
import net.java.efurture.reader.mybatis.domain.enums.CategoryStatusEnum;
import net.java.efurture.reader.mybatis.mapper.CategoryDOMapper;
import net.java.efurture.reader.mybatis.query.CategoryQuery;

public class CategoryDOMapperTest  extends BaseMapperTest{
	
	@Resource
	CategoryDOMapper categoryDOMapper;
	
	
	@Test
	public void testInsert(){
		categoryDOMapper.deleteByPrimaryKey(2L);
		CategoryDO category = new CategoryDO();
		category.setId(2);
		category.setName("你好");
		category.setPinyin(PinyinUtils.toPinyin("你好"));
		category.setSort(2);
		category.setStatus(CategoryStatusEnum.NORMAL.getValue());
		category.setGmtCreate(new Date());
		
		categoryDOMapper.insert(category);
	}
	
	
	
	@Test
	public void testSelectByQuery(){
		CategoryQuery query = new CategoryQuery();
		List<String> nameList = new ArrayList<String>();
		nameList.add("ok");
		nameList.add("你好");
		query.setNameList(nameList);
		
		categoryDOMapper.selectByQuery(query);
		
		categoryDOMapper.countByQuery(query);
		
	}
	
	
	@Test
	public void testSelectArticleCategory(){
		List<CategoryDO>  articleCategoryList = categoryDOMapper.selectArticleCategory(422);
		
		Assert.assertTrue( articleCategoryList.size() >= 1);
		
	}
	
	

}
