package net.java.efurture.common.springmvc.convertor;

import java.io.IOException;

import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

public class ResultMappingJacksonHttpMessageConverter  extends MappingJackson2HttpMessageConverter {

	@Override
	protected void writeInternal(Object object, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		super.writeInternal(object, outputMessage);
	}	
}
