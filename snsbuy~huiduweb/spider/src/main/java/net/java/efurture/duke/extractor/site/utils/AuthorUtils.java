package net.java.efurture.duke.extractor.site.utils;

public class AuthorUtils {
	
	
	public static final String authorFromTitle(String title){
		if(title == null){
			return title;
		}
		String[] tags =  title.split("[ ,|，“\";:?、/\\\\.~`^%&*()$#@!（）*&……%￥#@！]+");
		for(String tag : tags){
			if(tag.trim().length() > 1){
				return tag;
			}
		}
		return null;
	}

}
