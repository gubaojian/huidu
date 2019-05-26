package net.java.efurture.reader.clean.content.command;

import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import net.java.efurture.reader.clean.content.context.ContentContext;


/**
 * 清理结尾处的签名，转载等无用信息
 * */
public class CleanContentSourceSignTail extends ContentCommand {

	 /* 根据日志结尾删除，无关信息
	 * */
	private static  String CLEAN_HEADER_REGEX = "以下原文转载自";
	static{
		CLEAN_HEADER_REGEX +="|本文链接地址";
		CLEAN_HEADER_REGEX +="|转载时务必以超链接形式标明文章原始出处";
		CLEAN_HEADER_REGEX +="|无觅猜您也喜欢";
	}
	
	private static Pattern CLEAN_HEADER_PATTERN = Pattern.compile(CLEAN_HEADER_REGEX);
	
	  /**
	    * 传入文档根的dom对象, 在原对象上进行清理。 
	    * 清理尾部的友情链接，相关推荐等参数，防止对读者产生干扰
	    * */
	@Override
	protected void clean(ContentContext context) {
		
		    Document document  = context.getDocument();
		    Elements matchElements = document.getElementsMatchingText(CLEAN_HEADER_PATTERN);
		    
			if(matchElements.size() == 0){
				return;
			}

			/**
			 * 从后向前搜索，搜索到倒数第一应该删除的，然后依次向前删除
			 * */
			for(int i= matchElements.size() - 1; i>= 0; i--){
				Element matchElement = matchElements.get(i);
				Elements parents = matchElement.parents();
				
				if(parents.size() < 2){  //rootElement-->body->html
					continue;
				}
				
				Element matchRootParent = null;
				if(parents.size() == 2){//rootElement-->body->html
					 matchRootParent = matchElement;
				}else{
					matchRootParent = parents.get(parents.size() - 3);  //element-> ...->rootElement-->body->html
				}
				
				/**
				 * div {<p></p>  article}
				 * */
				while(matchRootParent.text().length() > matchRootParent.parent().text().length()/2){//处理文章被div包围的请求
					int index = parents.indexOf(matchRootParent);
					if(index  <= 0){
						matchRootParent = matchElement;
						break;
					}
					matchRootParent = parents.get(index - 1);
				}
				
				
				
				int position = matchRootParent.siblingIndex(); // 位置判断，在文章位置1/2之后才清理 防止误判
				int maxPostion = matchRootParent.siblingNodes().size()/3;
				if(maxPostion > 15){  //防止误判， 15个元素已经相当大了
					 maxPostion = 15;
				}
				if(position >  maxPostion){
					continue;
				}
				
				Node preSiblingElement = matchRootParent;
				while(preSiblingElement != null){
					Node next = preSiblingElement.previousSibling();
					if(preSiblingElement.parent() != null){
						preSiblingElement.remove();
					}
					
					preSiblingElement = next;
				}	 
				break;
			}
	}

}
