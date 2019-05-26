package net.java.efurture.reader.biz.spider.client;

import java.net.URI;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.efurture.common.result.Result;

public class GrapClient {
	
	private static final Logger logger = LoggerFactory.getLogger(GrapClient.class);
	
	/**
	 * 抓取网页的内容
	 * */
	public static Result<String> grapSite(String url){
		return grapSite(url, null);
	}
	
	public static Result<String> grapSite(String url, Map<Object,Object> context){
		 HttpClient client = null; 
		 try{
			 client  = ClientUtils.buildGrapClient();
			 HttpGet get = RequestUtils.toGet(url, context);
			 HttpResponse response = client.execute(get);
			 if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				 return ResponseUtils.toResult(response);
			 }
			
			 URI  location = ResponseUtils.getRedirectLocationURI(response);
			 if(location == null){
				 return ResponseUtils.toResult(response);
			 }

			 //释放上一次请求，发起定向的请求
			 EntityUtils.consume(response.getEntity());
			 get = RequestUtils.toGet(location, context);
			 response = client.execute(get);
			 return ResponseUtils.toResult(response);
		 }catch(Exception e){
			 logger.error("grapsite exception" + url, e);
			 return ResponseUtils.toFailedResult(e, GrapResultCode.GRAP_SITE_EXCEPTION);
		 }finally{
			 if(client != null){
				 client.getConnectionManager().shutdown();
			 } 
		 }
	}

}
