package net.java.efurture.reader.clean.title.command;

import java.util.regex.Pattern;

public class CleanHtmlEntityInTitle extends TitleCommand {

	
	private static String CLEAN_ENTITY_REGEX = "(&\\d+;)|(&#\\d+;)|(&nbsp;)";
	static{
		
		CLEAN_ENTITY_REGEX += "|(&amp;)";
	}
	
	
	private static Pattern CLEAN_ENTITY_PATTERN = Pattern.compile(CLEAN_ENTITY_REGEX);
	
	
	@Override
	protected void clean(CleanTitleContext context) {
		String title = context.getTitle(); 
		title = CLEAN_ENTITY_PATTERN.matcher(title).replaceAll("");
		context.setTitle(title);
	}
	
	
	public static void main(String [] args){
		
		System.out.println(CLEAN_ENTITY_PATTERN.matcher("慢连接&#38;LazyParser").replaceAll(""));
		System.out.println(CLEAN_ENTITY_PATTERN.matcher("  &nbsp; &nbsp;  &amp; sss").replaceAll(""));
	  
	}

}
