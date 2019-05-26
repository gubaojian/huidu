package net.java.efurture.duke.extractor.site.command;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import net.java.efurture.duke.extractor.site.utils.SiteUtils;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.StringUtils;

public class BlogRollCommand extends SiteCommand {

	

	private static String BLOG_ROLL_REGEX = ".*blogroll.*";
	static{
		// BLOG_ROLL_REGEX += "|*LinksWrapper*";
	};
	
	private static final Pattern BLOG_ROLL_PATTERN = Pattern.compile(BLOG_ROLL_REGEX, Pattern.CASE_INSENSITIVE);
	
	
	@Override
	public void extract(SiteContext context) {
		Document document = context.getSiteDocument();
		Elements elements = document.getElementsByAttributeValueMatching("class", BLOG_ROLL_PATTERN);
		if(elements != null && elements.size() > 0){
			Set<String> links = new HashSet<String>();
			for(Element element : elements){
				  Elements  as = element.getElementsByTag("a");
				  if(as  == null || as.size() == 0){
					  continue;
				  }
				  
				  for(Element a : as){
					  String href = StringUtils.trimAllWhitespace(a.attr("href"));
					  if(this.isLinkHrefValid(href, context)){
					     links.add(a.attr("href"));
					  }
				  }
			}
			if(links.size() > 0){
				context.getSiteDO().setLinks(links);
				return;
			}
			
		}
		
		
		//通过其它方式获取连接
		
	}
	
	
	private Set<String> extractLinksByIdLinks(String attrName, SiteContext context){
		Set<String> links = new HashSet<String>();
		Document document = context.getSiteDocument();
		Elements elements = document.getElementsByAttributeValueMatching(attrName, BLOG_ROLL_PATTERN);
		if(elements != null && elements.size() > 0){
			for(Element element : elements){
				  Elements  as = element.getElementsByTag("a");
				  if(as  == null || as.size() == 0){
					  continue;
				  }
				  
				  for(Element a : as){
					  String href = StringUtils.trimAllWhitespace(a.attr("href"));
					  if(this.isLinkHrefValid(href, context)){
					     links.add(a.attr("href"));
					  }
				  }
			}
			if(links.size() > 0){
				context.getSiteDO().setLinks(links);
			}
			
		}
		return links;
	}
	
	
	
	private boolean isLinkHrefValid(String href, SiteContext context){
		if(StringUtils.isEmpty(href)){
			return false;
		}
		if(SiteUtils.isSameDomain(href, context.getSiteDO().getUrl())){
			return false;
		}
		
		if(!href.startsWith("http")){
			return false;
		}
		
		return true;
	}

}
