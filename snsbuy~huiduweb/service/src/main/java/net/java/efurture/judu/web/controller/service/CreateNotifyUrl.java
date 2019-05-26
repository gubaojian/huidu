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

import net.java.efurture.judu.web.config.util.UrlUtils;
import net.java.efurture.judu.web.constants.ControllerName;
import net.java.mapyou.ao.DeviceAO;
import net.java.mapyou.ao.DeviceErrorCode;
import net.java.mapyou.ao.LocationAO;
import net.java.mapyou.ao.ShortUrlAO;
import net.java.mapyou.mybatis.domain.DeviceDO;
import net.java.mapyou.mybatis.domain.ShortUrlDO;

@Controller
@RequestMapping(ControllerName.CREATE_NOTIFY_LINK)
public class CreateNotifyUrl {
	@Resource
	ShortUrlAO shortUrlAO;
	
	
	@Resource
	DeviceAO deviceAO;
	
	@Resource 
	LocationAO locationAO;
	
	@Resource
	LocaleResolver  localeResolver;

	
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody Result<String> doGet(HttpServletRequest request, HttpServletResponse response){
		long deviceId = NumberUtils.toLong(request.getParameter("deviceId"));
		String deviceToken = request.getParameter("deviceToken");
		String lang = "en";
		Result<String> result = new DefaultResult<String>(false);
		Result<DeviceDO> checkResult = deviceAO.checkDevice(deviceId, deviceToken);
		if(!checkResult.isSuccess()){
			result.setResultCode(DeviceErrorCode.DEVICE_NOT_EXIST);
			return result;
		}
		if(checkResult.getResult() != null){
			lang = checkResult.getResult().getLang();
			if(StringUtils.isEmpty(lang)){
				lang = localeResolver.resolveLocale(request).getLanguage();
			}
		}
		String url = UrlUtils.toNotifyUrl(deviceId, lang);
		Result<ShortUrlDO> shortUrlResult = shortUrlAO.shortUrl(url);
		if(shortUrlResult.isSuccess()){
			String shortUrl = UrlUtils.toShortUrl(shortUrlResult.getResult().getToken());	
			result.setResult(shortUrl);
			result.setResultCode(DeviceErrorCode.SUCCESS);
			result.setSuccess(true);
			return result;
		}
		result.setResultCode(shortUrlResult.getResultCode());
		return result;
	}
}
