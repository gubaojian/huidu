package net.java.efurture.reader.clean.content.command;

import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.StringUtils;

import net.java.efurture.reader.clean.content.context.ContentContext;

public class ImgRelativeSrcToAbsLink extends ContentCommand{

	
	private static final String IMG_TAG = "img";
	
	@Override
	protected void clean(ContentContext context) {
		Document document = context.getDocument();
	    String baseUri = context.getBaseUri();
	    if(StringUtils.isEmpty(baseUri)){
	    	return;
	    }
	   
	    String domain = null;
	    try {
			URL url = new URL(baseUri);
			domain =  url.getProtocol() + "://" + url.getHost();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return;
		}
	    
	    //url.getHost()
	    Elements elements =  document.getElementsByTag(IMG_TAG);
	    for(Element element : elements){
	    	String src =   element.attr("src");
	    	if(src == null){
	    		continue;
	    	}
	    	src = src.trim();  //有些不规范的有可能开头是空格，如 src="  http://xxx.com/sss.png"
	    	if(src.startsWith("http://") || src.startsWith("https://")){
	    		element.attr("src", src);
	    		continue;
	    	}
	    	String absoluteUrl = null;
	    	if(src.startsWith("/")){
	    		absoluteUrl = domain + src;
	    	}else{
	    		absoluteUrl = domain + "/" +  src;
	    	}
	    	element.attr("src", absoluteUrl);
	    }
	}
	
	
	public static void main(String [] args) throws MalformedURLException{
		 URL url = new URL("http://www.baidu.com");
		System.out.println( url.getProtocol() + "://" + url.getHost());
	}
	

}
