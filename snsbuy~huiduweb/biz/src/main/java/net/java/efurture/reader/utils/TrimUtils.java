package net.java.efurture.reader.utils;

public class TrimUtils {
	
	
	/**
	 * trim特殊字符
	 * */
	public static String trim(String str){
		if(str == null || str.length() == 0){
			return str;
		}
		StringBuilder sb = new StringBuilder(str);
		while (sb.length() > 0 && (Character.isWhitespace(sb.charAt(0)) || Character.isSpaceChar(sb.charAt(0)))) {
			sb.deleteCharAt(0);
		}
		
		while (sb.length() > 0 && (Character.isWhitespace(sb.charAt(sb.length() - 1)) || Character.isSpaceChar(sb.charAt(sb.length() - 1)))) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

}
