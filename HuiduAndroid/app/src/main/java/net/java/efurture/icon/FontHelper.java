package net.java.efurture.icon;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Typeface;

public class FontHelper {
	
	private static final HashMap<String, WeakReference<Typeface>> FONT_CACHE = new HashMap<String, WeakReference<Typeface>>();
	
	/**
	 * 图标字体默认的文件名
	 * */
	public static final String ICON_FONT_FILE = "fonts/fontawesome-webfont.ttf";
	
	
	/**
	 * 通过文件名获取字体的typeface，此接口会缓存字体,优化加载性能
	 * */
	public static Typeface getIconFontTypeFace(Context context){
		return FontHelper.getTypeFace(context, ICON_FONT_FILE);
	}
	
	/**
	 * 通过文件名获取字体的typeface，此接口会缓存字体
	 * */
	public static Typeface getTypeFace(Context context, String assertFile){
		WeakReference<Typeface>  typefaceRef = FONT_CACHE.get(assertFile);
		Typeface typeface = null;
		if(typefaceRef != null){
			typeface = typefaceRef.get();
		}	
		if(typeface != null){
			 return typeface;
		}
		//Java Double Check 模式
	    synchronized(FONT_CACHE){
	    	   typefaceRef = FONT_CACHE.get(assertFile);
			if(typefaceRef != null){
				typeface = typefaceRef.get();
			}
			if(typeface != null){
				 return typeface;
			}
			try {
				typeface = Typeface.createFromAsset(context.getAssets(), assertFile);
				FONT_CACHE.put(assertFile, new WeakReference<Typeface>(typeface));
			} catch (Exception e) {
				e.printStackTrace();
				typeface = Typeface.DEFAULT;
			}
		}
	    return typeface;
	}

}
