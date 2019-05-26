package net.java.efurture.judu.web.controller.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import net.java.efurture.judu.web.controller.service.utils.VipUtils;
import net.java.mapyou.ao.DeviceAO;
import net.java.mapyou.ao.DeviceErrorCode;
import net.java.mapyou.ao.LocationAO;
import net.java.mapyou.mybatis.domain.BadgeDO;
import net.java.mapyou.mybatis.domain.DeviceDO;
import net.java.mapyou.mybatis.domain.vo.CountVO;
import net.java.mapyou.mybatis.enums.LocationStatus;
import net.java.mapyou.mybatis.enums.LocationType;
import net.java.mapyou.mybatis.query.LocationQuery;

@Controller
@RequestMapping(ControllerName.BADGE_DOT)
public class BadgeDot {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@Resource
	DeviceAO deviceAO;
	
	@Resource
	LocationAO locationAO;
	
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody Result<List<BadgeDO>> doGet(HttpServletRequest request, HttpServletResponse response){
		Result<List<BadgeDO>> result = new DefaultResult<List<BadgeDO>>();
		long deviceId = NumberUtils.toLong(request.getParameter("deviceId"));
		String deviceToken = request.getParameter("deviceToken");
		Result<DeviceDO> checkResult = deviceAO.checkDevice(deviceId, deviceToken);
		if(!checkResult.isSuccess()){
			result.setResultCode(DeviceErrorCode.DEVICE_NOT_EXIST);
			return result;
		}
		String badgeKey = request.getParameter("badgeKey");
		Date seeDate = VipUtils.getDotDate(request, "seeDate");
		if(badgeKey.equals("home")){
			executeHomeDot(checkResult.getResult(), result, seeDate);
		}else if(badgeKey.equals("deviceList")){
			executeDeviceListDot(checkResult.getResult(), result, seeDate);
		}
		return result;
	}
	
	
	private  void executeHomeDot(DeviceDO device, Result<List<BadgeDO>> result, Date seeDate){
		 Result<List<DeviceDO>>  deviceListResult = deviceAO.getTrackDeviceList(device.getId(), device.getToken());
	     result.setSuccess(true);
		 if(!deviceListResult.isSuccess() || deviceListResult.getResult().size() <= 0){
	    	     result.setResultCode(deviceListResult.getResultCode());
 			 return;
	     }
		 
		 List<DeviceDO> deviceList = deviceListResult.getResult();
	     List<Long> deviceIds = new ArrayList<Long>(4);
	     for(DeviceDO deviceItem : deviceList){
	    	      deviceIds.add(deviceItem.getId());
	     }
	     LocationQuery query = new LocationQuery();
		 query.setDeviceIds(deviceIds);
		 query.setType(LocationType.ME.getValue());
		 query.setStatus(LocationStatus.NORMAL.getValue());
		 query.setGmtCreateStart(seeDate);
		 Result<List<CountVO>>  countResult = locationAO.groupCountLocationByQuery(query);
		 if(!countResult.isSuccess()){
			 result.setResultCode(countResult.getResultCode());
			 return;
		 }
		 List<CountVO> countVOs = countResult.getResult();
		 int count = 0;
		 for(CountVO countVO : countVOs){
			 count += countVO.getCount();
		 }
		 List<BadgeDO> badgeDOs = new ArrayList<BadgeDO>();
		 if(count > 0){
			 BadgeDO badgeDO = new BadgeDO("301", count +"");
			 badgeDOs.add(badgeDO);
		 }
		 result.setResult(badgeDOs);
		 result.setSuccess(true);
		 return; 
	}
	
	private  void executeDeviceListDot(DeviceDO device, Result<List<BadgeDO>> result, Date seeDate){
		 Result<List<DeviceDO>>  deviceListResult = deviceAO.getTrackDeviceList(device.getId(), device.getToken());
	     result.setSuccess(true);
		 if(!deviceListResult.isSuccess() || deviceListResult.getResult().size() <= 0){
	    	     result.setResultCode(deviceListResult.getResultCode());
			 return;
	     }
		 
		 List<DeviceDO> deviceList = deviceListResult.getResult();
	     List<Long> deviceIds = new ArrayList<Long>(4);
	     for(DeviceDO deviceItem : deviceList){
	    	      deviceIds.add(deviceItem.getId());
	     }
	     LocationQuery query = new LocationQuery();
		 query.setDeviceIds(deviceIds);
		 query.setType(LocationType.ME.getValue());
		 query.setStatus(LocationStatus.NORMAL.getValue());
		 query.setGmtCreateStart(seeDate);
		 Result<List<CountVO>>  countResult = locationAO.groupCountLocationByQuery(query);
		 if(!countResult.isSuccess()){
			 result.setResultCode(countResult.getResultCode());
			 return;
		 }
		 List<CountVO> countVOs = countResult.getResult();
		 List<BadgeDO> badgeDOs = new ArrayList<BadgeDO>();
		 for(CountVO countVO : countVOs){
			 if(countVO.getCount() <= 0){
				 continue;
			 }
			 BadgeDO badgeDO = new BadgeDO("" + countVO.getKey(), countVO.getCount() +"");
			 badgeDOs.add(badgeDO);
		 }
		 result.setResult(badgeDOs);
		 result.setSuccess(true);
		 return; 
	}

}
