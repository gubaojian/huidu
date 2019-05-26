package net.java.efurture.reader.clean.content.command;

import java.util.regex.Pattern;

import net.java.efurture.reader.clean.content.context.ContentContext;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;


/**
 * 清理尾部信息: 参考，推荐、以及其他信息
 * */
public class CleanTailReferenceRelateEtc extends ContentCommand {

	
	/**
	 * 根据日志结尾删除，无关信息
	 * */
	private static  String CLEAN_TAIL_REGEX = "转载本站文章请注明作者";
	static{
		CLEAN_TAIL_REGEX +="|相关日志";
		CLEAN_TAIL_REGEX +="|相关文章";
		CLEAN_TAIL_REGEX +="|精彩推荐";
		CLEAN_TAIL_REGEX +="|您可能也喜欢";
		CLEAN_TAIL_REGEX +="|你可能也会喜欢";
		CLEAN_TAIL_REGEX +="|转载请注明";
		CLEAN_TAIL_REGEX +="|本文链接";
		CLEAN_TAIL_REGEX +="|本文地址";
		CLEAN_TAIL_REGEX +="|附录:";
		CLEAN_TAIL_REGEX +="|原文地址:";
		CLEAN_TAIL_REGEX +="|原文链接";
		CLEAN_TAIL_REGEX +="|下篇:";
		CLEAN_TAIL_REGEX +="|^[\\s\\w]*参考";
		CLEAN_TAIL_REGEX +="|参考文章[\\s]*$";
		CLEAN_TAIL_REGEX +="|参考链接[\\s]*$";
		CLEAN_TAIL_REGEX +="|参考资料[\\s]*$";
		CLEAN_TAIL_REGEX +="|^[\\s\\w]*摘自：";
		CLEAN_TAIL_REGEX +="|本文出自";
		CLEAN_TAIL_REGEX +="|更多文章会实时推送给你";
		CLEAN_TAIL_REGEX +="|更多文章会实时推送给你";
		CLEAN_TAIL_REGEX +="|欢迎转载，演绎或用于商业目的";
		CLEAN_TAIL_REGEX +="|必须保留本文的署名";
		CLEAN_TAIL_REGEX +="|FROM：";
		CLEAN_TAIL_REGEX +="|招聘信息";
		CLEAN_TAIL_REGEX +="|^[\\s]*Comments[\\s]*$";
		CLEAN_TAIL_REGEX +="|^[\\s]*Relative Posts:[\\s]*$";
		CLEAN_TAIL_REGEX +="|No related posts";
		CLEAN_TAIL_REGEX +="|的微信号：";
		CLEAN_TAIL_REGEX +="|这次就写到这儿";
		CLEAN_TAIL_REGEX +="|订阅Solidot";
	}
	
	private static Pattern CLEAN_TAIL_PATTERN = Pattern.compile(CLEAN_TAIL_REGEX);
	
	  /**
	    * 传入文档根的dom对象, 在原对象上进行清理。 
	    * 清理尾部的友情链接，相关推荐等参数，防止对读者产生干扰
	    * */
	@Override
	protected void clean(ContentContext context) {
		
		    Document document  = context.getDocument();
		    Elements matchElements = document.getElementsMatchingOwnText(CLEAN_TAIL_PATTERN);
		    
			if(matchElements.size() == 0){
				return;
			}

			/**执行清理，找到第一个不符合的，然后删除其后面的子元素*/
			for(Element matchElement :  matchElements){
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
				
				int position = matchRootParent.siblingIndex(); // 位置判断，在文章位置2/3之后才清理 防止误判
				if(position < matchRootParent.siblingNodes().size()*1/3){
					continue;
				}
				
				Node nextSiblingElement = matchRootParent;
				while(nextSiblingElement != null){
					Node next = nextSiblingElement.nextSibling();
					if(nextSiblingElement.parent() != null){
					   nextSiblingElement.remove();
					}
					nextSiblingElement = next;
				}
				break;
		  }
			
	}

}
