package net.java.efurture.huidu.service;

import net.java.efurture.huidu.util.GUConfig;

import org.apache.commons.lang.math.NumberUtils;

import android.content.Context;

public class FontConfigService {
	
	    public static final int FONT_SIZE_SMALL = 0;

		public static final int FONT_SIZE_NORMAL = 1;

		public static final int FONT_SIZE_LARGE = 2;

		public static final int FONT_SIZE_LARGEST = 3;
		
		
		public static void setFontSize(Context context,  int fontSize){
			GUConfig.put(context, "fontSize", fontSize + "");
		}
		public static int getFontSize(Context context){
			return NumberUtils.toInt(GUConfig.get(context, "fontSize"), FONT_SIZE_NORMAL);
		}
		
		public static String getFontSizePx(Context context){
			int fontSize = getFontSize(context);
			 if (fontSize == FONT_SIZE_SMALL) {
	        	    return "16px";
	        }else if (fontSize == FONT_SIZE_LARGE){
	        	    return "22px";
	        }else if (fontSize == FONT_SIZE_LARGEST){
	        	    return "24px";
	        }else{
	           	return "18px";
	        }
		}

}
