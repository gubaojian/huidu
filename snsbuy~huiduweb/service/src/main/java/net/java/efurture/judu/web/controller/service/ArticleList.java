package net.java.efurture.judu.web.controller.service;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.java.efurture.judu.web.constants.CacheTime;
import net.java.efurture.judu.web.constants.ControllerName;
import net.java.efurture.judu.web.util.ResponseCacheUtils;
import net.java.efurture.reader.biz.ao.ArticleAO;
import net.java.efurture.reader.mybatis.domain.ArticleDO;
import net.java.efurture.reader.mybatis.domain.enums.ArticleStatusEnum;
import net.java.efurture.reader.mybatis.query.ArticleQuery;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.code.efurture.common.result.Result;


/**
 * 
 * http://127.0.0.1:8080/service/articleList.json
 * */
@Controller
@RequestMapping(ControllerName.ARTICLE_LIST)
public class ArticleList {

	@Resource
	ArticleAO articleAO;

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody Result<List<ArticleDO>> doGet( HttpServletRequest request, HttpServletResponse response){
		int pageNum = NumberUtils.toInt(request.getParameter("pageNum"), 1);
		ArticleQuery query = new ArticleQuery();
		query.setIncludeContent(Boolean.FALSE);
		query.setCurrentPageNum(pageNum);
		query.setStatus(ArticleStatusEnum.NORMAL.getValue());
		query.setPageSize(ControllerName.PAGE_SIZE);
		Result<List<ArticleDO>> articleListResult = articleAO.findArticleByQueryForApp(query);
		if(articleListResult.isSuccess()){
			 List<ArticleDO> articleList = articleListResult.getResult();
			 for(ArticleDO article : articleList){
				 article.setSort(null);
				 article.setUrl(null);
				 article.setStatus(null);
			 }
			 ResponseCacheUtils.noneCache(response);
		 }else{
			 ResponseCacheUtils.noneCache(response);
		 }
		 return articleListResult;
	}
}
