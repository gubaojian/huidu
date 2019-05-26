package net.java.efurture.reader.clean.synd;

import java.util.List;

import net.java.efurture.reader.clean.synd.command.CleanAdSyndByTitle;
import net.java.efurture.reader.clean.synd.context.SyndContext;

import org.apache.commons.chain.Chain;
import org.apache.commons.chain.impl.ChainBase;

import com.sun.syndication.feed.synd.SyndEntry;


/**
 * 初次过滤feed 条目
 * */
public class SyndCleaner {
	
	private static final Chain ENTRY_CLEAN_CHAIN =  new ChainBase();
	static{
		ENTRY_CLEAN_CHAIN.addCommand(new CleanAdSyndByTitle()); //青春广告的招聘
	};
	
	

	public static List<SyndEntry> clean(List<SyndEntry> entryList){
		SyndContext context = new SyndContext();
		context.setEntryList(entryList);
		try {
			ENTRY_CLEAN_CHAIN.execute(context);
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return context.getEntryList();
	};
	
	
	
	

}
