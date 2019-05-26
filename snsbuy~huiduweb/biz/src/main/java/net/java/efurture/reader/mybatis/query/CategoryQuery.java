package net.java.efurture.reader.mybatis.query;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.google.code.efurture.common.pagination.PageQuery;
import com.google.code.efurture.common.pinyin.PinyinUtils;
import com.google.code.efurture.common.util.SqlUtils;

public class CategoryQuery extends PageQuery {
	
	
	private List<String> nameList;
	
	private Byte status;

	private String pinyin;
	
	
	
	public List<String> getNameList() {
		return nameList;
	}

	
	public void setNameList(List<String> nameList) {
		this.nameList = nameList;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = SqlUtils.filterLikeWildCardChar(pinyin);
	}
	
	public void setKeyword(String keyword){
		keyword = StringUtils.trimToNull(keyword);
		if(keyword != null){
			keyword = PinyinUtils.toPinyin(keyword);
			this.pinyin = SqlUtils.filterLikeWildCardChar(keyword);
		}
	}
	
	
}
