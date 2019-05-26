package net.java.efurture.reader.biz.spider.client;

import com.google.code.efurture.common.result.ResultCode;
import com.google.code.efurture.common.resultcode.BaseResultCode;

public class GrapResultCode extends BaseResultCode {
	private static final long serialVersionUID = 7841456386523751216L;
	
	
	public static final ResultCode RESPONSE_TO_RESULT_ERROR = create("RESPONSE_TO_RESULT_ERROR");
	
	public static final ResultCode RESPONSE_STATUS_NOT_OK = create("RESPONSE_STATUS_NOT_OK");
	
	public static final ResultCode GRAP_SITE_EXCEPTION = create("GRAP_SITE_EXCEPTION");

	public static final ResultCode RESPONSE_EMPTY = create("RESPONSE_EMPTY");
	
	public static final ResultCode RESPONSE_TOO_LONG = create("RESPONSE_TOO_LONG");
	
}
