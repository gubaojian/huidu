package org.biz.test;

import org.apache.http.impl.cookie.DateParseException;
import org.apache.http.impl.cookie.DateUtils;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
 
	
	public void testParset() throws DateParseException{
		
		String str = "Fri, 12 Jul 2013 23:46:37 GMT+8";

		if(str.endsWith("GMT+8")){
			str = str.substring(0, str.length()-2);
		}
		
		
		System.out.println(str);
		System.out.println(DateUtils.parseDate(str));
		
		
	}
}
