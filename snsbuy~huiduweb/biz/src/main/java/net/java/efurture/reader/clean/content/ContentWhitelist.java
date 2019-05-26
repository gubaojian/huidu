package net.java.efurture.reader.clean.content;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Whitelist;

/**
 * 
 * http://www.w3school.com.cn/tags/tag_abbr.asp
 * 
 * https://code.google.com/p/java-syntax-highlighter/
 * 
 * http://alexgorbatchev.com/SyntaxHighlighter/
 * 
 * */
public class ContentWhitelist {
	
	/**tag以及attribute允许的白名单*/
	private  static final Whitelist whiteList = new Whitelist();
	static{
		whiteList.addTags("bdo");
		whiteList.addTags("blockquote");
		whiteList.addTags("br");
		whiteList.addTags("caption");
		whiteList.addTags("center");
		whiteList.addTags("cite");
		whiteList.addTags("code");
		//whiteList.addTags("col");
		//whiteList.addTags("colgroup");
		whiteList.addTags("dd");
		whiteList.addTags("del");
		whiteList.addTags("dfn");
		whiteList.addTags("dir");
		whiteList.addTags("div");
		whiteList.addTags("dl");
		whiteList.addTags("dt");
		whiteList.addTags("h1");
		whiteList.addTags("h2");
		whiteList.addTags("h3");
		whiteList.addTags("h4");
		whiteList.addTags("h5");
		whiteList.addTags("h6");
		whiteList.addTags("hr");
		whiteList.addTags("ins");
		whiteList.addTags("img");
		whiteList.addAttributes("img", "src", "width", "height");
		whiteList.addProtocols("img", "src", "http", "https");
		
		
		whiteList.addTags("kbd");
		whiteList.addTags("li");
		whiteList.addTags("ol");
		whiteList.addTags("p");
		whiteList.addAttributes("p", "align");
		whiteList.addTags("pre");
		whiteList.addEnforcedAttribute("pre", "class", "prettyprint");
		
		whiteList.addTags("q");
		whiteList.addTags("s");
		whiteList.addTags("samp");
		whiteList.addTags("span");
		whiteList.addTags("strike");
		whiteList.addTags("sub");
		whiteList.addTags("sup");
		whiteList.addTags("table");
		whiteList.addEnforcedAttribute("table", "width", "100%");
		whiteList.addTags("tbody");
		whiteList.addTags("td");
		
		whiteList.addTags("tfoot");
		whiteList.addTags("th");
		whiteList.addTags("thead");
		whiteList.addTags("tr");
		whiteList.addTags("tt");
		whiteList.addTags("u");
		whiteList.addTags("ul");
		whiteList.addTags("var");
		
		
		//内嵌frame处理
		whiteList.addTags("iframe");
		whiteList.addAttributes("iframe","src", "height", "frameborder", "scrolling", "marginwidth", "marginheight");
		whiteList.addEnforcedAttribute("iframe", "width", "100%");
		//部分flash播放器
		//whiteList.addTags("object");
	};
	
	/**
	 * 清理器
	 * */
	private static final Cleaner  cleaner = new Cleaner(whiteList);
	
   /**
    * 根据白名单清理dom结构, 
    * */
   public static Document filter(Document document){
	  return  cleaner.clean(document);
   } 
   
	
   /**
    * 根据白名单清理html
    * */
   public static String filter(String doc){
	  return Jsoup.clean(doc, whiteList);
   } 
   

}
