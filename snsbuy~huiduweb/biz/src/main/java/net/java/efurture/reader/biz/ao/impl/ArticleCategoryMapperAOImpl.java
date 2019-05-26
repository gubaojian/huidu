package net.java.efurture.reader.biz.ao.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.math.NumberUtils;

import net.java.efurture.reader.biz.ao.ArticleCategoryMapperAO;
import net.java.efurture.reader.biz.sort.SortUtils;
import net.java.efurture.reader.mybatis.domain.ArticleCategoryMapperDO;
import net.java.efurture.reader.mybatis.mapper.ArticleCategoryMapperDOMapper;

import com.google.code.efurture.common.ao.BaseAO;
import com.google.code.efurture.common.result.Result;

public class ArticleCategoryMapperAOImpl extends BaseAO implements ArticleCategoryMapperAO{

	
	@Resource
	ArticleCategoryMapperDOMapper articleCategoryMapperDOMapper;
	
	@Override
	public Result<Boolean> addArticleCategoryMapper(List<ArticleCategoryMapperDO> articleCategoryMapperList) {
		Result<Boolean> result = this.createResult();
		try{
			if(articleCategoryMapperList == null || articleCategoryMapperList.size() == 0){
				result.setResult(true);
				result.setSuccess(true);
				return result;
			}
			
			Set<Long> articleIds = new HashSet<Long>();
			for(ArticleCategoryMapperDO articleCategoryMapper : articleCategoryMapperList){
				articleIds.add(articleCategoryMapper.getArticleId());
				articleCategoryMapper.setGmtCreate(SortUtils.getSortDate());
			}
			
			for(Long articleId : articleIds){
				articleCategoryMapperDOMapper.deleteByArticleId(articleId);
			}
			
			int effectCount = articleCategoryMapperDOMapper.batchInsert(articleCategoryMapperList);
		    result.setResult(effectCount > 0);
	        result.setSuccess(true);
			return result;
		}catch(Exception e){
			logger.error("addArticleCategoryMapper error", e);
		    result.setThrowable(e);
			result.setSuccess(false);
			return result;
		}
	}

	@Override
	public Result<Map<Integer, Integer>> categoryArticleCount(List<Integer> categoryIds) {
		Result<Map<Integer, Integer>> result = this.createResult();
		try{
			
			List<Map<String, Object>> countList = articleCategoryMapperDOMapper.categoryArticleCount(categoryIds);
			Map<Integer,Integer> coutMap = new HashMap<Integer,Integer>();
			for(Integer categoryId : categoryIds){
				boolean find = false;
				for(Map<String, Object> count : countList){
					int categoruIdNum = NumberUtils.toInt(count.get("categoryId").toString());
					if(categoryId == categoruIdNum){
						int countInt = NumberUtils.toInt(count.get("count").toString());
						coutMap.put(categoryId, countInt);
						find = true;
						break;
					}
				}
				if(!find){
				   coutMap.put(categoryId, 0);
				}
			}
		    result.setResult(coutMap);
	        result.setSuccess(true);
			return result;
		}catch(Exception e){
			logger.error("categoryArticleCount error", e);
		    result.setThrowable(e);
			result.setSuccess(false);
			return result;
		}
	}

}
