package net.java.efurture.reader.clean.title.command;

import java.util.regex.Pattern;

/**
 * 清除文章中的 [转载] (转载)等标记 
 * */
public  class CleanTitleSoureSign extends TitleCommand{

	
	private static String CLEAN_SIGN_REGEX = "\\[[^\\]]*\\]";
	static{
		CLEAN_SIGN_REGEX += "|【[^】]*】";  //中文括号
		//CLEAN_SIGN_REGEX += "|\\([^)]*\\)";  //英文括号 count(*)  count(x) count (猎命)
		//CLEAN_SIGN_REGEX += "|（[^）]*）";  //中文括号
	}
	
	
	private static Pattern CLEAN_SIGN_PATTERN = Pattern.compile(CLEAN_SIGN_REGEX);

	@Override
	protected void clean(CleanTitleContext context) {
		   String title = context.getTitle();
		   title = CLEAN_SIGN_PATTERN.matcher(title).replaceAll("");
		   context.setTitle(title);
	}
	
	  public static void main(String [] args){
			
			String title = "[转载]笑嘻嘻的的(远程)（原创）SDGDSG【转载】[原]";
			
			
			System.out.println(title);
			
			System.out.println(CLEAN_SIGN_PATTERN.matcher(title).replaceAll(""));
		}

}
