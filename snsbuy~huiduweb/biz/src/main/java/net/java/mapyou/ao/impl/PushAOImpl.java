package net.java.mapyou.ao.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

import com.google.code.efurture.common.ao.BaseAO;
import com.google.code.efurture.common.result.Result;
import com.google.code.efurture.common.resultcode.BaseResultCode;
import com.google.code.efurture.common.util.RunUtils;
import com.tencent.xinge.MessageIOS;
import com.tencent.xinge.XingeApp;

import net.java.efurture.reader.biz.configure.Configure;
import net.java.mapyou.ao.DeviceErrorCode;
import net.java.mapyou.ao.PushAO;
import net.java.mapyou.mybatis.domain.DeviceDO;
import net.java.mapyou.mybatis.enums.OS;
import net.java.mapyou.mybatis.mapper.DeviceDOMapper;

public class PushAOImpl extends BaseAO implements PushAO {

	@Resource 
	DeviceDOMapper deviceDOMapper;
	
	@Resource
	Configure configure;
	

	

	@Override
	public Result<Boolean> push(long deviceId, final String message, final String mapUrl) {
		Result<Boolean> result = createResult();
		try{
			//基本参数检查
			if (deviceId < 0 || StringUtils.isEmpty(message)) {
				result.setResultCode(DeviceErrorCode.ARGS_ERROR);
				result.setSuccess(false);
				return result;
			}
			final String content = message.trim();
			if (content.length() < 5 || content.length() > 128) {
				result.setResultCode(DeviceErrorCode.ARGS_ERROR);
				result.setSuccess(false);
				return result;
			}
			final DeviceDO deviceDO = deviceDOMapper.selectByPrimaryKey(deviceId);
		    if (deviceDO == null) {
				result.setResultCode(DeviceErrorCode.DEVICE_NOT_EXIST);
				result.setSuccess(false);
				return result;
			}
		    if(StringUtils.isEmpty(deviceDO.getPushToken())){
		    	    result.setResultCode(DeviceErrorCode.DEVICE_PUSH_NOT_EXIST);
				result.setSuccess(false);
				return result;
		    }
		    if(deviceDO.getOs() != OS.ANDROID.getValue()){
		    	    RunUtils.getPushExecutorService().execute(new Runnable() {
					@Override
					public void run() {
						MessageIOS pushMessage = new MessageIOS();
						pushMessage.setAlert(content);
						pushMessage.setSound("beep.wav");
				    	    if(!StringUtils.isEmpty(mapUrl)){
				    	    	    Map<String, Object> args = new HashMap<String, Object>();
				    	    	    args.put("ta_url", mapUrl);
				    	    	    pushMessage.setCustom(args);
				    	    }
				    	    XingeApp xingeApp = new XingeApp(2200147023L, "204ed2e9ec2f7b3a22b2a64fe01bdfa5");
				    	    JSONObject result = null;
				    	    if(configure.isDev()){
				    	    	   result = xingeApp.pushSingleDevice(deviceDO.getPushToken(), pushMessage,  XingeApp.IOSENV_DEV);
				    	    }else {
				    	    	   result  = xingeApp.pushSingleDevice(deviceDO.getPushToken(), pushMessage,  XingeApp.IOSENV_PROD);;	
						}
				    	    if(result.optInt("ret_code", 0) != 0){
				    	    	   logger.warn("push to device " + deviceDO.getPushToken() + " error " + result.toString());
				    	    }
					}
				});
		    }
		    
			result.setResult(true);
			result.setResultCode(BaseResultCode.SUCCESS);
			result.setSuccess(true);
			return result;
		}catch(Exception e){
		     logger.error("PushAOImpl push error ", e);
		     result.setResultCode(BaseResultCode.SYSTEM_EXCEPTION);
			 result.setThrowable(e);
		     result.setSuccess(false);
		     return result;
		}
	}

}
