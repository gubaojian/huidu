package net.java.mapyou.ao;

import com.google.code.efurture.common.result.Result;

import net.java.mapyou.mybatis.domain.ShortUrlDO;

public interface ShortUrlAO {

	/**
	 * @param url 缩短url参数， 返回url标示符号
	 * */
	public Result<ShortUrlDO> shortUrl(String url);
	

	/**
	 * @param shortUrlId 缩短shortUrlId，返回url标示符号
	 * */
	public Result<ShortUrlDO> fullUrl(String shortUrlToken);
	
	
	/**
	 * @param url 缩短url参数， 返回url删除是否成功
	 * */
	public Result<Boolean> deleteUrl(String url);
	

}
