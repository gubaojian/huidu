package net.java.efurture.reader.mybatis.mapper;

import java.util.List;
import java.util.Map;

import net.java.efurture.reader.mybatis.domain.ArticleCategoryMapperDO;

public interface ArticleCategoryMapperDOMapper {
	
    int deleteByPrimaryKey(Long id);

    int insert(ArticleCategoryMapperDO record);
    
    int batchInsert(List<ArticleCategoryMapperDO> articleCategoryMapperList);
    
    int deleteByArticleId(long articleId);
    
    List<Map<String,Object>> categoryArticleCount(List<Integer> categoryIds);

    ArticleCategoryMapperDO selectByPrimaryKey(Long id);

   // List<ArticleCategoryMapperDO> selectAll();

    int updateByPrimaryKey(ArticleCategoryMapperDO record);
}