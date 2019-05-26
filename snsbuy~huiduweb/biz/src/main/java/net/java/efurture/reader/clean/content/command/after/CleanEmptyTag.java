package net.java.efurture.reader.clean.content.command.after;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import net.java.efurture.reader.clean.content.command.ContentCommand;
import net.java.efurture.reader.clean.content.context.ContentContext;


/**
 * 清除空的标签
 * */
public class CleanEmptyTag  extends ContentCommand{
	
	private static Pattern CLEAN_EMPTY_PATTERN = Pattern.compile("^\\s*$");
	
	private static final Set<String> emptyTags = new HashSet<String>();
	static{
		emptyTags.add("span");
		emptyTags.add("p");
		emptyTags.add("div");
		emptyTags.add("h1");
		emptyTags.add("h2");
		emptyTags.add("h3");
		emptyTags.add("h4");
		emptyTags.add("h5");
	}
	

	@Override
	protected void clean(ContentContext context) {
		Element  body = context.getDocument().body();
		Boolean clean = false;
		
		//第一次清理
		clean = false;
		for(String tag : emptyTags){
			Elements elements = body.getElementsByTag(tag);
			if(elements == null || elements.size() == 0){
				return;
			}
			for(Element element : elements){
				String html = elements.html(); //采用html的形式，防止<div><img src></img><div>照成误判
				Matcher matcher = CLEAN_EMPTY_PATTERN.matcher(html);
				if(matcher.find()){
					 if(element.parent() != null){
						  element.remove();
				      }
					 clean = true;
				}
				
			}
		}
		if(!clean){
			return;
		}
		
		//第二次清理
		clean = false;
		for(String tag : emptyTags){
			Elements elements = body.getElementsByTag(tag);
			if(elements == null || elements.size() == 0){
				return;
			}
			for(Element element : elements){
				String html = elements.html(); //采用html的形式，防止<div><img src></img><div>照成误判
				Matcher matcher = CLEAN_EMPTY_PATTERN.matcher(html);
				if(matcher.find()){
					 if(element.parent() != null){
						  element.remove();
				      }
					 clean = true;
				}
				
			}
		}
		if(!clean){
			return;
		}
		
		//第三次清理
		clean = false;
		for(String tag : emptyTags){
			Elements elements = body.getElementsByTag(tag);
			if(elements == null || elements.size() == 0){
				return;
			}
			for(Element element : elements){
				String html = elements.html(); //采用html的形式，防止<div><img src></img><div>照成误判
				Matcher matcher = CLEAN_EMPTY_PATTERN.matcher(html);
				if(matcher.find()){
					 if(element.parent() != null){
						  element.remove();
				      }
					 clean = true;
				}
				
			}
		}
		if(!clean){
			return;
		}
		
		//第四次清理
		clean = false;
		for(String tag : emptyTags){
			Elements elements = body.getElementsByTag(tag);
			if(elements == null || elements.size() == 0){
				return;
			}
			for(Element element : elements){
				String html = elements.html(); //采用html的形式，防止<div><img src></img><div>照成误判
				Matcher matcher = CLEAN_EMPTY_PATTERN.matcher(html);
				if(matcher.find()){
					 if(element.parent() != null){
						  element.remove();
				      }
					 clean = true;
				}
				
			}
		}
		if(!clean){
			return;
		}
	}

}
