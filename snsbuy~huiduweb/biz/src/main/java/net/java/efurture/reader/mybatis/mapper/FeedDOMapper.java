package net.java.efurture.reader.mybatis.mapper;

import java.util.List;

import net.java.efurture.reader.mybatis.domain.FeedDO;
import net.java.efurture.reader.mybatis.query.FeedQuery;

public interface FeedDOMapper {
	
    

    int insert(FeedDO feed);

    FeedDO selectByPrimaryKey(Long id);

 
    
    
    List<FeedDO> selectByQuery(FeedQuery query);
    
    int countByQuery(FeedQuery query);

    int updateByPrimaryKey(FeedDO record);
    
    
    //int deleteByPrimaryKey(Long id);
    //List<FeedDO> selectAll();
}