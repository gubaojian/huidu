package net.java.efurture.reader.image;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;

import net.java.efurture.reader.biz.spider.client.ClientUtils;
import net.java.efurture.reader.biz.spider.client.RequestUtils;
import net.java.efurture.reader.biz.spider.client.ResponseUtils;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageClient {
	
	private static final Logger logger = LoggerFactory.getLogger(ImageClient.class);
	
	/**
	 * 图片解析的相关key
	 * */
	public static final String KEY_INPUT = "input";
	public static final String KEY_EXTENSION = "extension";
	
	
	
	
	/**
	 * 图片合法的后缀
	 * */
	private static final Map<Pattern,String> patternExtension = new HashMap<Pattern,String>();
	static{
		patternExtension.put(Pattern.compile("\\.jpg", Pattern.CASE_INSENSITIVE), "jpg");
		patternExtension.put(Pattern.compile("\\.jpeg", Pattern.CASE_INSENSITIVE), "jpeg");
		patternExtension.put(Pattern.compile("\\.png", Pattern.CASE_INSENSITIVE), "png");
		patternExtension.put(Pattern.compile("\\.gif", Pattern.CASE_INSENSITIVE), "gif");
		patternExtension.put(Pattern.compile("\\.bmp", Pattern.CASE_INSENSITIVE), "bmp");
		patternExtension.put(Pattern.compile("\\.tiff", Pattern.CASE_INSENSITIVE), "tiff");
		patternExtension.put(Pattern.compile("\\.ico", Pattern.CASE_INSENSITIVE), "ico");
		patternExtension.put(Pattern.compile("\\.jp2", Pattern.CASE_INSENSITIVE), "jp2");
	}
	
	/**
	 * 图片合法的后缀
	 * */
	private static final Pattern  VALID_IMG_PATTERN = Pattern.compile("(jpg)|(jpeg)|(png)|(gif)|(bmp)|(tiff)|(ico)|(jp2)", Pattern.CASE_INSENSITIVE);
	
	
	

	private static String connectTimeoutDomain  = null;
	private static long connectTimeoutTime = System.currentTimeMillis();
	
	private static final long tryTimes = 1000*60*60; //一个小时
	
	public static Map<String,Object> grapImage(String url) throws ClientProtocolException, IOException, URISyntaxException{
		if(StringUtils.isEmpty(url)){
			return null;
		}
		
		HttpClient client = ClientUtils.buildGrapClient();
	    URL validUrl = null;
		try{
			 url = url.trim();
			 validUrl = new URL(url);
			 if(validUrl.getHost() == null){
				 return null;
			 }
			 if(validUrl.getHost().equals(connectTimeoutDomain)){
				 if((System.currentTimeMillis() - connectTimeoutTime) <  tryTimes){
					 return null;
				 }
			 }		
			 HttpGet get =  RequestUtils.toGet(url);
			 HttpResponse response = client.execute(get);
			 if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				 return toMap(url, validUrl.getPath(), response);
			 }
			 
			 if(!(response.getStatusLine().getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY 
					 || response.getStatusLine().getStatusCode() == HttpStatus.SC_MOVED_PERMANENTLY
					 || response.getStatusLine().getStatusCode() == HttpStatus.SC_SEE_OTHER
					 || response.getStatusLine().getStatusCode() == HttpStatus.SC_TEMPORARY_REDIRECT)){
				 if(response.getStatusLine().getStatusCode() == HttpStatus.SC_FORBIDDEN){
					 logger.warn("image url " + url + " access forbidden");
				 }
				return null;
			 }
			 
			 URI  location = ResponseUtils.getRedirectLocationURI(response);
			 if(location == null){
				return null;
			 }
			 //释放上一次请求
			 EntityUtils.consume(response.getEntity());
			 //发起定向的请求
			 get = RequestUtils.toGet(location);
			 response = client.execute(get);
			 if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				 Map<String,Object> map =  toMap(location.toString(), location.getPath(), response);
				 if(map.get(KEY_EXTENSION) == null){
					 map.put(KEY_EXTENSION, getExtensionFrom(url, validUrl.getPath()));
				 }
				 return map;
			 }
			 return null;
		}catch(Exception e){
			  logger.error("grap image Exception " + ExceptionUtils.getRootCauseMessage(e));
			  if(e instanceof ConnectTimeoutException){
				  if(validUrl != null){
					  connectTimeoutDomain =  validUrl.getHost();
					  connectTimeoutTime  = System.currentTimeMillis();
				  }
			  }
			 
		}finally{
			client.getConnectionManager().shutdown();
		}
		 return null;
	}
	
	private static final Map<String,Object> toMap(String url, String path, HttpResponse response) throws IOException{
		Map<String,Object> map = new HashMap<String,Object>();
		byte[] bts = ResponseUtils.toByteArray(response.getEntity());
		ByteArrayInputStream in = new ByteArrayInputStream(bts);
		map.put(KEY_INPUT, in);
		String extension = getExtensionFrom(url, path);
		
		/**
		 * 如果取不到，从响应头中取
		 * */
		if(extension == null){
			Header header = response.getFirstHeader(HttpHeaders.CONTENT_TYPE);
			if(header != null){
				String  value = header.getValue();
				if(value != null){
					String[] mimeTypes = value.split("/");
					if(mimeTypes.length == 2){
						if(mimeTypes[1] != null && VALID_IMG_PATTERN.matcher(mimeTypes[1]).find()){
							extension =  mimeTypes[1];
						}
					}
				}
			}
		}
		//放入扩展中
		map.put(KEY_EXTENSION, extension);
		return map;
	}
	
	private static String getExtensionFrom(String url, String path){
		String extension = FilenameUtils.getExtension(path);
		if(StringUtils.isEmpty(extension) || !VALID_IMG_PATTERN.matcher(extension).find()){
			extension = getExtensionFromUrl(url);
		}
		return extension;
	}
	
	private static String getExtensionFromUrl(String url){
		Set<Map.Entry<Pattern,String>> entries =  patternExtension.entrySet();
		for(Entry<Pattern,String> entry : entries){
			Pattern pattern = entry.getKey();
			if(pattern.matcher(url).find()){
				return entry.getValue();
			}
		}
		return null;
	}
	
	
	public  static void main(String [] args){
		
		System.out.println( VALID_IMG_PATTERN.matcher("png").find());
		System.out.println( VALID_IMG_PATTERN.matcher("text").find());
	}

}
