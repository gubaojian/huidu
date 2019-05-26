package net.java.mapyou.mybatis.mapper;

import java.util.List;
import net.java.mapyou.mybatis.domain.KVDO;
import net.java.mapyou.mybatis.query.KVQuery;

public interface KVDOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(KVDO record);

    KVDO selectByPrimaryKey(Long id);

    List<KVDO> selectAll();
    
    
    KVDO selectByKey(Long id);
    
    
    List<KVDO> selectByQuery(KVQuery query);

    int updateByPrimaryKey(KVDO record);
}