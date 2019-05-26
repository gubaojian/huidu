package net.java.efurture.reader.admin.web.controller.me;



import java.util.List;

import javax.annotation.Resource;

import net.java.efurture.reader.admin.web.constants.ControllerName;
import net.java.efurture.reader.biz.ao.TaskAO;
import net.java.efurture.reader.mybatis.domain.TaskDO;
import net.java.efurture.reader.mybatis.query.TaskQuery;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.code.efurture.common.result.Result;


@Controller
@RequestMapping(ControllerName.ME_DATA_SYN_INFO)
public class DataSynInfo {
	
	@Resource
	private TaskAO taskAO;
	
	@RequestMapping(method = RequestMethod.GET)
	public String doGet(Byte status, Long relateId, Integer pageNum, Model model){
		if(pageNum == null || pageNum <= 0){
			pageNum = 1;
		}

		TaskQuery query = new TaskQuery();
	    query.setRelateId(relateId);
	    query.setStatus(status);
		query.setCurrentPageNum(pageNum);
		query.setPageSize(10);
		Result<List<TaskDO>> result = taskAO.getTaskListByQuery(query);
		if(!result.isSuccess()){
			model.addAttribute("message", result.getResultCode().getMessage());
		}
		model.addAttribute("taskList", result.getResult());
		model.addAttribute("query", query);
		return ControllerName.ME_DATA_SYN_INFO;
	}


}
