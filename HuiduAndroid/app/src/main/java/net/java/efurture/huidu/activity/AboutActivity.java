package net.java.efurture.huidu.activity;

import net.java.efurture.huidu.R;
import net.java.efurture.huidu.callback.VersionCheckCallback;
import net.java.efurture.huidu.common.TabBaseActivity;
import net.java.efurture.huidu.config.Pages;
import net.java.efurture.huidu.config.Params;
import net.java.efurture.huidu.config.SystemConfig;
import net.java.efurture.huidu.service.CheckUpdateService;
import net.java.efurture.huidu.service.FontConfigService;
import net.java.efurture.huidu.share.ShareUtils;
import net.java.efurture.huidu.util.DialogUtils;
import net.java.efurture.huidu.util.VersionUtils;
import net.java.efurture.nav.Nav;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.google.code.client.GUClient;

public class AboutActivity extends TabBaseActivity {

	private static final String EMAIL  = "mailto:loveaworld@qq.com";
	private static final String APP_SUFIX = "(汇读)";
	private VersionCheckCallback mVersionCheckCallback;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		initAbout();
		
	}

	@Override
	protected void onLoad(Uri uri) {
		
	}

	@Override
	protected void onDestoryClean() {
		if (mVersionCheckCallback != null) {
			mVersionCheckCallback.cancel();
			mVersionCheckCallback = null;
		}
	}
	
	
	
	private void initAbout(){
		//设置字体
		final String[] fontChoices = getResources().getStringArray(R.array.font_chooser_choices);
		final TextView fontSizeChoiceTextView  = (TextView) this.findViewById(R.id.font_size_choosed);
		fontSizeChoiceTextView.setText(fontChoices[FontConfigService.getFontSize(getBaseContext())]);
		this.findViewById(R.id.font_size_settings).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = DialogUtils.fromActivity(AboutActivity.this);
				builder.setTitle(R.string.font_size_settings);
					builder.setSingleChoiceItems(fontChoices, FontConfigService.getFontSize(getBaseContext()), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						FontConfigService.setFontSize(getBaseContext(), which);
						fontSizeChoiceTextView.setText(fontChoices[which]);
						dialog.dismiss();
					}
				});
				builder.create().show();
			}
		});
		
		//分享给朋友
		this.findViewById(R.id.share_with_friends).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ShareUtils.share(AboutActivity.this, R.string.share_app_to_friends_content);
			}
		});
		
		//评价此app
		this.findViewById(R.id.rate_app).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try{
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setData(Uri.parse("market://details?id=" + getPackageName()));
					startActivity(intent);
				}catch(Exception e){
					Toast.makeText(getBaseContext(), R.string.none_market, Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		//推荐我喜欢的博客
		this.findViewById(R.id.share_my_blog).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try{
				   Intent intent = new Intent(Intent.ACTION_SENDTO);
				   intent.setData(Uri.parse(EMAIL));    
				   intent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.share_my_blog_subject));    
				   intent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.share_my_blog) + APP_SUFIX);  
				   startActivity(intent);
				}catch(Exception e){
					Toast.makeText(getBaseContext(), R.string.email_not_support, Toast.LENGTH_SHORT).show();
				}
		   }
		});
		
		//Bug反馈及建议
		this.findViewById(R.id.bug_report).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try{
					   Intent intent = new Intent(Intent.ACTION_SENDTO);
					   intent.setData(Uri.parse(EMAIL));      
					   intent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.bug_report) + APP_SUFIX); 
					   startActivity(intent);
			    }catch(Exception e){
						Toast.makeText(getBaseContext(), R.string.email_not_support, Toast.LENGTH_SHORT).show();
			     }
			}
		});
		
		//联系作者
	     this.findViewById(R.id.contact_me).setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						try{
							   Intent intent = new Intent(Intent.ACTION_SENDTO);
							   intent.setData(Uri.parse(EMAIL));      
							   intent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.contact_me) + APP_SUFIX); 
							   startActivity(intent);
					    }catch(Exception e){
								Toast.makeText(getBaseContext(), R.string.email_not_support, Toast.LENGTH_SHORT).show();
					     }
					}
		});

	     
	     //检测更新
	     this.findViewById(R.id.check_update).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mVersionCheckCallback != null) {
					mVersionCheckCallback.cancel();
					mVersionCheckCallback = null;
				}
				mVersionCheckCallback = new VersionCheckCallback(AboutActivity.this, true);
				GUClient.create(CheckUpdateService.class).getVersion(mVersionCheckCallback);
			}
		});
	    String format = getResources().getString(R.string.current_version);
	    String message = String.format(format, VersionUtils.getVersionName(this));
	    ((TextView)this.findViewById(R.id.current_version)).setText(message);
	     
	     
	     //关于汇读
	     this.findViewById(R.id.about_huidu).setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Nav.from(AboutActivity.this).toPath(Pages.WEBVIEW, Params.URL, SystemConfig.ASSERT_DIR + "about.html");
					}
		 });
		
	 
	     
	}


}
