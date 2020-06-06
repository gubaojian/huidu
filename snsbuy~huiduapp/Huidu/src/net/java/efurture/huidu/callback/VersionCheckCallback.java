package net.java.efurture.huidu.callback;

import net.java.efurture.huidu.R;
import net.java.efurture.huidu.domain.Version;
import net.java.efurture.huidu.util.DialogUtils;
import net.java.efurture.huidu.util.VersionUtils;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

import com.google.code.callback.GUCallback;
import com.google.code.result.Result;

public class VersionCheckCallback extends GUCallback<Version>{

	private Activity mActivity;
	private boolean mActive;

	public VersionCheckCallback(Activity activity, boolean active) {
		super();
		this.mActivity = activity;
		this.mActive = active;
	}

	@Override
	public void onResult(Result<Version> result) {
		final Version version = result.getResult();
		if (version == null) {
			if (mActive) {
			   AlertDialog.Builder builder =   DialogUtils.fromActivity(mActivity, false);
			   builder.setMessage(R.string.latest_version_tips);
			   builder.create().show();
			}
			return;
		}
		if (version.getVersionCode() <= VersionUtils.getVersionCode(mActivity)) {
			/**
			 * 服务端版本理论上不小于客户端，如果 服务端版本号 + 4 还小于客户端的版本
			 * 说明客户端有问题,提示用户升级。
			 * */
			if ((version.getVersionCode() + 4) <= VersionUtils.getVersionCode(mActivity)) {
				AlertDialog.Builder builder =   DialogUtils.fromActivity(mActivity);
			    builder.setMessage("您下载的汇读非官方版本，可能包含木马病毒；去官网下载正版？\n\n提示：请先卸载此版本，再安装官方版本。");
			    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						Intent intent = new Intent();
						intent.setData(Uri.parse("http://huidu.lanxijun.com"));
						intent.setAction(Intent.ACTION_VIEW);
						mActivity.startActivity(intent);
					}
				});
			    builder.create().show();
			}else{
				if (mActive) {
					 AlertDialog.Builder builder =   DialogUtils.fromActivity(mActivity, false);
					 builder.setMessage(R.string.latest_version_tips);
					 builder.create().show();
				}
			}
			return;
		}
		//检测到新版本
	    String format = mActivity.getResources().getString(R.string.new_version_tips);
		String message = String.format(format, result.getResult().getVersionName());
		AlertDialog.Builder builder =   DialogUtils.fromActivity(mActivity);
		builder.setMessage(message);
		builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				Intent intent = new Intent();
				intent.setData(Uri.parse(version.getDownloadUrl()));
				intent.setAction(Intent.ACTION_VIEW);
				mActivity.startActivity(intent);
			}
		});
		builder.create().show();
	}
}
