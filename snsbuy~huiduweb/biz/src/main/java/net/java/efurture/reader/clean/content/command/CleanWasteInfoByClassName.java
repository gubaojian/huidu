package net.java.efurture.reader.clean.content.command;

import java.util.ArrayList;
import java.util.List;

import net.java.efurture.reader.clean.content.context.ContentContext;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CleanWasteInfoByClassName extends ContentCommand {

	//根据class name 清理
	private static List<String> CLEAN_CLASS_NAME_LIST = new ArrayList<String>();
	static {
		CLEAN_CLASS_NAME_LIST.add("yarpp-related-rss");
		CLEAN_CLASS_NAME_LIST.add("mf-viral");
		CLEAN_CLASS_NAME_LIST.add("copyright");
		CLEAN_CLASS_NAME_LIST.add("related_post");
		CLEAN_CLASS_NAME_LIST.add("post-footer");
		CLEAN_CLASS_NAME_LIST.add("breadcrumb");
		
		
		
		
	}
		
	
	@Override
	protected void clean(ContentContext context) {
		Document document  = context.getDocument();
		 for(String className : CLEAN_CLASS_NAME_LIST){
			   Elements elements =  document.getElementsByClass(className);
			   if(elements != null){
				   for( Element element :  elements){
					   if(element.parent() != null){
					      element.remove();
					   }
				   }
			   }
		   }
	}
	
	
	
}
