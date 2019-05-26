package net.java.efurture.reader.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;

import net.java.efurture.reader.mybatis.domain.enums.KVTypeEnum;
import net.java.mapyou.mybatis.domain.KVDO;
import net.java.mapyou.mybatis.mapper.KVDOMapper;
import net.java.mapyou.mybatis.query.KVQuery;

public class KVDOMapperTest extends BaseMapperTest{

	@Resource
	KVDOMapper kvDOMapper;
	
	@Test
	public void testInsert(){
		kvDOMapper.deleteByPrimaryKey(1L);
		KVDO kv = new KVDO();
		kv.setId(1L);
		kv.setKey(1L);
		kv.setType(KVTypeEnum.LAST_LOCATION.getValue());
		kv.setValue("测试KVStore");
		kv.setGmtCreate(new Date());
		kv.setGmtModify(new Date());
        
		int effectCount = kvDOMapper.insert(kv);
		Assert.assertTrue(effectCount > 0);
	}
	
	@Test
	public void testSelectByIds(){
		List<Long> ids = new ArrayList<Long>();
		ids.add(1L);
		KVQuery query = new KVQuery();
		query.setKeysList(ids);
		query.setType(KVTypeEnum.LAST_LOCATION.getValue());
		List<KVDO> kvdos = kvDOMapper.selectByQuery(query);
		
		int effectCount = kvdos.size();
		Assert.assertTrue(effectCount > 0);
	}
	

	
	
}
