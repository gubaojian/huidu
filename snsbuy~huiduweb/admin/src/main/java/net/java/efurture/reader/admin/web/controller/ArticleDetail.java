package net.java.efurture.reader.admin.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.java.efurture.judu.web.constants.CacheTime;
import net.java.efurture.judu.web.util.FromUtils;
import net.java.efurture.judu.web.util.ResponseCacheUtils;
import net.java.efurture.reader.admin.web.constants.ControllerName;
import net.java.efurture.reader.biz.ao.ArticleAO;
import net.java.efurture.reader.mybatis.domain.ArticleDO;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.code.efurture.common.result.Result;



@Controller
@RequestMapping(ControllerName.ARTICLE_DETAIL)
public class ArticleDetail {
	
	
	@Resource
	ArticleAO articleAO;
	
	@RequestMapping(method = RequestMethod.GET)
	public String doGet(HttpServletRequest request, HttpServletResponse response, Model model){
		long id = NumberUtils.toLong(request.getParameter("id"));
		if(id <= 0){
			return "redirect:http://huidu.lanxijun.com/";
		}
		
		Result<ArticleDO> result = articleAO.getArticleById(id);
		if(!result.isSuccess()){
			model.addAttribute("message", result.getResultCode().getMessage());
		}
		
		if(result.getResult() == null){
			model.addAttribute("message","文章不存在");
			ResponseCacheUtils.noneCache(response);
		}else{
			ArticleDO article = result.getResult();
		    model.addAttribute("article", article);	
		    response.setHeader("X-Frame-Options", "DENY");
		    if(FromUtils.isFromCDN(request)){
			    ResponseCacheUtils.publicCache(response, CacheTime.ONE_YEAR);
			}else{
		        ResponseCacheUtils.publicCache(response, CacheTime.ONE_DAY);
			}
		}
		return ControllerName.ARTICLE_DETAIL;
	
	}

}
