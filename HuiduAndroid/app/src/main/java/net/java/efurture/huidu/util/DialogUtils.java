package net.java.efurture.huidu.util;

import net.java.efurture.huidu.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class DialogUtils {

	
	public static AlertDialog.Builder  fromActivity(Activity activity, boolean cancel, boolean ok){
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setTitle(R.string.alert_title);
		if (cancel) {
			 builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
			 });
		}
		
        if (ok) {
    	       builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
   			@Override
   			public void onClick(DialogInterface dialog, int which) {
   				dialog.dismiss();
   			}
   		  });
	    }
		
		builder.setCancelable(true);
		return builder;
	}
	
	public static AlertDialog.Builder  fromActivity(Activity activity, boolean cancel){
		return fromActivity(activity, cancel, true);
	}
	
	public static AlertDialog.Builder  fromActivity(Activity activity){
		return fromActivity(activity, true, true);
	}
}
