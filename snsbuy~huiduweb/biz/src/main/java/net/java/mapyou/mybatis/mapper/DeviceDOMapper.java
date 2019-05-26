package net.java.mapyou.mybatis.mapper;

import java.util.List;

import net.java.mapyou.mybatis.domain.DeviceDO;
import net.java.mapyou.mybatis.query.DeviceQuery;

public interface DeviceDOMapper {
	
    int insert(DeviceDO record);

    DeviceDO selectByPrimaryKey(Long id);
    

    DeviceDO selectByTrackToken(String trackToken);
    
    int countByQuery(DeviceQuery query);
    
    int updateByPrimaryKey(DeviceDO record);
    
    int deleteByPrimaryKey(Long id);
    
    List<DeviceDO>  selectByTrackerDeviceId(Long deviceId);

    
}