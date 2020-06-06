package net.java.efurture.huidu.listener;

import net.java.efurture.huidu.listener.util.ClickUtils;
import android.view.View;

public abstract class OnClickListener implements android.view.View.OnClickListener{

	@Override
	public final void onClick(View v) {
		if (ClickUtils.isFastDoubleClick()) {
			return;
		}
		this.onClickEvent(v);
	}
	
	public abstract void onClickEvent(View v);
	
	

}
