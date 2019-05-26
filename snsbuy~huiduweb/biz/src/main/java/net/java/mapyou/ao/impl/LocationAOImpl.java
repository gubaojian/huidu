package net.java.mapyou.ao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.google.code.efurture.common.ao.BaseAO;
import com.google.code.efurture.common.api.ApiConstants;
import com.google.code.efurture.common.result.Result;
import com.google.code.efurture.common.resultcode.BaseResultCode;

import net.java.efurture.reader.mybatis.domain.enums.KVTypeEnum;
import net.java.mapyou.ao.DeviceErrorCode;
import net.java.mapyou.ao.LocationAO;
import net.java.mapyou.ao.LocationAOErrorCode;
import net.java.mapyou.device.TokenUtils;
import net.java.mapyou.mybatis.domain.KVDO;
import net.java.mapyou.mybatis.domain.LocationDO;
import net.java.mapyou.mybatis.domain.vo.CountVO;
import net.java.mapyou.mybatis.enums.LocationStatus;
import net.java.mapyou.mybatis.enums.LocationType;
import net.java.mapyou.mybatis.mapper.DeviceDOMapper;
import net.java.mapyou.mybatis.mapper.KVDOMapper;
import net.java.mapyou.mybatis.mapper.LocationDOMapper;
import net.java.mapyou.mybatis.query.LocationQuery;

public class LocationAOImpl extends BaseAO implements LocationAO {

	

	@Resource 
	DeviceDOMapper deviceDOMapper;
	
	@Resource
	LocationDOMapper locationDOMapper;
	
	@Resource
	KVDOMapper kvDOMapper;
	
	
	
	@Override
	public Result<List<LocationDO>> findDeviceLocationByQuery(LocationQuery query) {
		Result<List<LocationDO>> result = createResult();
		try{
			List<LocationDO> locationList = locationDOMapper.selectByQuery(query);
			if(locationList == null){
				locationList = new ArrayList<LocationDO>(2);
			}
			result.setResult(locationList);
			result.getModels().put(ApiConstants.HAS_MORE_KEY, locationList.size() == query.getPageSize());
			result.setSuccess(true);
			return result;
		}catch(Exception e){
		     logger.error("LocationAOImpl findDeviceLocationByQuery error ", e);
		     result.setResultCode(BaseResultCode.SYSTEM_EXCEPTION);
			 result.setThrowable(e);
		     result.setSuccess(false);
		     return result;
		}
	}
	
	public Result<Integer> countLocationByQuery(LocationQuery query){
		Result<Integer> result = createResult();
		try{
			int count = locationDOMapper.countByQuery(query);
			result.setResult(count);
			result.setSuccess(true);
			return result;
		}catch(Exception e){
		     logger.error("LocationAOImpl countLocationByQuery error ", e);
		     result.setResultCode(BaseResultCode.SYSTEM_EXCEPTION);
			 result.setThrowable(e);
		     result.setSuccess(false);
		     return result;
		}
	}

	public Result<List<CountVO>> groupCountLocationByQuery(LocationQuery query){
		Result<List<CountVO>> result = createResult();
		try{
			List<CountVO> countList = locationDOMapper.groupCountByQuery(query);
			if(countList == null){
				countList = new ArrayList<CountVO>(2);
			}
			result.setResult(countList);
			result.setSuccess(true);
			return result;
		}catch(Exception e){
		     logger.error("LocationAOImpl groupCountByQuery error ", e);
		     result.setResultCode(BaseResultCode.SYSTEM_EXCEPTION);
			 result.setThrowable(e);
		     result.setSuccess(false);
		     return result;
		}
	}
	



	@Override
	public Result<LocationDO> addDeviceLocation(LocationDO location) {
		Result<LocationDO> result = createResult();
		try{
			if(location == null 
					|| LocationType.type(location.getType()) == null){
				result.setResultCode(DeviceErrorCode.ARGS_ERROR);
				result.setSuccess(false);
				return result;
			}
			if(LocationType.ME.getValue() == location.getType()){
				if(location.getLatitude() == null 
						|| location.getLongitude() == null 
						|| location.getDeviceId() == null){
					result.setResultCode(DeviceErrorCode.ARGS_ERROR);
					result.setSuccess(false);
					return result;
				}
			}
			
		    LocationQuery query = new LocationQuery();
		    query.setDeviceId(location.getDeviceId());
			query.setType(location.getType());
			query.setGmtCreateStart(new Date(System.currentTimeMillis() - 10l*60*1000));
			query.setGmtCreateEnd(new Date());
			int count = locationDOMapper.countByQuery(query);
			if(count > 20){
				result.setResultCode(LocationAOErrorCode.OVER_LIMIT);
				result.setSuccess(false);
				return result;
			}
			location.setLocationToken(TokenUtils.token());
			location.setStatus(LocationStatus.NORMAL.getValue());
			location.setGmtCreate(new Date());
			location.setGmtModify(new Date());
			int effectCount = locationDOMapper.insert(location);
			/**
			if(effectCount > 0 && LocationType.ME.getValue() == location.getType()){
				String value = location.getPoi();
				if(StringUtils.isEmpty(value)){
					value = String.format("%s  %s", location.getLatitude(), location.getLongitude());
				}
				KVDO  kvDO = kvDOMapper.selectByKey(location.getDeviceId());
				if(kvDO == null){
					 kvDO = new KVDO();
					 kvDO.setGmtCreate(new Date());
					 kvDO.setKey(location.getDeviceId());
					 kvDO.setType(KVTypeEnum.LAST_LOCATION.getValue());
				}
				kvDO.setGmtModify(new Date());
				kvDO.setValue(value);
				if(kvDO.getId() != null){
					kvDOMapper.updateByPrimaryKey(kvDO);
				}else {
					kvDOMapper.insert(kvDO);
				}
			}*/
			result.setResult(location);
			result.setResultCode(BaseResultCode.SUCCESS);
			result.setSuccess(effectCount > 0);
			return result;
		}catch(Exception e){
		     logger.error("LocationAOImpl addDeviceLocation error ", e);
		     result.setResultCode(BaseResultCode.SYSTEM_EXCEPTION);
			 result.setThrowable(e);
		     result.setSuccess(false);
		     return result;
		}
	}



	@Override
	public Result<LocationDO> getLocationById(long id, String token) {
		Result<LocationDO> result = createResult();
		try{
			if(StringUtils.isEmpty(token)){
				result.setResultCode(LocationAOErrorCode.ARGS_ERROR);
				result.setSuccess(false);
				return result;
			}
			
			LocationDO location = locationDOMapper.selectByPrimaryKey(id);
			if (location == null) {
				result.setResultCode(LocationAOErrorCode.NOT_EXIST);
				result.setSuccess(false);
				return result;
			}
			
			if(!token.equals(location.getLocationToken())){
				result.setResultCode(LocationAOErrorCode.TOKEN_ERROR);
				result.setSuccess(false);
				return result;
			}
			result.setResult(location);
			result.setResultCode(BaseResultCode.SUCCESS);
			result.setSuccess(true);
			return result;
		}catch(Exception e){
		     logger.error("LocationAOImpl getLocationById error ", e);
		     result.setResultCode(BaseResultCode.SYSTEM_EXCEPTION);
			 result.setThrowable(e);
		     result.setSuccess(false);
		     return result;
		}
		
	}



	@Override
	public Result<LocationDO> updateLocation(long id, String token, LocationDO locationDO) {
		Result<LocationDO> result = createResult();
		try{
			if(StringUtils.isEmpty(token)){
				result.setResultCode(LocationAOErrorCode.ARGS_ERROR);
				result.setSuccess(false);
				return result;
			}
			
			LocationDO dbLocation = locationDOMapper.selectByPrimaryKey(id);
			if (dbLocation == null) {
				result.setResultCode(LocationAOErrorCode.NOT_EXIST);
				result.setSuccess(false);
				return result;
			}
			
			if(!token.equals(dbLocation.getLocationToken())){
				result.setResultCode(LocationAOErrorCode.TOKEN_ERROR);
				result.setSuccess(false);
				return result;
			}

			locationDO.setId(id);
			int effectCount = locationDOMapper.updateByPrimaryKey(locationDO);
			result.setResult(dbLocation);
			result.setResultCode(BaseResultCode.SUCCESS);
			result.setSuccess(effectCount > 0);
			result.getModels().put("deviceId", dbLocation.getDeviceId());
			return result;
		}catch(Exception e){
		     logger.error("LocationAOImpl updateLocation error ", e);
		     result.setResultCode(BaseResultCode.SYSTEM_EXCEPTION);
			 result.setThrowable(e);
		     result.setSuccess(false);
		     return result;
		}
	}
	
	

	/**
	 * 获取定位记录，及令牌token
	 * */
	public Result<Boolean> deleteLocationByIdToken(long id, String token){
		Result<Boolean> result = createResult();
		try{
			if(StringUtils.isEmpty(token)){
				result.setResultCode(LocationAOErrorCode.ARGS_ERROR);
				result.setSuccess(false);
				return result;
			}
			
			LocationDO location = locationDOMapper.selectByPrimaryKey(id);
			if (location == null) {
				result.setResultCode(LocationAOErrorCode.NOT_EXIST);
				result.setSuccess(false);
				return result;
			}
			
			if(location.getStatus() != null 
					&& location.getStatus() != LocationStatus.NORMAL.getValue()){
				result.setResultCode(LocationAOErrorCode.STATUS_ERROR);
				result.setSuccess(false);
				return result;
			}
			
			if(!token.equals(location.getLocationToken())){
				result.setResultCode(LocationAOErrorCode.TOKEN_ERROR);
				result.setSuccess(false);
				return result;
			}
			locationDOMapper.deleteByPrimaryKey(id);
			result.setResult(true);
			result.setResultCode(BaseResultCode.SUCCESS);
			result.setSuccess(true);
			return result;
		}catch(Exception e){
		     logger.error("LocationAOImpl deleteLocationByIdToken error ", e);
		     result.setResultCode(BaseResultCode.SYSTEM_EXCEPTION);
			 result.setThrowable(e);
		     result.setSuccess(false);
		     return result;
		}
	}

}
