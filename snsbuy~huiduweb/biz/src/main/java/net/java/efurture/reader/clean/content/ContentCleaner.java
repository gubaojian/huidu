package net.java.efurture.reader.clean.content;

import net.java.efurture.reader.clean.content.command.CleanCodeLineNumber;
import net.java.efurture.reader.clean.content.command.CleanContentSourceSignInHeader;
import net.java.efurture.reader.clean.content.command.CleanContentSourceSignTail;
import net.java.efurture.reader.clean.content.command.CleanTailReferenceRelateEtc;
import net.java.efurture.reader.clean.content.command.CleanTrackOrAdImgBySrc;
import net.java.efurture.reader.clean.content.command.CleanTrackOrAdLink;
import net.java.efurture.reader.clean.content.command.CleanWasteInfoByClassName;
import net.java.efurture.reader.clean.content.command.ImgRelativeSrcToAbsLink;
import net.java.efurture.reader.clean.content.command.StandardHighlightSourceCode;
import net.java.efurture.reader.clean.content.command.after.Base64ImageSrc;
import net.java.efurture.reader.clean.content.command.after.CleanEmptyTag;
import net.java.efurture.reader.clean.content.command.after.RemoveNbspInContent;
import net.java.efurture.reader.clean.content.context.ContentContext;

import org.apache.commons.chain.impl.ChainBase;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.util.StringUtils;

public class ContentCleaner {
	
	
	public static final int MIN_CONTENT_TEXT_LENGTH = 64;
	
	/**
	 * 白名单过滤之前进行的处理
	 * */
	private static final ChainBase PRE_WHITELIST_FILTER_CHAIN =  new ChainBase();
	static{
		 PRE_WHITELIST_FILTER_CHAIN.addCommand(new CleanCodeLineNumber());
         PRE_WHITELIST_FILTER_CHAIN.addCommand(new StandardHighlightSourceCode());
         PRE_WHITELIST_FILTER_CHAIN.addCommand(new ImgRelativeSrcToAbsLink());
		 PRE_WHITELIST_FILTER_CHAIN.addCommand(new CleanTrackOrAdLink());	
		 PRE_WHITELIST_FILTER_CHAIN.addCommand(new CleanWasteInfoByClassName());	
		 PRE_WHITELIST_FILTER_CHAIN.addCommand(new CleanTrackOrAdImgBySrc());
		 PRE_WHITELIST_FILTER_CHAIN.addCommand(new CleanContentSourceSignInHeader());
		 PRE_WHITELIST_FILTER_CHAIN.addCommand(new CleanContentSourceSignTail());
		 PRE_WHITELIST_FILTER_CHAIN.addCommand(new CleanTailReferenceRelateEtc());	
		//PRE_CLEAN_CHAIN.addCommand(new CleanTailByStatisticAHref());	暂时不用
	}
	
	/**
	 * 白名单过滤之前后的处理
	 * */
	private static final ChainBase AFTER_WHITE_LIST_FILTER_CHAIN = new ChainBase();
	static{
		 AFTER_WHITE_LIST_FILTER_CHAIN.addCommand(new Base64ImageSrc());
		 AFTER_WHITE_LIST_FILTER_CHAIN.addCommand(new RemoveNbspInContent());
		 AFTER_WHITE_LIST_FILTER_CHAIN.addCommand(new CleanEmptyTag());
	}
	

	/**
	 * 清理，标准化显示文章的内容. 如果文字长度很小，直接返回 ""
	 * */
	public static final String clean(String content, String baseUri){
		if(StringUtils.isEmpty(content)){
			return content;
		}
		Document document = Jsoup.parse(content);  
		ContentContext context = new ContentContext();
		context.setDocument(document);
		context.setBaseUri(baseUri);
		try {
			PRE_WHITELIST_FILTER_CHAIN.execute(context);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Document clean = ContentWhitelist.filter(context.getDocument());
		context.setDocument(clean);
		try {
			AFTER_WHITE_LIST_FILTER_CHAIN.execute(context);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//文章内容很少，直接标记为删除
		if(context.getDocument().body().text().length() < MIN_CONTENT_TEXT_LENGTH){
			return "";
		}
		
		return context.getDocument().body().html();
	}
	
	
	
	public static final String clean(String content){
		return clean(content, null);
	}
	
	

}
