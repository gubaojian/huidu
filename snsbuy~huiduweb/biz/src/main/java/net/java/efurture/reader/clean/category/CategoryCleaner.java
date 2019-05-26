package net.java.efurture.reader.clean.category;

import java.util.regex.Pattern;

public class CategoryCleaner {
	
	private static String REGEX = "\\<.+\\>";
	static{
		
		 REGEX += "|\\{.+\\}";
	}
	
	private  static final Pattern pattern = Pattern.compile(REGEX);
	
	
	public static final String clean(String category){
		
		return pattern.matcher(category).replaceAll("");
		
	}

	public static void main(String [] args){
		
		System.out.println(clean("xxxxx<ssss>"));
		

		System.out.println(clean("SSS{SSS}"));
	}
}
