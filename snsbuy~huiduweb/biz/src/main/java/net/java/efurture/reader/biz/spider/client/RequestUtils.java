package net.java.efurture.reader.biz.spider.client;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import net.java.efurture.reader.utils.TrimUtils;
import net.java.efurture.reader.utils.URIUtils;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpMessage;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.protocol.HTTP;

public class RequestUtils {
	
	public static HttpGet toGet(String url) throws MalformedURLException, UnsupportedEncodingException, URISyntaxException{
		return toGet(url,  null);
	}
	/**
	 * 根据上下文，创建get请求
	 * */
	public static HttpGet toGet(String url,  Map<Object,Object> context) throws MalformedURLException, UnsupportedEncodingException, URISyntaxException{
		url = TrimUtils.trim(url);
		if(url.lastIndexOf('?') < 0){
			 url += "?";
		}
		URI validUri =  URIUtils.toURI(url);			
		return RequestUtils.toGet(validUri, context);
	}
	
	
	public static HttpGet toGet(URI uri) throws MalformedURLException, UnsupportedEncodingException, URISyntaxException{
		return toGet(uri,  null);
	}
	
	/**
	 * 根据上下文，创建get请求
	 * */
	public static HttpGet toGet(URI uri,  Map<Object,Object> context) throws MalformedURLException, UnsupportedEncodingException, URISyntaxException{			
		HttpGet get = new HttpGet(uri);
		if(context != null){
			RequestUtils.wrapContextForRequest(get, context);
		}
		get.removeHeaders(HTTP.CONN_KEEP_ALIVE);
		get.setHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_CLOSE);
		get.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		get.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.6,en;q=0.4");
		get.setHeader("Host", uri.getHost());
		return get;
	}
	
	/**
	 * 在请求头部添加部分请求头。
	 * */
	public static HttpMessage wrapContextForRequest(HttpMessage request, Map<Object,Object> context){
		String etagValue = (String) context.get(HttpHeaders.ETAG);
		if(etagValue != null){
			request.addHeader(HttpHeaders.IF_NONE_MATCH, etagValue);
		}
		
		String lastModifiedValue = (String) context.get(HttpHeaders.LAST_MODIFIED);
		if(lastModifiedValue != null){
			request.addHeader(HttpHeaders.IF_MODIFIED_SINCE, lastModifiedValue);
		}
		return request;
	}
	
	
	
	
	
	

}
