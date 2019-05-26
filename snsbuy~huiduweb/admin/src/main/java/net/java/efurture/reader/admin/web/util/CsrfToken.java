package net.java.efurture.reader.admin.web.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class CsrfToken {

	/**
	 * CSRF TOKEN
	 * */
    public static final String CSRF_TOKEN = "csrf_token";
	
	
    /**
     * 检查token
     * */
	public  static final boolean checkCsrfToken(){
		String sessionToken = currentToken();
		if(sessionToken == null){
			return false;
		}

		String requestToken =requestToken();
		if(!sessionToken.equals(requestToken)){
			return false;
		}
		
		return true;
	}
	
	
	public  String getHiddenField(){
		 return hiddenField();
	}
	/**
	 * form表单token隐藏域
	 * */
	public static final String hiddenField(){
		StringBuffer buf = new StringBuffer();
		buf.append("<input type=\"hidden\" name=\"");
		buf.append(CSRF_TOKEN);
		buf.append("\"  value=\"");
		buf.append(currentToken());
		buf.append("\"  />");
		return buf.toString();
	}
	
	public  String getParam(){
		return param();
	}
	
	/** 用于异步请求 */
	public static final String param(){
		StringBuffer buf = new StringBuffer();
		buf.append(CSRF_TOKEN);
		buf.append("=");
		buf.append(currentToken());
		return buf.toString();
	}
	
	

	private static final String requestToken(){
		ServletRequestAttributes   arrtibutes = (ServletRequestAttributes)RequestContextHolder
			      .getRequestAttributes();
		if(arrtibutes == null){
			return "";
		}
		HttpServletRequest request = arrtibutes.getRequest();
		return request.getParameter(CSRF_TOKEN);
	}
	
	/**
	 * 返回当前的token，如果session不存在，则创建一个
	 * */
	private static final String currentToken(){
		ServletRequestAttributes   arrtibutes = (ServletRequestAttributes)RequestContextHolder
			      .getRequestAttributes();
		if(arrtibutes == null){
			return "";
		}

		HttpServletRequest request = arrtibutes.getRequest();
		HttpSession session = request.getSession(true);
		if(session.getAttribute(CSRF_TOKEN) == null){
			session.setAttribute(CSRF_TOKEN, RandomStringUtils.randomAlphanumeric(6));
		}
		return session.getAttribute(CSRF_TOKEN).toString();
	}
	
	
	
	
	
	
	public static void main(String [] args){
		System.out.println(hiddenField());
		System.out.println(param());
	}
}
