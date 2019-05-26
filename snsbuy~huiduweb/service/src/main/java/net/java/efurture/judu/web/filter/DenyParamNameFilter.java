package net.java.efurture.judu.web.filter;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.java.efurture.judu.web.util.FilterConfigMap;

import com.samaxes.filter.OnceFilter;

public class DenyParamNameFilter extends OnceFilter{

	private Map<String,String> denyParamNamesMap;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		 denyParamNamesMap = FilterConfigMap.fromValueName(filterConfig);
		 if(denyParamNamesMap.size() == 0){
				throw new ServletException("请配置不允许使用的参数名字");
		 }
	}

	@Override
	public void destroy() {
        if(denyParamNamesMap != null){
           	denyParamNamesMap.clear();
           	denyParamNamesMap = null;
        }
	}

	@Override
	public void doOnceFilter(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		Set<Entry<String,String>> entries = denyParamNamesMap.entrySet();
		for(Entry<String,String> entry : entries){
			if(request.getParameter(entry.getKey()) != null){
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
				return;
			}
		}
		chain.doFilter(request, response);
	}

}
