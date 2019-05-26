package net.java.efurture.reader.clean;

import java.io.InputStream;

import junit.framework.TestCase;
import net.java.efurture.reader.clean.content.command.CleanTailReferenceRelateEtc;
import net.java.efurture.reader.clean.content.context.ContentContext;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

public class CleanTailReferenceRelateEtcTest   extends TestCase{
	

	@Test
	public void testCleanerOneInCoolShell() throws Exception{
		InputStream in = this.getClass().getResourceAsStream("/article-sample.txt");
		String doc =  IOUtils.toString(in);
		Document document = Jsoup.parse(doc);  //html-->body-->Element
		
		ContentContext context = new ContentContext();
		context.setDocument(document);
		
		CleanTailReferenceRelateEtc cleaner = new CleanTailReferenceRelateEtc();
		cleaner.execute(context);
		
		System.out.println(document.body().html());
	}
	
	
	@Test
	public void testCleanerTwo() throws Exception{
		InputStream in = this.getClass().getResourceAsStream("/article-tail-two.txt");
		String doc =  IOUtils.toString(in);
		Document document = Jsoup.parse(doc);  //html-->body-->Element
		
		ContentContext context = new ContentContext();
		context.setDocument(document);
		
		CleanTailReferenceRelateEtc cleaner = new CleanTailReferenceRelateEtc();
		cleaner.execute(context);
	
		 System.out.println(document.body().html());
	}
	
	
	@Test
	public void testCleanerThree() throws Exception{
		InputStream in = this.getClass().getResourceAsStream("/article-tail-three.txt");
		String doc =  IOUtils.toString(in);
		Document document = Jsoup.parse(doc);  //html-->body-->Element
		
		ContentContext context = new ContentContext();
		context.setDocument(document);
		
		CleanTailReferenceRelateEtc cleaner = new CleanTailReferenceRelateEtc();
		cleaner.execute(context);
	
		 System.out.println(document.body().html());
	}
	
	
	
	@Test
	public void testCleanerFour() throws Exception{
		InputStream in = this.getClass().getResourceAsStream("/clean/article-tail-four.txt");
		String doc =  IOUtils.toString(in);
		Document document = Jsoup.parse(doc);  //html-->body-->Element
		
		ContentContext context = new ContentContext();
		context.setDocument(document);
		
		CleanTailReferenceRelateEtc cleaner = new CleanTailReferenceRelateEtc();
		cleaner.execute(context);
	
		 System.out.println(document.body().html());
	}
	
	

}
