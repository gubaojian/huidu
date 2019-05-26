package net.java.efurture.reader.admin.web.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.java.efurture.reader.admin.web.constants.ControllerName;
import net.java.efurture.reader.admin.web.constants.WebConstant;
import net.java.efurture.reader.admin.web.util.CsrfToken;
import net.java.efurture.reader.admin.web.util.LoginUtils;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping(ControllerName.LOGIN_OUT)
public class LoginOut {
	

	
	@RequestMapping(method ={RequestMethod.GET, RequestMethod.POST})
	public String doLoginOut(Model model, HttpServletRequest request, HttpServletResponse response){
		if(LoginUtils.isLogin(request)){
			if(!CsrfToken.checkCsrfToken()){
			   model.addAttribute(WebConstant.MESSAGE_KEY,  "你的请求已过期。");
			   //return ControllerName.SELLER_TIPS;
		     }
			request.getSession().invalidate();
			Cookie[] cookies = request.getCookies();
			for(Cookie cookie : cookies){
				cookie.setMaxAge(0);
				response.addCookie(cookie);
			}
		}
		return null;
	}

}
