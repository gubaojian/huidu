package net.java.efurture.reader.admin.web.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import net.java.efurture.reader.admin.web.constants.WebConstant;
import net.java.efurture.reader.admin.web.util.LoginUtils;
import net.java.efurture.reader.biz.configure.Configure;
import org.apache.commons.codec.binary.Base64;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


/**
 * 跳转到https链接
 * */
public class LoginInterceptor  extends HandlerInterceptorAdapter {
	
	private String loginUrl = "/login.html";
	
	
	
	@Resource
	Configure configure;

	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		    throws Exception {
		if(!LoginUtils.isLogin(request)){
			String redirectURI =  request.getRequestURI();
			if(request.getQueryString() != null){
				redirectURI += "?" + request.getQueryString();
			}	
			String redirectLoginUrl = null;
			redirectLoginUrl =  configure.getServerUrl() + loginUrl + "?"+ WebConstant.REDIRECT_URL_PARAM_NAME + "=" + Base64.encodeBase64URLSafeString(redirectURI.getBytes());
			response.sendRedirect(redirectLoginUrl);
			return false;
		}
	    return true;
	}
	
	

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}



	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

}