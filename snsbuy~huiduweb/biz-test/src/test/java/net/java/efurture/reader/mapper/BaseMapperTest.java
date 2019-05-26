package net.java.efurture.reader.mapper;

import javax.annotation.Resource;

import junit.framework.TestCase;

import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:development/spring-mybatis-admin-development.xml", "classpath:admin/spring-mybatis-admin-mapper.xml"})  
public class BaseMapperTest  extends TestCase{
	
	protected static Long relateId = 1L;
	
	@Resource
	JdbcTemplate  jdbcTemplate;

}
