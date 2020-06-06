package net.java.efurture.huidu.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;


public class GUConfig{
	
	private static final String CONFIG = "huidu_config";
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public static void put(Context context,String key, String value){
		SharedPreferences preferences = context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
	    Editor editor = preferences.edit();
	    editor.putString(key, value);
	    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
	        editor.apply();
	    }else {
	        editor.commit();
	    }
	}
	
	public static String get(Context context, String key){
		return get(context, key,  null);
	}
	
	public static String get(Context context, String key, String defaultValue){
		SharedPreferences preferences = context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
	    return preferences.getString(key, defaultValue);
	}
}
