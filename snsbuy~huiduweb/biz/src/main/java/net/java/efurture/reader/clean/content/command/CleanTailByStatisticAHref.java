package net.java.efurture.reader.clean.content.command;

import net.java.efurture.reader.clean.content.context.ContentContext;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;


/**
 * 根据统计处理
 * */
public class CleanTailByStatisticAHref extends ContentCommand{

	private static final String A_TAG = "a";
	
	
	@Override
	protected void clean(ContentContext context) {
		Document document = context.getDocument();
		Elements elements = document.body().getAllElements();
		if(elements.size() < 20){
			return;
		}
		
		int end = elements.size() - 8;
		int aTagNumber = 0;
		Element lastA = null;
		for(int i= elements.size() - 1; i > end; i++ ){
			Element element = elements.get(i);
			if( A_TAG.equals(element.tagName().toLowerCase())){
				aTagNumber++;
				lastA  = element;
				continue;
			}
			if(element.getElementsByTag(A_TAG).size() > 0){
					aTagNumber++;
					lastA  = element;
					continue;
			}
		}
		
		if(aTagNumber < 3){  //清理起点
			return;
		}
		
		Node nextSilbing = lastA;
		while(nextSilbing !=  null){
			 Node  temp  = nextSilbing.nextSibling();
			 if(nextSilbing.parent() != null){
			    nextSilbing.remove();
			 }
			 nextSilbing = temp;
		}		
	}
	
	
	
	

}
