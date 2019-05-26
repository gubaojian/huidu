package net.java.efurture.judu.web.controller.service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;

import com.google.code.efurture.common.result.DefaultResult;
import com.google.code.efurture.common.result.Result;

import net.java.efurture.judu.web.constants.ControllerName;
import net.java.efurture.judu.web.util.MessageUtils;
import net.java.efurture.reader.biz.timer.task.util.JSONUtils;
import net.java.mapyou.ao.DeviceAO;
import net.java.mapyou.ao.DeviceErrorCode;
import net.java.mapyou.ao.LocationAO;
import net.java.mapyou.ao.PushAO;
import net.java.mapyou.mybatis.domain.DeviceDO;
import net.java.mapyou.mybatis.domain.LocationDO;
import net.java.mapyou.mybatis.enums.LocationType;

@Controller
@RequestMapping(ControllerName.REPORT_LOCATION)
public class ReportLocation {
	
	
	@Resource
	DeviceAO deviceAO;
	
	@Resource 
	LocationAO locationAO;
	

	@Resource
	LocaleResolver  localeResolver;
	
	@Resource
	PushAO pushAO;
	
	@RequestMapping(  method = RequestMethod.GET)
	public @ResponseBody Result<Boolean> doGet(HttpServletRequest request, HttpServletResponse response){
		long deviceId = NumberUtils.toLong(request.getParameter("deviceId"));
		long fromDeviceId = NumberUtils.toLong(request.getParameter("fromDeviceId"));
		String deviceToken = request.getParameter("deviceToken");
		Result<DeviceDO> checkResult = deviceAO.checkDevice(deviceId, deviceToken);
		if(!checkResult.isSuccess()){
			Result<Boolean> result = new DefaultResult<Boolean>(false);
			result.setResultCode(DeviceErrorCode.DEVICE_NOT_EXIST);
			return result;
		}
		String data = request.getParameter("data");
		LocationDO location = (LocationDO) JSONUtils.fromJson(new String(Base64.decodeBase64(data)), LocationDO.class);
		location.setDeviceId(deviceId);
		location.setType(LocationType.ME.getValue());
		Result<LocationDO> locationResult = locationAO.addDeviceLocation(location);
		if (locationResult.isSuccess()) {
			if(fromDeviceId > 0){
				Result<Boolean> isDeviceTrackResult = deviceAO.checkDeviceTrack(fromDeviceId, deviceId);
				if (isDeviceTrackResult.isSuccess()) {
					String message = MessageUtils.getMessage("reportDeviceLocationSuccess", request,  localeResolver);
					message = String.format(message,  checkResult.getResult().getName());
					pushAO.push(fromDeviceId, message, "mapta://mapta/home");	
				}
			}
			Result<Boolean> result = new DefaultResult<Boolean>(true);
			result.setResultCode(DeviceErrorCode.SUCCESS);
			return result;
		}
		//定位成功才会被提交，下面代码不会被执行
		
		Result<Boolean> result = new DefaultResult<Boolean>(false);
		result.setResultCode(locationResult.getResultCode());
		return result;
	}

}
