package net.java.efurture.reader.web.functions;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import net.java.efurture.reader.mybatis.domain.enums.FeedTypeEnum;
import net.java.efurture.reader.mybatis.domain.enums.TaskTypeEnum;


public class WebFunctions {
	
	
	/**task枚举类型*/
	private static final Map<Byte,String> taskTypesMap = new HashMap<Byte,String>();
	static{
		TaskTypeEnum[] types = TaskTypeEnum.values();
		for(TaskTypeEnum type : types){
			taskTypesMap.put(type.getValue(), type.getDesc());
		}
		
	};
	
	
	/**
	 * feed枚举类型
	 * */
	private static final Map<Byte,String> feedTypesMap = new HashMap<Byte,String>();
	static{
		FeedTypeEnum[] types = FeedTypeEnum.values();
		for(FeedTypeEnum type : types){
			 feedTypesMap.put(type.getValue(), type.getDesc());
		}
		
	};
	
	
	
	
	
	/**
	 * 返回Task的类型
	 * */
	public static String nameForTaskType(Byte type){
		return taskTypesMap.get(type);
	}
	
	/**
	 * 返回类型
	 * */
	public static String nameForFeedType(Byte value){
		return feedTypesMap.get(value);
	}
	
	
	/**
	 * 比较日期
	 * */
	public static boolean isBefore(Date a, Date b){
		if(a == null){
			return false;
		}
		if(b == null){
			return false;
		}
		return a.before(b);
	}
	
	public static boolean isAfter(Date a, Date b){
		if(a == null){
			return false;
		}
		if(b == null){
			return false;
		}
		return a.after(b);
	}
	
	
	/**
	 * 显示错误信息
	 * */
	public static String errorMessage(BindingResult result, String fieldName){
		if(result == null){
			return null;
		}
		if(!result.hasErrors()){
			return null;
		}
		FieldError error = result.getFieldError(fieldName);
		if(error == null){
			return null;
		}
		return error.getDefaultMessage();	
	}
	
	
	
	
	
	
}
