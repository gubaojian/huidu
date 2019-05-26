package net.java.efurture.judu.web.controller.service;

import java.net.URLEncoder;

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
import net.java.mapyou.ao.ShortUrlAO;
import net.java.mapyou.mybatis.domain.DeviceDO;
import net.java.mapyou.mybatis.domain.ShortUrlDO;

@Controller
@RequestMapping(ControllerName.CREATE_SHARE_TRACK_LINK)
public class CreateShareTrackLink {

	@Resource
	ShortUrlAO shortUrlAO;
	
	@Resource
	DeviceAO deviceAO;
	
	
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
		try {
			Result<Boolean> result = new DefaultResult<Boolean>(true);
			String appUrl = "mapta://qrcode/addDevice/?from=st&qr=" + checkResult.getResult().getTrackToken();
			String url = "http://ta.lanxijun.com/d.html?appUrl=" + URLEncoder.encode(appUrl, "UTF-8");
			Result<ShortUrlDO>  shortUrlResult =  shortUrlAO.shortUrl(url);
			if (shortUrlResult.isSuccess()) {
				String shortUrl = "http://ta.lanxijun.com/mm/" + shortUrlResult.getResult().getToken();
				result.getModels().put("shortUrl", shortUrl);
			}else{
				result.getModels().put("shortUrl", url);
			}
		    return result;
		} catch (Exception e) {
			Result<Boolean> result = new DefaultResult<Boolean>(false);
			result.setResultCode(DeviceErrorCode.DEVICE_NOT_EXIST);
			return result;
		}
	}
}
