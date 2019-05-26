package net.java.efurture.reader.feed;

import java.io.StringReader;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import com.sun.syndication.feed.synd.SyndCategory;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;

import junit.framework.TestCase;

public class FeedTest  extends TestCase{
	
	@Test
	public void testGrapSample(){
		HttpClient client = new DefaultHttpClient();
		try{
			 HttpGet request = new HttpGet("http://blog.silverna.org/index/rss");
			
			 HttpResponse response = client.execute(request);
			 Header etagHeader = response.getFirstHeader(HttpHeaders.ETAG);
			// Header lastModifiedHeader =  response.getFirstHeader(HttpHeaders.LAST_MODIFIED);
			
			 
			 //System.out.println(etagHeader.getValue());
			// System.out.println(lastModifiedHeader.getValue());
			 
			 String  content = EntityUtils.toString(response.getEntity(), "GB2312");
			 
			 
			 System.out.println(content);
			 
			 SyndFeedInput input  = new SyndFeedInput();
			 SyndFeed rssFeed = input.build(new StringReader(content));
			 
			 
			List<SyndEntry> entries =   rssFeed.getEntries();
			for(SyndEntry entry : entries){
				
				System.out.println("title " + entry.getTitle());
				System.out.println("date " + entry.getPublishedDate());
				System.out.println("url " + entry.getLink());
				System.out.println("category " +  ToStringBuilder.reflectionToString(entry.getCategories()));
				
				List<SyndCategory> categoryList = entry.getCategories();
				for(SyndCategory category : categoryList){
					System.out.println(category.getName());
				}
				
				System.out.println("desc : " + entry.getDescription());
				List<SyndContent>  contents = entry.getContents();
				SyndContent synC = contents.get(0);
				System.out.println(contents.size() + "content : " + synC.getValue());
				break;
			}
			 
			 
			 
			 
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			client.getConnectionManager().shutdown();
		}
		
		
	}

}
