package com.google.code.efurture.common.pinyin;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinyinUtils {

	private static final HanyuPinyinOutputFormat PINYIN_OUT_FORMAT = new HanyuPinyinOutputFormat();  
	static{
		PINYIN_OUT_FORMAT.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		PINYIN_OUT_FORMAT.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		PINYIN_OUT_FORMAT.setVCharType(HanyuPinyinVCharType.WITH_V);
	}
	
	/**
	 * 返回小写的拼音及英文字母
	 * */
	public static String toPinyin(String name){
		StringBuffer buf = new StringBuffer();
		for(int i=0; i<name.length(); i++){
			char ch = name.charAt(i);
			if(ch < 128){
				buf.append(Character.toLowerCase(ch));
			}else{
				try {
					String[] pinyins =  PinyinHelper.toHanyuPinyinStringArray(ch, PINYIN_OUT_FORMAT);
					if(pinyins != null && pinyins.length > 0){
						buf.append(pinyins[0]);
						continue;
					}
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
				}
				buf.append(ch);
			}
		}
		return buf.toString();
	}
	
	
	
	public static void main(String [] args){
		
		for(int i=0; i<100; i++){
			System.out.println(toPinyin("谷宝剑测错错错错侧耳测四点多是否").length());
			
			System.out.println(toPinyin("陈浩 测试"));
			
			System.out.println(toPinyin("robbin的自言自语"));
		}		
	}
	
}
