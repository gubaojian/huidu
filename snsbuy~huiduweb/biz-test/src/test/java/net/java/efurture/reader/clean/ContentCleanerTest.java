package net.java.efurture.reader.clean;

import java.io.IOException;
import java.io.InputStream;

import junit.framework.Assert;
import junit.framework.TestCase;
import net.java.efurture.reader.clean.content.ContentCleaner;
import net.java.efurture.reader.clean.content.ContentWhitelist;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class ContentCleanerTest extends TestCase{
	
	
	@Test
	public void testCleanerEmptyErrorOne() throws IOException{
		
		InputStream in = this.getClass().getResourceAsStream("/clean/article-empty-content-one.txt");
		
		String doc =  IOUtils.toString(in);
		 
		 
		String output = ContentCleaner.clean(doc);
		
		System.out.println(output);
	}
	
	@Test
	public void testCleanerOne() throws IOException{
		
		InputStream in = this.getClass().getResourceAsStream("/article-sample.txt");
		
		String doc =  IOUtils.toString(in);
		 
		 
		String output = ContentWhitelist.filter(doc);
		
		System.out.println(output);
	}
	
	
	@Test
	public void testCleanerTwo() throws IOException{
		
		InputStream in = this.getClass().getResourceAsStream("/article-sample-two.txt");
		
		String doc =  IOUtils.toString(in);
		 
		 
		String output = ContentWhitelist.filter(doc);
		
		System.out.println(output);
	}
	
	
	@Test
	public void testCleanerThree() throws IOException{
		
		InputStream in = this.getClass().getResourceAsStream("/article-sample-three.txt");
		
		String doc =  IOUtils.toString(in);
		 
		 
		String output = ContentWhitelist.filter(doc);
		
		System.out.println(output);
	}
	
	
	
	@Test
	public void testCleanerFour() throws IOException{
		
		InputStream in = this.getClass().getResourceAsStream("/clean/article-class-name-one.txt");
		
		String doc =  IOUtils.toString(in);
		 
		 
		String output = ContentCleaner.clean(doc);
		
		System.out.println(output);
	}
	
	
	@Test
	public void testCleanerFive() throws IOException{
		
		InputStream in = this.getClass().getResourceAsStream("/clean/article-sample.txt");
		
		String doc =  IOUtils.toString(in);

		String output = ContentCleaner.clean(doc);
		
		System.out.println(output);
	}
	
	@Test
	public void testCleanerNbsp(){
		String html = "<p align=\"left\"> &nbsp;love</p><span>&nbsp;</span>";
		
		System.out.println(ContentCleaner.clean(html));
		
		Assert.assertEquals("<p align=\"left\">love</p>", ContentCleaner.clean(html));
		
	}
	
	
	@Test
	public void testCleanImgInA(){
		String html = "<div id=\"attachment_2792\" class=\"wp-caption aligncenter\" style=\"width: 610px\"><a href=\"http://laruence-wordpress.stor.sinaapp.com/uploads/ab-c100-n300002.png\"><img src=\"http://laruence-wordpress.stor.sinaapp.com/uploads/ab-c100-n300002.png\" alt=\"\" title=\"ab-c100-n30000\" width=\"600\" height=\"400\" class=\"size-full wp-image-2792\"></a><p class=\"wp-caption-text\">Bechmark</p></div>";
	
		System.out.println(ContentCleaner.clean(html));
	
	}
	
	
	
	
	

}
