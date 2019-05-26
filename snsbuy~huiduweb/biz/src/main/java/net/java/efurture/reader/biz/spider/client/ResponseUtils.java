package net.java.efurture.reader.biz.spider.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.java.efurture.reader.utils.URIUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.efurture.common.result.DefaultResult;
import com.google.code.efurture.common.result.Result;
import com.google.code.efurture.common.result.ResultCode;

public class ResponseUtils {
	private static final Logger logger = LoggerFactory.getLogger(ResponseUtils.class);
	/**
	 * 响应的上下文
	 * */
	public static final String CHARSET_KEY = "charset";
	public static final String CONTEXT_KEY = "context";
	public static final long MAX_RESPONSE_LENGTH = 1024*1024*4; //最大4MB
	
	public static final Pattern DETECT_ENCODING_PATTERN  = Pattern.compile("(ENCODING=\"([A-Z1-9]+)\")");
	
	/**
	 * 将请求转换成，规定结果
	 * */
	public static Result<String> toResult(HttpResponse response){
		Result<String> result = new DefaultResult<String>();
        try {
      
         	String charset = HTTP.UTF_8;
        	    if(response.getEntity() != null){
        		  charset = EntityUtils.getContentCharSet(response.getEntity());
        	    }
        	    if(charset == null){
        	    	   charset = HTTP.UTF_8;
        	    }
           	result.getModels().put(CONTEXT_KEY, ResponseUtils.getContextFromResponse(response));
         	result.getModels().put(CHARSET_KEY, charset);
        	    if(response.getEntity() == null || response.getStatusLine().getStatusCode() == HttpStatus.SC_NOT_MODIFIED){
        	    	     result.setResultCode(GrapResultCode.RESPONSE_EMPTY);
     			 result.setSuccess(false);
     			 return result;
        	    }
        	    
           	if(response.getEntity() != null && response.getEntity().getContentLength() > MAX_RESPONSE_LENGTH){
        		    result.setResultCode(GrapResultCode.RESPONSE_TOO_LONG);
    			    result.setSuccess(false);
    			    return result;
           	}
           	
	       
	   
	        	if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
	        		try{
	        			byte[] bts = ResponseUtils.toByteArray(response.getEntity());
	        			if(bts != null){
	        				String detectEncoding = ResponseUtils.detectEncoding(bts, charset);
		    			    result.setResult(new String(bts, detectEncoding));
		    			    result.getModels().put(CHARSET_KEY, detectEncoding);
	        			}
	    			    result.setResultCode(GrapResultCode.SUCCESS);
	    	            result.setSuccess(true);
	    	            return result;
	        		}catch(IllegalArgumentException ille){
	        			 logger.error("Entity To String error", ille);
	        			 result.setResultCode(GrapResultCode.RESPONSE_TOO_LONG);
	     			 result.setSuccess(false);
	     			 return result;
	        		}
	        	}
	        	result.setResultCode(GrapResultCode.RESPONSE_STATUS_NOT_OK);
	        	result.setSuccess(false);
			return result;
		} catch (Exception e) {
			logger.error("Entity To Result error", e);
			Map<Object,Object> context = new HashMap<Object,Object>();
			context.put("exception", ExceptionUtils.getRootCauseMessage(e));
			result.setThrowable(e);
			result.getModels().put(CONTEXT_KEY, context);
			result.setResultCode(GrapResultCode.RESPONSE_TO_RESULT_ERROR);
			result.setSuccess(false);
			return result;
		} 
	}
	
	
	
    
    public static String detectEncoding(byte[] bts, String defaultEncoding){
        if(bts == null || bts.length <= 0){
        	   return defaultEncoding;
        }
        int length = Math.min(bts.length, 128);
        String head = new String(bts, 0, length);
        if(head.indexOf('>')  <= 0){
        	   return defaultEncoding;
        }
 	    String firstLine = head.substring(0, head.indexOf('>') + 1);
 	    firstLine = firstLine.toUpperCase();
 	    Matcher matcher = DETECT_ENCODING_PATTERN.matcher(firstLine);
 	    if(matcher.find()){
 	      	return matcher.group(matcher.groupCount());
 	    }
        return defaultEncoding; 
    }
    
    
    
    
    public static byte[] toByteArray(final HttpEntity entity) throws IOException {
        if (entity == null) {
            throw new IllegalArgumentException("HTTP entity may not be null");
        }
        InputStream instream = entity.getContent();
        if (instream == null) {
            return null;
        }
        try {
            if (entity.getContentLength() > Integer.MAX_VALUE) {
                throw new IllegalArgumentException("HTTP entity too large to be buffered in memory");
            }
            int i = (int)entity.getContentLength();
            if (i < 0) {
                i = 4096;
            }
            ByteArrayBuffer buffer = new ByteArrayBuffer(i);
            byte[] tmp = new byte[4096];
            int l;
            int length =0 ;
            while((l = instream.read(tmp)) != -1) {
                buffer.append(tmp, 0, l);
                length += l;
                if(length >  MAX_RESPONSE_LENGTH) {
                    throw new IllegalArgumentException("HTTP entity too large to be buffered in memory");
                }
            }
            return buffer.toByteArray();
        } finally {
            instream.close();
        }
    }

	
	public static Result<String> toFailedResult(Exception e, ResultCode resultCode){
		Map<Object,Object> context = new HashMap<Object,Object>();
		context.put("exception", ExceptionUtils.getRootCauseMessage(e));
		Result<String> result = new DefaultResult<String>();
		result.setThrowable(e);
		result.getModels().put(CONTEXT_KEY, context);
		result.setResultCode(resultCode);
		result.setSuccess(false);
		return result;
	}
	
	
	/**
	 * 获取对应的响应头
	 * */
	public static Map<Object,Object> getContextFromResponse(HttpResponse response){
		Map<Object,Object> context = new HashMap<Object,Object>();
		context.put("status", response.getStatusLine().getStatusCode());
		Header etagHeader = response.getFirstHeader(HttpHeaders.ETAG);
		if(etagHeader != null){
			context.put(HttpHeaders.ETAG,  etagHeader.getValue());
		}
		
		Header lastModifiedHeader =  response.getFirstHeader(HttpHeaders.LAST_MODIFIED);
		if(lastModifiedHeader != null){
			context.put(HttpHeaders.LAST_MODIFIED, lastModifiedHeader.getValue());
		}
		
		return context;
	}
	
	
	/**
	 * 获取重定向的链接
	 * */
	public static URI getRedirectLocationURI(HttpResponse response) throws UnsupportedEncodingException, MalformedURLException, URISyntaxException{
		Header header =  response.getFirstHeader("Location");
		 String location = null;
		 if(header != null){
			 location =  header.getValue();
		 }
		 if(StringUtils.isEmpty(location)){
			 return null;
		 }
		 URI uri = null;
		 String charset = null;
		 if(response.getEntity().getContentEncoding() != null){
			 charset = response.getEntity().getContentEncoding().getValue();
		 }else{
			 charset = HTTP.DEFAULT_CONTENT_CHARSET;
		 }
		 if(!(location.indexOf('?') > 0 && location.indexOf('%') > 0)){ //对url正确解码，转换成UTF-8字符串。防止乱码
				location = new String(location.getBytes(charset), "UTF-8");
		 }
		 uri = URIUtils.toURI(location);
		 return  uri;
	}
	
	public static void main(String args[]){
		String body = "<?xml version=\"1.0\" encoding=\"gb2312\"?>" +  "<feed xmlns=\"http://www.w3.org/2005/Atom\">中国中国中格式格式的格式的工地施工的身高多少根深蒂固";
	
	
		System.out.println(body.getBytes().length + "detect ensureEncoding " + detectEncoding(body.getBytes(), "UTF-8"));
	}

}
