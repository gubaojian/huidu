package net.java.efurture.icon;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

public class IconButton  extends Button{

	public IconButton(Context context) {
		super(context);
		init();
	}

	public IconButton(Context context, AttributeSet attrs) {
		super(context, attrs, 0);
		init();
	}

	public IconButton(Context context, AttributeSet attrs, int defStyle) {
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
