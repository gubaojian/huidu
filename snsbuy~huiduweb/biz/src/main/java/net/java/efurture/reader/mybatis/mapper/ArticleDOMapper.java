package net.java.efurture.reader.mybatis.mapper;

import java.util.List;
import net.java.efurture.reader.mybatis.domain.ArticleDO;
import net.java.efurture.reader.mybatis.query.ArticleQuery;
import net.java.efurture.reader.mybatis.query.CategoryArticleQuery;

public interface ArticleDOMapper {
	
	
	/**
	 * 物理删除，逻辑删除请采用update
	 * */
    int deleteByPrimaryKey(Long id);

    int insert(ArticleDO record);

    ArticleDO selectByPrimaryKey(Long id);

    
    int countByQuery(ArticleQuery query);
    
    
    List<ArticleDO>  selectByQuery(ArticleQuery query);
    
    
    
    List<ArticleDO>  selectByCategoryArticleQuery(CategoryArticleQuery query);
    


    int updateByPrimaryKey(ArticleDO record);
}