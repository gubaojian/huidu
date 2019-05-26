package net.java.efurture.reader.admin.web.controller.ta;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.LocaleResolver;

import com.google.code.efurture.common.result.Result;

import net.java.mapyou.ao.ShortUrlAO;
import net.java.mapyou.mybatis.domain.ShortUrlDO;

@Controller
@RequestMapping("/mm/{token}")
public class ShortUrl {

	@Resource
	ShortUrlAO shortUrlAO;
	
	@Resource
	LocaleResolver  localeResolver;

	
	
	@RequestMapping(method = RequestMethod.GET)
	public String doGet(HttpServletRequest request, Model model, @PathVariable String token){
		String lang = request.getParameter("lang");
		if(StringUtils.isEmpty(lang)){
			lang = localeResolver.resolveLocale(request).getLanguage().toLowerCase();
		}
		model.addAttribute("lang", lang);
		Result<ShortUrlDO> result = shortUrlAO.fullUrl(token);
		if (result.isSuccess()) {
			if(result.getResult() != null){
			    model.addAttribute("url", result.getResult().getUrl());
			}
		}
		return "/ta/shortUrl";
	}
}
