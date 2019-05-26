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

import com.google.code.efurture.common.result.Result;

import net.java.efurture.judu.web.constants.ControllerName;
import net.java.mapyou.ao.DeviceAO;
import net.java.mapyou.mybatis.domain.DeviceDO;



@Controller
@RequestMapping(ControllerName.TRACK_DEVICE_LIST)
public class TrackDeviceList {

	@Resource
	DeviceAO deviceAO;
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody Result<List<DeviceDO>> doGet(HttpServletRequest request, HttpServletResponse response){
		long deviceId = NumberUtils.toLong(request.getParameter("deviceId"));
		String deviceToken = request.getParameter("deviceToken");
		//AO中已检查设备权限
		return deviceAO.getTrackDeviceList(deviceId, deviceToken);
	}
}
