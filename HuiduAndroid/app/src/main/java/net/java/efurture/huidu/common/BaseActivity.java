package net.java.efurture.huidu.common;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import android.view.Menu;

import com.umeng.analytics.MobclickAgent;

public abstract class BaseActivity  extends FragmentActivity  {
	
	protected final String TAG = this.getClass().getSimpleName();

	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        this.load();
	 }
	 
	 @Override
	 protected void onResume() {
		    super.onResume();
		    MobclickAgent.onResume(this);
	 }
	 
	 @Override
	 protected void onPause() {
		    super.onPause();
		    MobclickAgent.onPause(this); 
	 }
	 
	 
	 protected String getPageName(){
		 return getClass().getSimpleName();
	 }

	 
	 @Override
	 public final boolean onCreateOptionsMenu(Menu menu) {
		 return false;
	 }

	 //加载数据，统一加载
     private void load(){
    	     Intent intent = getIntent();
    	     Uri  uri = null;
	     if (intent != null && intent.getData() != null) {
	    	     uri = intent.getData();
	     }
	     if (uri == null) {
			uri = Uri.parse("NullUri");
		}
	    onLoad(uri);
     }
     
     protected void onDestroy() {
    	     super.onDestroy();
    	     onDestoryClean();
     } 
     
  
	/**
     * 通用导航 子类通过此方法获取参数。
     * 该方法调用时view还未加载，子类不能在此方法中访问视图。。
     * */
	protected abstract void onLoad(Uri uri);
	
	
	/**
	 * 在destory时调用，采用抽象方法，防止子类忘记清理资源
	 * */
	protected abstract void onDestoryClean();
	
}
