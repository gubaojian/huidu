package net.java.efurture.reader.utils;

import org.aspectj.weaver.ast.Var;

public class FilterUtils {

	
	
	public static String filterUtf8(String text){
		byte[] bts = text.getBytes();
		for (int i = 0; i < bts.length; i++)  
		{  
		    if((bts[i] & 0xF8)== 0xF0){  
		        for (int j = 0; j < 4; j++) {                          
		        	bts[i+j]=0x30;                     
		    }  
		    i+=3;  
		    }  
		}  
		return new String(bts);
	}
	
	
	public static void main(String[] args){
		
		System.out.println(filterUtf8("中国"));
		
	}
}
