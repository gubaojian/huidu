package net.java.efurture.reader.biz.configure;

public class Configure {

	
	
	/**
	 * 服务器链接
	 * */
	private String serverUrl = "http://huidu.lanxijun.com";
	
	/**
	 * push的key
	 * */
	private String serverMode = "product";
	
	
	
	
	public String getServerUrl() {
		return serverUrl;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}
	
	
	
	public String getServerMode() {
		return serverMode;
	}

	public void setServerMode(String serverMode) {
		this.serverMode = serverMode;
	}

	public boolean isDev(){
		return "dev".equals(serverMode);
	}
	
	
	
}
