package net.java.efurture.reader.biz.ao;

import java.util.List;

import com.google.code.efurture.common.result.Result;

import net.java.efurture.reader.mybatis.domain.ArticleDO;
import net.java.efurture.reader.mybatis.query.ArticleQuery;
import net.java.efurture.reader.mybatis.query.CategoryArticleQuery;

public interface ArticleAO {
	
	
	
	/**
	 * 文章排序需要处理。
	 * */
	public  Result<Boolean>  addArticle(ArticleDO article);
	
	/**
	 * 获取记录总数，返回当前页数据
	 * */
	public Result<List<ArticleDO>> findArticleByQuery(ArticleQuery query);
	
	/**
	 * 仅返回当前页数据，并返回是否有下一页
	 * */
	public Result<List<ArticleDO>> findArticleByQueryForApp(ArticleQuery query);
	
	
	
	public Result<ArticleDO> getArticleById(long id);
	
	
	public Result<ArticleDO> getArticleByIdFromSource(long id);
	
	
	
	public Result<Boolean> deleteArticleById(long id);
	
	
	
	
	public Result<List<ArticleDO>> getArticleListByCategoryArticleQuery(CategoryArticleQuery query);
	
	
	
	
	
	
	
	
	

}
