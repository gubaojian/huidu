package net.java.mapyou.mybatis.query;

import java.util.List;

import com.google.code.efurture.common.pagination.PageQuery;

public class KVQuery extends PageQuery{

	private Byte type;
	
	private List<Long> keysList;

	
	
	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
	}

	public List<Long> getKeysList() {
		return keysList;
	}

	public void setKeysList(List<Long> keysList) {
		this.keysList = keysList;
	}

	
}
