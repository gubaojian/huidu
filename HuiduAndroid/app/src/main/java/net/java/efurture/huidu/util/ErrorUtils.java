package net.java.efurture.huidu.util;

import android.content.Context;
import android.widget.Toast;

import com.google.code.result.Result;


public class ErrorUtils {
	
	public static void showErrorResult(Context context,  Result<?> result){
		Toast.makeText(context, result.getResultCode().getMessage(), Toast.LENGTH_SHORT).show();
	}
	
	public static void showError(Context context, String error){
		Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
	}

}
