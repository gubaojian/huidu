package net.java.efurture.reader.admin.web.controller.me;

import java.util.List;

import javax.annotation.Resource;

import net.java.efurture.reader.admin.web.constants.ControllerName;
import net.java.efurture.reader.biz.ao.CategoryAO;
import net.java.efurture.reader.biz.ao.TaskAO;
import net.java.efurture.reader.mybatis.domain.CategoryDO;
import net.java.efurture.reader.mybatis.domain.TaskDO;
import net.java.efurture.reader.mybatis.query.CategoryQuery;
import net.java.efurture.reader.mybatis.query.TaskQuery;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.code.efurture.common.result.Result;



@Controller
@RequestMapping(ControllerName.ME_CATEGORY_LIST)
public class CategoryList {
	
	
	@Resource
	private CategoryAO categoryAO;
	
	@RequestMapping(method = RequestMethod.GET)
	public String doGet(CategoryQuery query, Model model){
		query.setPageSize(40);
		Result<List<CategoryDO>> result = categoryAO.getCategoryListByQuery(query);
		if(!result.isSuccess()){
			model.addAttribute("message", result.getResultCode().getMessage());
		}
		model.addAttribute("categoryList", result.getResult());
		model.addAttribute("query", query);
		return ControllerName.ME_CATEGORY_LIST;
	}


}
