package net.java.efurture.judu.web.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

public class FromUtils {

	public static final String FROM = "From";
	
	public static final boolean isFromCDN(HttpServletRequest request){
		Object from = request.getAttribute(FROM);
		if(from == null){
			from = checkWhereFrom(request);
		}
		return From.CDN.getSource().equals(from);
	}
	
	
	public static final boolean isFromTA(HttpServletRequest request){
		Object from = request.getAttribute(FROM);
		if(from == null){
			from = checkWhereFrom(request);
		}
		return From.TA.getSource().equals(from);
	}
	
	/**
	 * 检查请求来自哪里
	 * */
	private static String checkWhereFrom(HttpServletRequest request) {
		String from = request.getHeader("from");
		if(StringUtils.isEmpty(from)){
			from = request.getHeader("From");
		}
		From source = From.NONE;
		if(!StringUtils.isEmpty(from)){
			from = from.trim().toLowerCase();
			for(From item : From.values()){
				if(from.equals(item.getSource())){
					source = item;
					break;
				}
			}
		}
		request.setAttribute(FROM, source.getSource());
		return source.getSource();
	}


	public static enum From{
		CDN("cdn"),
		TA("ta"),
		NONE("none");
		
		private final String source;

		private From(String source) {
			this.source = source;
		}
		
		public String getSource() {
			return source;
		}
	}

}
