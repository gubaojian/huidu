package net.java.efurture.reader.admin.web.controller.me;

import javax.annotation.Resource;

import net.java.efurture.reader.admin.web.constants.ControllerName;
import net.java.efurture.reader.admin.web.constants.WebConstant;
import net.java.efurture.reader.admin.web.util.CsrfToken;
import net.java.efurture.reader.biz.ao.FeedAO;
import net.java.efurture.reader.biz.ao.TaskAO;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.code.efurture.common.result.Result;




@Controller
@RequestMapping(ControllerName.ME_DELETE_FEED)
public class DeleteFeed {
	
	@Resource
	FeedAO feedAO;
	
	@Resource
	TaskAO taskAO;
	
	@RequestMapping(method= RequestMethod.POST)
	public String doSubmit(Long id, Model model){
		if(id == null || id <= 0){
			model.addAttribute(WebConstant.MESSAGE_KEY, "参数非法");
			model.addAttribute(WebConstant.LATER_REDIRECT_KEY, ControllerName.ME_FEED_LIST);
			return ControllerName.ME_TIPS;
		}
		
		if(!CsrfToken.checkCsrfToken()){
			model.addAttribute(WebConstant.MESSAGE_KEY, "您的请求已过期!");
			return ControllerName.ME_TIPS;
		}

		Result<Boolean> result =  feedAO.deleteFeed(id);		
		
		
		
		
		model.addAttribute(WebConstant.MESSAGE_KEY, result.getResultCode().getMessage());
		model.addAttribute(WebConstant.LATER_REDIRECT_KEY, ControllerName.ME_FEED_LIST);
		return ControllerName.ME_TIPS;
		
	}

}
