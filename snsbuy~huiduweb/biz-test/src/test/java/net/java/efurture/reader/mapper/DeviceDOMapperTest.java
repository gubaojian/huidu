package net.java.efurture.reader.mapper;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;

import net.java.mapyou.device.TokenUtils;
import net.java.mapyou.mybatis.domain.DeviceDO;
import net.java.mapyou.mybatis.enums.DeviceStatus;
import net.java.mapyou.mybatis.enums.OS;
import net.java.mapyou.mybatis.mapper.DeviceDOMapper;
import net.java.mapyou.mybatis.query.DeviceQuery;

public class DeviceDOMapperTest extends BaseMapperTest{

	@Resource
	DeviceDOMapper deviceDOMapper;
	
	@Test
	public void testInsert(){
		deviceDOMapper.deleteByPrimaryKey(1L);
		DeviceDO device = new DeviceDO();
		device.setId(1L);
		device.setTrackToken(TokenUtils.trackToken());
		device.setGmtCreate(new Date());
		device.setGmtModify(new Date());
		device.setLang("cn");
		device.setToken(TokenUtils.locationToken());
		device.setName("Gubaojian's PC");
		device.setOs(OS.IOS.getValue());
		device.setStatus(DeviceStatus.NORMAL.getValue());
	
        
		int effectCount =deviceDOMapper.insert(device);
		Assert.assertTrue(effectCount > 0);
		
		
		
	}
	
	@Test
	public void testUpdateByPk(){
		DeviceDO device = new DeviceDO();
		device.setId(1L);
		device.setName("UpdateGubaojian's PC");
		device.setOs(OS.ANDROID.getValue());
	
        
		int effectCount = deviceDOMapper.updateByPrimaryKey(device);
		Assert.assertTrue(effectCount > 0);
		
		
		
	}
	
	
	@Test
	public void testCountByQuery(){
		DeviceQuery query = new DeviceQuery();
		query.setGmtCreateStart(new Date(System.currentTimeMillis() - 1000*60));
		query.setGmtCreateEnd(new Date());
		int count = deviceDOMapper.countByQuery(query);	
	}
	
	@Test
	public void testSelectByTrackerDeviceId(){
		long deviceId = 2;
		List<DeviceDO>  deviceList = deviceDOMapper.selectByTrackerDeviceId(deviceId);
		Assert.assertTrue(deviceList != null);
	}
	
	@Test
	public void testSelectByTrackToken(){
		deviceDOMapper.selectByTrackToken("4444");
		
	}
	
	
	
	
}
