package net.java.efurture.reader.admin.web.controller.me;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.java.efurture.judu.web.util.FromUtils;
import net.java.efurture.reader.admin.web.constants.ControllerName;




@Controller
@RequestMapping(ControllerName.ME_INDEX)
public class Index {
	
	
	@RequestMapping(method = RequestMethod.GET)
	public String doGet(HttpServletRequest request, HttpServletResponse response, Model model){
		if(FromUtils.isFromTA(request)){
			String appUrl = request.getParameter("appUrl");
			if (!StringUtils.isEmpty(appUrl)) {
				model.addAttribute("appUrl", appUrl);
			}
			
			
		    return "/ta/d";	
		}
		return ControllerName.ME_INDEX;
	}

}
