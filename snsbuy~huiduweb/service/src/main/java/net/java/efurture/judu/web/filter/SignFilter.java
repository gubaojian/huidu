package net.java.efurture.judu.web.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.google.code.efurture.common.sign.HMacUtils;
import com.samaxes.filter.OnceFilter;

public class SignFilter  extends OnceFilter{

	private Map<String,String> appIdSecretMap = new HashMap<String,String>();
	
    private static final String APP_HOME = "http://huidu.lanxijun.com";
    
    public static final String SIGN_WITHOUT_TIMESTAMP = "1";
    
    public static boolean debug = false;
	/**
	 * http://suijimimashengcheng.51240.com
	 * */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		Enumeration<String> names = filterConfig.getInitParameterNames();
		while(names.hasMoreElements()){
			String name = names.nextElement(); 
			if(name == null){
				continue;
			}
			name = StringUtils.trimToNull(name);
			String value = StringUtils.trimToNull(filterConfig.getInitParameter(name));
			if(StringUtils.length(name) == 16 && StringUtils.length(value) == 32){  //appId 长度16位， appSecret长度32位
				appIdSecretMap.put(name, value);
			}  
			if(StringUtils.length(name) == 32 && StringUtils.length(value) == 64 ){  //appId 长度32位， appSecret长度64位
				appIdSecretMap.put(name, value);
			} 
			if ("debug".equals(name)) {
				debug = BooleanUtils.toBoolean(value);
			}
		}
		if(appIdSecretMap.size() == 0){
			throw new ServletException("appId(16)及appSecret(32)或高强度秘钥没有配置，请配置");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void doOnceFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		if(debug){
			chain.doFilter(request, response);
			return;
		}
		String appId = request.getParameter("appId");
		String sign = request.getParameter("sign");
		String signType = request.getParameter("signType");
		String appSecret = appIdSecretMap.get(appId);
		if(StringUtils.isEmpty(appId) 
				|| StringUtils.isEmpty(appSecret) 
				|| StringUtils.isEmpty(sign)){
			response.sendRedirect(APP_HOME);
			return;
		}
		if(!SIGN_WITHOUT_TIMESTAMP.equals(signType)){
			//检查时间搓的有效性
			String timestamp = request.getParameter("timestamp");
			if(StringUtils.isEmpty(timestamp)){
				response.sendRedirect(APP_HOME);
				return;
			}
			//采用秒为单位
			long stamp = NumberUtils.toLong(timestamp);
			long now =   System.currentTimeMillis()/1000;
			long abs = Math.abs(now - stamp);
			if(abs > 12*60*60){  //有效期半个小时, 时间挫已经失效
				response.sendRedirect(APP_HOME);
				return;
			}
		}
		
		List<String> keys =  new ArrayList<String>();
		Set<String> names = request.getParameterMap().keySet();
		keys.addAll(names);
		//去除签名参数
		keys.remove("sign");
		
		//排序参数
		Collections.sort(keys);
		
		//获取所有参数
		StringBuilder message = new StringBuilder();
		message.append(appSecret);
		message.append('&');
		for(String key : keys){
			if(key == null || key.length() == 0){
				continue;
			}
			message.append(key);
			String value = request.getParameter(key);
			message.append('=');
			if(value != null){
				message.append(value);
			}
			message.append("&");
		}
		message.append(appSecret);
	
		String  messageSign = HMacUtils.base64HMacSha256(appSecret, message.toString());
		if(StringUtils.isEmpty(messageSign)){
			response.sendRedirect(APP_HOME);
			return;
		}
		
		if(!messageSign.equals(sign)){
			response.sendRedirect(APP_HOME);
			return;
		}
		chain.doFilter(request, response);
	}
	
	
	@Override
	public void destroy() {
		if(appIdSecretMap != null){
			appIdSecretMap.clear();
			appIdSecretMap = null;
		}
	}

}
