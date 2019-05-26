package com.google.code.efurture.common.util;

import java.util.Map;
import java.util.Set;

public class MapUtils {
	
	
	public static String toString(Map<String, String> tokenMaps) {
		Set<Map.Entry<String, String>>  entriesSet = tokenMaps.entrySet();
		StringBuffer buffer = new StringBuffer("{");
		for(Map.Entry<String, String> entry : entriesSet){
			 buffer.append(entry.getKey());
			 buffer.append("=");
			 buffer.append(entry.getValue());
			 buffer.append(";");
		}
		buffer.append("}");
		return buffer.toString();
	}

}
