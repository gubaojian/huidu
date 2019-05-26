package net.java.mapyou.mybatis.mapper;

import java.util.List;

import net.java.mapyou.mybatis.domain.LocationDO;
import net.java.mapyou.mybatis.domain.vo.CountVO;
import net.java.mapyou.mybatis.query.LocationQuery;

public interface LocationDOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LocationDO record);

    LocationDO selectByPrimaryKey(Long id);
    
    List<LocationDO>  selectByQuery(LocationQuery query);
    
   int countByQuery(LocationQuery query);
    

   /**
    * deviceId groupBy 
    * */
   List<CountVO> groupCountByQuery(LocationQuery query);
   
   
   // List<LocationDO> selectAll();

    int updateByPrimaryKey(LocationDO record);
}