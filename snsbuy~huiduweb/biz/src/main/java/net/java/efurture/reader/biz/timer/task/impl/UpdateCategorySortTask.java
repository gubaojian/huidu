package net.java.efurture.reader.biz.timer.task.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.java.efurture.reader.biz.timer.task.Task;
import net.java.efurture.reader.mybatis.mapper.CategoryDOMapper;


public class UpdateCategorySortTask  implements Task{
	
	
	private static final Logger logger = LoggerFactory.getLogger(UpdateCategorySortTask.class);
	
	@Resource
	CategoryDOMapper categoryMapper;

	@Override
	public void execute() {
		try{
			long start = System.currentTimeMillis();
			categoryMapper.updateCategorySort();
			long used = (System.currentTimeMillis() - start);
			logger.warn("Update Category Sort Used : " + used + " ms");
		}catch(Exception e){
			logger.error("update category sort error", e);
		}
	}



}
