package net.java.efurture.judu.web.config.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class UrlUtils {
	
	
	/**
	 * 创建链接辅助定位链接
	 * */
	public static String toAdLinkUrl(long id, String locationToken, String lang){
		String url = "http://ta.lanxijun.com/map.htm?id=" + id 
				+ "&token=" + locationToken +"&lang=" + lang;
		return url;
	}

	
	/**
	 * 创建通知链接
	 * */
	public static String toNotifyUrl(long deviceId, String lang){
		String appUrl = "mapta://mapta/reportLocation?fromDeviceId=" + deviceId;
		try {
			String url  = "http://ta.lanxijun.com?appUrl=" + URLEncoder.encode(appUrl, "UTF-8") + 
					"&lang=" + lang;
			return url;
		} catch (UnsupportedEncodingException e) {
			return "http://ta.lanxijun.com";
		}
	}
	
	
	/**
	 *  根据shortUrlToken生产短链接
	 * */
	public static String toShortUrl(String shortUrlToken){
		String shortUrl = "http://ta.lanxijun.com/mm/" + shortUrlToken;
		return shortUrl;
	}
}
