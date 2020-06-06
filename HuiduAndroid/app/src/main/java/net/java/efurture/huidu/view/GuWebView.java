package net.java.efurture.huidu.view;

import net.java.efurture.huidu.R;
import net.java.efurture.huidu.util.ScreenUtil;
import net.java.efurture.util.GLog;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.webkit.ConsoleMessage;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AbsoluteLayout;
import android.widget.Toast;

public class GuWebView  extends WebView{
	
	public static interface PageFinishedListener{
		  public void onPageFinished(WebView view);
	}
	
	private static final String TAG = "GuWebView";
	private Activity mActivity;
	private ViewGroup mFooterView;
	private PageFinishedListener mPageFinisedListener;
	private Runnable mFooterCallback;
	
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public GuWebView(Context context, AttributeSet attrs, int defStyle,
			boolean privateBrowsing) {
		super(context, attrs, defStyle, privateBrowsing);
		init();
	}

	public GuWebView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public GuWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public GuWebView(Context context) {
		super(context);
		init();
	}
	
	@SuppressLint("SetJavaScriptEnabled")
	private void init(){
		 if (isInEditMode()) {
			 return;
		 }
		 this.getSettings().setAllowFileAccess(true);
		 this.getSettings().setJavaScriptEnabled(true);
		 this.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		 this.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		 this.setHorizontalScrollBarEnabled(false);
		 this.setHorizontalFadingEdgeEnabled(false);
		 this.setHorizontalScrollbarOverlay(false);
		 this.setWebChromeClient(new WebChromeClient() {     
	            @Override     
			    public boolean onJsAlert(WebView view, String url, String message, final android.webkit.JsResult result)     
			    {     
			    	   AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
			    	   builder.setTitle(R.string.alert_title);
			    	   builder.setMessage(message);
			    	   builder.setPositiveButton(android.R.string.ok, new AlertDialog.OnClickListener(){     
			                   public void onClick(DialogInterface dialog, int which){     
			                            result.confirm();     
			                    }     
			        });
			    	   builder.setCancelable(true);
			    	   AlertDialog dialog =  builder.create();
			    	   dialog.show();
			       return true;     
			    }
				@Override
				public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
					GLog.e("GuWebView", "JavaScript Error" + consoleMessage.message()   + "  " + consoleMessage.lineNumber()
							+ consoleMessage.sourceId()  + consoleMessage.messageLevel());
					return super.onConsoleMessage(consoleMessage);
				};   
		  });       
		 this.setDownloadListener(new DownloadListener() {
			@Override
			public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
				 GLog.d(TAG, "onDownloadStart " + userAgent + "load url" +  url);
			}
		});
		this.setWebViewClient(new WebViewClient(){
			  public void onLoadResource(WebView view, String url) {
				  GLog.d(TAG, "load url" +  url);
				  super.onLoadResource(view, url);
			  }

			 @Override  
			  public boolean shouldOverrideUrlLoading(WebView view, String url){  
				 GLog.d(TAG, "load url" +  url);
			     return super.shouldOverrideUrlLoading(view, url);
			  }  
			 
			  @Override
			  public void onReceivedError(WebView view, int errorCod,String description, String failingUrl) {
		            Toast.makeText(getContext(), "加载失败：" + failingUrl , Toast.LENGTH_SHORT).show();
		            GLog.d(TAG, "onReceivedError " + failingUrl);
		      }
			  
			  @Override
		      public void onPageFinished(WebView view, String url) {
				    GLog.d(TAG, "onPageFinished " +  url);
				    super.onPageFinished(view, url);
				    if (mPageFinisedListener != null) {
				       	mPageFinisedListener.onPageFinished(view);
					}
				    postDelayed(mFooterCallback,  300);
			  }
		 });
		mFooterCallback  = new Runnable() {
			@Override
			public void run() {
				 addFooterView();
			}
		};
	}
	

	protected void onSizeChanged(int w, int h, int ow, int oh) {
		super.onSizeChanged(w, h, ow, oh);
		GLog.d(TAG, "onSizeChanged" + w  +" " + h);
	}
	
	

	public Activity getActivity() {
		return mActivity;
	}

	public void setActivity(Activity activity) {
		this.mActivity = activity;
	}

	
	
	public PageFinishedListener getPageFinisedListener() {
		return mPageFinisedListener;
	}

	public void setPageFinisedListener(PageFinishedListener pageFinisedListener) {
		this.mPageFinisedListener = pageFinisedListener;
	}

	public ViewGroup getFooterView() {
		return mFooterView;
	}

	public void setFooterView(ViewGroup footerView) {
		this.mFooterView = footerView;
	}
	
	private void addFooterView(){
		if (mFooterView == null) {
			return;
		}
		int height = 0;
		if (mFooterView.getParent() != null && (mFooterView.getLayoutParams() instanceof AbsoluteLayout.LayoutParams)) {
			AbsoluteLayout.LayoutParams  params = (LayoutParams) mFooterView.getLayoutParams();
			int position = getDocumentHeight() -  mFooterView.getHeight();
			if (params.y == position) {
				 return;
			}
			height = mFooterView.getHeight();
		}else {
			height = ScreenUtil.getHeight(getContext());
		}
		addFooterView(height, mFooterCallback);
	}

	private void addFooterView(int height, final Runnable callback){
		removeView(mFooterView);
		int y = getDocumentHeight() - height;
		AbsoluteLayout.LayoutParams  params =  new AbsoluteLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT, 0, y);
		this.addView(mFooterView, params);
		mFooterView.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {
			@Override
			public boolean onPreDraw() {
				mFooterView.getViewTreeObserver().removeOnPreDrawListener(this);
				int height = mFooterView.getHeight();
				int position = getDocumentHeight() - height;
				AbsoluteLayout.LayoutParams  params =  (LayoutParams) mFooterView.getLayoutParams();
				params.y = position;
				mFooterView.setLayoutParams(params);
				if (callback != null) {
					postDelayed(callback, 600);
				}
				GLog.d(TAG, "mFooterView onPreDraw " + position   + " "  + getDocumentHeight());
				return false;
			}
		});
	}
	
	
	private int getDocumentHeight(){
		int height = (int)Math.floor(getContentHeight()*getScale());
	    return height;
	}
	
	@Override
	public void destroy() {
		if (mFooterCallback != null) {
			removeCallbacks(mFooterCallback);
			mFooterCallback = null;
		}
	    if (mActivity != null) {
	       	mActivity = null;
		}
	    if(mFooterView != null){
	    	   mFooterView = null;
	    }
	    
	    if (mPageFinisedListener != null) {
			mPageFinisedListener = null;
		}
		super.destroy();
	}
}
