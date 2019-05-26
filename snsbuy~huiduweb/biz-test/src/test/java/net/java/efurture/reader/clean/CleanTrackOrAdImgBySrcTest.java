package net.java.efurture.reader.clean;

import java.io.InputStream;

import junit.framework.TestCase;
import net.java.efurture.reader.clean.content.command.CleanTrackOrAdImgBySrc;
import net.java.efurture.reader.clean.content.context.ContentContext;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

public class CleanTrackOrAdImgBySrcTest extends TestCase {
	
	
	

	@Test
	public void testCleanBySrcOne() throws Exception{
		InputStream in = this.getClass().getResourceAsStream("/clean/article-src-one.txt");
		
		String doc =  IOUtils.toString(in);
		Document document = Jsoup.parse(doc);  //html-->body-->Element
		
		ContentContext context = new ContentContext();
		context.setDocument(document);
		
		CleanTrackOrAdImgBySrc cleaner = new CleanTrackOrAdImgBySrc();
		cleaner.execute(context);
	
		 System.out.println(document.body().html());
		
	}

}
