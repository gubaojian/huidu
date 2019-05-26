package net.java.efurture.judu.web.controller.service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;

import com.google.code.efurture.common.result.DefaultResult;
import com.google.code.efurture.common.result.Result;

import net.java.efurture.judu.web.constants.ControllerName;
import net.java.mapyou.ao.DeviceAO;
import net.java.mapyou.ao.DeviceErrorCode;
import net.java.mapyou.ao.LocationAO;
import net.java.mapyou.ao.ShortUrlAO;
import net.java.mapyou.mybatis.domain.DeviceDO;
import net.java.mapyou.mybatis.domain.LocationDO;
import net.java.mapyou.mybatis.domain.ShortUrlDO;
import net.java.mapyou.mybatis.enums.LocationStatus;

@Controller
@RequestMapping(ControllerName.RE_SEND_AD_LINK)
public class ReSendAddLink {
	
	@Resource
	ShortUrlAO shortUrlAO;
	
	@Resource
	DeviceAO deviceAO;
	
	@Resource 
	LocationAO locationAO;
	
	@Resource
	LocaleResolver  localeResolver;

	
	
	
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody Result<Boolean> doGet(HttpServletRequest request, HttpServletResponse response){
		long deviceId = NumberUtils.toLong(request.getParameter("deviceId"));
		String deviceToken = request.getParameter("deviceToken");
		
		Result<DeviceDO> checkResult = deviceAO.checkDevice(deviceId, deviceToken);
		if(!checkResult.isSuccess()){
			Result<Boolean> result = new DefaultResult<Boolean>(false);
			result.setResultCode(DeviceErrorCode.DEVICE_NOT_EXIST);
			return result;
		}
	    long locationId = NumberUtils.toLong(request.getParameter("id"), 0);
	    String locationToken = request.getParameter("token");
	    Result<LocationDO>  deviceResult = locationAO.getLocationById(locationId, locationToken);
		if (!deviceResult.isSuccess()) {
			Result<Boolean> result = new DefaultResult<Boolean>(false);
			result.setResultCode(DeviceErrorCode.SYSTEM_EXCEPTION);
			return result;
		}
		String lang = "en";
		if(checkResult.getResult() != null){
			lang = checkResult.getResult().getLang();
			if(StringUtils.isEmpty(lang)){
				lang = localeResolver.resolveLocale(request).getLanguage();
			}
		}
		String url = "http://ta.lanxijun.com/map.htm?id=" + deviceResult.getResult().getId() 
				+ "&token=" + deviceResult.getResult().getLocationToken()  +"&lang=" + lang;;
		Result<ShortUrlDO>  shortUrlResult =  shortUrlAO.shortUrl(url);
		Result<Boolean> result = new DefaultResult<Boolean>(true);
		if (shortUrlResult.isSuccess()) {
			String shortUrl = "http://ta.lanxijun.com/mm/" + shortUrlResult.getResult().getToken();
			result.getModels().put("shortUrl", shortUrl);
			
			if(deviceResult.getResult().getStatus() != null  
					&& deviceResult.getResult().getStatus() != LocationStatus.AD_SUCCESS.getValue()
					&& (deviceResult.getResult().getStatus() != LocationStatus.NORMAL.getValue())){
				LocationDO updateLocation = new LocationDO();
				updateLocation.setStatus(LocationStatus.NORMAL.getValue());
				locationAO.updateLocation(locationId, locationToken, updateLocation);
			}
		}else{
			result.getModels().put("shortUrl", url);
		}
		return result;
	}
	
	

}
