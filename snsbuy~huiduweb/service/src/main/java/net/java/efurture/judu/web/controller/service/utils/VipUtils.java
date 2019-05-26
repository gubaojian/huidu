package net.java.efurture.judu.web.controller.service.utils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.java.mapyou.mybatis.domain.DeviceDO;

public class VipUtils {
	
	protected static final Logger logger = LoggerFactory.getLogger(VipUtils.class);
	
	
    /**豁免设备ID，用于处理内置购买无效的情况*/
	public static final List<Long> buyDeviceIds = new ArrayList<Long>();
	static{
		buyDeviceIds.add(96L);
		buyDeviceIds.add(124L);
		buyDeviceIds.add(159L);
	}
	
	
	public static void main(String []args){
		
		System.out.println(showRemoteTrackGpsGuideVip(2, new Date()));
		
		System.out.println(showRemoteTrackGpsGuideVip(10, new Date()));
		
		
		System.out.println(showRemoteTrackGpsGuideVip(7, new Date(System.currentTimeMillis() - 1000L*60*60*24*40)));
	}
	
	public static boolean checkIsVip(HttpServletRequest request, DeviceDO device){
		String buyDateStr = request.getParameter("buyDate");
		if(buyDeviceIds.contains(device.getId())){
			logger.error("checkIsVip private " + buyDateStr + " device id " + device.getId() 
			+ "  " + device.getName());
			return true;
		}
		if(StringUtils.isEmpty(buyDateStr)){
			logger.error("checkIsVip buy date null " + buyDateStr + " device id " + device.getId() 
			+ "  " + device.getName());
			return true;
		}
	
		try {
			logger.error("checkIsVip buy date " + buyDateStr + " device id " + device.getId() 
			+ "  " + device.getName());
			Date buyDate = DateUtils.parseDate(buyDateStr, new String[]{"yyyy-MM-dd HH:mm:ss"});
			Date now = new Date();
			return DateUtils.addDays(buyDate, 367).after(now);
		} catch (ParseException e) {
			logger.error("checkIsVip error " + buyDateStr + " " + device.getId(), e);
			return false;
		}
	}
	
	/**
	 * 10条以上一律收费
	 * 5-10条， 30天内免费，30天以上收费
	 * 5条一下 一律免费，
	 * */
	public static boolean showRemoteTrackGpsGuideVip(int count, Date gmtCreate){
		if(gmtCreate == null){
			return false;
		}
		if(count < 5){
			return false;
		}
		if(count >= 10){
			return true;
		}
		Date now = new Date();
		return DateUtils.addDays(gmtCreate, 30).before(now);
	}
	
	
	
	/**
	 * 超过100条，一律开始收费
	 * 低于10条一条或者7天以内免费
	 * 10-100条之间，超过30条收费，低于30天免费
	 * */
	public static boolean showDeviceTrackGpsGuideVip(int count, Date gmtCreate){
		if(gmtCreate == null){
			return false;
		}
		if(count < 10){
			return false;
		}
		if(count >= 100){
			return true;
		}
		Date now = new Date();
		return DateUtils.addDays(gmtCreate, 30).before(now);
	}
	
	
	public static Date getDotDate(HttpServletRequest request, String key){
		String dateStr = request.getParameter(key);
		if(StringUtils.isEmpty(dateStr)){
			return new Date(System.currentTimeMillis() - 1000l*60*60*24*7);
		}
		try {
			Date date = DateUtils.parseDate(dateStr, new String[]{"yyyy-MM-dd HH:mm:ss"});
			return date;
		} catch (ParseException e) {
			return new Date(System.currentTimeMillis() - 1000l*60*60*24*7);
		}
	}
	
}
