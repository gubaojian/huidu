 package net.java.efurture.judu.web.controller.service;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.java.efurture.judu.web.constants.CacheTime;
import net.java.efurture.judu.web.constants.ControllerName;
import net.java.efurture.judu.web.util.FromUtils;
import net.java.efurture.judu.web.util.ResponseCacheUtils;
import net.java.efurture.reader.biz.ao.ArticleAO;
import net.java.efurture.reader.biz.ao.CategoryAO;
import net.java.efurture.reader.mybatis.domain.ArticleDO;
import net.java.efurture.reader.mybatis.domain.CategoryDO;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.code.efurture.common.result.Result;


/*
 * http://127.0.0.1:8080/service/articleDetail.json?id=1020
 * */
@Controller
@RequestMapping(ControllerName.ARTICLE_DETAIL)
public class ArticleDetail {
	
	@Resource
	ArticleAO articleAO;
	
	@Resource
	CategoryAO categoryAO;
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody Result<ArticleDO> doGet(HttpServletRequest request, HttpServletResponse response){
		long id = NumberUtils.toLong(request.getParameter("id"));
		Result<ArticleDO> articleResult = articleAO.getArticleById(id);
		if(articleResult.isSuccess() && articleResult.getResult() != null){
			Result<List<CategoryDO>> categoryResult = categoryAO.getArticleCategory(id);
			if(categoryResult.isSuccess()){
				articleResult.getModels().put("categoryList", categoryResult.getResult());
				if(FromUtils.isFromCDN(request)){
					 ResponseCacheUtils.publicCache(response, CacheTime.ONE_YEAR);
				}else{
				    ResponseCacheUtils.publicCache(response, CacheTime.HALF_HOUR);
				}
			}else{
				ResponseCacheUtils.noneCache(response);
			}	
		}else{
			ResponseCacheUtils.noneCache(response);
		}
		
		
		
		return articleResult;
	}

}
