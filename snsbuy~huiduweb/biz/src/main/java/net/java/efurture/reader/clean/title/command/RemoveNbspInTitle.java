package net.java.efurture.reader.clean.title.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 清除开头中的空格
 * */
public  class RemoveNbspInTitle  extends TitleCommand {

	
	/**
	 * 参考： RemoveNbspInContent
	 * */
	private static Pattern CLEAN_NBSP_PATTERN = Pattern.compile("^\\s*(&nbsp;)+\\s*");
	
	
	@Override
	protected void clean(CleanTitleContext context) {
		String title = context.getTitle(); 
		Matcher matcher = CLEAN_NBSP_PATTERN.matcher(title);
		while(matcher.find()){
			title = matcher.replaceFirst("");
			matcher = CLEAN_NBSP_PATTERN.matcher(title);
		}
		context.setTitle(title);
	}
	
	
	public static void main(String[] args){
		String title =   "  &nbsp; &nbsp;   sss"; 
		Matcher matcher = CLEAN_NBSP_PATTERN.matcher(title);
		while(matcher.find()){
			title = matcher.replaceFirst("");
			 matcher = CLEAN_NBSP_PATTERN.matcher(title);
		}
		System.out.println(title);
	}

}
