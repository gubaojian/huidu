package net.java.efurture.duke.extractor.site.command;

import java.util.HashSet;
import java.util.Iterator;
import java.util.regex.Pattern;

import net.java.efurture.duke.extractor.site.domain.SiteDO;
import net.java.efurture.duke.extractor.site.domain.SiteEntryType;
import net.java.efurture.duke.extractor.site.feed.FeedClient;
import net.java.efurture.duke.extractor.site.utils.SiteUtils;
import net.java.efurture.reader.utils.TrimUtils;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sun.syndication.feed.synd.SyndFeed;

public class ExtractSiteEntryCommand extends SiteCommand{
	
	private static String FEED_REGEX = "feed";
	static{
		FEED_REGEX +="|atom.xml";  // .  \\.
		FEED_REGEX +="|rss.xml";  
	};
	
	private static final Pattern FEED_PATTERN = Pattern.compile(FEED_REGEX, Pattern.CASE_INSENSITIVE);
	
	
	
	private static String  FEED_COMMENT_REGEX = "comment";
	static{
		
	}
	private static final Pattern FEED_COMMENT_PATTERN = Pattern.compile(FEED_COMMENT_REGEX, Pattern.CASE_INSENSITIVE);
	
	
	

	@Override
	public void extract(SiteContext context) {
		Document document = context.getSiteDocument();
		SiteDO site = context.getSiteDO();
		Elements elements = document.getElementsByAttributeValueMatching("href", FEED_PATTERN);
		if(elements == null || elements.size() == 0){
			site.setEntry(site.getUrl());
			site.setEntryType(SiteEntryType.SITE.getValue());
			context.setSiteDO(site);
			return;
		}
		
		
		HashSet<String> feeds = new HashSet<String>();
		for(Element element : elements){
			String url = TrimUtils.trim(element.absUrl("href"));
			if(url == null){
				continue;
			}
			feeds.add(url);
		}
		
		String url = null;
		if(feeds.size() == 0){
			url = feeds.iterator().next();
		}else{
			Iterator<String> it = feeds.iterator();
			while(it.hasNext()){
				String feed = it.next();
				if(FEED_COMMENT_PATTERN.matcher(feed).find()){
					continue;
				}
				if(SiteUtils.isSameDomain(site.getUrl(), feed)){
					url = feed;
					break;
				}
			}
			
		}
		
		if(url != null){
			SyndFeed feed = FeedClient.grapFeedUrl(url);
			if(feed != null){
				context.setFeed(feed);
			}else{
				url = null;
			}
		}
		
		
		
		
		if(url == null){
			site.setEntry(site.getUrl());
			site.setEntryType(SiteEntryType.SITE.getValue());
		}else{
		    site.setEntry(url);
		    site.setEntryType(SiteEntryType.FEED.getValue());
		}
		context.setSiteDO(site);
	}

}
