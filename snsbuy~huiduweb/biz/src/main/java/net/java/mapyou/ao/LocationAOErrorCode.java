package net.java.mapyou.ao;

import com.google.code.efurture.common.result.ResultCode;
import com.google.code.efurture.common.resultcode.BaseResultCode;

public class LocationAOErrorCode extends BaseResultCode{

	private static final long serialVersionUID = -2582774448754595498L;

	

	public static final ResultCode ARGS_ERROR = create(200, "参数非法");
	

	public static final ResultCode NOT_EXIST = create(201, "记录不存在");
	

	public static final ResultCode TOKEN_ERROR = create(202, "token非法");
	
	public static final ResultCode STATUS_ERROR = create(203, "记录状态异常");
	
	public static final ResultCode OVER_LIMIT = create(204, "操作频繁，请稍后再试");
	
}
