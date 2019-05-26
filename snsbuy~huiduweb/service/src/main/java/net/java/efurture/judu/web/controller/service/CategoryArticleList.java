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
import net.java.efurture.reader.mybatis.query.CategoryArticleQuery;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.code.efurture.common.result.Result;

/**
 * http://127.0.0.1:8080/service/categoryArticleList.json?categoryId=50
 * */
@Controller
@RequestMapping(ControllerName.CATEGORY_ARTICLE_LIST)
public class CategoryArticleList {
	
	@Resource
	ArticleAO articleAO;
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody Result<List<ArticleDO>> doGet(CategoryArticleQuery query, HttpServletRequest request, HttpServletResponse response){
		query.setStatus(ArticleStatusEnum.NORMAL.getValue());
		query.setPageSize(ControllerName.PAGE_SIZE);
		Result<List<ArticleDO>> articleResult = articleAO.getArticleListByCategoryArticleQuery(query);
		if(articleResult.isSuccess()){
			ResponseCacheUtils.publicCache(response, CacheTime.HALF_HOUR);
		}else{
			ResponseCacheUtils.noneCache(response);
		}
		
		return articleResult;
	}

}
