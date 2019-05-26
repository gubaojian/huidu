package com.samaxes.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class OnceFilter  implements Filter {



	@Override
	public final void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if(request.getAttribute(this.getClass().getName()) != null){
			chain.doFilter(request, response);
		}else{
			if (!(request instanceof HttpServletRequest) || !(response instanceof HttpServletResponse)) {
				throw new ServletException("OncePerRequestFilter just supports HTTP requests");
			}
			request.setAttribute(this.getClass().getName(), Boolean.TRUE);
			this.doOnceFilter((HttpServletRequest)request, (HttpServletResponse)response, chain);
		}
		
	}

	public abstract void doOnceFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException;


}
