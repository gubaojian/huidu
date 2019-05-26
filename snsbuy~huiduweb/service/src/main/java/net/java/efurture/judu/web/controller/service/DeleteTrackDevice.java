package net.java.efurture.judu.web.controller.service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.code.efurture.common.result.DefaultResult;
import com.google.code.efurture.common.result.Result;

import net.java.efurture.judu.web.constants.ControllerName;
import net.java.efurture.reader.utils.URIUtils;
import net.java.mapyou.ao.DeviceAO;
import net.java.mapyou.ao.DeviceErrorCode;
import net.java.mapyou.mybatis.domain.DeviceDO;

@Controller
@RequestMapping(ControllerName.DELETE_TRACK_DEVICE)
public class DeleteTrackDevice {

	
protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@Resource
	DeviceAO deviceAO;
	
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody Result<Boolean> doGet(HttpServletRequest request, HttpServletResponse response){
		long deviceId = NumberUtils.toLong(request.getParameter("deviceId"));
		String deviceToken = request.getParameter("deviceToken");
		long trackId = NumberUtils.toLong(request.getParameter("trackId"));
		Result<DeviceDO> checkResult = deviceAO.checkDevice(deviceId, deviceToken);
		if(!checkResult.isSuccess()){
			Result<Boolean> result = new DefaultResult<Boolean>(false);
			result.setResultCode(DeviceErrorCode.DEVICE_NOT_EXIST);
			return result;
		}
		Result<Boolean> result = deviceAO.deleteTrackDevice(deviceId, trackId);
		return  result ;
	}
}
