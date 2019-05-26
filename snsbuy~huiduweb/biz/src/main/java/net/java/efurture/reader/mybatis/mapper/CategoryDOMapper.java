package net.java.efurture.reader.mybatis.mapper;

import java.util.List;

import net.java.efurture.reader.mybatis.domain.CategoryDO;
import net.java.efurture.reader.mybatis.query.CategoryQuery;

public interface CategoryDOMapper {
	
    int deleteByPrimaryKey(Long id);

    int insert(CategoryDO record);

    CategoryDO selectByPrimaryKey(Long id);
    
    int countByQuery(CategoryQuery query);
    
    List<CategoryDO> selectByQuery(CategoryQuery query);


    int updateByPrimaryKey(CategoryDO record);
       
    
    List<CategoryDO> selectArticleCategory(long articleId);
    
    
    int updateCategorySort();
    
}