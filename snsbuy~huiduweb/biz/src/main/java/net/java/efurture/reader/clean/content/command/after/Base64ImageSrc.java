package net.java.efurture.reader.clean.content.command.after;

import java.util.regex.Pattern;

import org.apache.commons.lang.math.NumberUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.StringUtils;

import net.java.efurture.reader.clean.content.command.ContentCommand;
import net.java.efurture.reader.clean.content.context.ContentContext;
import net.java.efurture.reader.image.ImageUtils;



public class Base64ImageSrc extends ContentCommand{

	private static final Pattern GIF_PATTERN = Pattern.compile("\\.gif", Pattern.CASE_INSENSITIVE);
	
	@Override
	protected void clean(ContentContext context) {
		Document document = context.getDocument();
		Elements elements = document.getElementsByTag("img"); 
		if(elements == null || elements.size() == 0){
			return;
		}
		for(Element element : elements){
			String src = element.attr("src");
			if(StringUtils.isEmpty(src)){
				element.remove();
				continue;
			}
			String base64 = ImageUtils.base64ImageFromUrl(src);
			if(base64 != null){
				element.attr("src", base64);
			}else{
				logger.warn("Base64ImageSrc base64image is null, img src " + src);
			}
			
			int width = NumberUtils.toInt(element.attr("width"), -1);
			if((!GIF_PATTERN.matcher(src).find() && (width > 120 || (width == -1 && base64 != null && base64.length() >1024*8))) || width > 240){
			
				element.removeAttr("width");
				element.removeAttr("height");
				//clone
				Element img = element.clone();
				element.tagName("div");
				element.removeAttr("src");
				Elements childs = element.children();
				if(childs != null && childs.size() > 0){
					for(Element child : childs){
		               if(child.parent() != null){
		            	   child.remove(); 
		               }
					}
				}
				
				element.attr("class", "img-center");
				img.attr("width", "100%");
				element.appendChild(img);
			}
		}
	}
	
	public static void main(String[] args){
		
		System.out.println(GIF_PATTERN.matcher(".jpg").find());
		System.out.println(GIF_PATTERN.matcher("xxx.gif").find());
		System.out.println(GIF_PATTERN.matcher(".Gif").find());

	}

}
