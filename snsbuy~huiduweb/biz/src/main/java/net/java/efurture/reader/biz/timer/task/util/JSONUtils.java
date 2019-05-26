package net.java.efurture.reader.biz.timer.task.util;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.util.StringUtils;

public class JSONUtils {
	
	public static final ObjectMapper mapper = new ObjectMapper();
	
	
	public static String toJSONString(Object target){
		if(target == null){
			return null;
		}
		try {
			return mapper.writeValueAsString(target);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static Object fromJson(String json, Class<?> type){
		if(StringUtils.isEmpty(json)){
			return null;
		}
		try {
			return mapper.readValue(json, type);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	

}
