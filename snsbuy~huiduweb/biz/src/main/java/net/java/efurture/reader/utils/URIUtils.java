package net.java.efurture.reader.utils;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;



/**
 * http://stackoverflow.com/questions/749709/how-to-deal-with-the-urisyntaxexception
 * 
 *
 * */
public class URIUtils {
	
	
	public static final String CHARSET = "utf-8";
	
	/**
	 *  uri中的合法字符：http://www.ietf.org/rfc/rfc2396.txt
	 *  uri中必须被排除的字符
	 *  "{" | "}" | "|" | "\" | "^" | "[" | "]" | "`"
	 * */
    private static final Map<Character,String> excludeCharMappings = new HashMap<Character,String>();
    static{
    	try {
    	   excludeCharMappings.put('{', URLEncoder.encode("{", CHARSET));
    	   excludeCharMappings.put('}', URLEncoder.encode("}", CHARSET));
    	   excludeCharMappings.put('|', URLEncoder.encode("|", CHARSET));
    	   excludeCharMappings.put('\\', URLEncoder.encode("\\", CHARSET));
    	   excludeCharMappings.put('^', URLEncoder.encode("^", CHARSET));
    	   excludeCharMappings.put('[', URLEncoder.encode("[", CHARSET));
    	   excludeCharMappings.put(']', URLEncoder.encode("]", CHARSET));
    	   excludeCharMappings.put('`', URLEncoder.encode("`", CHARSET));
    	   excludeCharMappings.put(' ', URLEncoder.encode(" ", CHARSET));
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
   
	
	/*
	 * 自动检测url中的非法字符，并对其进行编码处理。防止非法字符导致转换的问题
	 * */
	public static URI toURI(String url) throws URISyntaxException, MalformedURLException, UnsupportedEncodingException{
		if(url == null){
			return null;
		}
		url = url.trim();
		StringBuffer buf = new StringBuffer();
		for(int i=0; i< url.length(); i++){
			char ch = url.charAt(i);
			if(ch == '%'){  //检测%
				try{
					int end = i + 3;
					if(end > url.length()){
						end = url.length();
					}
					String target = url.substring(i, end);
					URLDecoder.decode(target, CHARSET); //尝试解码，如果解码成功，则说明已经编码。
					buf.append(target);
					i+= 2;
				}catch(Exception e){
					buf.append(URLEncoder.encode("%", CHARSET));
				}
				continue;
			}
			
			String code = excludeCharMappings.get(ch);
			if(code == null){
				buf.append(ch);
			}else{
				buf.append(code);
			}
		}	
		return  new URI(buf.toString());
	}
	
	public static Map<String, String> parseQueryBase64Url(String url){
		if (StringUtils.isEmpty(url)) {
			return new HashMap<String, String>();
		}
		try{
			return parseQuery(new String(Base64.decodeBase64(url)));
		}catch(Exception e){
			return new HashMap<String, String>();
		}
	}
	
	public static Map<String, String> parseQuery(String url){
		Map<String, String> map = new HashMap<String, String>();
		try{
			if (url == null) {
				return map;
			}
			URI  homeUrl = new URI(url);
			String query = homeUrl.getQuery();
			if (StringUtils.isEmpty(query)) {
				return map;
			}
			String[] params = query.split("&");
			for(String param : params){
				String[] kvs = param.split("=");
				if (kvs.length <= 1) {
					continue;
				}
				map.put(kvs[0], URLDecoder.decode(kvs[1], "UTF-8"));
			}
			return map;
		}catch(Exception e){
			 e.printStackTrace();
			 return map;
		}
	}
	
	
	
	public static void main(String[] args) throws UnsupportedEncodingException, MalformedURLException, URISyntaxException{
		
		System.out.println( URLDecoder.decode("112F中国", "UTF-8"));
		
		
		System.out.println( URLDecoder.decode("http://finance.yahoo.com/q/h?s=^IXIC", "UTF-8"));
		
		
		System.out.println( URLDecoder.decode("http://finance.yahoo.com/q/h?s=^IXIC", "UTF-8"));

		System.out.println(URLEncoder.encode("^", CHARSET));
		
		System.out.println(URLEncoder.encode("%", CHARSET));
		
		System.out.println(Character.forDigit(14, 16));
		
		System.out.println(toURI("http://finance.yahoo.com/q/h?s=^IXIC"));
		
		System.out.println(toURI("http://finance.yahoo.com/ q/h?s=^IXIC&p=中国"));
		
        System.out.println(toURI("http://www.zuoche.com/pda/showmap.jsp?f=1%25&s=5508107&d=5511127&xy=32786,2559633,35024,2560043&k=fd120733&m=1&tt=1.%"));
	
	
        System.out.println(parseQuery("http://www.zuoche.com/pda/showmap.jsp?qr=444444"));
    	
	}

}
