package net.java.efurture.reader.mybatis.mapper;

import java.util.List;
import net.java.efurture.reader.mybatis.domain.ArticleDO;
import net.java.efurture.reader.mybatis.query.ArticleQuery;

public interface ArticleSourceMapper {
	
    int deleteByPrimaryKey(Long id);

    int insert(ArticleDO record);

    ArticleDO selectByPrimaryKey(Long id);

    
    int countByQuery(ArticleQuery query);
    
    
    List<ArticleDO>  selectByQuery(ArticleQuery query);


    int updateByPrimaryKey(ArticleDO record);
}