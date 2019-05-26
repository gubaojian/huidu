package net.java.mapyou.mybatis.mapper;

import java.util.List;
import net.java.mapyou.mybatis.domain.KVDO;

public interface KVDOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(KVDO record);

    KVDO selectByPrimaryKey(Long id);

    List<KVDO> selectAll();

    int updateByPrimaryKey(KVDO record);
}