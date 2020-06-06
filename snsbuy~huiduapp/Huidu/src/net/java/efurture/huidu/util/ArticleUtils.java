package net.java.efurture.huidu.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import net.java.efurture.huidu.domain.Article;
import net.java.efurture.huidu.service.FontConfigService;

import org.apache.commons.io.IOUtils;

import android.content.Context;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;

public class ArticleUtils {
	//渲染模板
	private static Template template;
	
	public static String renderArticle(Context context,  Map<String, Object> contextMap) throws IOException{
		if (template == null) {
			synchronized (ArticleUtils.class) {
				if (template == null) {
					InputStream inputStream = context.getAssets().open("html/readerDetail.html");
					String content = IOUtils.toString(inputStream, "UTF-8");
					template = Mustache.compiler().escapeHTML(false).defaultValue("").compile(content);
				}
			} 
		}
		
        Article article = (Article) contextMap.get("article");
        
        contextMap.put("minWidth", "200px");
        contextMap.put("maxWidth", "640px");
        contextMap.put("paddingTop", "10px");
        contextMap.put("titleFontSize", "22px");
        contextMap.put("contentFontSize", "16px");
            
        int  mod =  article.getId().intValue()%7;
        switch (mod) {
            case 0:
            	    contextMap.put("borderBottomColor", "#1989e0");
                break;
            case 1:
            	    contextMap.put("borderBottomColor", "#e06827");
                break;
            case 2:
        	        contextMap.put("borderBottomColor", "#d7b319");
                break;
            case 3:
        	       contextMap.put("borderBottomColor", "#dedfde");
                break;
            case 4:
        	        contextMap.put("borderBottomColor", "#339933");
                break;
            case 5:
        	        contextMap.put("borderBottomColor", "#F09609");
                break;
            case 6:
        	        contextMap.put("borderBottomColor", "#8CBF26");
                break;
            default:
        	        contextMap.put("borderBottomColor", "#00ABA9");
                break;
        }
         contextMap.put("contentFontSize", FontConfigService.getFontSizePx(context));
		return template.execute(contextMap);
	}
}
