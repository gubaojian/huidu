package net.java.mapyou.ao;

import java.util.List;

import com.google.code.efurture.common.result.Result;

import net.java.mapyou.mybatis.domain.LocationDO;
import net.java.mapyou.mybatis.domain.vo.CountVO;
import net.java.mapyou.mybatis.query.LocationQuery;

public interface LocationAO {
	
	
	/**
	 *  记录设备信息，
	 *  @param location  最快10分钟内20次, 外界确保deviceid的存在性
	 * */
	public Result<LocationDO> addDeviceLocation(LocationDO location);
	

	/**
	 * 查询设备的地理位置信息信息
	 * */
	public Result<List<LocationDO>> findDeviceLocationByQuery(LocationQuery query);
	
	
	public Result<Integer> countLocationByQuery(LocationQuery query);
	
	
	public Result<List<CountVO>> groupCountLocationByQuery(LocationQuery query);
	
	
	/**
	 * 获取定位记录，及令牌token
	 * */
	public Result<LocationDO> getLocationById(long id, String token);
	

	/**
	 * 获取定位记录，及令牌token
	 * */
	public Result<LocationDO> updateLocation(long id, String token, LocationDO locationDO);
	
	
	

	/**
	 * 获取定位记录，及令牌token
	 * */
	public Result<Boolean> deleteLocationByIdToken(long id, String token);

}
