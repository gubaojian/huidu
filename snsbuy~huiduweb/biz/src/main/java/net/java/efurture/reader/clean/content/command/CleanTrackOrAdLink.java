package net.java.efurture.reader.clean.content.command;

import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import net.java.efurture.reader.clean.content.context.ContentContext;

public class CleanTrackOrAdLink extends ContentCommand {

	private static String CLEAN_HREF_REGEX = "\\.feedsportal\\.com";
	static{
		CLEAN_HREF_REGEX +="|\\.feedsky\\.com";
		CLEAN_HREF_REGEX +="|\\.allyes\\.com";
		//
	}
	private static Pattern CLEAN_HREF_PATTERN = Pattern.compile(CLEAN_HREF_REGEX);
	
	@Override
	protected void clean(ContentContext context) {
	  Document document = context.getDocument();
	  Elements elements = document.getElementsByAttributeValueMatching("href", CLEAN_HREF_PATTERN);
	  if(elements == null){
		  return;
	  }
	  for(Element element : elements){
		  if(element.parent() != null){
		    element.remove();
		  }
	  }
	}

}
