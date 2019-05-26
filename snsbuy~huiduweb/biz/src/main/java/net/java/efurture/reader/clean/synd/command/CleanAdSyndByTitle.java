package net.java.efurture.reader.clean.synd.command;

import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import net.java.efurture.reader.clean.synd.context.SyndContext;

import com.sun.syndication.feed.synd.SyndEntry;


/**
 * 清除招聘及广告信息
 * */
public class CleanAdSyndByTitle extends SyndCommand {

	private static  String CLEAN_HEADER_REGEX = "招聘";
	static{
		CLEAN_HEADER_REGEX +="|招贤纳士";		
	}
	
	private static Pattern CLEAN_HEADER_PATTERN = Pattern.compile(CLEAN_HEADER_REGEX);

	@Override
	protected void clean(SyndContext cleanContext) {
		List<SyndEntry> source = cleanContext.getEntryList();
		Iterator<SyndEntry>  it = source.iterator();
		while(it.hasNext()){
			SyndEntry entry = it.next();
			String title = entry.getTitle();
			if(CLEAN_HEADER_PATTERN.matcher(title).find()){
				logger.warn("remove feed for title"+ entry.getTitle() + " ---> " +  entry.getLink());
				it.remove();
			}
		}
		
	}
	
	
	public static void main(String [] args){
		
		String title = "广州凯兰高信息科技有限公司招聘软件工程师";
		
		System.out.println(CLEAN_HEADER_PATTERN.matcher(title).find());
	}
	
	
	

}
