package net.java.efurture.judu.web.controller.service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.code.efurture.common.result.Result;

import net.java.efurture.judu.web.constants.ControllerName;
import net.java.efurture.reader.biz.timer.task.util.JSONUtils;
import net.java.efurture.reader.utils.FilterUtils;
import net.java.mapyou.ao.DeviceAO;
import net.java.mapyou.mybatis.domain.DeviceDO;

/**
 * 
 * http://127.0.0.1:8080/service/articleList.json
 * */
@Controller
@RequestMapping(ControllerName.DEVICE_REGISTER)
public class DeviceRegister {

	@Resource
	DeviceAO deviceAO;
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody Result<DeviceDO> doGet(HttpServletRequest request, HttpServletResponse response){
		String dt = request.getParameter("dt");
		String json = new String(Base64.decodeBase64(dt));
		DeviceDO device = (DeviceDO) JSONUtils.fromJson(json, DeviceDO.class);
		if(!StringUtils.isEmpty(device.getName())){
			 device.setName(FilterUtils.filterUtf8(device.getName()));
		}
		return deviceAO.registerDevice(device);
	}
}
