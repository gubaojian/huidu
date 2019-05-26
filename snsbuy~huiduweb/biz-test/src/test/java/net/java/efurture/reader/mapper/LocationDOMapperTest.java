package net.java.efurture.reader.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;

import net.java.mapyou.device.TokenUtils;
import net.java.mapyou.mybatis.domain.LocationDO;
import net.java.mapyou.mybatis.domain.vo.CountVO;
import net.java.mapyou.mybatis.enums.LocationStatus;
import net.java.mapyou.mybatis.enums.LocationType;
import net.java.mapyou.mybatis.mapper.LocationDOMapper;
import net.java.mapyou.mybatis.query.LocationQuery;

public class LocationDOMapperTest extends BaseMapperTest {

	
	@Resource
	LocationDOMapper locationDOMapper;
	
	
	

	@Test
	public void testInsert(){
		locationDOMapper.deleteByPrimaryKey(1L);
		LocationDO locationDO = new LocationDO();
		locationDO.setDeviceId(2L);
		locationDO.setGmtCreate(new Date());
		locationDO.setGmtModify(new Date());
		locationDO.setId(1L);
		locationDO.setLocationToken(TokenUtils.locationToken());
		locationDO.setLatitude(33.45);
		locationDO.setLongitude(44.56);
		locationDO.setPoi("浙江省杭州市西湖区青枫墅园");
		locationDO.setStatus(LocationStatus.NORMAL.getValue());
		locationDO.setType(LocationType.ME.getValue());
		
		
	
		int effectCount = locationDOMapper.insert(locationDO);
		Assert.assertTrue(effectCount > 0);
	}
	
	
	@Test
	public void testUpdate(){
		LocationDO locationDO = new LocationDO();
		locationDO.setDeviceId(2L);
		locationDO.setGmtModify(new Date());
		locationDO.setId(1L);
		locationDO.setPoi("浙江省杭州市西湖区青枫墅园更新测试更新测试");
	
		int effectCount = locationDOMapper.updateByPrimaryKey(locationDO);
		Assert.assertTrue(effectCount > 0);
	}
	
	
	@Test
	public void testQuery(){
		LocationQuery  query = new LocationQuery();
		query.setDeviceId(2L);
		int count = locationDOMapper.countByQuery(query);
		Assert.assertTrue(count > 0);
		List<LocationDO>  list = locationDOMapper.selectByQuery(query);
		Assert.assertTrue(list.size() > 0);
	}
	
	
	@Test
	public void countByQuery(){
		LocationQuery query = new LocationQuery();
	    query.setDeviceId(2L);
		query.setType((byte)0);
		query.setGmtCreateStart(new Date(System.currentTimeMillis() - 60*1000));
		query.setGmtCreateEnd(new Date());
		int count = locationDOMapper.countByQuery(query);
		Assert.assertTrue(count >= 0);
	}
	
	@Test
	public void groupCountByQuery(){
		LocationQuery query = new LocationQuery();
		List<Long> deviceIds = new ArrayList<Long>();
		deviceIds.add(2L);
		deviceIds.add(16L);
		deviceIds.add(17L);
		deviceIds.add(26L);
		query.setType(LocationType.ME.getValue());
		List<CountVO> deviceCountMap = locationDOMapper.groupCountByQuery(query);
		Assert.assertTrue(deviceCountMap.size() > 0);
	}
	
	
	
}
