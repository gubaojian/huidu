package net.java.efurture.huidu.common;

import net.java.efurture.huidu.R;
import net.java.efurture.huidu.activity.AboutActivity;
import net.java.efurture.huidu.activity.DiscoverActivity;
import net.java.efurture.huidu.activity.FavoriteActivity;
import net.java.efurture.huidu.activity.HomeActivity;
import net.java.efurture.huidu.util.DensityUtil;
import net.java.efurture.icon.FontHelper;
import net.java.efurture.icon.HighLightStatesSet;
import net.java.efurture.icon.IconDrawable;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.util.StateSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;


public abstract class TabBaseActivity extends BaseActivity{
	
	 private LinearLayout mContentContainer;
	 private RadioButton mCurrentRadioButton;
	 private boolean exit = false;
	
	 
	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        super.setContentView(R.layout.base_tab_host);
	        mContentContainer = (LinearLayout) this.findViewById(R.id.tab_content_container);
	        this.registerTabToActivityMap(R.id.tab_main, R.string.fa_home, HomeActivity.class);
	        this.registerTabToActivityMap(R.id.tab_favorite,R.string.fa_star_o, FavoriteActivity.class);
	        this.registerTabToActivityMap(R.id.tab_search,R.string.fa_search, DiscoverActivity.class);
	        this.registerTabToActivityMap(R.id.tab_about,R.string.fa_info_circle, AboutActivity.class);
	 }

	@Override
	public void setContentView(int layoutResID) {
		mContentContainer.removeAllViews();
		LayoutInflater.from(getBaseContext()).inflate(layoutResID, mContentContainer);
	}

	@Override
	public void setContentView(View view) {
		mContentContainer.removeAllViews();
		mContentContainer.addView(view);
	}

	@Override
	public void setContentView(View view, LayoutParams params) {
		mContentContainer.removeAllViews();
		mContentContainer.addView(view, params);
	}

	@Override
	public void addContentView(View view, LayoutParams params) {
		mContentContainer.addView(view, params);
	} 
	
	@Override
	protected void onResume(){
		 super.onResume();
		 if (!mCurrentRadioButton.isChecked()) {
			 mCurrentRadioButton.setChecked(true);
		 }
		 overridePendingTransition(0, 0);
	} 
	
	@Override
	public boolean dispatchKeyEvent(KeyEvent event){
		if(event.getAction() != KeyEvent.ACTION_UP) {
			return super.dispatchKeyEvent(event);
		}
		if (event.getKeyCode() != KeyEvent.KEYCODE_BACK) {
			return super.dispatchKeyEvent(event);
		}
		if (exit) {
			exitHuidu();
			return true;
		}
		exit = true;
		Toast.makeText(this, R.string.click_more_exit, Toast.LENGTH_SHORT).show();
		mContentContainer.postDelayed(new Runnable() {
			@Override
			public void run() {
				exit = false;
			}
		}, 2000);
		return true;
	}
	
	
	
	
	protected void onDestoryClean(){
		 mContentContainer = null;
		 mCurrentRadioButton = null;
	}
	
	
	
	private void registerTabToActivityMap(int tabId, int iconId, Class<?> activityClass){
		  RadioButton radioButton = (RadioButton) this.findViewById(tabId);
		  radioButton.setCompoundDrawables(null, getTabIconDrawableFromString(iconId), null, null);
	      radioButton.setOnCheckedChangeListener(new TabHostListener(this, activityClass));
	      if (this.getClass() == activityClass) {
			  radioButton.setChecked(true);
			  mCurrentRadioButton = radioButton;
		  }  
	}
	
	private StateListDrawable getTabIconDrawableFromString(int stringId){
		StateListDrawable stateListDrawable = new StateListDrawable();
		IconDrawable iconFontDrawable = new IconDrawable(
				this.getResources().getString(stringId));
		iconFontDrawable.setHighlightColor(this.getResources().getColor(R.color.tab_icon_checked));
		iconFontDrawable.getPaint().setTypeface(FontHelper.getIconFontTypeFace(getBaseContext()));
		iconFontDrawable.getPaint().setTextSize(DensityUtil.dip2px(getBaseContext(), 26));
		iconFontDrawable.setVerticalOffset(-DensityUtil.dip2px(getBaseContext(), 16));
		iconFontDrawable.setHightLightedStates(HighLightStatesSet.CHECK_BOX_HIGHT_LIGHT_STATES_SET);
		stateListDrawable.addState(StateSet.WILD_CARD, iconFontDrawable);
	    return stateListDrawable;	
	}
	
	private void exitHuidu(){
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}
	

	public static class TabHostListener implements OnCheckedChangeListener{
         private Activity currentActivity;
         private Class<?> targetTabClass;
   
		public TabHostListener(Activity currentActivity, Class<?> targetTabClass) {
			super();
			this.currentActivity = currentActivity;
			this.targetTabClass = targetTabClass;
		}

		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			if (isChecked) {
			    if (currentActivity.getClass() != targetTabClass) {
			    	    Intent intent = new Intent(currentActivity, targetTabClass);
			    	    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
					currentActivity.startActivity(intent);
					currentActivity.overridePendingTransition(0, 0);
				}	
			}
		}
	}
}
