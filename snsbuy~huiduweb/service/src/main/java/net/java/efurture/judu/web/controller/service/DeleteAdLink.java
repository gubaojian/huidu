package net.java.efurture.judu.web.controller.service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;

import com.google.code.efurture.common.result.DefaultResult;
import com.google.code.efurture.common.result.Result;

import net.java.efurture.judu.web.config.util.UrlUtils;
import net.java.efurture.judu.web.constants.ControllerName;
import net.java.mapyou.ao.DeviceAO;
import net.java.mapyou.ao.DeviceErrorCode;
import net.java.mapyou.ao.LocationAO;
import net.java.mapyou.ao.ShortUrlAO;
import net.java.mapyou.mybatis.domain.DeviceDO;
import net.java.mapyou.mybatis.domain.LocationDO;
import net.java.mapyou.mybatis.domain.ShortUrlDO;
import net.java.mapyou.mybatis.enums.LocationStatus;
import net.java.mapyou.mybatis.enums.LocationType;

@Controller
@RequestMapping(ControllerName.DELETE_AD_LINK)
public class DeleteAdLink {

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
		long id = NumberUtils.toLong(request.getParameter("id"), -1);
		String locationToken = request.getParameter("token");
		String lang = "en";
		Result<DeviceDO> checkResult = deviceAO.checkDevice(deviceId, deviceToken);
		if(!checkResult.isSuccess()){
			Result<Boolean> result = new DefaultResult<Boolean>(false);
			result.setResultCode(DeviceErrorCode.DEVICE_NOT_EXIST);
			return result;
		}
		if(checkResult.getResult() != null){
			lang = checkResult.getResult().getLang();
			if(StringUtils.isEmpty(lang)){
				lang = localeResolver.resolveLocale(request).getLanguage();
			}
		}
		
		Result<Boolean> deviceResult = locationAO.deleteLocationByIdToken(id, locationToken);
		
		shortUrlAO.deleteUrl(UrlUtils.toAdLinkUrl(id, locationToken, lang));
		
		return deviceResult;
	}
}
