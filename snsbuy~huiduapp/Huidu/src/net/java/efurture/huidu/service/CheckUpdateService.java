package net.java.efurture.huidu.service;

import net.java.efurture.huidu.domain.Version;
import retrofit.http.GET;

import com.google.code.callback.GUCallback;

public interface CheckUpdateService {
	
	
	@GET(ServiceConstants.VERSION)
	public void getVersion(GUCallback<Version> callback);


}
