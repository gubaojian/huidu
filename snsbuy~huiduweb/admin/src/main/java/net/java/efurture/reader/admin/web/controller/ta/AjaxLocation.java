package net.java.efurture.reader.admin.web.controller.ta;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.LocaleResolver;

import com.google.code.efurture.common.result.Result;

import net.java.mapyou.ao.LocationAO;
import net.java.mapyou.ao.PushAO;
import net.java.mapyou.mybatis.domain.LocationDO;
import net.java.mapyou.mybatis.enums.LocationStatus;
import net.java.efurture.judu.web.util.MessageUtils;
import net.java.efurture.reader.admin.web.constants.ControllerName;


@Controller
@RequestMapping(ControllerName.AJAX_LOCATION)
public class AjaxLocation {

	
	@Resource
	LocationAO locationAO;
	
	

	@Resource 
	PushAO pushAO;
	
	@Resource
	LocaleResolver  localeResolver;
	
	
	
	@RequestMapping(method = RequestMethod.POST)
	public String doGet(HttpServletRequest request, Model model){
		long id = NumberUtils.toLong(request.getParameter("id"));
		byte status = NumberUtils.toByte(request.getParameter("status"), (byte)-8);
		
		String token = request.getParameter("token");
		if (LocationStatus.type(status) == null) {
			return "/ta/ajaxLocation";
		}
		LocationDO locationDO = new LocationDO();
		String  latitude = request.getParameter("latitude");
		if (!StringUtils.isEmpty(latitude)) {
			locationDO.setLatitude(NumberUtils.toDouble(latitude, 0));
		}
		String longitude  = request.getParameter("longitude");
		if (!StringUtils.isEmpty(longitude)) {
			locationDO.setLongitude(NumberUtils.toDouble(longitude, 0));
		}
		if(LocationStatus.type(status) == null){
			status = LocationStatus.AD_TIMEOUT.getValue();
		}
		locationDO.setStatus(status);
		Result<LocationDO> result = locationAO.updateLocation(id, token, locationDO);
		if(result.isSuccess()){
			Long deviceId = (Long) result.getModels().get("deviceId");
			if(deviceId != null){
				String message = null;
				if(status == LocationStatus.AD_SUCCESS.getValue()){
					 message = MessageUtils.getMessage("NotifyMessageSuccess", request,  localeResolver);
				}else{
					 message = MessageUtils.getMessage("NotifyMessageFailed", request,  localeResolver);
				}
				pushAO.push(deviceId, message, "mapta://mapta/remoteLocationList");	
			}
			model.addAttribute("success", result.isSuccess());
		}	
		return "/ta/ajaxLocation";
	}
}
