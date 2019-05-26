package net.java.efurture.judu.web.controller.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateUtils;
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
@RequestMapping(ControllerName.REMOTE_TRACK_LOCATION_LIST)
public class RemoteTrackLocationList {

	@Resource  
	DeviceAO deviceAO;

	@Resource 
	LocationAO locationAO;
	
	
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody Result<List<LocationDO>> doGet(HttpServletRequest request, HttpServletResponse response){
		long deviceId = NumberUtils.toLong(request.getParameter("deviceId"), -1);
		String deviceToken = request.getParameter("deviceToken");
		Result<DeviceDO> checkResult = deviceAO.checkDevice(deviceId, deviceToken);
		if(!checkResult.isSuccess()){
			Result<List<LocationDO>> result = new DefaultResult<List<LocationDO>>(false);
			result.setResultCode(DeviceErrorCode.DEVICE_NOT_EXIST);
			return result;
		}
		
		
		
		int pageNum = NumberUtils.toInt(request.getParameter("pageNum"), 1);
		LocationQuery query = new LocationQuery();
		query.setPageNum(pageNum);
		query.setPageSize(12);
		query.setDeviceId(deviceId);
		query.setType(LocationType.AD.getValue());
		Result<List<LocationDO>> locationResult = locationAO.findDeviceLocationByQuery(query);
		
		/** 检查是否应该是vip **/
		if(VipUtils.checkIsVip(request, checkResult.getResult()) || pageNum > 1){
			return locationResult;
		}
		
		int count = locationResult.getResult().size();
		if(VipUtils.showRemoteTrackGpsGuideVip(locationResult.getResult().size(), checkResult.getResult().getGmtCreate())){
			Result<List<LocationDO>> result = new DefaultResult<List<LocationDO>>(false);
			result.getModels().put("count", count);
			result.setResultCode(DeviceErrorCode.DEVICE_BUY_GUIDE);
			return result;
		}
		return locationResult;
	}
	
	
	
	
}
