package net.java.efurture.huidu.activity;

import net.java.efurture.huidu.R;
import net.java.efurture.huidu.common.NavBaseActivity;
import net.java.efurture.huidu.config.Params;
import net.java.efurture.huidu.listener.BackListener;
import net.java.efurture.huidu.view.GuWebView;
import net.java.efurture.huidu.view.GuWebView.PageFinishedListener;
import net.java.efurture.huidu.view.LoadMaskView;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebView;
import android.widget.Toast;

public class GUWebViewActivity extends NavBaseActivity {

	private GuWebView mGuWebView;
	private LoadMaskView mLoadMaskView;
	private String url;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guweb_view);
		
		if (TextUtils.isEmpty(url)) {
			return;
		}
		mGuWebView = (GuWebView) this.findViewById(R.id.webview);
		mGuWebView.setActivity(this);
		mGuWebView.loadUrl(url);
		mLoadMaskView = (LoadMaskView) this.findViewById(R.id.load_mask);
		mGuWebView.setPageFinisedListener(new PageFinishedListener() {
			
			@Override
			public void onPageFinished(WebView view) {
				view.postDelayed(new Runnable() {
					@Override
					public void run() {
						if (mLoadMaskView != null) {
							mLoadMaskView.hideMask();
						}
					}
				}, 50);
			}
		});
		this.findViewById(R.id.back).setOnClickListener(new BackListener(this));
		mLoadMaskView.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				if (mLoadMaskView != null) {
					mLoadMaskView.hideMask();
				}
			}
		}, 5000);
		
	}

	@Override
	protected void onLoad(Uri uri) {
	     url = uri.getQueryParameter(Params.URL);
	     if (TextUtils.isEmpty(url)) {
	    	    Toast.makeText(getBaseContext(), "url参数为空", Toast.LENGTH_SHORT).show();
	    	     finish();
			return;
		}
	}
	
	
	@Override
	protected void onDestoryClean() {
		mGuWebView = null;
		mLoadMaskView = null;
		super.onDestoryClean();
	}
	
}
