package net.java.efurture.reader.clean;

import java.io.InputStream;

import net.java.efurture.reader.clean.content.command.CleanContentSourceSignInHeader;
import net.java.efurture.reader.clean.content.command.CleanTailReferenceRelateEtc;
import net.java.efurture.reader.clean.content.context.ContentContext;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import junit.framework.TestCase;

public class CleanHeaderByTextTest extends TestCase {
	
	
	
	@Test
	public void testCleanerFour() throws Exception{
		InputStream in = this.getClass().getResourceAsStream("/clean/article-header-one.txt");
		String doc =  IOUtils.toString(in);
		Document document = Jsoup.parse(doc);  //html-->body-->Element
		
		ContentContext context = new ContentContext();
		context.setDocument(document);
		
		CleanContentSourceSignInHeader cleaner = new CleanContentSourceSignInHeader();
		cleaner.execute(context);
	
		 System.out.println(document.body().html());
	}

}
