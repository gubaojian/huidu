package net.java.efurture.judu.web.controller.service;

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
import net.java.mapyou.ao.DeviceAO;
import net.java.mapyou.ao.DeviceErrorCode;
import net.java.mapyou.ao.LocationAO;
import net.java.mapyou.ao.PushAO;
import net.java.mapyou.mybatis.domain.DeviceDO;


@Controller
@RequestMapping(ControllerName.SEND_LOCATE_NOTIFY)
public class SendLocateNotify {
	
	@Resource  
	DeviceAO deviceAO;

	@Resource 
	LocationAO locationAO;
	
	@Resource
	PushAO pushAO;
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody Result<Boolean> doGet(HttpServletRequest request, HttpServletResponse response){
		long deviceId = NumberUtils.toLong(request.getParameter("deviceId"), -1);
		String deviceToken = request.getParameter("deviceToken");
		long trackId =  NumberUtils.toLong(request.getParameter("trackId"), -1);
	    String message  =  request.getParameter("message"); //查岗位置
		Result<DeviceDO> checkResult = deviceAO.checkDevice(deviceId, deviceToken);
		if(!checkResult.isSuccess()){
			Result<Boolean> result = new DefaultResult<Boolean>(false);
			result.setResultCode(DeviceErrorCode.DEVICE_NOT_EXIST);
			return result;
		}
		
		Result<Boolean> isDeviceTrackResult = deviceAO.checkDeviceTrack(deviceId, trackId);
		if (!isDeviceTrackResult.isSuccess()) {
			Result<Boolean> result = new DefaultResult<Boolean>(false);
			result.setResultCode(DeviceErrorCode.DEVICE_NOT_TRACK);
			return result;
		}
		return pushAO.push(trackId, message, "mapta://mapta/reportLocation?fromDeviceId=" + deviceId);
	}
	

}
