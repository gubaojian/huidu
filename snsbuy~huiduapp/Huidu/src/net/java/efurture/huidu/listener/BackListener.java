package net.java.efurture.huidu.listener;

import net.java.efurture.huidu.config.Pages;
import net.java.efurture.nav.Nav;
import android.app.Activity;
import android.view.View;

public class BackListener extends OnClickListener{
	
	private Activity attachActivity;

	public BackListener(Activity activity){
		attachActivity = activity;
	}

	@Override
	public void onClickEvent(View v) {
		if(attachActivity.isTaskRoot()){
			attachActivity.finish();
			Nav.from(attachActivity).toPath(Pages.INDEX);
		}else {
			attachActivity.finish();
		}
	}
}