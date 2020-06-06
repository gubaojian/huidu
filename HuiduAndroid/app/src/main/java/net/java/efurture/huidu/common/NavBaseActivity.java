package net.java.efurture.huidu.common;

import net.java.efurture.huidu.R;
import net.java.efurture.huidu.gesture.SwipGestureListener;
import net.java.efurture.huidu.listener.util.ClickUtils;
import android.R.anim;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;




public abstract class NavBaseActivity extends BaseActivity{
	
	private GestureDetector mGestureDetector;
	
	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        mGestureDetector = new GestureDetector(this, new SwipGestureListener(){
	        	    @Override
	        	    public void onSwipeRight() { 
	        	    	    if (ClickUtils.isFastDoubleClick()) {
								return;
					        }
	        	    	    finish();
	        	    }
	        });
	       overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
	 }
	
	 @Override
	 public void finish() {
		 super.finish();
	     overridePendingTransition(anim.slide_in_left, anim.slide_out_right);
	 }


	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		mGestureDetector.onTouchEvent(ev);
		return super.dispatchTouchEvent(ev);
	}

	@Override
	protected void onDestoryClean() {
		
	}
}
