package net.java.efurture.reader.admin.web.controller.ta;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.LocaleResolver;

import com.google.code.efurture.common.result.Result;

import net.java.efurture.reader.admin.web.constants.ControllerName;
import net.java.mapyou.ao.LocationAO;
import net.java.mapyou.mybatis.domain.LocationDO;
import net.java.mapyou.mybatis.enums.LocationStatus;

@Controller
@RequestMapping(ControllerName.MAP)
public class Map {

	
	@Resource
	LocationAO locationAO;
	
	@Resource
	LocaleResolver  localeResolver;

	
	@RequestMapping(method = RequestMethod.GET)
	public String doGet(HttpServletRequest request, Model model){
		long id = NumberUtils.toLong(request.getParameter("id"));
		String token = request.getParameter("token");
		String lang = request.getParameter("lang");
		if(StringUtils.isEmpty(lang)){
			lang = localeResolver.resolveLocale(request).getLanguage().toLowerCase();
		}
		model.addAttribute("lang", lang);
		Result<LocationDO> result = locationAO.getLocationById(id, token);
		model.addAttribute("message", result.getResultCode().getMessage());
		if(result.isSuccess()){
			if(!(result.getResult().getStatus() == LocationStatus.AD_FAILED.getValue() 
					|| result.getResult().getStatus() == LocationStatus.AD_SUCCESS.getValue())){
				model.addAttribute("token", result.getResult().getLocationToken());
				model.addAttribute("id", result.getResult().getId());
				return "/ta/map";
			}
		}	
		return "/ta/tips";
	}
}
