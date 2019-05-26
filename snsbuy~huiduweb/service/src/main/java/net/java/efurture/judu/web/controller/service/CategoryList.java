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

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.code.efurture.common.result.Result;


/**
 * 
 * http://127.0.0.1:8080/service/categoryList.json
 * */
@Controller
@RequestMapping(ControllerName.CATEGORY_LIST)
public class CategoryList {
	
	
	
	@Resource
	CategoryAO categoryAO;
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody Result<List<CategoryDO>>  doGet(HttpServletRequest request, HttpServletResponse response){
		int pageNum = NumberUtils.toInt(request.getParameter("pageNum"), 1);
		CategoryQuery  query = new CategoryQuery();
		query.setCurrentPageNum(pageNum);
		query.setStatus(CategoryStatusEnum.NORMAL.getValue());
		query.setPinyin(null);
		query.setNameList(null);
		query.setPageSize(24);
		Result<List<CategoryDO>>  categoryListResult = categoryAO.getCategoryListByQueryForApp(query);
		if(categoryListResult.isSuccess()){
			ResponseCacheUtils.publicCache(response, CacheTime.HALF_HOUR);
		}else{
			ResponseCacheUtils.noneCache(response);
		}
		
		return categoryListResult;
	}

}
