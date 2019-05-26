package net.java.efurture.reader.format;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatTest {
	
	
	public static void main(String [] args) throws ParseException{
		
		
	
		
		// 	Wed, 08 Jan 2014 06:42:59 GMT
		SimpleDateFormat format = new SimpleDateFormat("E, dd MMM yyyy hh:mm:ss ZZZZ", Locale.US);
		
		System.out.println(format.format(new Date()));
		
		
		
		//Tue, 03 Sep 2013 00:55:59 +0800
		
		
        format = new SimpleDateFormat("E, dd MMM yyyy hh:mm:ss ZZZZ", Locale.US);
        
        String target = "Tue, 03 Sep 2013 00:55:59 +0800"; 
		
		System.out.println(format.format(new Date()));
		
		System.out.println(format.parse(target));
		
		System.out.println(format.parse("Fri, 30 Aug 2013 03:37:32 +0000"));
		 System.out.println(format.parse("Tue, 03 Sep 2013 12:00:38 +0000"));
		 
		 
		 
		 format = new SimpleDateFormat("E,dd MMM yyyy hh:mm:ss ZZZZ", Locale.US);
		 System.out.println(format.parse("Wed,14 Nov 2012 17:19:52 +0800"));
			
			

		 
		 
		 format = new SimpleDateFormat("E, dd MMM yyyy hh:mm:ss", Locale.US);
	     target = "Wed, 21 Aug 2013 16:00:00 +800";
	     System.out.println(format.format(new Date()));
		 System.out.println(format.parse("Wed, 21 Aug 2013 16:00:00 +800"));

		
		

        format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.US);
        target = "2013-08-20 10:52:41";
		
		System.out.println(format.format(new Date()));
		
		System.out.println(format.parse(target));
		
		
        format = new SimpleDateFormat("E, dd MMM yyyy hh:mm:ss", Locale.US);
        
        target = "Wed, 21 Aug 2013 16:00:00 +800"; 
		
		System.out.println(format.format(new Date()));
		
		System.out.println(format.parse(target));
		
		System.out.println(format.parse("Fri, 30 Aug 2013 12:30:00 GMT"));
		
		System.out.println(format.parse("Tue, 03 Sep 2013 02:16:38 GMT"));
		
		
		 format = new SimpleDateFormat("MMM dd yyyy - hh:mm:ss ZZZZ", Locale.US);
	        
	     target = "Aug 03 2013 - 11:18:01 +0000"; 
			
	     System.out.println(format.format(new Date()));
		 System.out.println(format.parse(target));
		
		 
		 format = new SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.US);
	        
	     target = "2013-08-31 20:26"; 
			
	     System.out.println(format.format(new Date()));
		 System.out.println(format.parse(target));
		 
		 
		 format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.US); 
	     target = "2013-08-20 10:52:41"; 
	     System.out.println(format.format(new Date()));
		 System.out.println(format.parse(target));
		 
		 
		 format = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'", Locale.US); 
	     target = " 2013-08-31T10:28:27Z"; 
	     System.out.println(format.format(new Date()));
		 System.out.println(format.parse(target));
		 
		 
		
		 
		 
		 
		 
		
		
	}

}
