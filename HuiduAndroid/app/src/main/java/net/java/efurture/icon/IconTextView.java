package net.java.efurture.icon;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class IconTextView  extends TextView{
	
	public IconTextView(Context context) {
		super(context);
		init();
	}

	public IconTextView(Context context, AttributeSet attrs) {
	    super(context, attrs, 0);
	    init();
	}
	
	public IconTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	
	private void init(){
		if (isInEditMode()) {
			return;
		}
		this.setTypeface(FontHelper.getIconFontTypeFace(getContext()));
	}
	
}
