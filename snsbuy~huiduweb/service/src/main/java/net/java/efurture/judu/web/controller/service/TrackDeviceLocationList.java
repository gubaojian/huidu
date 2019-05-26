package net.java.efurture.judu.web.controller.service;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.code.efurture.common.result.DefaultResult;
import com.google.code.efurture.common.result.Result;

import net.java.efurture.judu.web.constants.ControllerName;
import net.java.efurture.judu.web.controller.service.utils.VipUtils;
import net.java.mapyou.ao.DeviceAO;
import net.java.mapyou.ao.DeviceErrorCode;
import net.java.mapyou.ao.LocationAO;
import net.java.mapyou.mybatis.domain.DeviceDO;
import net.java.mapyou.mybatis.domain.LocationDO;
import net.java.mapyou.mybatis.enums.LocationStatus;
import net.java.mapyou.mybatis.enums.LocationType;
import net.java.mapyou.mybatis.query.LocationQuery;



@Controller
@RequestMapping(ControllerName.TRACK_DEVICE_LOCATION_LIST)
public class TrackDeviceLocationList {

	@Resource  
	DeviceAO deviceAO;

	@Resource 
	LocationAO locationAO;
	
	
	/**
	 * 超过50条，一律开始收费
	 * 低于10条一条或者7天以内免费
	 * 10-50条之间，超过30条收费，低于30天免费
	 * */
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody Result<List<LocationDO>> doGet(HttpServletRequest request, HttpServletResponse response){
		long deviceId = NumberUtils.toLong(request.getParameter("deviceId"), -1);
		String deviceToken = request.getParameter("deviceToken");
		long trackId =  NumberUtils.toLong(request.getParameter("trackId"), -1);
		Result<DeviceDO> checkResult = deviceAO.checkDevice(deviceId, deviceToken);
		if(!checkResult.isSuccess()){
			Result<List<LocationDO>> result = new DefaultResult<List<LocationDO>>(false);
			result.setResultCode(DeviceErrorCode.DEVICE_NOT_EXIST);
			return result;
		}
		
	
		Result<Boolean> isDeviceTrackResult = deviceAO.checkDeviceTrack(deviceId, trackId);
		if (!isDeviceTrackResult.isSuccess()) {
			Result<List<LocationDO>> result = new DefaultResult<List<LocationDO>>(false);
			result.setResultCode(DeviceErrorCode.DEVICE_NOT_TRACK);
			return result;
		}
		
		
		
		int pageNum = NumberUtils.toInt(request.getParameter("pageNum"), 1);
		LocationQuery query = new LocationQuery();
		query.setPageNum(pageNum);
		query.setPageSize(12);
		query.setDeviceId(trackId);
		query.setType(LocationType.ME.getValue());
		query.setStatus(LocationStatus.NORMAL.getValue());
		
		/**
		 * 检查是否开通vip，展示开通vip引导
		 * */
		if(!VipUtils.checkIsVip(request, checkResult.getResult())){
			Result<Integer> countResult = locationAO.countLocationByQuery(query);
			if(!countResult.isSuccess()){
				Result<List<LocationDO>> result = new DefaultResult<List<LocationDO>>(false);
				result.setResultCode(countResult.getResultCode());
				return result;
			}
			int count =  countResult.getResult();
			if(VipUtils.showDeviceTrackGpsGuideVip(count, checkResult.getResult().getGmtCreate())){
				Result<List<LocationDO>> result = new DefaultResult<List<LocationDO>>(false);
				result.getModels().put("count", count);
				result.setResultCode(DeviceErrorCode.DEVICE_BUY_GUIDE);
				return result;
			}
		}
		
		
		
		Result<List<LocationDO>> locationResult =  locationAO.findDeviceLocationByQuery(query);
		
		
		
		return locationResult;
	}
}
