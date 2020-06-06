package net.java.efurture.huidu.view;

import net.java.efurture.huidu.R;
import net.java.efurture.icon.IconTextView;
import android.content.Context;
import android.os.IBinder;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class SearchView  extends RelativeLayout{

	private EditText mEditText;
	private IconTextView mSearchIcon;
	private IconTextView mClearIcon;
	private SearchListener mSearchListener;
	
	public SearchView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public SearchView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public SearchView(Context context) {
		super(context);
		init();
	}
	
    private void init(){
    	     LayoutInflater.from(getContext()).inflate(R.layout.searchview, this);
    	     mEditText = (EditText) this.findViewById(R.id.search_edit_text);
    	     mSearchIcon = (IconTextView) this.findViewById(R.id.search_icon);
    	     mClearIcon = (IconTextView) this.findViewById(R.id.clear_icon);
    	     mEditText.addTextChangedListener(new TextWatcher() {
				
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {}
				
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count,
						int after) {}
				@Override
				public void afterTextChanged(Editable s) {
					if (!TextUtils.isEmpty(mEditText.getText())) {
						if (mClearIcon.getVisibility() != View.VISIBLE) {
							mClearIcon.setVisibility(View.VISIBLE);
						}
					}else{
						mClearIcon.setVisibility(View.GONE);
					}
				}
			});
    	     mEditText.setOnKeyListener(new OnKeyListener() {
				
				@Override
				public boolean onKey(View v, int keyCode, KeyEvent event) {
					if (event.getAction() != KeyEvent.ACTION_UP) {
						return false;
					}
					
					if (!(keyCode == KeyEvent.KEYCODE_SEARCH 
							|| keyCode == KeyEvent.KEYCODE_ENTER)) {
						return false;
					}
					return startSearch();
				}
			});
    	     mClearIcon.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					mEditText.setText("");
				}
	     });
    	     
    	     mSearchIcon.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					startSearch();
				}
		 });
    	     this.postDelayed(new Runnable() {
				
				@Override
				public void run() {
					InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
					if (imm == null) {
						return;
					}
					imm.showSoftInput(mEditText, InputMethodManager.SHOW_IMPLICIT);
				}
			}, 100);
    }
	
    private boolean startSearch(){
    	    if (TextUtils.isEmpty(mEditText.getText())
    	    		|| TextUtils.isEmpty(mEditText.getText().toString().trim())) {
    	    	    Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
			this.startAnimation(animation);	
			this.requestFocus();
			return false;
		}
    	    hidenKeyboad();
    	    if (mSearchListener != null) {
    	       	mSearchListener.search(mEditText.getText().toString());
		}
    	    return true;
    }
    
    private void hidenKeyboad(){
      	InputMethodManager inputManager =  (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE); 
      	if (inputManager == null) {
			return;
		}
      	IBinder token  = getWindowToken();
     	if (token == null) {
			return;
		}
      	inputManager.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS); 
    }
    
    
    public SearchListener getSearchListener() {
		return mSearchListener;
	}

	public void setSearchListener(SearchListener searchListener) {
		this.mSearchListener = searchListener;
	}


	public static interface SearchListener{
    	      public void search(String keyword);
    }
}
