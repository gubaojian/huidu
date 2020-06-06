package net.java.efurture.huidu.util;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;


public class ScreenUtil {
	
	
    public static Point getSize(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return new Point(metrics.widthPixels, metrics.heightPixels);
    }
    
    public static int getWidth(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.widthPixels;
    }

    public static int  getHeight(Context context) {
       	DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.heightPixels;
    }
   

}
