package net.java.efurture.reader.admin.web.controller.me;

import javax.annotation.Resource;

import net.java.efurture.reader.admin.web.constants.ControllerName;
import net.java.efurture.reader.biz.ao.ArticleAO;
import net.java.efurture.reader.clean.article.ArticleCleaner;
import net.java.efurture.reader.mybatis.domain.ArticleDO;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.code.efurture.common.result.Result;


@Controller
@RequestMapping(ControllerName.ME_ARTICLE_DETAIL)
public class AdminArticleDetail {
	
	
	@Resource
	ArticleAO articleAO;
	
	@RequestMapping(method = RequestMethod.GET)
	public String doGet(Long id,  Boolean source,  Model model){
		if(id == null){
			model.addAttribute("message", "文章标id不能为空");
			return ControllerName.ME_TIPS;
		}
		if(source== null){
			source = Boolean.TRUE;
		}
		
		Result<ArticleDO> result = null;
		if(source){
			result = articleAO.getArticleByIdFromSource(id); //重原文中取
		}else{
			result = articleAO.getArticleById(id);
		}
		
		if(!result.isSuccess()){
			model.addAttribute("message", result.getResultCode().getMessage());
			return ControllerName.ME_TIPS;
		}
		
		if(result.getResult() == null){
			model.addAttribute("message","文章不存在");
			return ControllerName.ME_TIPS;
		}
		
		ArticleDO article = result.getResult();
		model.addAttribute("source", source);	
	    model.addAttribute("article", article);	
		return ControllerName.ME_ARTICLE_DETAIL;
	}

}
