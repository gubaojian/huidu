package net.java.efurture.icon;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

public class IconDrawable extends Drawable{
	
	private String icons; //图标字体
	private Paint mPaint; //默认状态画笔
	private int color;
	private int highlightColor;
	private float verticalOffset;
	private int[] hightLightedStates;


	public IconDrawable(String icons) {
		super();
		this.icons = icons;
		this.color  = Color.argb(128, 255, 255, 255);
		this.highlightColor = Color.argb(204, 255, 255, 255);
		this.mPaint = this.getPaint();
	}

	@Override
	public void draw(Canvas canvas) {
		if (TextUtils.isEmpty(icons)) {
			return;
		}
		canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG));
		int[] states = this.getState();
		boolean highlighted = false;
		 if (hightLightedStates != null) {
			 for (int state : states) {
	             for (int hightLightState : hightLightedStates) {
					if (state == hightLightState) {
						highlighted = true;
						break;
					}
				}
			 }
		 }
		 
		 if (highlighted) {
			mPaint.setColor(highlightColor);
		 }else {
			 mPaint.setColor(color);
		 }
		
		 Rect bounds = canvas.getClipBounds();
		 
		 float x = bounds.left +  bounds.width()/2;
		 float y = ((bounds.height() / 2) - ((mPaint.descent() + mPaint.ascent()) / 2)) + verticalOffset ; 
		 canvas.drawText(icons, x, y, mPaint);
	}

	@Override
	public void setAlpha(int alpha) {
		
	}

	@Override
	public void setColorFilter(ColorFilter cf) {
		
	}

	@Override
	public int getOpacity() {
		return 0;
	}
	
	
	public String getIcons() {
		return icons;
	}

	public void setIcons(String icons) {
		this.icons = icons;
	}
	
	public float getVerticalOffset() {
		return verticalOffset;
	}

	public void setVerticalOffset(float verticalOffset) {
		this.verticalOffset = verticalOffset;
	}

	public Paint getPaint(){
		if (mPaint == null) {
			mPaint = new Paint();
			mPaint.setAntiAlias(true);
			mPaint.setTextAlign(Align.CENTER);
		}
		return mPaint;
	}

	public int[] getHightLightedStates() {
		return hightLightedStates;
	}

	public void setHightLightedStates(int[] hightLightedStates) {
		this.hightLightedStates = hightLightedStates;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public int getHighlightColor() {
		return highlightColor;
	}

	public void setHighlightColor(int highlightColor) {
		this.highlightColor = highlightColor;
	}
	
	
}
