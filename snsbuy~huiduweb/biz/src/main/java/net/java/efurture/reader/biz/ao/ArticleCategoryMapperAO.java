package net.java.efurture.reader.biz.ao;

import java.util.List;
import java.util.Map;

import com.google.code.efurture.common.result.Result;

import net.java.efurture.reader.mybatis.domain.ArticleCategoryMapperDO;

public interface ArticleCategoryMapperAO {
	
	
	/**
	 * 添加类目映射
	 * */
	public Result<Boolean> addArticleCategoryMapper(List<ArticleCategoryMapperDO> articleCategoryMapperList);
	
	
	
	public Result<Map<Integer,Integer>> categoryArticleCount(List<Integer> categoryIds);

}
