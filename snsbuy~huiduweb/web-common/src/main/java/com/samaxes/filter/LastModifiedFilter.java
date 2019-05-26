/*
 * $Id: CacheFilter.java 90 2011-03-07 22:06:02Z samaxes $ 
 *
 * Copyright 2008 samaxes.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.samaxes.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.samaxes.filter.util.HTTPCacheHeader;

/**
 * Filter responsible for browser caching.
 * 和ResultMappingJacksonHttpMessageConverter  RequestCacheUtils根据是否成功进行缓存
 * @author Samuel Santos
 * @version $Revision: 25 $
 */
public class LastModifiedFilter  extends OnceFilter{

    
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

    /**
     * Set cache header directives.
     * 
     * @param servletRequest provides data including parameter name and values, attributes, and an input stream
     * @param servletResponse assists a servlet in sending a response to the client
     * @param filterChain gives a view into the invocation chain of a filtered request
     */
    @Override
	public void doOnceFilter(HttpServletRequest request, HttpServletResponse response,FilterChain chain) throws IOException, ServletException { 
        try {
        	long requestIfModifiedSince = request.getDateHeader(HTTPCacheHeader.IfModifiedSince.getName());
        	if(requestIfModifiedSince > 0){
        		requestIfModifiedSince +=  8*60*60*1000L;
        		if(requestIfModifiedSince > System.currentTimeMillis()){
          	      response.setHeader(HTTPCacheHeader.LastModified.getName(), request.getHeader(HTTPCacheHeader.IfModifiedSince.getName()));
          	      response.sendError(HttpServletResponse.SC_NOT_MODIFIED);
          	      return;
             }
        	}
        	
        } catch (Exception e) {}
        chain.doFilter(request, response);
    }

    /**
     * Take this filter out of service.
     */
    public void destroy() {
    }

	
	
}
