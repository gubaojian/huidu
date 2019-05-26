package net.java.efurture.reader.biz.ao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;

import net.java.efurture.reader.biz.ao.CategoryAO;
import net.java.efurture.reader.biz.sort.SortUtils;
import net.java.efurture.reader.mybatis.domain.CategoryDO;
import net.java.efurture.reader.mybatis.domain.enums.CategoryStatusEnum;
import net.java.efurture.reader.mybatis.mapper.CategoryDOMapper;
import net.java.efurture.reader.mybatis.query.CategoryQuery;

import com.google.code.efurture.common.ao.BaseAO;
import com.google.code.efurture.common.api.ApiConstants;
import com.google.code.efurture.common.pinyin.PinyinUtils;
import com.google.code.efurture.common.result.Result;
import com.google.code.efurture.common.resultcode.BaseResultCode;

public class CategoryAOImpl extends BaseAO implements CategoryAO {

	@Resource
	CategoryDOMapper categoryDOMapper;
	
	
	

	
	/**
	 * 增加拼音字段
	 * */
	@Override
	public Result<Boolean> addCategoryList(List<CategoryDO> categoryList) {
		Result<Boolean> result = this.createResult();
		try{
			if(categoryList == null || categoryList.size() == 0){
				result.setResult(true);
				result.setSuccess(true);
				return result;
			}
			
			
			CategoryQuery query = new CategoryQuery();
			List<String> nameList = new ArrayList<String>(categoryList.size());
			for(CategoryDO category : categoryList){
				category.setGmtCreate(SortUtils.getSortDate());
				if(category.getName().length() > 32){
					category.setName(category.getName().substring(0, 32));
				}
				category.setName(category.getName().toLowerCase().trim());
				category.setPinyin(PinyinUtils.toPinyin(category.getName()));
				if(category.getGmtModified() == null){
					category.setGmtModified(new Date());
				}
				nameList.add(category.getName());
			}
			query.setNameList(nameList); //仅根据名字查询, 忽略是否删除
			
			List<CategoryDO> addedCategoryList = categoryDOMapper.selectByQuery(query);
			List<CategoryDO> needAddList = new ArrayList<CategoryDO>();
			
			for(CategoryDO  category : categoryList){
				boolean contains = false;
				for(CategoryDO addedCategory : addedCategoryList){
					if(category.getName().equals(addedCategory.getName())){
						BeanUtils.copyProperties(category, addedCategory);
						contains = true;
					}
				}
				
				if(!contains){
					needAddList.add(category);
				}
			}
			
			
			if(needAddList.size() == 0){
				result.setResult(true);
				result.setSuccess(true);
				return result;
			}

			
			for(CategoryDO needAdd : needAddList){
				
				try{
					needAdd.setStatus(CategoryStatusEnum.WAIT.getValue()); //默认是待升级的状态
					categoryDOMapper.insert(needAdd);  //长多超过规定长度的等会出错，出错后直接忽略该条。
				}catch(Exception e){
					logger.error("insert category error " + needAdd.getName() , e);
					categoryList.remove(needAdd);
				}
			}
			result.setResult(true);
			result.setSuccess(true);
			return result;
		}catch(Exception e){
			logger.error("addCategoryList error", e);
			result.setResultCode(BaseResultCode.SYSTEM_EXCEPTION);
		    result.setThrowable(e);
			result.setSuccess(false);
			return result;
		}
	}

	
	
	@Override
	public Result<List<CategoryDO>> getCategoryListByQuery(CategoryQuery query) {
		Result<List<CategoryDO>> result = this.createResult();
		try{
			
			int count = categoryDOMapper.countByQuery(query);
			query.setTotalCount(count);
			if(count <= 0){
				result.setResult(new ArrayList<CategoryDO>());
				result.setSuccess(true);
				return result;
			}
		
			List<CategoryDO> categoryList = categoryDOMapper.selectByQuery(query);
			result.setResult(categoryList);
			result.setSuccess(true);
			return result;
		}catch(Exception e){
			logger.error("getCategoryListByQuery error", e);
			result.setResultCode(BaseResultCode.SYSTEM_EXCEPTION);
		    result.setThrowable(e);
			result.setSuccess(false);
			return result;
		}
	}
	
	public Result<List<CategoryDO>> getCategoryListByQueryForApp(CategoryQuery query){
		Result<List<CategoryDO>> result = this.createResult();
		try{
			List<CategoryDO> categoryList = categoryDOMapper.selectByQuery(query);
			if(categoryList == null){
				 categoryList = new  ArrayList<CategoryDO>();
			}
			result.setResult(categoryList);
			result.getModels().put(ApiConstants.HAS_MORE_KEY, categoryList.size() == query.getPageSize());
			result.setSuccess(true);
			return result;
		}catch(Exception e){
			logger.error("getCategoryListByQueryForApp error", e);
			result.setResultCode(BaseResultCode.SYSTEM_EXCEPTION);
		    result.setThrowable(e);
			result.setSuccess(false);
			return result;
		}
	}

	
	@Override
	public Result<Boolean> updateCategory(CategoryDO category) {
		Result<Boolean> result = this.createResult();
		try{
			if(category == null || category.getId() == null){
				result.setResult(false);
				result.setSuccess(false);
				return result;
			}
			int effectCount = categoryDOMapper.updateByPrimaryKey(category);
			result.setResult(effectCount > 0);
			result.setSuccess(true);
			return result;
		}catch(Exception e){
			logger.error("updateCategory error", e);
			result.setResultCode(BaseResultCode.SYSTEM_EXCEPTION);
		    result.setThrowable(e);
			result.setSuccess(false);
			return result;
		}
	}

	


	@Override
	public Result<Boolean> deleteCategoryById(int categoryId) {
		Result<Boolean> result = this.createResult();
		try{
			CategoryDO category = new CategoryDO();
			category.setStatus(CategoryStatusEnum.DELETE.getValue());
			category.setId(categoryId);
			int effectCount = categoryDOMapper.updateByPrimaryKey(category);
			result.setResult(effectCount > 0);
			result.setSuccess(true);
			return result;
		}catch(Exception e){
			logger.error("deleteCategoryById Exception", e);
			result.setResultCode(BaseResultCode.SYSTEM_EXCEPTION);
			result.setThrowable(e);	
			result.setSuccess(false);
			return result;
		}
	}



	@Override
	public Result<List<CategoryDO>> getArticleCategory(long articleId) {
		Result<List<CategoryDO>> result = this.createResult();
		try{
			 List<CategoryDO> categoryList = categoryDOMapper.selectArticleCategory(articleId);
			result.setResult(categoryList);
			result.setSuccess(true);
			return result;
		}catch(Exception e){
			logger.error("getArticleCategory Exception" + articleId, e);
			result.setResultCode(BaseResultCode.SYSTEM_EXCEPTION);
			result.setThrowable(e);	
			result.setSuccess(false);
			return result;
		}
	}



	
	
	

}
