package net.java.mapyou.ao;

import com.google.code.efurture.common.result.ResultCode;
import com.google.code.efurture.common.resultcode.BaseResultCode;

public class ShortUrlAOErrorCode extends BaseResultCode{
	private static final long serialVersionUID = 1273932899596247583L;
	public static final ResultCode ARGS_ERROR = create(301, "参数非法");
	
}
