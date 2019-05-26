package net.java.efurture.judu.web.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

public class MessageUtils {
	
	public static String getMessage(String key, HttpServletRequest request, LocaleResolver localeResolver){
		String lang = request.getParameter("lang");
		if(StringUtils.isEmpty(lang)){
			lang = localeResolver.resolveLocale(request).getLanguage().toLowerCase();
		}
		if("zh".equals(lang)){
			return zhMessageMaps.get(key);
		}
		return enMessageMaps.get(key);
	}
	
	private static final Map<String, String> zhMessageMaps = new HashMap<String, String>();
	private static final Map<String, String> enMessageMaps = new HashMap<String, String>();
	private static void put(String key, String zh, String en){
		zhMessageMaps.put(key, zh);
		enMessageMaps.put(key, en);
	}
	
	static{
		 put("NotifyMessageSuccess", "远程定位成功",  "Remote GPS Locating Success");
		 put("NotifyMessageFailed", "远程定位失败",  "Remote GPS Locating Failed");
		 put("addDeviceSuccess", "%s 关注了您的设备",  "%s Follow With Your Device");
		 put("reportDeviceLocationSuccess", "%s 报告位置啦",  "%s Report Location");
	}
	

}
