package net.java.efurture.huidu.service;

import java.util.List;

import net.java.efurture.huidu.config.Params;
import net.java.efurture.huidu.domain.Article;
import retrofit.http.GET;
import retrofit.http.Query;

import com.google.code.callback.GUCallback;

public interface ArticleService {

	 //获取主页文章列表
	 @GET(ServiceConstants.HOME_ARTICLE_LIST)
     public void getArticleList(@Query(Params.PAGE_NUM) int pageNum, GUCallback<List<Article>> callback);
	
     //获取文章详情
	
	 @GET(ServiceConstants.ARTICLE_DETAIL)
     public void getArticleDetailById(@Query(Params.ID) long articleId, GUCallback<Article> articleDetail);
	
     //获取类目文章列表
	 @GET(ServiceConstants.CATEGORY_ARTICLE_LIST)
     public void getCategoryArticleList(@Query(Params.CATEGORY_ID) long categoryId, @Query(Params.PAGE_NUM) int pageNum, GUCallback<List<Article>> callback);
     
     //获取收藏文章列表
	 @GET(ServiceConstants.FAVORITE_ARTICLE_LIST)
     public void getFavoriteArticleList(@Query(Params.CATEGORY_IDS) String categoryIds,  @Query(Params.PAGE_NUM) int pageNum, GUCallback<List<Article>> callback);
}
