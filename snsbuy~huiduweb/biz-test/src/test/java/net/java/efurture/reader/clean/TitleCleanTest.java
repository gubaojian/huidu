package net.java.efurture.reader.clean;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.springframework.util.StringUtils;

import net.java.efurture.reader.clean.title.TitleCleaner;
import net.java.efurture.reader.utils.TrimUtils;

public class TitleCleanTest {
	
	@Test
	public void testTrim(){
		
		String title = "       Transfer是一个主从多线程同步工具，直接patch在MySQL中。2011年";

		System.out.println(TitleCleaner.clean(title));
		
		
		System.out.println(TrimUtils.trim(title));
		
		Pattern pattern = Pattern.compile("[ \t\n\\f\r]+");
		Matcher matcher = pattern.matcher(title);
		
		System.out.println("find " + matcher.find());
		System.out.println("find " + matcher.replaceAll(""));
		
		System.out.println(pattern.matcher(title).replaceAll(""));
		for(int i=0; i<10; i++){
			char ch = title.charAt(i);
			System.out.println(Character.isSpaceChar(ch));
		}
		
	}
	@Test
	public void testEnptyTitleTrim(){
		
		String title = " InnoDB建表时设定初始大小 (Setting InnoDB table datafile initial size when create new table)";
		System.out.println(TitleCleaner.clean(title));
	}
	
	

}
