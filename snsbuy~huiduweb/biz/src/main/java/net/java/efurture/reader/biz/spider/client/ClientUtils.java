package net.java.efurture.reader.biz.spider.client;

import javax.net.ssl.HostnameVerifier;

import org.apache.http.client.HttpClient;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.CookieSpecFactory;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.ContentEncodingHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.impl.cookie.BrowserCompatSpec;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;


/**
 * 抓取的链接客户端
 * */
public class ClientUtils {
	
	public static final String CHROME_USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.116 Safari/537.36";

	/**
	 * 自定义的cookie策略
	 * */
	private  static final String COOKIE_NONE_VALIDATE_SPEC_NAME = "CookieNoneValid";
	private  static final CookieSpecFactory COOKIE_NONE_VALIDATE = new CookieSpecFactory() {
	    public CookieSpec newInstance(HttpParams params) {
	        return new BrowserCompatSpec() {   
	            @Override
	            public void validate(Cookie cookie, CookieOrigin origin)throws MalformedCookieException {
	                //对cookie不做验证
	            }
	        };
	    }
	};
	
	static{
		System.setProperty("sun.net.client.defaultConnectTimeout", String.valueOf(8000));// （单位：毫秒）  
        System.setProperty("sun.net.client.defaultReadTimeout", String.valueOf(8000)); // （单位：毫秒） 
	}
	
	/**
	 * 创建一个抓取的链接客户端
	 * */
	public static HttpClient buildGrapClient(){		
		HttpClient client = new ContentEncodingHttpClient();
		((AbstractHttpClient) client).getCookieSpecs().register(COOKIE_NONE_VALIDATE_SPEC_NAME, COOKIE_NONE_VALIDATE);
		client.getParams().setParameter(ClientPNames.HANDLE_REDIRECTS, Boolean.TRUE);
		client.getParams().setParameter(ClientPNames.MAX_REDIRECTS, 2);
		client.getParams().setParameter(CoreProtocolPNames.USER_AGENT, CHROME_USER_AGENT); //设置请求头为chrome，防止被某些网站屏蔽
		client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 8000); //超时时间5秒
		client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 8000); //响应超时时间
		client.getParams().setBooleanParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE, false);
		
		if(client.getConnectionManager() instanceof SingleClientConnManager){
			HostnameVerifier hostnameVerifier = org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
			SSLSocketFactory socketFactory = SSLSocketFactory.getSocketFactory();
			socketFactory.setHostnameVerifier((X509HostnameVerifier) hostnameVerifier);
			SingleClientConnManager connManager = (SingleClientConnManager)client.getConnectionManager();
			connManager.getSchemeRegistry().register(new Scheme("https",  443,  socketFactory));
		}
		return client;
	};
	
	

}
