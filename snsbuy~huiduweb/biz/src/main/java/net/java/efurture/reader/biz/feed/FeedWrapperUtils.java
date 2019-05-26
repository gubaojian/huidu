package net.java.efurture.reader.biz.feed;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.java.efurture.reader.biz.sort.SortUtils;
import net.java.efurture.reader.clean.category.CategoryCleaner;
import net.java.efurture.reader.clean.synd.util.AuthorUtils;
import net.java.efurture.reader.mybatis.domain.ArticleCategoryMapperDO;
import net.java.efurture.reader.mybatis.domain.ArticleDO;
import net.java.efurture.reader.mybatis.domain.CategoryDO;
import net.java.efurture.reader.mybatis.domain.FeedDO;
import net.java.efurture.reader.mybatis.domain.enums.CategoryStatusEnum;
import net.java.efurture.reader.utils.TrimUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpMessage;
import org.apache.http.HttpResponse;
import org.apache.http.impl.cookie.DateUtils;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.sun.syndication.feed.module.DCModule;
import com.sun.syndication.feed.synd.SyndCategory;
import com.sun.syndication.feed.synd.SyndCategoryImpl;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndPerson;


/**
 * 处理Feed的相关工作，有待重构分离
 * 重构这个类
 * */
public class FeedWrapperUtils {
	
	private static Logger logger = LoggerFactory.getLogger(FeedWrapperUtils.class);
	
	private static final Set<String> ONE_NAME_SETS = new HashSet<String>();
	static{
		ONE_NAME_SETS.add("C");
		ONE_NAME_SETS.add("c");
		ONE_NAME_SETS.add("R");
		ONE_NAME_SETS.add("r");
	}
	
		

	public  static List<ArticleCategoryMapperDO> getArticleCategoryMapper(ArticleDO article, List<CategoryDO> categoryList){
		List<ArticleCategoryMapperDO> articleCategoryMapperList = new ArrayList<ArticleCategoryMapperDO>();
		for(CategoryDO category : categoryList){
			 ArticleCategoryMapperDO articleCategoryMapper = new ArticleCategoryMapperDO();
			 articleCategoryMapper.setCategoryId(category.getId());
			 articleCategoryMapper.setArticleId(article.getId());
			 articleCategoryMapperList.add(articleCategoryMapper);
		}
		return articleCategoryMapperList;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public static List<CategoryDO>  getCategoryFromFeedEntry(FeedDO feed, SyndEntry entry){
		 List<CategoryDO>  categoryList = new ArrayList<CategoryDO>();
		 List<SyndCategory> syndCategoryList = entry.getCategories();
		 Set<String> namesSet = new HashSet<String>(); //去除重复的分类
		 for(SyndCategory synd : syndCategoryList){
			 String name = CategoryCleaner.clean(synd.getName());
			 if(StringUtils.isEmpty(synd.getName()) ){ //分类至少两个字
				 logger.warn("feedUtils invalid category name " + synd.getName());
				 continue;
			 }
			 if(name.length() <= 1 && !ONE_NAME_SETS.contains(name)){ //很多中文名字长度为2
				 logger.warn("feedUtils invalid category name " + synd.getName());
				 continue;
			 }
			 
			 if(namesSet.contains(name)){
				 continue;
			 }
			 namesSet.add(name);
			 CategoryDO category = new CategoryDO();
			 category.setName(name);
			 category.setSort(SortUtils.randSort());
			 category.setStatus(CategoryStatusEnum.NORMAL.getValue());
			 categoryList.add(category);
		 }
		return categoryList;
	}
	
	
	@SuppressWarnings("unchecked")
	public static ArticleDO getArticleFromFeedEntry(FeedDO feed, SyndEntry entry){
		ArticleDO article = new ArticleDO();
		article.setTitle(entry.getTitle().trim());
		article.setAuthor(entry.getAuthor());
		article.setShortDesc(getShortDesc(entry));  //获取文章的简短描述
		article.setContent(buildContent(entry.getContents())); ////获取文章的内容
		if(entry.getPublishedDate() == null){
			article.setPublishDate(new Date());
		}else{
			if(entry.getPublishedDate().getTime() > System.currentTimeMillis()){
				entry.setPublishedDate(new Date());
			}
			article.setPublishDate(entry.getPublishedDate());	
		}	
		article.setUrl(entry.getLink());
		article.setSort(SortUtils.randSort());
		article.setFeedId(feed.getId());
		return article;
	}
	
	/**
	 * 获取文章的剪短描述
	 * */
	private static String getShortDesc(SyndEntry entry){
		String shortDesc =  null;
		if(entry.getDescription() != null && entry.getDescription().getValue() != null){
		    shortDesc = entry.getDescription().getValue();
		}else{
			SyndContent content = (SyndContent)entry.getContents().get(0);
			shortDesc = content.getValue();
		}
		shortDesc = TrimUtils.trim(Jsoup.parseBodyFragment(shortDesc).body().text());
		int length = 48;
		if(shortDesc.length() > length){
			shortDesc = shortDesc.substring(0, length);
		}
		return shortDesc;
	}
	
	
	
	
	private static String  buildContent(List<SyndContent> syndList){
		StringBuffer buf = new StringBuffer();
		for(SyndContent synd : syndList){
			buf.append(synd.getValue());
			buf.append("<br/>");
		}
		return buf.toString();
	}
	 
	
	
	/**
	 * 在请求头部添加部分请求头。
	 * */
	public static   void attachUsefulHeadersToRequest(HttpMessage request, Map<Object,Object> lastHeaders){
		String etagValue = (String) lastHeaders.get(HttpHeaders.ETAG);
		if(etagValue != null){
			request.addHeader(HttpHeaders.IF_NONE_MATCH, etagValue);
		}
		
		String lastModifiedValue = (String) lastHeaders.get(HttpHeaders.LAST_MODIFIED);
		if(lastModifiedValue != null){
			request.addHeader(HttpHeaders.IF_MODIFIED_SINCE, lastModifiedValue);
		}
	}
	
	
	/**
	 * 获取对应的响应头
	 * */
	public static Map<Object,Object> getUsefulHeaderMapFromResponse(HttpResponse response){
		Map<Object,Object> headers = new HashMap<Object,Object>();
		
		headers.put("status", response.getStatusLine().getStatusCode());
		Header etagHeader = response.getFirstHeader(HttpHeaders.ETAG);
		if(etagHeader != null){
			headers.put(HttpHeaders.ETAG,  etagHeader.getValue());
		}
		
		Header lastModifiedHeader =  response.getFirstHeader(HttpHeaders.LAST_MODIFIED);
		if(lastModifiedHeader != null){
			headers.put(HttpHeaders.LAST_MODIFIED, lastModifiedHeader.getValue());
		}
		
		return headers;
	}
	
	
	/**
	 * 过滤过期的Feed 以及TODO: 非法的feed（比如招聘信息，及广告信息）
	 * */
	public static List<SyndEntry> filterSyndFeed(List<SyndEntry> entries, Map<Object, Object> lastHeaders){
		if(entries == null || entries.size() == 0){
			return new LinkedList<SyndEntry>();
		}
		
		List<SyndEntry> filteredEntry = new ArrayList<SyndEntry>();
		String  dateStr =  (String) lastHeaders.get(HttpHeaders.LAST_MODIFIED); 
		Date date = null;
		if(dateStr != null){
			try {
				if(dateStr.endsWith("GMT+8")){ //新浪响应头不合法，会导致parse异常
					dateStr = dateStr.substring(0, dateStr.length() - 2);
				}
				date = DateUtils.parseDate(dateStr);
			} catch (Exception e) {
				e.printStackTrace();
				date = null;
			}
			
		}
		Set<String> titlesSet = new HashSet<String>();
		for(SyndEntry entry : entries){
			 String title = TrimUtils.trim(entry.getTitle());
			 
			 if(StringUtils.isBlank(title)){
				 continue;
			 }

			 title = title.trim();
			 
			 if(entry.getPublishedDate() == null){
				 entry.setPublishedDate(entry.getUpdatedDate());
			 }
			 
			Date publishedDate = entry.getPublishedDate();
		
			if(publishedDate == null){ //发布日期未解析出来的，不显示，直接过滤掉
				logger.warn("publisdedDate is null, site: " +  entry.getLink());
				continue;
			}
			
			if(titlesSet.contains(title)){
				 continue;
		    }
			
		    titlesSet.add(title);
			if(date == null){
				filteredEntry.add(entry);
				continue;
			}
		
			if(date.after(publishedDate) || date.equals(publishedDate)){
				continue;
			}
			
			filteredEntry.add(entry);
		}
		return filteredEntry;
	}
	
	
	/**
	 * 处理feed，如果conent为空，则将description作为contents
	 * */
	@SuppressWarnings("unchecked")
	public static void adapterForStandardSynedFeed(List<SyndEntry> entries,  SyndFeed syndFeed, FeedDO feed){
		for(SyndEntry entry : entries){
			
			 //适配内容
			 if(entry.getContents() == null || entry.getContents().size() == 0){
				 if(entry.getDescription() != null){
					 List<SyndContent> contents = new ArrayList<SyndContent>(1);
					 contents.add(entry.getDescription());
					 entry.setContents(contents); 
				 }
			 }
			 
			 //适配作者
			 String author = null;
			 if(!StringUtils.isEmpty(entry.getAuthor())){
				 author = entry.getAuthor();
			 }else{
				 List<DCModule> modules = entry.getModules();
				 if(modules != null && modules.size() > 0){
					 author = modules.get(0).getCreator();
				 }
			 }
			 
			 if(author == null){
				 List<SyndPerson>  persons = syndFeed.getAuthors();
				 if(persons != null && persons.size() > 0){
				    author = persons.get(0).getName();
				 }
			 }
			 
			 if(author == null){
				 author = AuthorUtils.authorFromTitle(syndFeed.getTitle());
			 }
			 
			 
			 if(author != null){
				 entry.setAuthor(author);
				 List<SyndCategory> syndCategoryList =  entry.getCategories();
				 if(syndCategoryList == null || syndCategoryList.size() == 0){
					 syndCategoryList = new LinkedList<SyndCategory>();
					 SyndCategory category = new SyndCategoryImpl();
					 category.setName(entry.getAuthor());
					 syndCategoryList.add(category);
					 entry.setCategories(syndCategoryList);
				 }
				
			 }
			 
			 //适配类目
			 List<SyndCategory> syndCategoryList =  entry.getCategories();
			 if(CollectionUtils.isEmpty(syndCategoryList)){
				 if(!StringUtils.isEmpty(syndFeed.getAuthor())){
					 SyndCategory category = new SyndCategoryImpl();
					 category.setName(syndFeed.getAuthor());
					 syndCategoryList.add(category);
					 entry.setCategories(syndCategoryList);
				 }
			 }
			 
			 syndCategoryList =  entry.getCategories();
			 if(CollectionUtils.isEmpty(syndCategoryList)){
				 if(!StringUtils.isEmpty(syndFeed.getTitle())){
					 String title = syndFeed.getTitle();
					 if(title.length() > 28){
						 title = title.substring(0, 28);
					 }
					 SyndCategory category = new SyndCategoryImpl();
					 category.setName(syndFeed.getTitle());
					 syndCategoryList.add(category);
					 entry.setCategories(syndCategoryList);
					 if(author == null){
						 entry.setAuthor(syndFeed.getTitle());
					 }
					 
				 }
			 }

			 //修复相对连接为绝对连接
			 entry.setLink(FeedWrapperUtils.fixLink(entry.getLink(), feed.getSite())); 
			 
			 //日期错误
			 if(entry.getPublishedDate() == null || entry.getPublishedDate().getTime() > System.currentTimeMillis()){
			    entry.setPublishedDate(new Date());
			}
		}
	}
	
	/**
	 * 判断feed是否有效
	 * */
	public static boolean isValidSyndFeed(SyndEntry entry){
		if(entry == null){
			return false;
		}
		if(entry.getContents() == null || entry.getContents().size() == 0){
			return false;
		}
		if(entry.getCategories() == null || entry.getCategories().size() == 0){
			return false;
		}
		
		if(entry.getLink() == null){
			return false;
		}
		
		return true;
	}
	
	
	public static List<String> parseTagsList(String tags){
		List<String> tagsList = new ArrayList<String>();
		if(StringUtils.isEmpty(tags)){
			return tagsList;
		}
		String[] words = tags.split(" ");
		for(String word : words){
			if(StringUtils.isEmpty(word)){
				continue;
			}
			tagsList.add(word.trim());
		}
		return tagsList;
	}
	
	
	public static final String fixLink(String link, String site){
		if(StringUtils.isEmpty(link)){
			return link;
		}
		if(link.startsWith("http")){
			return link;
		}
         
		if(link.startsWith("/")){
			link = link.substring(1, link.length());
		}
		
		if(!site.endsWith("/")){
			site = site + "/";
		}
		
		return site + link;
	}
	
	
	
	public static void main(String [] args){
		
		List<String> wordList = parseTagsList("冯达辉  冯达辉  DBA     马云 老赵 赵劼");
	    for(String word : wordList){
	    	System.out.println(word);
	    }
	    
	    System.out.println(wordList.size());
	   long id = 1;
		
		System.out.println((id<<32) + 1);
		
		
		System.out.println( Jsoup.parseBodyFragment("<h2>xxxx</h2>222<span>Maaa<a").body().text());
		
		
		System.out.println(fixLink("link", "http://www.baidu.com"));
		
		System.out.println(fixLink("/link", "http://www.baidu.com/"));
		
		
	}
	
	
	
	
	
	
	
	

}
