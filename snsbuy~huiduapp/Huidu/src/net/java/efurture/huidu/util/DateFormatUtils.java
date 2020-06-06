package net.java.efurture.huidu.util;

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class DateFormatUtils {

	private static final SimpleDateFormat TODAY_FORMAT = new SimpleDateFormat("今天 HH:mm:ss");
	private static final SimpleDateFormat YESTERDAY_FORMAT = new SimpleDateFormat("昨天 HH:mm:ss");
	private static final SimpleDateFormat BEFORE_YESTERDAY_FORMAT = new SimpleDateFormat("前天 HH:mm:ss");
	private static final SimpleDateFormat NORMAL_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	
	public static final String format(Date publishDate){
		if (publishDate == null) {
			return "";
		}
		Calendar now = Calendar.getInstance();
		Calendar target = Calendar.getInstance();
		target.setTime(publishDate);
		if(now.get(Calendar.YEAR) == target.get(Calendar.YEAR)){
			int day = now.get(Calendar.DAY_OF_YEAR) - target.get(Calendar.DAY_OF_YEAR);
			if(day == 0){
				return TODAY_FORMAT.format(publishDate);
			}else if (day == 1) {
				return YESTERDAY_FORMAT.format(publishDate);
			}else if (day == 2) {
				return BEFORE_YESTERDAY_FORMAT.format(publishDate);
			}
		}
		return NORMAL_FORMAT.format(publishDate);
	}
}
