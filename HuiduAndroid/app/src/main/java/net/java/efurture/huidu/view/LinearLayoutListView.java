package net.java.efurture.huidu.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Adapter;
import android.widget.LinearLayout;

public class LinearLayoutListView extends LinearLayout{

	private Adapter mAdapter;

	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public LinearLayoutListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public LinearLayoutListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public LinearLayoutListView(Context context) {
		super(context);
	}

	public Adapter getAdapter() {
		return mAdapter;
	}

	public void setAdapter(Adapter mAdapter) {
		this.mAdapter = mAdapter;
		initForAdapter();
	}

	private void initForAdapter(){
		this.removeAllViews();
		if (mAdapter == null) {
			return;
		}
		int count = mAdapter.getCount();
		if (count == 0) {
			return;
		}
		for(int position =0; position<count; position++){
			View view =  mAdapter.getView(position, null, this);
			if (view != null) {
				this.addView(view);
			}
		}
	}	
}
