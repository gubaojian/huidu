package net.java.mapyou.ao;

import com.google.code.efurture.common.result.ResultCode;
import com.google.code.efurture.common.resultcode.BaseResultCode;

public class DeviceErrorCode  extends BaseResultCode{

	
	private static final long serialVersionUID = 2102445155877956124L;
	
	public static final ResultCode ARGS_ERROR = create(401, "设备参数非法");
	
	public static final ResultCode UPDATE_LIMIT = create(10, "操作太频繁");
	
	public static final ResultCode DEVICE_ERROR = create(-11, "设备信息无效");
	
	public static final ResultCode DEVICE_NOT_EXIST = create(-18, "设备不存在");
	
	public static final ResultCode DEVICE_BUY_GUIDE = create(168, "设备未购买");
	
	public static final ResultCode SAME_DEVICE = create(-16, "设备不能关注设备自己");
	
	public static final ResultCode DEVICE_NOT_TRACK = create(1104, "设备未关联");
	
	public static final ResultCode DEVICE_PUSH_NOT_EXIST = create(1004, 
			"设备已下线，请用短信通知");
	
	
	public static final ResultCode DEVICE_TRACKED = create(1200, "设备已经关注啦");
	
	public static final int LIMIT_COUNT = 10;
	public static final ResultCode DEVICE_OVER_LIMIT = create(1201, "亲，最多关注10个设备哦");
	
}
