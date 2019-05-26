package net.java.efurture.duke.extractor.site.command;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import net.java.efurture.duke.extractor.site.domain.SiteDO;
import net.java.efurture.duke.extractor.site.domain.SiteEntryType;
import net.java.efurture.duke.extractor.site.feed.FeedClient;
import net.java.efurture.duke.extractor.site.utils.AuthorUtils;
import net.java.efurture.reader.utils.TrimUtils;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.StringUtils;

import com.sun.syndication.feed.synd.SyndFeed;

public class SignDescriptionCommand extends SiteCommand {
	
	
	private static String AUTHOR_REL_REGEX = "author";
	private static final Pattern AUTHOR_REL_PATTERN = Pattern.compile(AUTHOR_REL_REGEX, Pattern.CASE_INSENSITIVE);
	
	private static final String INVALID_META_REGEX = "^[a-zA-Z0-9]+$";
	
	private static final Pattern INVALID_META_PATTERN = Pattern.compile(INVALID_META_REGEX, Pattern.CASE_INSENSITIVE);

	@Override
	public void extract(SiteContext context) {
	    SiteDO site = context.getSiteDO();
	    String sign = null;
	    String description = null;
	    if(SiteEntryType.FEED.getValue() == site.getEntryType()){
	    	SyndFeed feed =  context.getFeed();
	    	if(feed == null){
	    	   feed = FeedClient.grapFeedUrl(site.getEntry());
	    	}
	    	if(feed != null){
	    		 sign = feed.getAuthor();
	    		 description = feed.getDescription();
	    		 if(sign == null){
	    			 sign = AuthorUtils.authorFromTitle(feed.getTitle());
	    		 }
	    		 if(description == null){
	    			 description = feed.getTitle();
	    		 }
	    		 if(sign == null){
	    			 sign = AuthorUtils.authorFromTitle(description);
	    		 }
	    	}
	    }
		
	    if(sign != null){
	    	site.setSign(sign);
	    	site.setDescription(description);
	    	context.setSiteDO(site);
	    	return;
	    }
	    
	    Document document = context.getSiteDocument();
	    Elements elements = document.getElementsByAttributeValueMatching("rel", AUTHOR_REL_PATTERN);
	    if(elements != null){
	    	Set<String> authors = new HashSet<String>();
	    	for(Element  element : elements){
	    	  String author = element.text();
	    	  author = TrimUtils.trim(author);
	    	  if(author != null && author.length() > 0){
	    		  authors.add(author);
	    	  }
	    	}
	    	if(authors.size() == 1){
	    		sign = authors.iterator().next();
	    	}
	    	
	    }
	    Elements titles = document.getElementsByTag("title");
	    if(titles.size() > 0){
	    	description = titles.get(0).text();
	    }
	    
	    if(description == null){
	    	Elements metas = document.getElementsByTag("meta");
	    	for(Element meta : metas){
	    		String content = meta.attr("content");
	    		if(StringUtils.isEmpty(content)){
	    			continue;
	    		}
	    		
	    		if(INVALID_META_PATTERN.matcher(content).find()){
	    			continue;
	    		}
	    		description = content;
	    	}
	    }
	    
	    if(sign == null){
	    	sign = AuthorUtils.authorFromTitle(description);
	    }
		
	    site.setSign(sign);
	    site.setDescription(description);
	    context.setSiteDO(site);
	}

}
