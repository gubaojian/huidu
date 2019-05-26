package net.java.efurture.duke.extractor.site.utils;

import java.net.MalformedURLException;
import java.net.URL;

public class SiteUtils {
	
	
	public static boolean isSameDomain(String url, String two){
		String domainOne = urlDomain(url);
		String domainTwo = urlDomain(two);
		if(domainOne == null && domainTwo == null){
			return true;
		}
		if(domainOne == null){
			return false;
		}		
		return domainOne.equals(domainTwo);
	}
	
	
	public static String urlDomain(String urlStr){
		if(urlStr == null || urlStr.length() == 0){
			return urlStr;
		}
		try {
			String host = null;
			urlStr = urlStr.toLowerCase();
			if(urlStr.startsWith("http")){
				URL url = new URL(urlStr);
				host = url.getHost();
			}else{
				host = urlStr;
			}
			
			String mainDomain = host;
			int index = host.lastIndexOf('.');
			if(index > 0){
				index = host.lastIndexOf('.', index -1);
			}
			if(index > 0 && index < (host.length() - 1)){
				mainDomain = host.substring(index + 1, host.length());
			}
			return mainDomain;
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return urlStr;
		}
	}
	
	
	
	
	
	
	
	

}
