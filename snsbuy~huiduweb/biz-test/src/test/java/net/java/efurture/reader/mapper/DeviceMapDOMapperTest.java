package net.java.efurture.reader.mapper;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;

import net.java.mapyou.mybatis.domain.DeviceMapDO;
import net.java.mapyou.mybatis.enums.DeviceMapStatus;
import net.java.mapyou.mybatis.enums.NotifyType;
import net.java.mapyou.mybatis.mapper.DeviceMapDOMapper;

public class DeviceMapDOMapperTest extends BaseMapperTest{

	@Resource
	DeviceMapDOMapper deviceMapDOMapper;
	
	@Test
	public void testInsert(){
		deviceMapDOMapper.deleteByPrimaryKey(1L);
		DeviceMapDO deviceMap = new DeviceMapDO();
		deviceMap.setId(1L);
		deviceMap.setDeviceId(1L);
		deviceMap.setTrackDeviceId(2L);
		deviceMap.setGmtCreate(new Date());
		deviceMap.setGmtModified(new Date());
		deviceMap.setStatus(DeviceMapStatus.NORMAL.getValue());
		int effectCount = deviceMapDOMapper.insert(deviceMap);
		Assert.assertTrue(effectCount > 0);
	}
	
	
	@Test
	public void testUpdae(){
		DeviceMapDO deviceMap = new DeviceMapDO();
		deviceMap.setId(1L);
		deviceMap.setDeviceId(3L);
		deviceMap.setTrackDeviceId(4L);
		int effectCount = deviceMapDOMapper.updateByPrimaryKey(deviceMap);
		Assert.assertTrue(effectCount > 0);
	}
	@Test
	public void testSelectByDeviceId(){
		DeviceMapDO deviceMap  = deviceMapDOMapper.selectByDeviceId(3L, 4L);
		Assert.assertNotNull(deviceMap);
	}
	
	@Test
	public void testSelectAllByDeviceId(){
		List<DeviceMapDO> deviceMap  = deviceMapDOMapper.getListByDeviceId(3L);
		Assert.assertNotNull(deviceMap);
	}
	
	   
}
