package net.java.efurture.reader.biz.spider.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.java.efurture.reader.biz.ao.ArticleAO;
import net.java.efurture.reader.biz.ao.ArticleCategoryMapperAO;
import net.java.efurture.reader.biz.ao.CategoryAO;
import net.java.efurture.reader.biz.feed.FeedWrapperUtils;
import net.java.efurture.reader.biz.feed.RssFeedClient;
import net.java.efurture.reader.biz.spider.JuduSpider;
import net.java.efurture.reader.mybatis.domain.ArticleCategoryMapperDO;
import net.java.efurture.reader.mybatis.domain.ArticleDO;
import net.java.efurture.reader.mybatis.domain.CategoryDO;
import net.java.efurture.reader.mybatis.domain.FeedDO;
import net.java.efurture.reader.mybatis.domain.enums.ArticleStatusEnum;
import net.java.efurture.reader.mybatis.domain.enums.CategoryStatusEnum;
import net.java.efurture.reader.utils.TrimUtils;

import org.springframework.util.StringUtils;

import com.google.code.efurture.common.ao.BaseAO;
import com.google.code.efurture.common.result.Result;
import com.sun.syndication.feed.synd.SyndCategory;
import com.sun.syndication.feed.synd.SyndCategoryImpl;
import com.sun.syndication.feed.synd.SyndEntry;

public class JuduSpiderImpl extends BaseAO implements JuduSpider {

	@Resource
	ArticleAO articleAO;
	
	@Resource
	CategoryAO categoryAO;
	
	@Resource
	ArticleCategoryMapperAO articleCategoryMapperAO;
	
	
	@Resource
	RssFeedClient rssFeedClient;
	
	@Override
	public Result<Map<Object, Object>> grapAndSaveArticleOnSite(FeedDO feed, Map<Object, Object> info) {
		Result<Map<Object, Object>> result = this.createResult();
		try{
			Result<List<SyndEntry>> entryResult = rssFeedClient.grapFeedFromSite(feed, info);
			if(!entryResult.isSuccess()){
				result.setThrowable(entryResult.getThrowable());
				result.setSuccess(false);
				return result;
			}
			
			Result<Boolean> saveResult = this.saveFeedEntryList(feed, entryResult.getResult());
			if(!saveResult.isSuccess()){
				result.setThrowable(saveResult .getThrowable());
				result.setSuccess(false);
				return result;
			}
			
			 Map<Object,Object> headers = (Map<Object, Object>) entryResult.getModels().get(RssFeedClient.HEADER_KEY);
			 if(headers == null){
				 headers = new HashMap<Object, Object>();
			}
			result.setResult(headers);
	        result.setSuccess(true);
			return result;
		}catch(Exception e){
			logger.error("grapArticleOnSite error", e);
		    result.setThrowable(e);
			result.setSuccess(false);
			return result;
		}
	}

	
	
	

	@Override
	public Result<Boolean> saveFeedEntryList(FeedDO feed, List<SyndEntry> entries) {
		Result<Boolean> result = this.createResult();
		try{
			if(entries == null || entries.size() == 0 ){
				result.setResult(true);
				result.setSuccess(true);
				return result;
			}
	
			Set<String> titlesSet = new HashSet<String>();
			
			for(SyndEntry entry : entries){
				String title = TrimUtils.trim(entry.getTitle());
				if(StringUtils.isEmpty(title)){
					continue;
				}
				
				
				
				/** 设置网站的tag作为类目 */
				List<String> tags = FeedWrapperUtils.parseTagsList(feed.getTags());
				for(String tag : tags){
					 List<SyndCategory> syndCategoryList =  entry.getCategories();
					 if(syndCategoryList == null){
						 syndCategoryList = new LinkedList<SyndCategory>();
					 }
					 SyndCategory category = new SyndCategoryImpl();
					 category.setName(tag);
					 syndCategoryList.add(category);
				}
				
				if(!FeedWrapperUtils.isValidSyndFeed(entry)){
					logger.warn( feed.getId() + " invalid feeds " + feed.getUrl());
					continue;
				}
				

				ArticleDO article = FeedWrapperUtils.getArticleFromFeedEntry(feed, entry);
				
				//防止重复
				if(titlesSet.contains(article.getTitle())){
					continue;
				}
				titlesSet.add(article.getTitle());
				
				
				Result<Boolean> articleResult = articleAO.addArticle(article);
				
				if(!articleResult.isSuccess() || !articleResult.getResult()){
					continue;
				}
				
				//无效的文章，直接删除处理
				if(article.getSort() < 0 || article.getStatus() == ArticleStatusEnum.DELETE.getValue()){
					continue;
				}
				
				
				List<CategoryDO> categoryList = FeedWrapperUtils.getCategoryFromFeedEntry(feed, entry);
				
				if(categoryList == null || categoryList.size() == 0){
					continue;
				}
				
				
				Result<Boolean> addCategoryResult  = categoryAO.addCategoryList(categoryList);
				if(!addCategoryResult.isSuccess()){
					continue;
				}
				
				List<ArticleCategoryMapperDO> articleCategoryMapperList = FeedWrapperUtils.getArticleCategoryMapper(article, categoryList);
				articleCategoryMapperAO.addArticleCategoryMapper(articleCategoryMapperList);
				
				
				//动态更新类目信息
				List<Integer> categoryIds = new ArrayList<Integer>();
				for(CategoryDO category : categoryList){
					categoryIds.add(category.getId());
				}
				Result<Map<Integer,Integer>> articleCountResult = articleCategoryMapperAO.categoryArticleCount(categoryIds);
				if(!articleCountResult.isSuccess()){
					continue;
				}
				Map<Integer,Integer> countMap = articleCountResult.getResult();
				for(CategoryDO category : categoryList){
					if(category.getStatus() == CategoryStatusEnum.DELETE.getValue()){
						continue;
					}
					
					Integer count = countMap.get(category.getId());
					if(count == null || count < 5){
						continue;
					}
					
					int sort = 0;
					CategoryDO target = new CategoryDO();
					
					//新类目出现的增加权重
					if(category.getStatus() == CategoryStatusEnum.WAIT.getValue()){
						target.setStatus(CategoryStatusEnum.NORMAL.getValue());
						sort += 300;
					}
					
					//文章个数的权重，主要的权重
					int countSort = (int)((1000.0*count)/200);
				    if(countSort > 1000){
				    	 countSort = 1000;
				    }
					sort += countSort;
					
					//
					target.setSort(sort);
					target.setGmtModified(new Date());
					target.setId(category.getId());
					
					categoryAO.updateCategory(target);					
				}
				
				
				
				
				
			}
			
			result.setResult(true);
			result.setSuccess(true);
			return result;
		}catch(Exception e){
			logger.error("handle synd feed error", e);
		    result.setThrowable(e);
			result.setSuccess(false);
			return result;
		}
	}








	
}
