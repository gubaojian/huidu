package net.java.efurture.reader.admin.web.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.java.efurture.reader.admin.web.constants.SessionConstant;
import net.java.efurture.reader.admin.web.domain.AccountDO;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class LoginUtils {
	

	
	
	public static void loginIn(HttpServletRequest request,HttpServletResponse response, AccountDO account){
		HttpSession session = request.getSession(true);
		session.setAttribute(SessionConstant.ACCOUNT_ID, account.getId());
		session.setAttribute(SessionConstant.ACCOUNT_NICK, account.getNick());
		
	}

	
	
	
	public static boolean isLogin(HttpServletRequest request){
		HttpSession session = request.getSession(false);
		if(session == null || session.getAttribute(SessionConstant.ACCOUNT_ID) == null){
			return false;
		}
		long accountId = NumberUtils.toLong(session.getAttribute(SessionConstant.ACCOUNT_ID).toString());
		if(accountId <= 0){
			return false;
		}
		return true;
	}
	
	
	/**获取当前用户*/
	private static long getAccountId(HttpServletRequest request){
		HttpSession session = request.getSession(false);
		if(session == null || session.getAttribute(SessionConstant.ACCOUNT_ID) == null){
			return -1l;
		}
		long accountId = NumberUtils.toLong(session.getAttribute(SessionConstant.ACCOUNT_ID).toString(), -1);
		return accountId;
	}
	
	/**获取当前用户*/
	public static long getCurrentAccountId(){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder
			      .getRequestAttributes()).getRequest();
		return getAccountId(request);
	}
	
	/**获取当前用户*/
	public static String getCurrentAccountNick(){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder
			      .getRequestAttributes()).getRequest();
		HttpSession session = request.getSession(false);
		if(session == null || session.getAttribute(SessionConstant.ACCOUNT_NICK) == null){
			return null;
		}
		return session.getAttribute(SessionConstant.ACCOUNT_NICK).toString();
	}
	
	


}
