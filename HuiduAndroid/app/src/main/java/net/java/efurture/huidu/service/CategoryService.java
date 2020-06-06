package net.java.efurture.huidu.service;

import java.util.List;

import net.java.efurture.huidu.config.Params;
import net.java.efurture.huidu.domain.Category;
import retrofit.http.GET;
import retrofit.http.Query;

import com.google.code.callback.GUCallback;

public interface CategoryService {
	
	@GET(ServiceConstants.CATEGORY_LIST)
	public void getCategoryList(@Query(Params.PAGE_NUM) int pageNum, GUCallback<List<Category>> callback);

	@GET(ServiceConstants.SEARCH_CATEGORY)
	public void searchCategoryList(@Query(Params.KEYWORD) String keyword,   @Query(Params.PAGE_NUM) int pageNum, GUCallback<List<Category>> callback);


}
