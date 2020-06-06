package net.java.efurture.huidu.view;

import net.java.efurture.huidu.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

public class LoadMaskView extends FrameLayout{
	
	public static interface ReloadTask {
		public void reloadForError();
	}
	
	private  ReloadTask  reloadTask;

	public LoadMaskView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public LoadMaskView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public LoadMaskView(Context context) {
		super(context);
		init();
	}

	private void init(){
		LayoutInflater.from(getContext()).inflate(R.layout.load_mask, this);
	}
	
	
	public void showLoadingMask(){
		this.findViewById(R.id.loading_app_name).setVisibility(View.VISIBLE);
		this.findViewById(R.id.loading_slogan).setVisibility(View.VISIBLE);
		this.findViewById(R.id.loading).setVisibility(View.VISIBLE);
		this.findViewById(R.id.load_error_tips).setVisibility(View.GONE);
		this.findViewById(R.id.load_error_icon).setVisibility(View.GONE);
		this.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {}
		});
		this.setVisibility(View.VISIBLE);
	}
	
	
	public void showErrorMask(){
		this.findViewById(R.id.loading_app_name).setVisibility(View.GONE);
		this.findViewById(R.id.loading_slogan).setVisibility(View.GONE);
		this.findViewById(R.id.loading).setVisibility(View.GONE);
		this.findViewById(R.id.load_error_tips).setVisibility(View.VISIBLE);
		this.findViewById(R.id.load_error_icon).setVisibility(View.VISIBLE);
		this.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (reloadTask != null) {
					reloadTask.reloadForError();
					showLoadingMask();
				}
			}
		});
		this.setVisibility(View.VISIBLE);
	}
	
	public void hideMask(){
		if (this.getVisibility() != View.GONE) {
			this.setVisibility(View.GONE);
		}
	}
	

	public ReloadTask getReloadTask() {
		return reloadTask;
	}
	
	public void setReloadTask(ReloadTask reloadTask) {
		this.reloadTask = reloadTask;
	}	
}
