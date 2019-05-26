package net.java.efurture.reader.admin.web.controller.me;

import java.util.List;

import javax.annotation.Resource;

import net.java.efurture.reader.admin.web.constants.ControllerName;
import net.java.efurture.reader.biz.ao.ArticleAO;
import net.java.efurture.reader.mybatis.domain.ArticleDO;
import net.java.efurture.reader.mybatis.domain.enums.ArticleStatusEnum;
import net.java.efurture.reader.mybatis.query.ArticleQuery;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.code.efurture.common.result.Result;



@Controller
@RequestMapping(ControllerName.ME_ARTICLE_LIST)
public class ArticleList {
	
	@Resource
	ArticleAO articleAO;
	
	
	@RequestMapping(method = RequestMethod.GET)
	public String doGet(@ModelAttribute("query") ArticleQuery articleQuery, Model model){
		if(articleQuery == null){
			articleQuery = new ArticleQuery();
		}
		articleQuery.setTitle(StringUtils.trimToNull(articleQuery.getTitle()));
		articleQuery.setIncludeContent(Boolean.FALSE);
		articleQuery.setPageSize(30);
		
		Result<List<ArticleDO>> result = articleAO.findArticleByQuery(articleQuery);
		if(!result.isSuccess()){
			model.addAttribute("message", result.getResultCode().getMessage());
			return ControllerName.ME_TIPS;
		}
		
		model.addAttribute("query", articleQuery);
		model.addAttribute("articleList", result.getResult());
		
		return ControllerName.ME_ARTICLE_LIST;
	}

}
