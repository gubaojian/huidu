package net.java.mapyou.ao.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

import com.google.code.efurture.common.ao.BaseAO;
import com.google.code.efurture.common.result.Result;
import com.google.code.efurture.common.resultcode.BaseResultCode;

import net.java.mapyou.ao.ShortUrlAO;
import net.java.mapyou.ao.ShortUrlAOErrorCode;
import net.java.mapyou.mybatis.domain.ShortUrlDO;
import net.java.mapyou.mybatis.enums.ShortUrlStatus;
import net.java.mapyou.mybatis.mapper.ShortUrlDOMapper;

public class ShortUrlAOImpl  extends BaseAO implements ShortUrlAO {

	@Resource
	ShortUrlDOMapper shortUrlDOMapper;
	
	@Override
	public Result<ShortUrlDO> shortUrl(String url) {
		Result<ShortUrlDO> result = createResult();
		try{
			//基本参数检查
			if (StringUtils.isEmpty(url)) {
				result.setResultCode(ShortUrlAOErrorCode.ARGS_ERROR);
				result.setSuccess(false);
				return result;
			}
			
			ShortUrlDO dbShortUrlDO = shortUrlDOMapper.selectByUrl(url);
			if (dbShortUrlDO != null) {
				ShortUrlDO updateDO = new ShortUrlDO();
				updateDO.setId(dbShortUrlDO.getId());
				updateDO.setGmtModified(new Date());
				shortUrlDOMapper.updateByPrimaryKey(updateDO);
				result.setResult(dbShortUrlDO);
				result.setResultCode(BaseResultCode.SUCCESS);
				result.setSuccess(true);
				return result;
			}
			
			String token = RandomStringUtils.randomAlphanumeric(4);
			ShortUrlDO tokenShortUrlDO = shortUrlDOMapper.selectByToken(token);
			ShortUrlDO shortUrlDO = new ShortUrlDO();
			shortUrlDO.setUrl(url);
			shortUrlDO.setToken(token);
			shortUrlDO.setStatus(ShortUrlStatus.NORMAL.getValue());
			shortUrlDO.setGmtCreate(new Date());
			shortUrlDO.setGmtModified(new Date());
			int count = shortUrlDOMapper.insert(shortUrlDO);
			if (tokenShortUrlDO != null) {
				token = shortUrlDO.getId() + "";
				ShortUrlDO updateToken = new ShortUrlDO();
				updateToken.setId(shortUrlDO.getId());
				updateToken.setToken(token);
				shortUrlDOMapper.updateByPrimaryKey(updateToken);
				shortUrlDO.setToken(token);
			}
			result.setResult(shortUrlDO);
			result.setResultCode(BaseResultCode.SUCCESS);
			result.setSuccess(count > 0);
			return result;
		}catch(Exception e){
		     logger.error("ShortUrlAOImpl shortUrl error ", e);
		     result.setResultCode(BaseResultCode.SYSTEM_EXCEPTION);
			 result.setThrowable(e);
		     result.setSuccess(false);
		     return result;
		}
	}

	@Override
	public Result<ShortUrlDO> fullUrl(String shortUrlId) {
		Result<ShortUrlDO> result = createResult();
		try{
			//基本参数检查
			if (StringUtils.isEmpty(shortUrlId)) {
				result.setResultCode(ShortUrlAOErrorCode.ARGS_ERROR);
				result.setSuccess(false);
				return result;
			}
			String token = shortUrlId.toLowerCase();
			ShortUrlDO tokenShortUrlDO = shortUrlDOMapper.selectByToken(token);
			result.setResult(tokenShortUrlDO);
			result.setResultCode(BaseResultCode.SUCCESS);
			result.setSuccess(tokenShortUrlDO != null);
			return result;
		}catch(Exception e){
		     logger.error("ShortUrlAOImpl fullUrl error ", e);
		     result.setResultCode(BaseResultCode.SYSTEM_EXCEPTION);
			 result.setThrowable(e);
		     result.setSuccess(false);
		     return result;
		}
	}
	
	/**
	 * @param url 缩短url参数， 返回url删除是否成功
	 * */
	public Result<Boolean> deleteUrl(String url){
		Result<Boolean> result = createResult();
		try{
			//基本参数检查
			if (StringUtils.isEmpty(url)) {
				result.setResultCode(ShortUrlAOErrorCode.ARGS_ERROR);
				result.setSuccess(false);
				return result;
			}
			
			ShortUrlDO dbShortUrlDO = shortUrlDOMapper.selectByUrl(url);
			if(dbShortUrlDO == null){
				result.setResult(true);
				result.setResultCode(BaseResultCode.SUCCESS);
				result.setSuccess(true);
				return result;
			}
			shortUrlDOMapper.deleteByPrimaryKey(dbShortUrlDO.getId());
			result.setResult(true);
			result.setResultCode(BaseResultCode.SUCCESS);
			result.setSuccess(true);
			return result;
		}catch(Exception e){
		     logger.error("ShortUrlAOImpl deleteUrl error ", e);
		     result.setResultCode(BaseResultCode.SYSTEM_EXCEPTION);
			 result.setThrowable(e);
		     result.setSuccess(false);
		     return result;
		}
	}

}
