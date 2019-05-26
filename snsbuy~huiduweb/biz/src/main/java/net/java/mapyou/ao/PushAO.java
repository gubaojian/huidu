package net.java.mapyou.ao;

import com.google.code.efurture.common.result.Result;

public interface PushAO {

	
	public Result<Boolean> push(long deviceId, String message, String mapUrl);
}
