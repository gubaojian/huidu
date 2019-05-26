package net.java.efurture.reader.mybatis.query;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.util.CollectionUtils;

import com.google.code.efurture.common.pagination.PageQuery;

public class CategoryArticleQuery extends PageQuery {
	
	
	private Long categoryId;
	
	private Byte status;
	
	private List<Long> categoryIdList;


	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public List<Long> getCategoryIdList() {
		return categoryIdList;
	}

	public void setCategoryIdList(List<Long> categoryIdList) {
		this.categoryIdList = categoryIdList;
	}
	
	public void setCategoryIds(String categoryIds){
		if(categoryIds != null){
			List<Long> categoryIdList = new ArrayList<Long>();
			String[] categoryIdNums = categoryIds.split(",");
			for(String categoryIdNum : categoryIdNums){
				if(StringUtils.isEmpty(categoryIdNum)){
					continue;
				}
				long num = NumberUtils.toLong(categoryIdNum);
				categoryIdList.add(num);
			}
			if(CollectionUtils.isEmpty(categoryIdList)){
				this.categoryIdList = null;
			}else{
				this.categoryIdList = categoryIdList;
			}
		}
	}
	
	
	
}
