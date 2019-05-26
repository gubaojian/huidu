package net.java.efurture.duke.extractor.site.utils;

import junit.framework.Assert;
import junit.framework.TestCase;

public class SiteUtilsTest extends TestCase{
	
	
	
	public void testUrlDomain(){
		Assert.assertEquals("google.com",  SiteUtils.urlDomain("http://www.google.com"));

		Assert.assertEquals("google.com",  SiteUtils.urlDomain("www.google.com"));
		

		Assert.assertEquals("google.com",  SiteUtils.urlDomain("www.Google.com"));
		

		Assert.assertEquals("google.com",  SiteUtils.urlDomain("Http://www.Google.com"));
		
		


		Assert.assertEquals("google.com",  SiteUtils.urlDomain("Http://www.Google.com/"));
		
		

		Assert.assertEquals("google.com",  SiteUtils.urlDomain("Http://www.Google.com/love"));
		

		Assert.assertEquals("google.com",  SiteUtils.urlDomain("Http://search.Google.com/love"));
		
	}

}
