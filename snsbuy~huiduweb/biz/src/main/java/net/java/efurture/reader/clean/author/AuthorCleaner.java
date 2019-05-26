package net.java.efurture.reader.clean.author;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class AuthorCleaner {
	
	/**
	 * 参考文档：http://www.w3schools.com/html/html_entities.asp
	 * */
	private static final Map<String,String> entityMap = new HashMap<String,String>();
	static{
		entityMap.put("&nbsp;", " ");
		entityMap.put("&#160;", " ");
		entityMap.put("&lt;", "<");
		entityMap.put("&#60;", "<");
		entityMap.put("&gt;", ">");
		entityMap.put("&#62;", ">");
		entityMap.put("&#38;", "&");
		entityMap.put("&amp;", "&");
		
	}
	
	/**处理作者的名字*/
	public static final String clean(String author){
		if(author == null || author.length() == 0){
			return author;
		}
		Set<Entry<String, String>> entries =   entityMap.entrySet();
		for(Entry<String,String> entry: entries){
			author = author.replaceAll(entry.getKey(), entry.getValue());
		}
		return author.trim();
	}
	
	
	
	public static void main(String[] args){
		
		System.out.println(clean("你好 &amp;测试"));
	}

}
