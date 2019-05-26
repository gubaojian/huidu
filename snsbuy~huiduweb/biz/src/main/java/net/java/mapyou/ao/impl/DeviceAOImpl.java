package net.java.mapyou.ao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.google.code.efurture.common.ao.BaseAO;
import com.google.code.efurture.common.result.Result;
import com.google.code.efurture.common.resultcode.BaseResultCode;

import net.java.mapyou.ao.DeviceAO;
import net.java.mapyou.ao.DeviceErrorCode;
import net.java.mapyou.device.TokenUtils;
import net.java.mapyou.mybatis.domain.DeviceDO;
import net.java.mapyou.mybatis.domain.DeviceMapDO;
import net.java.mapyou.mybatis.enums.DeviceStatus;
import net.java.mapyou.mybatis.enums.OS;
import net.java.mapyou.mybatis.mapper.DeviceDOMapper;
import net.java.mapyou.mybatis.mapper.DeviceMapDOMapper;
import net.java.mapyou.mybatis.query.DeviceQuery;

public class DeviceAOImpl  extends BaseAO implements DeviceAO{

	@Resource 
	DeviceDOMapper deviceDOMapper;
	
	@Resource 
	DeviceMapDOMapper deviceMapDOMapper;
	
	
	@Override
	public Result<DeviceDO> registerDevice(DeviceDO device) {
		Result<DeviceDO> result = createResult();
		try{
			//基本参数检查
			if (device == null 
					|| StringUtils.isEmpty(device.getLang()) 
					|| StringUtils.isEmpty(device.getName())
					|| OS.type(device.getOs()) == null) {
				result.setResultCode(DeviceErrorCode.ARGS_ERROR);
				result.setSuccess(false);
				return result;
			}
			DeviceDO  dbDevice =   deviceDOMapper.selectByPrimaryKey(device.getId());
			if (dbDevice == null 
					|| DeviceStatus.NORMAL.getValue() != dbDevice.getStatus()
					|| !StringUtils.equals(dbDevice.getToken(), device.getToken())
					|| !StringUtils.equals(dbDevice.getTrackToken(), device.getTrackToken())) {
				//检测1分钟的频率
				DeviceQuery query = new DeviceQuery();
				query.setGmtCreateStart(new Date(System.currentTimeMillis() - 1000*60));
				query.setGmtCreateEnd(new Date());
				int count = deviceDOMapper.countByQuery(query);
				if (count >= 100) {
					logger.error("DeviceAOImpl registerDevice Over Limit");
					result.setResultCode(DeviceErrorCode.UPDATE_LIMIT);
					return result;
				}
				dbDevice = new DeviceDO();
				dbDevice.setLang(device.getLang());
				dbDevice.setToken(TokenUtils.locationToken());
				dbDevice.setGmtCreate(new Date());
				dbDevice.setGmtModify(new Date());
				dbDevice.setName(device.getName());
				dbDevice.setOs(device.getOs());
				dbDevice.setPushToken(device.getPushToken());
				dbDevice.setStatus(DeviceStatus.NORMAL.getValue());
				dbDevice.setTrackToken(TokenUtils.trackToken());
				long id = deviceDOMapper.insert(dbDevice);
				result.setResult(dbDevice);
				result.setResultCode(BaseResultCode.SUCCESS);
				result.setSuccess(id > 0);
				return result;
			}
			if(!StringUtils.isEmpty(device.getPushToken()) && !StringUtils.equals(dbDevice.getPushToken(), device.getPushToken())){
				DeviceDO update = new DeviceDO();
				update.setId(dbDevice.getId());
				update.setPushToken(device.getPushToken());
				deviceDOMapper.updateByPrimaryKey(update);
			}
			result.setResult(null);
			result.setResultCode(BaseResultCode.SUCCESS);
			result.setSuccess(true);
			return result;
		}catch(Exception e){
		     logger.error("DeviceAOImpl registerDevice error ", e);
		     result.setResultCode(BaseResultCode.SYSTEM_EXCEPTION);
			 result.setThrowable(e);
		     result.setSuccess(false);
		     return result;
		}
	}

	@Override
	public Result<Boolean> reportPushToken(long id, String locationToken, String pushToken) {
		Result<Boolean> result = createResult();
		try{
			//基本参数检查
			if (StringUtils.isEmpty(locationToken) 
					|| StringUtils.isEmpty(pushToken)) {
				result.setResultCode(DeviceErrorCode.ARGS_ERROR);
				result.setSuccess(false);
				return result;
			}
			pushToken = pushToken.trim();
			DeviceDO  dbDevice =   deviceDOMapper.selectByPrimaryKey(id);
			if (!locationToken.equals(dbDevice.getToken()) 
					|| DeviceStatus.NORMAL.getValue() != dbDevice.getStatus()) {
				result.setResultCode(DeviceErrorCode.DEVICE_NOT_EXIST);
				result.setSuccess(false);
				return result;
			}
			if (pushToken.equals(dbDevice.getPushToken())) {
				result.setResultCode(DeviceErrorCode.SUCCESS);
				result.setResult(true);
				result.setSuccess(true);
				return result;
			}
			
			DeviceDO update = new DeviceDO();
			update.setId(dbDevice.getId());
			update.setPushToken(pushToken);
			deviceDOMapper.updateByPrimaryKey(update);
			result.setResult(true);
			result.setResultCode(BaseResultCode.SUCCESS);
			result.setSuccess(true);
			return result;
		}catch(Exception e){
		     logger.error("DeviceAOImpl reportPushToken error ", e);
		     result.setResultCode(BaseResultCode.SYSTEM_EXCEPTION);
			 result.setThrowable(e);
		     result.setSuccess(false);
		     return result;
		}
	}

	@Override
	public Result<List<DeviceDO>> getTrackDeviceList(long deviceId, String deviceToken) {
		Result<List<DeviceDO>> result = createResult();
		try{
			//基本参数检查
			if (StringUtils.isEmpty(deviceToken)) {
				result.setResultCode(DeviceErrorCode.ARGS_ERROR);
				result.setSuccess(false);
				return result;
			}
			DeviceDO  dbDevice =   deviceDOMapper.selectByPrimaryKey(deviceId);
			if (!deviceToken.equals(dbDevice.getToken())
					|| DeviceStatus.NORMAL.getValue() != dbDevice.getStatus()) {
				result.setResultCode(DeviceErrorCode.DEVICE_NOT_EXIST);
				result.setSuccess(false);
				return result;
			}
			
			
			List<DeviceDO>  deviceList = deviceDOMapper.selectByTrackerDeviceId(deviceId);
			if(deviceList == null){
				deviceList = new ArrayList<DeviceDO>(2);
			}
			for(DeviceDO device : deviceList){ //敏感信息去掉
				device.setToken(null);
				device.setTrackToken(null);
				device.setPushToken(null);
			}
			
			result.setResult(deviceList);
			result.setResultCode(BaseResultCode.SUCCESS);
			result.setSuccess(true);
			return result;
		}catch(Exception e){
		     logger.error("DeviceAOImpl getTrackDeviceList error ", e);
		     result.setResultCode(BaseResultCode.SYSTEM_EXCEPTION);
			 result.setThrowable(e);
		     result.setSuccess(false);
		     return result;
		}
	}

	@Override
	public Result<Boolean> addTrackDevice(long id, String locationToken, String trackToken) {
		Result<Boolean> result = createResult();
		try{
			//基本参数检查
			if (StringUtils.isEmpty(locationToken) 
					|| StringUtils.isEmpty(trackToken)) {
				result.setResultCode(DeviceErrorCode.ARGS_ERROR);
				result.setSuccess(false);
				return result;
			}
			trackToken = trackToken.trim();
			DeviceDO  dbDevice = deviceDOMapper.selectByPrimaryKey(id);
			if (!locationToken.equals(dbDevice.getToken())
					|| DeviceStatus.NORMAL.getValue() != dbDevice.getStatus()) {
				result.setResultCode(DeviceErrorCode.DEVICE_NOT_EXIST);
				result.setSuccess(false);
				return result;
			}
			
			DeviceDO trackDeviceDO = deviceDOMapper.selectByTrackToken(trackToken);
			if(trackDeviceDO == null 
					|| DeviceStatus.NORMAL.getValue() != trackDeviceDO.getStatus()){
				result.setResultCode(DeviceErrorCode.DEVICE_NOT_EXIST);
				result.setSuccess(false);
				return result;
			}
			
			if(trackDeviceDO.getId() == id){
				result.setResultCode(DeviceErrorCode.SAME_DEVICE);
				result.setSuccess(false);
				return result;
			}
			
			int count = deviceMapDOMapper.countListByDeviceId(dbDevice.getId());
			if(count >= DeviceErrorCode.LIMIT_COUNT){  //最多添加10个设备
				result.setResultCode(DeviceErrorCode.DEVICE_OVER_LIMIT);
				result.setSuccess(false);
				return result;
			}
			
			DeviceMapDO deviceMapDO  = deviceMapDOMapper.selectByDeviceId(dbDevice.getId(), trackDeviceDO.getId());
			if(deviceMapDO != null){
				//被动关注
				checkTrackedDevice(trackDeviceDO, dbDevice);
				result.setResult(true);
				result.setResultCode(DeviceErrorCode.DEVICE_TRACKED);
				result.setSuccess(true);
				return result;
			}
			
			//添加双向的关注
			
			//主动关注
			DeviceMapDO oneDeviceMapDO = new DeviceMapDO();
			oneDeviceMapDO.setStatus(DeviceStatus.NORMAL.getValue());
			oneDeviceMapDO.setDeviceId(dbDevice.getId());
			oneDeviceMapDO.setTrackDeviceId(trackDeviceDO.getId());
			oneDeviceMapDO.setGmtCreate(new Date());
			oneDeviceMapDO.setGmtModified(new Date());
			deviceMapDOMapper.insert(oneDeviceMapDO);
			//被动关注
			checkTrackedDevice(trackDeviceDO, dbDevice);
			
			result.getModels().put("pushDeviceId", trackDeviceDO.getId());
			result.getModels().put("deviceName", dbDevice.getName());
			result.setResult(true);
			result.setResultCode(BaseResultCode.SUCCESS);
			result.setSuccess(true);
			return result;
		}catch(Exception e){
		     logger.error("DeviceAOImpl addTrackDevice error ", e);
		     result.setResultCode(BaseResultCode.SYSTEM_EXCEPTION);
			 result.setThrowable(e);
		     result.setSuccess(false);
		     return result;
		}
	}
	
	//被动关注
	private void checkTrackedDevice(DeviceDO trackDeviceDO, DeviceDO  dbDevice){
		DeviceMapDO dbTwoDeviceMapDO  = deviceMapDOMapper.selectByDeviceId(trackDeviceDO.getId(), dbDevice.getId());
		if(dbTwoDeviceMapDO != null){
			if(dbTwoDeviceMapDO.getStatus() != DeviceStatus.NORMAL.getValue()){
				  DeviceMapDO update = new DeviceMapDO();
				  update.setId(dbTwoDeviceMapDO.getId());
				  update.setStatus(DeviceStatus.NORMAL.getValue());
			      deviceMapDOMapper.updateByPrimaryKey(update);
			}
		}else{
			DeviceMapDO twoDeviceMapDO = new DeviceMapDO();
			twoDeviceMapDO.setStatus(DeviceStatus.NORMAL.getValue());
			twoDeviceMapDO.setDeviceId(trackDeviceDO.getId());
			twoDeviceMapDO.setTrackDeviceId(dbDevice.getId());
			twoDeviceMapDO.setGmtCreate(new Date());
			twoDeviceMapDO.setGmtModified(new Date());
			deviceMapDOMapper.insert(twoDeviceMapDO);
		}
		
	}
	
	
	public Result<Boolean> deleteTrackDevice(long deviceId, long trackId){
		Result<Boolean> result = createResult();
		try{
			//基本参数检查
			if (deviceId <= 0 || trackId <= 0) {
				result.setResultCode(DeviceErrorCode.ARGS_ERROR);
				result.setSuccess(false);
				return result;
			}
			DeviceMapDO deviceMapDO  = deviceMapDOMapper.selectByDeviceId(deviceId, trackId);
			if(deviceMapDO == null){
				result.setResultCode(DeviceErrorCode.DEVICE_NOT_TRACK);
				result.setResult(true);
				result.setSuccess(true);
				return result;
			}
			deviceMapDOMapper.deleteByPrimaryKey(deviceMapDO.getId());
			result.setResult(true);
			result.setResultCode(BaseResultCode.SUCCESS);
			result.setSuccess(true);
			return result;
		}catch(Exception e){
		     logger.error("DeviceAOImpl deleteByPrimaryKey error ", e);
		     result.setResultCode(BaseResultCode.SYSTEM_EXCEPTION);
			 result.setThrowable(e);
		     result.setSuccess(false);
		     return result;
		}
	}
	

	/**
	 * 
	 * FIXME 性能优化
	 * */
	@Override
	public Result<DeviceDO> checkDevice(long deviceId, String deviceToken) {
		Result<DeviceDO> result = createResult();
		try{
			//基本参数检查
			if (StringUtils.isEmpty(deviceToken)) {
				result.setResultCode(DeviceErrorCode.ARGS_ERROR);
				result.setSuccess(false);
				return result;
			}
			deviceToken = deviceToken.trim();
			DeviceDO  dbDevice = deviceDOMapper.selectByPrimaryKey(deviceId);
			if (!deviceToken.equals(dbDevice.getToken())
					|| DeviceStatus.NORMAL.getValue() != dbDevice.getStatus()) {
				result.setResultCode(DeviceErrorCode.DEVICE_NOT_EXIST);
				result.setSuccess(false);
				return result;
			}
			result.setResult(dbDevice);
			result.setResultCode(BaseResultCode.SUCCESS);
			result.setSuccess(true);
			return result;
		}catch(Exception e){
		     logger.error("DeviceAOImpl checkDevice error ", e);
		     result.setResultCode(BaseResultCode.SYSTEM_EXCEPTION);
			 result.setThrowable(e);
		     result.setSuccess(false);
		     return result;
		}
	}
	

	/**
	 * 检测设备是否跟踪
	 * @param deviceId 设备的id
	 * @param trackId  关注设备的id
	 * */
	public Result<Boolean> checkDeviceTrack(long deviceId, long trackId){
		Result<Boolean> result = createResult();
		try{
			//基本参数检查
			if (deviceId < 0 || trackId < 0) {
				result.setResultCode(DeviceErrorCode.ARGS_ERROR);
				result.setSuccess(false);
				return result;
			}
			DeviceMapDO deviceMapDO = deviceMapDOMapper.selectByDeviceId(deviceId, trackId);
		    if (deviceMapDO == null) {
				result.setResultCode(DeviceErrorCode.DEVICE_NOT_TRACK);
				result.setSuccess(false);
				return result;
			}
			result.setResult(true);
			result.setResultCode(BaseResultCode.SUCCESS);
			result.setSuccess(true);
			return result;
		}catch(Exception e){
		     logger.error("DeviceAOImpl checkDeviceTrack error ", e);
		     result.setResultCode(BaseResultCode.SYSTEM_EXCEPTION);
			 result.setThrowable(e);
		     result.setSuccess(false);
		     return result;
		}
	}

}
