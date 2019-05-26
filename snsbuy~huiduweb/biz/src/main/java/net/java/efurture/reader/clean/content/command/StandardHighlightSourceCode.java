package net.java.efurture.reader.clean.content.command;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import net.java.efurture.reader.clean.content.context.ContentContext;

/**
 * 
 * http://127.0.0.1:8080/admin/me/articleDetail.html?id=649&source=false
 * http://127.0.0.1:8080/admin/me/articleDetail.htm?id=373
 * 标准化，高亮文章中的代码
 * */
public class StandardHighlightSourceCode extends ContentCommand{
	
	/**
	 * 非正规的代码格式
	 * */
	private static final List<String> CODE_TAG = new ArrayList<String>();
    static{
    	CODE_TAG.add("code");
    	CODE_TAG.add("blockquote");
    }
	
    /**
     * 标准的代码格式
     * */
    private static final String STANDARD_CODE_TAG = "pre";
	
	@Override
	protected void clean(ContentContext context) {
		Document document = context.getDocument();
		for(String tag : CODE_TAG){
			Elements elements = document.getElementsByTag(tag);
			for(Element element : elements){
				Element parent = element.parent();
				boolean parentIsPre = false;
				while(parent != document.body()){
					if(STANDARD_CODE_TAG.equals(parent.tagName().toLowerCase())){
						parentIsPre = true;
					}
					parent = parent.parent();
				}
				if(!parentIsPre){
				    element.tagName(STANDARD_CODE_TAG);
				}
			}
		}
		
	}
	

}
