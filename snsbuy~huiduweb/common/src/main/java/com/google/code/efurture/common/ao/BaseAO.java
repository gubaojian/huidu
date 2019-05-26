package com.google.code.efurture.common.ao;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.efurture.common.result.DefaultResult;
import com.google.code.efurture.common.result.Result;
import com.google.code.efurture.common.resultcode.BaseResultCode;


public class BaseAO {
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	

	protected <T> Result<T> createResult(){
		Result<T> result = new DefaultResult<T>(false);
		result.setResultCode(BaseResultCode.SUCCESS);
		return result;
	}
	
	
}
