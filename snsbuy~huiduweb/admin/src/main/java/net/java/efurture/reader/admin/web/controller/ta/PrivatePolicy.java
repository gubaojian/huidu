package net.java.efurture.reader.admin.web.controller.ta;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.LocaleResolver;

@Controller
@RequestMapping("/privatePolicy")
public class PrivatePolicy {
	
	@Resource
	LocaleResolver  localeResolver;

	@RequestMapping(method = RequestMethod.GET)
	public String doGet(HttpServletRequest request, Model model){
		String lang = request.getParameter("lang");
		if(StringUtils.isEmpty(lang)){
			lang = localeResolver.resolveLocale(request).getLanguage().toLowerCase();
		}
		model.addAttribute("lang", lang);
		return "/ta/privatePolicy";
	}
}
