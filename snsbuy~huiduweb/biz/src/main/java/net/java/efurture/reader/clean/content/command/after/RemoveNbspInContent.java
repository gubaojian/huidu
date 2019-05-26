package net.java.efurture.reader.clean.content.command.after;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.java.efurture.reader.clean.content.command.ContentCommand;
import net.java.efurture.reader.clean.content.context.ContentContext;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 清理空的采用 &nbsp;&nbsp; 采用css控制缩进的tag
 *  android企业开发与现状
 * */
public class RemoveNbspInContent extends ContentCommand{

	/**
	 * 参考：RemoveNbspInTitle
	 * */
	private static Pattern CLEAN_NBSP_PATTERN = Pattern.compile("^\\s*(&nbsp;)+\\s*");
	private static final Set<String> tags = new HashSet<String>();
	static{
		tags.add("p");
		tags.add("div");
		tags.add("span");
		tags.add("h1");
		tags.add("h2");
		tags.add("h3");
		tags.add("h4");
		tags.add("h5");
	}
	
	@Override
	protected void clean(ContentContext context) {
		Element  body = context.getDocument().body();
		for(String tag : tags){
			Elements  elements = body.getElementsByTag(tag);
			if(elements == null || elements.size() == 0){
				continue;
			}
			for(Element element : elements){
				String html = element.html();
				Matcher matcher = CLEAN_NBSP_PATTERN.matcher(html);
				boolean update = false;
				while(matcher.find()){  //循环删除   &nbsp; &nbsp; 
					html = matcher.replaceFirst("");
					matcher = CLEAN_NBSP_PATTERN.matcher(html);
					update = true;
				}
				if(update){
				   element.html(html);
				}
			}
		}
	}
	
	public static void main(String [] args){
		System.out.println(CLEAN_NBSP_PATTERN.matcher("&nbsp;&nbsp;sss").find());
		System.out.println(CLEAN_NBSP_PATTERN.matcher("&nbsp;&nbsp;sss").replaceFirst(""));
		System.out.println(CLEAN_NBSP_PATTERN.matcher(" &nbsp;&nbsp;  sss").replaceFirst(""));
		System.out.println(CLEAN_NBSP_PATTERN.matcher(CLEAN_NBSP_PATTERN.matcher("  &nbsp; &nbsp;   sss").replaceFirst("")).replaceFirst(""));
		
	}

}
