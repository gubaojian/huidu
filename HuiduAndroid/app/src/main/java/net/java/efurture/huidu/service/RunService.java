package net.java.efurture.huidu.service;

import net.java.efurture.huidu.util.GUConfig;
import android.content.Context;

public class RunService {
	
	public static final long ONE_DAY = 24*60*60*1000;
	public static final long TWO_DAY = ONE_DAY*2;
	public static final long THREE_DAY = ONE_DAY*3;
	public static final long FOUR_DAY = ONE_DAY*4;
	public static final long FIVE_DAY = ONE_DAY*5;
	public static final long SIX_DAY = ONE_DAY*6;
	public static final long SEVEN_DAY = ONE_DAY*7;
	
	/**
	 * 是否应该启动
	 * */
	public static boolean shouldRun(Context context, String key, long frequency, boolean defaultValue){
		String value = GUConfig.get(context, key);
		if (value == null) {
			markRun(context, key);
			return defaultValue;
		}
		try {
			long pre = Long.parseLong(value);
			long now = System.currentTimeMillis();
			if ((now - pre) > frequency) {
				return true;
			}else {
				return false;
			}
		} catch (Exception e) {
			markRun(context, key);
			return defaultValue;
		}
	}
	
	public static void markRun(Context context, String key){
		 GUConfig.put(context, key, System.currentTimeMillis() + "");
	}
	
	private static final String CHECK_UPDATE_KEY = "CHECK_UPDATE_KEY_RUN";
	
	/**
	 * 是否应该启动版本更新检测，一星期检测一次。
	 * */
	public static boolean shouldRunCheckUpdate(Context context){
		return shouldRun(context, CHECK_UPDATE_KEY, SEVEN_DAY, false);
	}
	
	public static void markRunCheckUpdate(Context context){
		markRun(context, CHECK_UPDATE_KEY);
	}
	
	
}
