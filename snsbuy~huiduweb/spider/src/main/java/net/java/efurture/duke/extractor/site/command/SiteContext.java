package net.java.efurture.duke.extractor.site.command;

import net.java.efurture.duke.extractor.site.domain.SiteDO;

import org.apache.commons.chain.impl.ContextBase;
import org.jsoup.nodes.Document;

import com.sun.syndication.feed.synd.SyndFeed;


public class SiteContext extends ContextBase {
	private static final long serialVersionUID = -8425037004159039952L;
	
	private static final String DOCUMENT_KEY = "DOCUMENT_KEY";
	
	private static final String SITE_KEY = "SITE_KEY";
	
	
	private static final String FEED_KEY = "FEED_KEY";
	
	
	public void setSiteDocument(Document document){
		this.put(DOCUMENT_KEY, document);
	}
	
	
	public Document getSiteDocument(){
		return (Document) this.get(DOCUMENT_KEY);
	}
	

	public void setSiteDO(SiteDO site){
		this.put(SITE_KEY, site);
	}
	
	public SiteDO getSiteDO(){
		return (SiteDO) this.get(SITE_KEY);
	}
	
	
	public SyndFeed getFeed(){
		return (SyndFeed) this.get(FEED_KEY);
	}
	
	public void setFeed(SyndFeed feed){
		this.put(FEED_KEY, feed);
	}

}
