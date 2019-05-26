package net.java.efurture.reader.biz.spider.client;

import java.io.StringReader;
import java.util.Map;

import net.java.efurture.reader.biz.spider.client.GrapClient;
import net.java.efurture.reader.biz.spider.client.ResponseUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.efurture.common.result.DefaultResult;
import com.google.code.efurture.common.result.Result;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;

public class FeedClient {
	
	protected final static Logger logger = LoggerFactory.getLogger(FeedClient.class);
	

	private static final String [] DEFAULT_CHARSET = {"UTF-8","GBK", "GB2312",  "GB18030"};  //按顺序尝试，尝试成功停止

	
	public static Result<SyndFeed> grapFeedUrl(String url){
		return grapFeedUrl(url, null);
	}
	
	public static Result<SyndFeed> grapFeedUrl(String url, Map<Object,Object> context){
		 Result<SyndFeed>  syndFeedResult = new DefaultResult<SyndFeed>();
		 Result<String> result = GrapClient.grapSite(url, context);
		 if(result.getModels() != null){
		     syndFeedResult.getModels().putAll(result.getModels());
		 }
		 if(result.getThrowable() != null){
		    syndFeedResult.setThrowable(result.getThrowable());
		 }
		 
		 if(result.getResultCode() != null){
			 syndFeedResult.setResultCode(result.getResultCode()); 
		 }
		 
		 if(!result.isSuccess() || result.getResult() == null){
			 return syndFeedResult;
		 }
		 SyndFeedInput input  = new SyndFeedInput(false);
		 SyndFeed syndFeed = null;
		 String  entityBody = result.getResult();
		 String charset = (String) result.getModels().get(ResponseUtils.CHARSET_KEY);
		 byte[] entityBytes = null;
		 boolean clean = false;
		 int index = 0;
		 do{
			 try{
			    syndFeed = input.build(new StringReader(entityBody));
			   break;
			 }catch(Exception e){
				 try{
					 if(entityBytes == null){
						 entityBytes = entityBody.getBytes(charset);
					 }
					 if(clean){
						 if(index == (DEFAULT_CHARSET.length - 1)){ //无法编码成功，显示错误 
							 logger.error("grapFeedUrl encoding error" + url, e);
						 }
						 entityBody = new String(entityBytes,  DEFAULT_CHARSET[index]);
						 index++;
						 clean= false;
					 }else{
						 //有些异常并非编码不对引起，而是特殊字符，先清理特殊字符，然后尝试处理
						 entityBody = entityBody.replaceAll("[\\x00-\\x08|\\x0b-\\x0c|\\x0e-\\x1f]", ""); //除去某些不允许的特殊字符 
						 entityBody = entityBody.trim().replaceFirst("^([\\W]+)<","<"); //去除xml头部非法内容，消除此异常 Invalid XML: Error on line 1: Content is not allowed in prolog.
						 clean = true; 
					 } 
				 }catch(Exception ie){
					 logger.error("grapFeedUrl try encoding" + url, ie);
					 break;
				 }
			 }
		 }while(index < DEFAULT_CHARSET.length);
		 syndFeedResult.setSuccess(syndFeed != null);
		 syndFeedResult.setResult(syndFeed);
		 return syndFeedResult;
	}

}
