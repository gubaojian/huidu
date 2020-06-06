package net.java.efurture.huidu;

import java.io.IOException;
import java.net.ResponseCache;
import java.util.List;

import net.java.efurture.huidu.domain.Category;
import net.java.efurture.util.GLog;
import android.app.Application;

import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.SimpleType;
import com.google.code.client.GUClient;
import com.google.code.efurture.kvdb.KVDBManager;
import com.squareup.okhttp.HttpResponseCache;
import com.umeng.analytics.MobclickAgent;

public class DuApplication  extends Application{

	    @Override
	    public void onCreate() {
	    	     super.onCreate();
	    	     init();
	    	     
	    }

	    @Override
	    public void onTerminate() {
	       MobclickAgent.onKillProcess(getApplicationContext());
	       super.onTerminate();
	    }
	    
	    private void init(){
   	         KVDBManager.setContext(getApplicationContext());
	    	     GLog.LEVEL = Integer.valueOf(this.getResources().getString(R.string.log_level).trim());
	    	     initHttpClient();
	    }
	    
	    private void initHttpClient(){
	    	    try {
			   GUClient.initClient(this.getResources().getString(R.string.server));
			   ResponseCache.setDefault(new HttpResponseCache(this.getFilesDir(), 1024*1024*10));
			   initDomainModels();
	    	    } catch (IOException e) {
				e.printStackTrace();
		    }
	    }
	    
	    private void initDomainModels(){
	    	    GUClient.resister("categoryList", CollectionType.construct(List.class,  SimpleType.construct(Category.class)));
	    } 
}
