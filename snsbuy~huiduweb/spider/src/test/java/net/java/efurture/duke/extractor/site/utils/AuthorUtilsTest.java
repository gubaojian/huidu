package net.java.efurture.duke.extractor.site.utils;

import junit.framework.TestCase;

public class AuthorUtilsTest extends TestCase{
	
	
	
	public void testAuthorFromTitle(){
		System.out.println(AuthorUtils.authorFromTitle("息影"));
		

		System.out.println(AuthorUtils.authorFromTitle("||息.影||你好"));
		

		System.out.println(AuthorUtils.authorFromTitle("息影,   测试"));
		
		
		System.out.println(AuthorUtils.authorFromTitle("lijian&#039;s homepage"));
		
		
	}

}
