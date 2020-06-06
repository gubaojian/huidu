package net.java.efurture.nav;

import java.util.Map;
import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

public class Nav {
    private static final String TAG = "Nav";
	private static String mScheme = "nav";
	private static String mHost  = "huidu.lanxijun.com";
	private static String mAction = Intent.ACTION_VIEW;
	
	/**导航配置*/
	public static void setScheme(String scheme) {
		mScheme = scheme;
	}


	public static void setHost(String host) {
		mHost = host;
	}
	
	public static void setAction(String action){
		mAction = action;
	}

	/**
	 * 创建一个导航
	 * */
	public static Nav from(Context context){ 
		return new Nav(context);
	}


	protected  Context mContext;
	
	private Nav(Context context){
		mContext = context;
	}
	

	public boolean toPath(String path, Object...parameters){
		Uri.Builder builder = getBuilder(path);  
		if (parameters == null || parameters.length  == 0) {
			return toUri(builder.build());
		}
		int kvCount = parameters.length/2;
		for(int i=0; i<kvCount; i++){
           Object k = parameters[2*i];
           Object v = parameters[2*i+1];
           builder.appendQueryParameter(k == null ? "" : k.toString(), 
        		                            v == null ? "" : v.toString());
		}
		if(parameters.length %2 != 0){
			Object k = parameters[parameters.length -1];
			 builder.appendQueryParameter(k == null ? "" : k.toString(), "");
		}
		return toUri(builder.build());
	}
	
	public boolean toPath(String path, Map<String, String> parameters){
		Uri.Builder builder =  getBuilder(path);  
		if (parameters == null || parameters.size()  == 0) {
			return toUri(builder.build());
		}
		Set<Map.Entry<String, String>> entries =  parameters.entrySet();
		for (Map.Entry<String, String> entry : entries) {
			 builder.appendQueryParameter(entry.getKey(), entry.getValue());
		}
		return toUri(builder.build());
	}
	
	public boolean toUri(Uri uri){
		   try{
			    if (uri == null) {
					return false;
				}
				Intent intent = new Intent();
				intent.setPackage(mContext.getPackageName());
				intent.setAction(mAction);
				intent.addCategory(Intent.CATEGORY_DEFAULT);
				intent.setData(uri);
				if(!(mContext instanceof Activity)){
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				}
				mContext.startActivity(intent);
				return true;
			}catch(Exception e){
				Log.e(TAG, "Nav To Uri " + uri.toString(), e);
				Toast.makeText(mContext, "Nav to " + uri.toString() + "\nError: "+ e, Toast.LENGTH_SHORT).show();
				return false;
			}
	}
		
	
	private Uri.Builder getBuilder(String path){
		Uri.Builder builder = new Uri.Builder();
		builder.scheme(mScheme);
		builder.authority(mHost);
		builder.path(path);
		return builder;
	}
}
