package net.java.efurture.reader.utils;

import java.io.UnsupportedEncodingException;

import org.mozilla.universalchardet.UniversalDetector;

public class CharsetDetectorUtils {
	
	
	public static String detectCharset(String content){
		return detectCharset(content.getBytes());	
	}
	
	public static String detectCharset(byte[] bts){
		UniversalDetector detector = new UniversalDetector(null);  
		detector.handleData(bts, 0, bts.length);
		detector.dataEnd();
		String charset = detector.getDetectedCharset();
		if(charset == null){
			charset = "UTF-8";
		}
		return charset;	
	}
	
	public static String toString(String content){
		try {
			return toString(content.getBytes());
		} catch (UnsupportedEncodingException e) {
			return content;
		}
	}
	
	public static String toString(byte[] bts) throws UnsupportedEncodingException{
		String charset = detectCharset(bts);
		return new String(bts, charset);
	}
	

	

}
