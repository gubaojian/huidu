package net.java.efurture.huidu.share;

import net.java.efurture.huidu.R;
import net.java.efurture.huidu.util.DialogUtils;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;

public class ShareUtils{
	
	public static void share(final Activity activity, int stringId){
		share(activity, activity.getResources().getString(stringId));
	}
  
	public static void share(final Activity activity, final String text){
		  AlertDialog.Builder builder = DialogUtils.fromActivity(activity);
		  Intent intent = new Intent(Intent.ACTION_SEND);
		  intent.setType(MimeType.TEXT.getDesc());
		  intent.putExtra(Intent.EXTRA_TEXT, text);
		  final ShareAdapter adpterAdapter = new ShareAdapter(activity, intent);
		  builder.setAdapter(adpterAdapter, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		 });
		 builder.setTitle(R.string.share_title);
	     Dialog dialog = builder.create();
	     adpterAdapter.setDialog(dialog);
	     dialog.show();
	}
}
