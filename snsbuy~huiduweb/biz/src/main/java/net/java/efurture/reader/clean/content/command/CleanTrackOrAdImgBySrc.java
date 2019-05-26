package net.java.efurture.reader.clean.content.command;

import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import net.java.efurture.reader.clean.content.context.ContentContext;

public class CleanTrackOrAdImgBySrc  extends ContentCommand {
	private static String CLEAN_SRC_REGEX = "\\.feedsportal\\.com";
	static{
		CLEAN_SRC_REGEX +="|\\.feedsky\\.com";
		CLEAN_SRC_REGEX +="|simg\\.sinajs\\.cn";
		CLEAN_SRC_REGEX +="|feedburner\\.com";
	}
	private static Pattern CLEAN_SRC_PATTERN = Pattern.compile(CLEAN_SRC_REGEX);
	
	@Override
	protected void clean(ContentContext context) {
	  Document document = context.getDocument();
	  Elements elements = document.getElementsByAttributeValueMatching("src", CLEAN_SRC_PATTERN);
	  if(elements == null){
		  return;
	  }
	  
	  for(Element element : elements){
		  if(element.parent() != null){
		    element.remove();
		  }
	  }
	  
	}

	public static void main(String [] args){
		
		System.out.println(CLEAN_SRC_PATTERN.matcher("http://share.feedsportal.com/share/twitter").find());
	}

}
