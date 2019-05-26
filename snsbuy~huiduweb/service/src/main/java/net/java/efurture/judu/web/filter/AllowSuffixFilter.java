package net.java.efurture.judu.web.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.java.efurture.judu.web.util.FilterConfigMap;

import org.apache.commons.io.FilenameUtils;

import com.samaxes.filter.OnceFilter;

public class AllowSuffixFilter  extends OnceFilter{

	private Map<String,String> allowSuffixMap;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		allowSuffixMap = FilterConfigMap.fromValueName(filterConfig);
		if(allowSuffixMap.size() == 0){
			throw new ServletException("请配置允许访问的url后缀名");
		}
	}
	
	@Override
	public void doOnceFilter(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String uri = request.getRequestURI();
		if(uri == null || uri.length() <= 1){
			chain.doFilter(request, response);
			return;
		}
		String suffix = FilenameUtils.getExtension(uri).toLowerCase();
		if(!allowSuffixMap.containsKey(suffix)){
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return;
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		if(allowSuffixMap != null){
			allowSuffixMap.clear();
			allowSuffixMap = null;
		}
	}

	

}
