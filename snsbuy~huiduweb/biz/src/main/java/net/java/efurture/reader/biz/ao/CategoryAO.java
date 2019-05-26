package net.java.efurture.reader.biz.ao;

import java.util.List;

import net.java.efurture.reader.mybatis.domain.CategoryDO;
import net.java.efurture.reader.mybatis.query.CategoryQuery;

import com.google.code.efurture.common.result.Result;

public interface CategoryAO {

	public Result<Boolean> addCategoryList(List<CategoryDO> categoryList);
	
	
	
	public Result<List<CategoryDO>> getCategoryListByQuery(CategoryQuery query);
	
	
	public Result<List<CategoryDO>> getCategoryListByQueryForApp(CategoryQuery query);
	
	
	
	public Result<Boolean> deleteCategoryById(int categoryId);
	
	
	public Result<Boolean> updateCategory(CategoryDO category);
	
	
	
	
	public Result<List<CategoryDO>> getArticleCategory(long articleId);
	
}
