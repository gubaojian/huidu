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

import com.samaxes.filter.util.CacheConfigParameter;
import com.samaxes.filter.util.Cacheability;
import com.samaxes.filter.util.HTTPCacheHeader;

/**
 * Filter responsible for browser caching.
 *  配置示例如下：
 *   <!-- 缓存过滤  -->
	<filter>
		<filter-name>normalCache</filter-name>
		<filter-class>com.samaxes.filter.CacheFilter</filter-class>
		<!-- 作为精通内容输出 -->
		<init-param>
			<param-name>static</param-name>
			<param-value>true</param-value>
		</init-param>
		<!-- 允许中间代理服务器缓存 -->
		<init-param>
			<param-name>private</param-name> 
			<param-value>false</param-value>
		</init-param>
		<!-- 缓存33分钟,时间错有效时间为30 -->
		<init-param>
			<param-name>expirationTime</param-name>
			<param-value>2000</param-value>
		</init-param>
	</filter>
	<filter-mapping>
		<filter-name>normalCache</filter-name>
		<url-pattern>*.json</url-pattern>
	</filter-mapping>
 * @author Samuel Santos
 * @version $Revision: 25 $
 */
public class CacheFilter  extends OnceFilter{

    private Cacheability cacheability;

    private boolean isStatic;

    private long seconds;

    /**
     * Place this filter into service.
     * 
     * @param filterConfig the filter configuration object used by a servlet container to pass information to a filter
     *        during initialization
     * @throws ServletException to inform the container to not place the filter into service
     */
    public void init(FilterConfig filterConfig) throws ServletException {
        cacheability = (Boolean.valueOf(filterConfig.getInitParameter(CacheConfigParameter.PRIVATE.getName()))) ? Cacheability.PRIVATE
                : Cacheability.PUBLIC;
        isStatic = Boolean.valueOf(filterConfig.getInitParameter(CacheConfigParameter.STATIC.getName()));

        try {
            seconds = Long.valueOf(filterConfig.getInitParameter(CacheConfigParameter.EXPIRATION_TIME.getName()));
        } catch (NumberFormatException e) {
            throw new ServletException(new StringBuilder("The initialization parameter ").append(
                    CacheConfigParameter.EXPIRATION_TIME.getName()).append(" is missing for filter ").append(
                    filterConfig.getFilterName()).append(".").toString());
        }
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

        
        StringBuilder cacheControl = new StringBuilder(cacheability.getValue()).append(", max-age=").append(seconds);

        if (!isStatic) {
            cacheControl.append(", must-revalidate");
        }
        // Set cache directives
        long expire =  System.currentTimeMillis() + seconds*1000L + 8*60*60*1000L;  //中美时差
        response.setHeader(HTTPCacheHeader.CACHE_CONTROL.getName(), cacheControl.toString());
        response.setDateHeader(HTTPCacheHeader.EXPIRES.getName(), expire);
        response.setDateHeader(HTTPCacheHeader.LastModified.getName(), expire);
        /*
         * By default, some servers (e.g. Tomcat) will set headers on any SSL content to deny caching. Setting the
         * Pragma header to null or to an empty string takes care of user-agents implementing HTTP 1.0.
         */
        if (response.containsHeader("Pragma")) {
            response.setHeader(HTTPCacheHeader.PRAGMA.getName(), null);
        }

        chain.doFilter(request, response);
    }

    
    /**
     * Take this filter out of service.
     */
    public void destroy() {
    }

	
}
