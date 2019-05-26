package net.java.mapyou.ao;

import com.google.code.efurture.common.result.Result;

public interface MapAO {

	
	public Result<String> getPoi(Double longitude, Double latitude);
}
