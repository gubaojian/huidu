package net.java.efurture.judu.web.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterConfig;

import org.apache.commons.lang.StringUtils;

public class FilterConfigMap {

	
	public static Map<String,String> fromValueName(FilterConfig filterConfig){
		Map<String,String> map  = new HashMap<String,String>();;
		Enumeration<String> names = filterConfig.getInitParameterNames();
		while(names.hasMoreElements()){
			String desc = names.nextElement(); 
			desc = StringUtils.trimToNull(desc);
			if(desc == null){
				continue;
			}
			String value = StringUtils.trimToNull(filterConfig.getInitParameter(desc));
			if(value == null){
				continue;
			}
			map.put(value, desc);
		}
		return map;
	}
	
}
