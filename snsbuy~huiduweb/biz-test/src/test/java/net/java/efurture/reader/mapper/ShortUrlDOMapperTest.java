package net.java.efurture.reader.mapper;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;

import net.java.mapyou.device.TokenUtils;
import net.java.mapyou.mybatis.domain.LocationDO;
import net.java.mapyou.mybatis.domain.ShortUrlDO;
import net.java.mapyou.mybatis.enums.LocationStatus;
import net.java.mapyou.mybatis.enums.LocationType;
import net.java.mapyou.mybatis.enums.ShortUrlStatus;
import net.java.mapyou.mybatis.mapper.LocationDOMapper;
import net.java.mapyou.mybatis.mapper.ShortUrlDOMapper;

public class ShortUrlDOMapperTest extends BaseMapperTest{

	
	@Resource
	ShortUrlDOMapper shortUrlDOMapper;
	
	
	

	@Test
	public void testInsert(){
		shortUrlDOMapper.deleteByPrimaryKey(1L);
		String token = RandomStringUtils.randomAlphanumeric(4);
		ShortUrlDO tokenShortUrlDO = shortUrlDOMapper.selectByToken(token);
		ShortUrlDO shortUrlDO = new ShortUrlDO();
		shortUrlDO.setId(1L);
		shortUrlDO.setUrl("http://www.cnblogs.com/fsjohnhuang/p/4078659.html");
		shortUrlDO.setToken(token);
		shortUrlDO.setStatus(ShortUrlStatus.NORMAL.getValue());
		shortUrlDO.setGmtCreate(new Date());
		shortUrlDO.setGmtModified(new Date());
		int effectCount = shortUrlDOMapper.insert(shortUrlDO);
		Assert.assertTrue(effectCount > 0);
		
		Assert.assertNotNull(shortUrlDOMapper.selectByUrl("http://www.cnblogs.com/fsjohnhuang/p/4078659.html"));
	}
}
