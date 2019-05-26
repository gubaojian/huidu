package net.java.efurture.reader.clean.article;

import org.apache.commons.lang.StringUtils;

import net.java.efurture.reader.clean.author.AuthorCleaner;
import net.java.efurture.reader.clean.content.ContentCleaner;
import net.java.efurture.reader.clean.title.TitleCleaner;
import net.java.efurture.reader.mybatis.domain.ArticleDO;
import net.java.efurture.reader.mybatis.domain.enums.ArticleStatusEnum;

public class ArticleCleaner {
	
	
	
	/**
	 * 标准化文章内容、标题、简要描述的显示。并根据标准化结果，确定文章是否显示
	 * */
	public static final void clean(ArticleDO article){
	    article.setTitle(TitleCleaner.clean(article.getTitle()));
	    article.setShortDesc(TitleCleaner.clean(article.getShortDesc()));		
	    article.setContent(ContentCleaner.clean(article.getContent(), article.getUrl()));
	    article.setAuthor(AuthorCleaner.clean(article.getAuthor())); //清理作者信息
		if(StringUtils.isBlank(article.getContent()) 
				|| StringUtils.isBlank(article.getShortDesc()) 
				|| StringUtils.isBlank(article.getTitle())
				|| StringUtils.isBlank(article.getAuthor())
				|| article.getPublishDate() == null
				|| article.getContent().length() < 256){ //文章内容很少，直接标记为删除
			article.setSort(-1);
			article.setStatus(ArticleStatusEnum.DELETE.getValue());
		}else{
			article.setStatus(ArticleStatusEnum.NORMAL.getValue());
		}
		
		
		
		//控制文章长度在4MB以内
		if(article.getContent() != null && article.getContent().length() > 1024*1024*8){
			article.setContent(article.getContent().substring(0, 1024*1024*8));
		}
		
	}
	

}
