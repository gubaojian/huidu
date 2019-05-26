package com.google.code.efurture.common.resultcode;

import com.google.code.efurture.common.result.ResultCode;

public class BaseResultCode extends ResultCode{
	private static final long serialVersionUID = 8514917922254668847L;
	
	/**
	 * 异常信息
	 * */
	public static final ResultCode SYSTEM_EXCEPTION = create(0, "系统异常,请稍后再试。");
	
	/**成功
	 * */
	public static final ResultCode SUCCESS = create(1, "操作成功");

}
