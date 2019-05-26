package net.java.efurture.judu.web.util;

import javax.servlet.http.HttpServletResponse;

import com.samaxes.filter.util.HTTPCacheHeader;

public class ResponseCacheUtils {	
	//时区的时差
	private static final long ZONE_TIME_OFFSET = 8*60*60*1000;
	
	/**
	 * 在客户端缓存，代理服务器也缓存
	 * */
	public  static final void publicCache(HttpServletResponse response, int seconds){
		long expire =  System.currentTimeMillis() + seconds*1000L + ZONE_TIME_OFFSET;  //中美时差
	    response.setHeader("Cache-Control", "public, max-age=" + seconds);
	    response.setDateHeader("Expires", expire);
	    response.setDateHeader("Last-Modified", expire);
	    if (response.containsHeader("Pragma")) {
            response.setHeader(HTTPCacheHeader.PRAGMA.getName(), null);
        }
	}

	/**
	 * 尽在客户端缓存，代理服务器不缓存
	 * */
	public  static final void privateCache(HttpServletResponse response, int seconds){
		long expire =  System.currentTimeMillis() + seconds*1000L + ZONE_TIME_OFFSET;  //中美时差
	    response.setHeader("Cache-Control", "private, max-age=" + seconds);
	    response.setDateHeader("Expires", expire);
	    response.setDateHeader("Last-Modified", expire);
	    if (response.containsHeader("Pragma")) {
            response.setHeader(HTTPCacheHeader.PRAGMA.getName(), null);
        }
	}
	
	/**
	 * 任何地方都不缓存
	 * */
	public  static final void noneCache(HttpServletResponse response){
		long expire =  System.currentTimeMillis()  + ZONE_TIME_OFFSET;  //中美时差
	    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	    response.setDateHeader("Expires", expire);
	    response.setDateHeader("Last-Modified", expire);
	    response.setHeader("Pragma", "no-cache");
	}
}
