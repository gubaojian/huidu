package net.java.efurture.judu.web.util;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

public class RedirectUtils  {
	
	 private static final String APP_HOME = "http://huidu.lanxijun.com";
		
	
	public static void toHome(HttpServletResponse response) throws IOException{
		   response.sendRedirect(APP_HOME);
	}

}
