package net.java.efurture.reader.admin.web.controller.ta;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.LocaleResolver;

@Controller
@RequestMapping("/d")
public class D {
	
	@Resource
	LocaleResolver  localeResolver;

	@RequestMapping(method = RequestMethod.GET)
	public String doGet(HttpServletRequest request, Model model){
		String lang = request.getParameter("lang");
		String env = request.getParameter("env");
		if(StringUtils.isEmpty(lang)){
			lang = localeResolver.resolveLocale(request).getLanguage().toLowerCase();
		}
		model.addAttribute("lang", lang);
		model.addAttribute("env", env);
		if("true".equals(request.getParameter("hiddenDownload"))){
			model.addAttribute("hiddenDownload", true);
		}
		String appUrl = request.getParameter("appUrl");
		if (!StringUtils.isEmpty(appUrl)) {
			model.addAttribute("appUrl", appUrl);
		}
		return "/ta/d";
	}
}
