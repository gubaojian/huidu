package net.java.efurture.judu.web.controller.service;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;

import com.google.code.efurture.common.result.Result;
import com.google.code.efurture.common.util.MapUtils;

import net.java.efurture.judu.web.constants.ControllerName;
import net.java.efurture.judu.web.util.MessageUtils;
import net.java.efurture.reader.utils.URIUtils;
import net.java.mapyou.ao.DeviceAO;
import net.java.mapyou.ao.PushAO;
import net.java.mapyou.mybatis.enums.LocationStatus;

@Controller
@RequestMapping(ControllerName.ADD_TRACK_DEVICE)
public class AddTrackDevice {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@Resource
	DeviceAO deviceAO;
	
	@Resource 
	PushAO pushAO;
	

	@Resource
	LocaleResolver  localeResolver;
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody Result<Boolean> doGet(HttpServletRequest request, HttpServletResponse response){
		long deviceId = NumberUtils.toLong(request.getParameter("deviceId"));
		String deviceToken = request.getParameter("deviceToken");
		String sourceTrackToken = request.getParameter("trackToken");
		Map<String, String> tokenMaps = URIUtils.parseQueryBase64Url(sourceTrackToken);
		String trackToken = tokenMaps.get("qr");
		Result<Boolean> result = deviceAO.addTrackDevice(deviceId, deviceToken, trackToken);
		if(!result.isSuccess()){
			logger.error("AddTrackDevice error token " + trackToken  + " deviceId " + deviceId
					 + sourceTrackToken 
					 + " token maps " + result.getResultCode().getMessage()
					 + "  " + MapUtils.toString(tokenMaps) 
					  + request.getRequestURI());
		}
		
		if(result.isSuccess()){
			Long pushDeviceId = (Long) result.getModels().get("pushDeviceId");
			String deviceName  =  (String)result.getModels().get("deviceName");
			if(pushDeviceId != null && !StringUtils.isEmpty(deviceName)){
				String message = MessageUtils.getMessage("addDeviceSuccess", request,  localeResolver);
				message = String.format(message, deviceName);
				pushAO.push(pushDeviceId, message, "mapta://mapta/trackDeviceList");	
			}
		}
		//AO中已检查设备权限
		return  result ;
	}

}
