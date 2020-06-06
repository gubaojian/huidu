package net.java.efurture.huidu.util;


import net.java.efurture.huidu.R;
import android.content.Context;

public class VersionUtils {
   
	public static int getVersionCode(Context context){
		int versionCode = context.getResources().getInteger(R.integer.version_code);
		try {
			versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return versionCode;
	}
	
	
	public static String getVersionName(Context context){
		String versionName = context.getResources().getString(R.string.version_name);
		try {
			versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return versionName;
	}
}
