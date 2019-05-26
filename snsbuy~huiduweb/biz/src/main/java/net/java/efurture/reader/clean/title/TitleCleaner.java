package net.java.efurture.reader.clean.title;

import net.java.efurture.reader.clean.title.command.CleanHtmlEntityInTitle;
import net.java.efurture.reader.clean.title.command.CleanTitleContext;
import net.java.efurture.reader.clean.title.command.CleanTitleSoureSign;
import net.java.efurture.reader.clean.title.command.TrimTitleCommand;

import org.apache.commons.chain.impl.ChainBase;
import org.springframework.util.StringUtils;

/**
 * 清理文章的标题
 * */
public class TitleCleaner {
	
	
	private static final ChainBase TITLE_CLEAN_CHAIN =  new ChainBase();
	static{
		TITLE_CLEAN_CHAIN.addCommand(new CleanTitleSoureSign());
		TITLE_CLEAN_CHAIN.addCommand(new CleanHtmlEntityInTitle());	
		TITLE_CLEAN_CHAIN.addCommand(new TrimTitleCommand());
	}
	
	public static String clean(String title){
		if(StringUtils.isEmpty(title)){
			return title;
		}
		CleanTitleContext context = new CleanTitleContext();
		context.setTitle(title);
		try {
			TITLE_CLEAN_CHAIN.execute(context);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return context.getTitle();
	}
	
	
   public static void main(String [] args){
		
		String title = "[转载]笑嘻嘻的的(远程)（原创）SDGDSG【转载】[原]";
		System.out.println(TitleCleaner.clean(title) +  " == " + "笑嘻嘻的的SDGDSG");
		
		title = "慢连接&#38;LazyParser";
		System.out.println(TitleCleaner.clean(title) +  " == " + "慢连接LazyParse");
	}


}
