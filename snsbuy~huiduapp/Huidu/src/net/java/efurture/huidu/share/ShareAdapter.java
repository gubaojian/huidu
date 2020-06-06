package net.java.efurture.huidu.share;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.java.efurture.huidu.R;
import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ShareAdapter extends BaseAdapter{
	

	
	private PackageManager mPackageManager;
	private List<ResolveInfo> mResolverInfo;
	private Activity mActivity;
	private Dialog mDialog;
	private Intent mIntent;
	
	
	public ShareAdapter(Activity activity, Intent intent){
		mActivity = activity;
		mPackageManager = mActivity.getPackageManager();
		mIntent = intent;
		mResolverInfo = getResolveInfo();
	}
	
	private List<ResolveInfo> getResolveInfo(){
		PackageManager pm = mActivity.getPackageManager();
		List<ResolveInfo> resolverInfoList = pm.queryIntentActivities(mIntent, 0);
		Collections.sort(resolverInfoList, new Comparator<ResolveInfo>() {
			@Override
			public int compare(ResolveInfo lhs, ResolveInfo rhs) {
				return resolveInfoToValue(lhs) - resolveInfoToValue(rhs);
			}
		});
		
		for(ResolveInfo resolverInfo : resolverInfoList){
		     System.out.println(resolverInfo.activityInfo.name
		    		 + "  "  + resolverInfo.activityInfo.packageName);
		}
		
		return resolverInfoList;
	}
	
	private int resolveInfoToValue(ResolveInfo resolveInfo){
		  int MAX = 100;
		  if (resolveInfo == null) {
			 return MAX;
		  }
		  ActivityInfo activityInfo = resolveInfo.activityInfo;
		  if (activityInfo == null || activityInfo.name == null) {
				 return MAX;
		   }
		  String name = activityInfo.name + "" + activityInfo.packageName;
		  if (name.contains("sina")) {
			return 50;
		  }
		  
		  if (name.contains("tencent") || name.contains("qzone") || name.contains("mobileqq")) {
			  return 60;
		  }
		  
		  if (name.contains("android.mail") || name.contains("note")) {
			  return 70;
		  }
		  
		  if (name.contains("android.mms")) {
			 return 80;
		  }
		  
		  if (name.contains("bluetooth")) {
			  return MAX + 1;
		  }
		  return MAX;
	}
	
	
	
	@Override
	public int getCount() {
		if (mResolverInfo == null) {
			return 0;
		}
		return mResolverInfo.size();
	}

	@Override
	public Object getItem(int position) {
		return mResolverInfo.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(mActivity).inflate(R.layout.share_item, null);
			convertView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					ViewHolder holder = (ViewHolder) v.getTag();
					if (holder == null) {
						return;
					}
					if (mDialog != null) {
						mDialog.dismiss();
					}
					ResolveInfo resolveInfo = holder.getResolveInfo();
					ActivityInfo targetActivity = resolveInfo.activityInfo;
					ComponentName componentName = new ComponentName(targetActivity.packageName, targetActivity.name);
					Intent intent = new Intent(mIntent);
					intent.setComponent(componentName);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
					mActivity.startActivity(intent);
				}
			});
		}
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if(holder == null){
			holder = new ViewHolder(convertView, mPackageManager);
		}
		holder.bindResolveInfo(mResolverInfo.get(position));
		return convertView;
	}
	
	
	
	public Dialog getDialog() {
		return mDialog;
	}

	public void setDialog(Dialog mDialog) {
		this.mDialog = mDialog;
	}


	public static class ViewHolder{
		private ImageView mAppLogoImageView;
		private TextView mAppNameTextView;
		private ResolveInfo mResolveInfo;
		private PackageManager mPackageManager;
		
		public ViewHolder(View view, PackageManager packageManager){
			 mAppLogoImageView = (ImageView)view.findViewById(R.id.app_logo);
			 mAppNameTextView = (TextView) view.findViewById(R.id.app_name);
			 view.setTag(this);
			 mPackageManager = packageManager;
		}
		
		public void bindResolveInfo(ResolveInfo resolveInfo){
			mResolveInfo = resolveInfo;
			mAppLogoImageView.setImageDrawable(resolveInfo.loadIcon(mPackageManager));
			mAppNameTextView.setText(resolveInfo.loadLabel(mPackageManager));
		}

		public ResolveInfo getResolveInfo() {
			return mResolveInfo;
		}

		public void setResolveInfo(ResolveInfo resolveInfo) {
			this.mResolveInfo = resolveInfo;
		}

		public PackageManager getPackageManager() {
			return mPackageManager;
		}

		public void setPackageManager(PackageManager packageManager) {
			this.mPackageManager = packageManager;
		}
	}
	
	

}
