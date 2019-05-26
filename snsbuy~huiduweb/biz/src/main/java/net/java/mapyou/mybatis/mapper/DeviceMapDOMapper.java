package net.java.mapyou.mybatis.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import net.java.mapyou.mybatis.domain.DeviceMapDO;

public interface DeviceMapDOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DeviceMapDO record);

    DeviceMapDO selectByPrimaryKey(Long id);
    

    DeviceMapDO  selectByDeviceId(@Param("deviceId") Long deviceId, @Param("trackDeviceId") Long trackDeviceId);
    
    List<DeviceMapDO>  getListByDeviceId(Long deviceId);
    
    int  countListByDeviceId(Long deviceId);
   
    //List<DeviceMapDO> selectAll();

    int updateByPrimaryKey(DeviceMapDO record);
}