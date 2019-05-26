package net.java.efurture.duke.extractor.grap;

import net.java.efurture.reader.biz.spider.client.GrapClient;

import com.google.code.efurture.common.result.Result;

import junit.framework.Assert;
import junit.framework.TestCase;

public class GrapClientTest extends TestCase {
	
	
	public void testGrapSite(){
		String url = "http://www.bjt.name/";
		Result<String>result = GrapClient.grapSite(url, null);
		Assert.assertTrue(result.isSuccess());
		Assert.assertNotNull(result.getResult());
		
		
		System.out.println(result.getResult());
		
	}

}
