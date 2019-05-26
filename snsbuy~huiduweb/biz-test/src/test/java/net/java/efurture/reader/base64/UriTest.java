package net.java.efurture.reader.base64;

import java.net.URI;
import java.net.URL;
import java.util.Locale;

import net.java.efurture.reader.utils.URIUtils;

public class UriTest {

	public static void main(String[] args) throws Exception{
		String url = "http://stblog.baidu-tech.com/wp-content/uploads/wp-display-data.php?filename=1352266305111.jpg&type=image%2Fjpeg&width=328&height=150#XX";
		URL validUrl = new URL(url);
		URI validUri =  new URI(validUrl.getProtocol(), validUrl.getHost(), validUrl.getPath(), validUrl.getQuery(), validUrl.getRef());
		
		
		System.out.println(validUrl.toURI());
		System.out.println( validUri);
		
		
		url = "http://bs.baidu.com/stblogbucket/%2F1352266902222.jpg?sign=MBO:JYLQxknrnu2S63qJK:5hz%2FxryuHOgtz%2BalST8rZSjwbws%3D";
		validUrl = new URL(url);
		validUri =  new URI(validUrl.getProtocol(), validUrl.getHost(), validUrl.getPath(), validUrl.getQuery(), validUrl.getRef());
		
		
		System.out.println(validUrl.toURI());
		System.out.println( validUri);
		
		
		url = "http://bs.baidu.com/stblogbucket/测试.jpg?id=22";
		validUrl = new URL(url);
		validUri =  new URI(validUrl.getProtocol(), validUrl.getHost(), validUrl.getPath(), validUrl.getQuery(), validUrl.getRef());
		System.out.println(validUrl.toURI());
		System.out.println( validUri);
		
		
		
		System.out.println(URIUtils.toURI(" http://huandu.me/wp-content/uploads/2012/04/Strategy-Dependent.png"));
		
		System.out.println(URIUtils.toURI("http://blog.devtang.com/images/block-capture-2.jpg"));
	
		System.out.println(Locale.getDefault().getLanguage());
		
	}
	
}
