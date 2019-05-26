package net.java.efurture.reader.base64;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import junit.framework.TestCase;
import net.java.efurture.reader.image.ImageUtils;

import org.apache.http.client.ClientProtocolException;
import org.junit.Test;

public class ImageUtilsTest extends TestCase{
	
	@Test
	public void testBase64Image(){
	  String url = "http://img01.taobaocdn.com/tps/i1/T1WqOlFXJbXXcr_tT7-490-170.jpg";
	  String data = 	ImageUtils.base64ImageFromUrl(url);
	  System.out.println(data.getBytes().length/1000.0);
	}
	
	@Test
	public void testBase64ImageFrom() throws ClientProtocolException, IOException, URISyntaxException{
		 String url = "http://cdc.tencent.com/?attachment_id=7532";
		 String data = 	ImageUtils.base64ImageFromUrl(url);
		 System.out.println(data.getBytes().length/1000.0);
		 System.out.println(data);
		 
	}
	
	@Test
	public void testBase64ImageFromGif() throws ClientProtocolException, IOException, URISyntaxException{
		 String url = "http://img.my.csdn.net/uploads/201105/27/0_13064827730ZkX.gif";
		 String data = 	ImageUtils.base64ImageFromUrl(url);
		 System.out.println(data.getBytes().length/1000.0);
		 System.out.println(data);
		 
	}
	@Test
	public void testBase64ImageFromGifTwo() throws ClientProtocolException, IOException, URISyntaxException{
		 String url = "http://img.my.csdn.net/uploads/201103/30/0_1301502905Eu5I.gif";
		 String data = 	ImageUtils.base64ImageFromUrl(url);
		 System.out.println(data.getBytes().length/1000.0);
		 System.out.println(data);
		 
	}
	
	
	
	@Test
	public void testFileNameInParameter(){
		String url = "http://stblog.baidu-tech.com/wp-content/uploads/wp-display-data.php?filename=1352266305111.jpg&type=image%2Fjpeg&width=328&height=150";
		 String data = 	ImageUtils.base64ImageFromUrl(url);
		 System.out.println(data.getBytes().length/1000.0);
		 System.out.println(data);
	}
	
	
	@Test
	public void testFileNameWithNoFileParameter(){
		String url = "http://s8.sinaimg.cn/mw690/68b67143ge05de15c0577&690";
		 String data = 	ImageUtils.base64ImageFromUrl(url);
		 System.out.println(data.getBytes().length/1000.0);
		 System.out.println(data);
		
	}
	
	@Test
	public void testFileNameWithNoFileAndTypeNotRight(){
		String url = "http://127.0.0.1/reader/320x320.js";
		 String data = 	ImageUtils.base64ImageFromUrl(url);
		 System.out.println(data.getBytes().length/1000.0);
		 System.out.println(data);
		
	}
	
	
	@Test
	public void testFileNameWithNoFileAndTypeNotRightTwo(){
		String url = "http://127.0.0.1/reader/320x320.css";
		 String data = 	ImageUtils.base64ImageFromUrl(url);
		 System.out.println(data.getBytes().length/1000.0);
		 System.out.println(data);
		
	}
	
	
	@Test
	public void testCsdnGifRedirect(){
		String url = "http://hi.csdn.net/attachment/201105/27/0_1306483969IiB2.gif";
		 String data = 	ImageUtils.base64ImageFromUrl(url);
		 System.out.println(data.getBytes().length/1000.0);
		 System.out.println(data);
		
	}
	
	
	@Test
	public void testInvalidImage(){
		String url = "http://blog.devtang.com/images/wwdc-2011.jpg";
		 String data = 	ImageUtils.base64ImageFromUrl(url);
		 System.out.println(data.getBytes().length/1000.0);
		 System.out.println(data);
	}
	
	
	@Test
	public void testInvalidImageNullTwo(){
		String url = "http://huandu.me/wp-content/uploads/2012/04/Strategy-Dependent.png";
		 String data = 	ImageUtils.base64ImageFromUrl(url);
		 System.out.println(data.getBytes().length/1000.0);
		 System.out.println(data);
	}
	
	
	@Test
	public void testDevTangInvalidImageNullTwo(){
		String url = "http://blog.devtang.com/images/block-capture-2.jpg";
		 String data = 	ImageUtils.base64ImageFromUrl(url);
		 System.out.println(data.getBytes().length/1000.0);
		 System.out.println(data);
	}
	
	
	
	
	
	
	
	
	
	


	
	
	
	
	
	
	
	/**
	 * 
	 * <img src="" width="720" height="105">
	 * 
	 * 
	 * */
	
	


}
