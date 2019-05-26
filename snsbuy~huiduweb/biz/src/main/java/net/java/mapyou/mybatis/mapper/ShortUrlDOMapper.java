package net.java.mapyou.mybatis.mapper;

import net.java.mapyou.mybatis.domain.ShortUrlDO;

public interface ShortUrlDOMapper {
	
	
    int deleteByPrimaryKey(Long id);

    int insert(ShortUrlDO record);

    ShortUrlDO selectByPrimaryKey(Long id);
    

    ShortUrlDO selectByToken(String token);
    
    ShortUrlDO selectByUrl(String url);

    //List<ShortUrlDO> selectAll();

    int updateByPrimaryKey(ShortUrlDO record);
}