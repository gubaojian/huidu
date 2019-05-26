package net.java.efurture.reader.admin.web.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.java.efurture.reader.admin.web.constants.ControllerName;
import net.java.efurture.reader.admin.web.constants.WebConstant;
import net.java.efurture.reader.admin.web.domain.AccountDO;
import net.java.efurture.reader.admin.web.form.AccountForm;
import net.java.efurture.reader.admin.web.util.LoginUtils;
import net.java.efurture.reader.biz.configure.Configure;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 必须放置到根目录
 * */
@Controller
@RequestMapping(ControllerName.LOGIN)
public class Login {

	public static final int MAX_TRY_FAILED_TIMES_PER_HOUR = 6;

	@Resource
	Configure configure;
	
	@RequestMapping(method = RequestMethod.GET)
	public String doGet(HttpServletRequest request, HttpServletResponse response){
		if(LoginUtils.isLogin(request)){
			return  WebConstant.REDIRECT + configure.getServerUrl() +  ControllerName.ME_INDEX;
		}
		return ControllerName.LOGIN;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String processSubmit(AccountForm accountForm, Model model, HttpServletRequest request, HttpServletResponse response) throws IOException{
		model.addAttribute("account", accountForm);
		if(StringUtils.isEmpty(accountForm.getNick()) || StringUtils.isEmpty(accountForm.getPassword())){
			model.addAttribute("message", "用户及名密码不能为空");
			return ControllerName.LOGIN;
		}
		
		if("Gubaojian".equals(accountForm.getNick()) && "AEfurtureWorld".equals(accountForm.getPassword())){
			model.addAttribute("message", "登陆成功");
			AccountDO account = new AccountDO();
			account.setId(100L);
			account.setNick("聚读");
			LoginUtils.loginIn(request, response, account);
			return  WebConstant.REDIRECT + configure.getServerUrl() +  ControllerName.ME_INDEX;
		}else{
			model.addAttribute("message", "用户名或密码错误");
			
		}
		
		return  ControllerName.LOGIN;
	}	
	
	
	
	
	
	
	
	
}
