package net.java.efurture.judu.web.controller.service;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.java.efurture.judu.web.constants.CacheTime;
import net.java.efurture.judu.web.constants.ControllerName;
import net.java.efurture.judu.web.util.ResponseCacheUtils;
import net.java.efurture.reader.biz.ao.CategoryAO;
import net.java.efurture.reader.mybatis.domain.CategoryDO;
import net.java.efurture.reader.mybatis.domain.enums.CategoryStatusEnum;
import net.java.efurture.reader.mybatis.query.CategoryQuery;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.code.efurture.common.result.Result;


@Controller
@RequestMapping(ControllerName.SEARCH_CATEGORY)
public class SearchCategory {
	@Resource
	CategoryAO categoryAO;
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody Result<List<CategoryDO>>  doGet(CategoryQuery query, HttpServletRequest request, HttpServletResponse response){
		query.setStatus(CategoryStatusEnum.NORMAL.getValue());
		query.setPageSize(ControllerName.PAGE_SIZE);
		Result<List<CategoryDO>>  categoryListResult = categoryAO.getCategoryListByQueryForApp(query);
		if(categoryListResult.isSuccess()){
			ResponseCacheUtils.privateCache(response, CacheTime.HALF_HOUR);
		}else{
		    ResponseCacheUtils.noneCache(response);
		}
		return categoryListResult;
	}

}
