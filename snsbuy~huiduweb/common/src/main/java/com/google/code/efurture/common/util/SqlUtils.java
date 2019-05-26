package com.google.code.efurture.common.util;

import org.apache.commons.lang.StringUtils;

public class SqlUtils {
	
	/**
	 * 过滤关键字中包含的like通配符
	 * */
	public static final String filterLikeWildCardChar(String keyword){
		if(keyword == null){
			return keyword;
		}
		StringBuffer buf = new StringBuffer(keyword);
		for(int i=0; i<buf.length(); i++){
			char ch = buf.charAt(i);
			if(ch == '%' || ch == '_' || ch == ']' || ch == '[' || ch == '^' || ch == '!'){
				buf.setCharAt(i, ' ');
			}
		}
		return  StringUtils.trimToNull(buf.toString());
	}
	
	/**
	 * 过滤关键字中包含的like通配符, 并加%keyword%
	 * */
	public static final String toLike(String keyword){
		keyword = StringUtils.trimToNull(keyword);
		if(keyword == null){
			return keyword;
		}
		StringBuffer buf = new StringBuffer(keyword.length());
		buf.append('%');
		for(int i=0; i< keyword.length(); i++){
			char ch = keyword.charAt(i);
			if(ch == '%' || ch == '_' || ch == ']' || ch == '[' || ch == '^' || ch == '!'){
				continue;
			}
			buf.append(ch);
		}
		buf.append('%');
		return  buf.toString();
	}

}
