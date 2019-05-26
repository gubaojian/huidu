package net.java.efurture.reader.biz.ao.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.java.efurture.reader.biz.ao.ArticleAO;
import net.java.efurture.reader.biz.resultcode.AOResultCode;
import net.java.efurture.reader.biz.sort.SortUtils;
import net.java.efurture.reader.clean.article.ArticleCleaner;
import net.java.efurture.reader.mybatis.domain.ArticleDO;
import net.java.efurture.reader.mybatis.domain.enums.ArticleStatusEnum;
import net.java.efurture.reader.mybatis.mapper.ArticleCategoryMapperDOMapper;
import net.java.efurture.reader.mybatis.mapper.ArticleDOMapper;
import net.java.efurture.reader.mybatis.mapper.ArticleSourceMapper;
import net.java.efurture.reader.mybatis.query.ArticleQuery;
import net.java.efurture.reader.mybatis.query.CategoryArticleQuery;

import com.google.code.efurture.common.ao.BaseAO;
import com.google.code.efurture.common.api.ApiConstants;
import com.google.code.efurture.common.result.Result;
import com.google.code.efurture.common.resultcode.BaseResultCode;
import com.google.code.filestore.FileStore;

public class ArticleAOImpl extends BaseAO implements ArticleAO{
	
	@Resource
	ArticleDOMapper articleDOMapper;
	
	@Resource
	ArticleSourceMapper articleSourceMapper;
	
	@Resource
	ArticleCategoryMapperDOMapper  articleCategoryMapperDOMapper ;
	
	@Resource
	FileStore fileStore;
	
	private static  String URL_SIGN = null;
	static{
	   try {
		   URL_SIGN = "from=huidu&appUrl=huidu.lanxijun.com"; //客户端会进行编码
	   } catch (Exception e) {
		 URL_SIGN = "from=huidu&appUrl=huidu.lanxijun.com";
	  }
	}
	
	@Override
	public Result<Boolean> addArticle(ArticleDO article) {
		Result<Boolean> result = this.createResult();
		try{
			if(article == null || article.getTitle() == null  || article.getFeedId() == null){
				result.setResultCode(AOResultCode.ARTICLE_ARGS_ILLEGAL);
				result.setResult(false);
				result.setSuccess(false);
				return result;
			}
			
			//备份标题及内容
			String sourceContent = article.getContent();
			String sourceTitle = article.getTitle();
			String sourceShortDesc = article.getShortDesc();
			
			ArticleQuery sourceQuery = new ArticleQuery();
			sourceQuery.setFeedId(article.getFeedId());
			sourceQuery.setTitle(sourceTitle);
			int sourceCount = articleSourceMapper.countByQuery(sourceQuery);
			if(sourceCount > 0){
				result.setResultCode(AOResultCode.ARTICLE_ALREADY_EXIST);
				result.setResult(false);
				result.setSuccess(false);
				return result;
			}
			 //清理比较耗时间，因此需要清理时再进行清理
			//清理标准化文章，主要处理内容及标题。 如果文章不符合要求标记为删除状态，并将其排序标记为-1 。其它情况标记为正常状态
			ArticleCleaner.clean(article);

			synchronized(this){
				//根据清理后的标题查询，文章是否已经插入过
				ArticleQuery query = new ArticleQuery();
				query.setFeedId(article.getFeedId());
				query.setTitle(article.getTitle());
				int count = articleDOMapper.countByQuery(query);
				if(count > 0){
					result.setResultCode(AOResultCode.ARTICLE_ALREADY_EXIST);
					result.setResult(false);
					result.setSuccess(false);
					return result;
				}
				
				//文章创建时间
				article.setGmtCreate(new Date());
				//设置更新时间
				if(article.getGmtModified() == null){
					article.setGmtModified(new Date());
				}
				
				String fileKey = fileStore.save(article.getContent().getBytes());
				if(fileKey == null){
					logger.error("save article to store error");
					result.setResultCode(AOResultCode.ARTICLE_ALREADY_EXIST);
					result.setResult(false);
					result.setSuccess(false);
					return result;
				}
				article.setFileKey(fileKey);
				int effectCount = articleDOMapper.insert(article);
				
				//存储一份处理前的，并存储下来，便于问题定位
				String sourceFileKey = fileStore.save(sourceContent.getBytes());
				if(sourceFileKey == null){
					result.setResultCode(AOResultCode.ARTICLE_ALREADY_EXIST);
					result.setResult(false);
					result.setSuccess(false);
					return result;
				}
				article.setTitle(sourceTitle);
				article.setContent(sourceContent);
				article.setShortDesc(sourceShortDesc);
				article.setFileKey(sourceFileKey);
				articleSourceMapper.insert(article);

		        result.setResult(effectCount > 0);
		        result.setSuccess(true);
				return result;
			}
		}catch(Exception e){
			logger.error("addArticle error feedId " + article.getFeedId(), e);
			result.setResultCode(BaseResultCode.SYSTEM_EXCEPTION);
		    result.setThrowable(e);
			result.setSuccess(false);
			return result;
		}
	}

	@Override
	public Result<List<ArticleDO>> findArticleByQuery(ArticleQuery query) {
		Result<List<ArticleDO>> result = this.createResult();
		try{
			int count = articleDOMapper.countByQuery(query);
			query.setTotalCount(count);
			if(count <= 0){
				result.setResult(new ArrayList<ArticleDO>());
				result.setSuccess(true);
				return result;
			}
			
			List<ArticleDO> articleList = articleDOMapper.selectByQuery(query);
			if(query.getIncludeContent()){
				for(ArticleDO article : articleList){
					byte[]  bts = fileStore.get(article.getFileKey());
					if(bts != null){
 					    article.setContent(new String(bts));
					}
				}
			}
			result.setResult(articleList);
			result.setSuccess(true);
			return result;
		}catch(Exception e){
			logger.error("findArticleByQuery Exception", e);
			result.setResultCode(BaseResultCode.SYSTEM_EXCEPTION);
			result.setThrowable(e);	
			result.setSuccess(false);
			return result;
		}
	}
	
	
	public Result<List<ArticleDO>> findArticleByQueryForApp(ArticleQuery query){
		Result<List<ArticleDO>> result = this.createResult();
		try{
			List<ArticleDO> articleList = articleDOMapper.selectByQuery(query);
			if(articleList == null){
				articleList = new ArrayList<ArticleDO>();
			}
			if(query.getIncludeContent()){
				for(ArticleDO article : articleList){
					byte[]  bts = fileStore.get(article.getFileKey());
					if(bts != null){
 					    article.setContent(new String(bts));
					}
				}
			}
			result.setResult(articleList);
			result.getModels().put(ApiConstants.HAS_MORE_KEY, articleList.size() == query.getPageSize());
			result.setSuccess(true);
			return result;
		}catch(Exception e){
			logger.error("findArticleByQueryForApp Exception", e);
			result.setResultCode(BaseResultCode.SYSTEM_EXCEPTION);
			result.setThrowable(e);	
			result.setSuccess(false);
			return result;
		}
		
	}
	
	
	
	
	
	
	

	@Override
	public Result<ArticleDO> getArticleById(long id) {
		Result<ArticleDO> result = this.createResult();
		try{
			ArticleDO article = articleDOMapper.selectByPrimaryKey(id);
			if(article != null){
				String url = article.getUrl();
				if(url.lastIndexOf('%') > 0){
					try{
					    url = URLDecoder.decode(url, "UTF-8");
					}catch(Exception se){}
				}
				if(url.lastIndexOf('?') > 0){
					url += "&" + URL_SIGN;
				}else{
					url += "?" + URL_SIGN;
				}
				article.setUrl(url);
				
				byte[]  bts = fileStore.get(article.getFileKey());
				if(bts != null){
					article.setContent(new String(bts));
				}
			}
			result.setResult(article);
			result.setSuccess(true);
			return result;
		}catch(Exception e){
			logger.error("getArticleById Exception", e);
			result.setResultCode(BaseResultCode.SYSTEM_EXCEPTION);
			result.setThrowable(e);	
			result.setSuccess(false);
			return result;
		}
	}
	
	
	@Override
	public Result<ArticleDO> getArticleByIdFromSource(long id) {
		Result<ArticleDO> result = this.createResult();
		try{
			ArticleDO article = articleSourceMapper.selectByPrimaryKey(id);
			if(article != null){
				byte[]  bts = fileStore.get(article.getFileKey());
				if(bts != null){
					article.setContent(new String(bts));
				}
			}
			result.setResult(article);
			result.setSuccess(true);
			return result;
		}catch(Exception e){
			logger.error("getArticleById Exception", e);
			result.setResultCode(BaseResultCode.SYSTEM_EXCEPTION);
			result.setThrowable(e);	
			result.setSuccess(false);
			return result;
		}
	}

	@Override
	public Result<Boolean> deleteArticleById(long id) {
		Result<Boolean> result = this.createResult();
		try{
			ArticleDO target = new ArticleDO();
			target.setStatus(ArticleStatusEnum.DELETE.getValue());
			target.setId(id);
			int effectCount = articleDOMapper.updateByPrimaryKey(target);
			articleCategoryMapperDOMapper.deleteByArticleId(id);
			result.setResult(effectCount > 0);
			result.setSuccess(true);
			return result;
		}catch(Exception e){
			logger.error("deleteArticleById Exception", e);
			result.setResultCode(BaseResultCode.SYSTEM_EXCEPTION);
			result.setThrowable(e);	
			result.setSuccess(false);
			return result;
		}
	}

	@Override
	public Result<List<ArticleDO>> getArticleListByCategoryArticleQuery(CategoryArticleQuery query) {
		Result<List<ArticleDO>> result = this.createResult();
		try{
			 List<ArticleDO> articleList = articleDOMapper.selectByCategoryArticleQuery(query);
			 if(articleList == null){
			     articleList = new ArrayList<ArticleDO>();
			 }
			 result.setResult(articleList);
			 result.getModels().put(ApiConstants.HAS_MORE_KEY, articleList.size() == query.getPageSize());
			 result.setSuccess(true);
			 return result;
		}catch(Exception e){
			logger.error("getArticleListByCategoryArticleQuery Exception", e);
			result.setResultCode(BaseResultCode.SYSTEM_EXCEPTION);
			result.setThrowable(e);	
			result.setSuccess(false);
			return result;
		}
	}

}
