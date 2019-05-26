package net.java.efurture.judu.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * 暂时无法使用
 * */
@Configuration
public class WebConfig extends WebMvcConfigurationSupport {


	  @Override
	  @Bean
	  public RequestMappingHandlerMapping requestMappingHandlerMapping() {
	    RequestMappingHandlerMapping hm = super.requestMappingHandlerMapping();
	    hm.setUseSuffixPatternMatch(false);
	    return hm;
	  }

	
}
