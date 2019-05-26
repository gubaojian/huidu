package net.java.efurture.reader.admin.web.controller.me;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import net.java.efurture.reader.admin.web.constants.ControllerName;
import net.java.efurture.reader.admin.web.constants.WebConstant;
import net.java.efurture.reader.admin.web.form.FeedForm;
import net.java.efurture.reader.admin.web.util.CsrfToken;
import net.java.efurture.reader.biz.ao.FeedAO;
import net.java.efurture.reader.biz.ao.TaskAO;
import net.java.efurture.reader.mybatis.domain.FeedDO;
import net.java.efurture.reader.mybatis.domain.TaskDO;
import net.java.efurture.reader.mybatis.domain.enums.TaskPriorityEnum;
import net.java.efurture.reader.mybatis.domain.enums.TaskStatusEnum;
import net.java.efurture.reader.mybatis.domain.enums.TaskTypeEnum;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.code.efurture.common.result.Result;



@Controller
@RequestMapping(ControllerName.ME_FEED)
public class Feed {

	
	@Resource
	FeedAO feedAO;
	
	@Resource
	TaskAO taskAO;
	

	@RequestMapping(method = RequestMethod.GET)
	public String doGet(Long id,  HttpServletRequest request, Model model){
		if(id == null){
			model.addAttribute("feed", new FeedDO());
			return ControllerName.ME_FEED;
		}
		
		Result<FeedDO> result = feedAO.getFeedById(id);
		
		if(!result.isSuccess()){
			model.addAttribute(WebConstant.MESSAGE_KEY,  result.getResultCode().getMessage());
			model.addAttribute(WebConstant.LATER_REDIRECT_KEY, ControllerName.ME_FEED_LIST);
			return ControllerName.ME_TIPS;
		}
		
		if(result.getResult() == null){
			model.addAttribute(WebConstant.MESSAGE_KEY,  "feed不存在");
			model.addAttribute(WebConstant.LATER_REDIRECT_KEY, ControllerName.ME_FEED_LIST);
			return ControllerName.ME_TIPS;
		}
		
		FeedDO feedDO = result.getResult();
		model.addAttribute("feed", feedDO);
		return ControllerName.ME_FEED;
	}
	
	
	@RequestMapping(method = RequestMethod.POST)
	public String doPost(@Valid @ModelAttribute("feed") FeedForm feed, BindingResult validateResult, Model model, HttpServletRequest request){
		if(!CsrfToken.checkCsrfToken()){
			model.addAttribute(WebConstant.MESSAGE_KEY, "您的请求已过期!");
			return ControllerName.ME_TIPS;
		}

		if(validateResult.hasErrors()){
			model.addAttribute("errors", validateResult);
			return ControllerName.ME_FEED;
		}
		
		model.addAttribute("feed", feed);
		FeedDO  feedDO = new FeedDO();
		BeanUtils.copyProperties(feed, feedDO);
		
		
		Result<Boolean> result = null;
		
		//插入或者编辑
		if(feed.getId() != null){
			result  = feedAO.updateFeed(feedDO);
		}else{
			result =  feedAO.addFeed(feedDO);
		}
		
		this.addTaskIfAbsent(feedDO, result);
		
		if(!result.isSuccess()){
			model.addAttribute(WebConstant.MESSAGE_KEY, result.getResultCode().getMessage());
			return ControllerName.ME_TIPS;
		}
		model.addAttribute(WebConstant.MESSAGE_KEY, "提示：操作成功");
		model.addAttribute(WebConstant.LATER_REDIRECT_KEY, ControllerName.ME_FEED_LIST);
		return  ControllerName.ME_TIPS;
	}
	


	/** 添加同步任务  */
	private void addTaskIfAbsent(FeedDO feedDO, Result<Boolean> result) {
		if(result.isSuccess()){
			TaskDO task = new TaskDO();
			task.setRelateId(feedDO.getId());
			task.setStatus(TaskStatusEnum.SYN_CIRCLE.getValue());
			task.setPriority(TaskPriorityEnum.DEFAULT_PRIORITY.getValue());
			Byte type = TaskTypeEnum.SYN_NORMAL.getValue();
			if(TaskTypeEnum.valueOf(feedDO.getType()) != null){
				type = TaskTypeEnum.valueOf(feedDO.getType()).getValue();
			}
			task.setType(type);
			task.setScheduleTime(new Date());
			taskAO.addTaskIfAbsent(task);
		}
	}
}
