package net.java.efurture.reader.biz.timer.task.impl;

import java.util.List;

import javax.annotation.Resource;

import net.java.efurture.reader.biz.timer.task.Task;
import net.java.efurture.reader.clean.article.ArticleCleaner;
import net.java.efurture.reader.mybatis.domain.ArticleDO;
import net.java.efurture.reader.mybatis.domain.enums.ArticleStatusEnum;
import net.java.efurture.reader.mybatis.mapper.ArticleDOMapper;
import net.java.efurture.reader.mybatis.mapper.ArticleSourceMapper;
import net.java.efurture.reader.mybatis.query.ArticleQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.google.code.filestore.FileStore;

/**
 * 迁移文章到新的结构
 * */
public class UpgradeArticleTask implements Task {
	
	private static final Logger logger = LoggerFactory.getLogger(DefaultClaimerTask.class);

	@Resource
	ArticleDOMapper articleDOMapper;
	
	@Resource
	ArticleSourceMapper articleSourceMapper;
	
	@Resource
	JdbcTemplate jdbcTemplate;
	
	@Resource
	FileStore fileStore;
	
	@Override
	public void execute() {
		logger.info("UpgradeArticleTask start");
		int count = 0;
		try{
			ArticleQuery query =  new ArticleQuery();
			query.setIncludeContent(Boolean.TRUE);
			query.setPageNum(1);
			while(true){
				 List<ArticleDO> articleSourceList = articleSourceMapper.selectByQuery(query);
				 if(CollectionUtils.isEmpty(articleSourceList)){
					 break;
				 }
				 for(ArticleDO articleSource : articleSourceList){
					 
					  ArticleDO article =  articleDOMapper.selectByPrimaryKey(articleSource.getId());
					   if(article == null){
						   continue;
					  }
					  if(!StringUtils.isEmpty(article.getContent())){
						  ArticleDO target = new ArticleDO();
						  target.setId(article.getId());
						  long start = System.currentTimeMillis();
						  target.setFileKey(fileStore.save(article.getContent().getBytes()));
						  logger.info("save file "  + articleSource.getId() + " used " + (System.currentTimeMillis() - start) + " ms");;
						  articleDOMapper.updateByPrimaryKey(target);
						  
						  ArticleDO sourceTarget = new ArticleDO();
						  sourceTarget.setId(articleSource.getId());
						  sourceTarget.setFileKey(fileStore.save(articleSource.getContent().getBytes()));
						  articleSourceMapper.updateByPrimaryKey(sourceTarget);
					  }else{
						  logger.warn("article " + article.getId()  + " is null");
					  }
				 }
				 count  += articleSourceList.size();
				 //取下一页
				query.setPageNum(query.getPageNum() + 1);
				logger.info("UpgradeArticleTask running, upgrade count " + count);
			}
		}catch(Exception e){
			logger.info("UpgradeArticleTask exception", e);
		}
		logger.info("UpgradeArticleTask end, upgrade count " + count);
	}

}
