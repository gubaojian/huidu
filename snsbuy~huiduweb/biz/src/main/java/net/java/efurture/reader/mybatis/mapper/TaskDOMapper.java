package net.java.efurture.reader.mybatis.mapper;

import java.util.List;
import net.java.efurture.reader.mybatis.domain.TaskDO;
import net.java.efurture.reader.mybatis.query.TaskQuery;

public interface TaskDOMapper {
	
    int deleteByPrimaryKey(Long id);

    int insert(TaskDO record);

    TaskDO selectByPrimaryKey(Long id);

    List<TaskDO> selectAll();
    
    
    List<TaskDO> selectByQuery(TaskQuery query);
    
    int countByQuery(TaskQuery query);
    

    int updateByPrimaryKey(TaskDO task);
}