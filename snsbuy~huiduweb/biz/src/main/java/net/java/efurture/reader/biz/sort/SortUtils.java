package net.java.efurture.reader.biz.sort;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class SortUtils {
	
	
	
	public static Date getSortDate(){
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY);
	    int sort = (hour/1)*1;  //一个小时处理一次拍下，暂时
		cal.set(Calendar.HOUR_OF_DAY, sort);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);		
		return cal.getTime();
		
	}
	
	
	

	private static final Random rand = new Random();
	
	public static int randSort(){
		return rand.nextInt(1000);
	}
	
	public static int randSort(int num){
		return rand.nextInt(num);
	}
	

}
