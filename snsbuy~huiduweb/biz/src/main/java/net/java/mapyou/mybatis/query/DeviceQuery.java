package net.java.mapyou.mybatis.query;

import java.util.Date;
import java.util.List;

import com.google.code.efurture.common.pagination.PageQuery;

public class DeviceQuery extends PageQuery{

	private Date gmtCreateStart;

	private Date gmtCreateEnd;
	
	private Byte status;
	
	private List<Long> idList;
	 

	public Date getGmtCreateStart() {
		return gmtCreateStart;
	}

	public void setGmtCreateStart(Date gmtCreateStart) {
		this.gmtCreateStart = gmtCreateStart;
	}

	public Date getGmtCreateEnd() {
		return gmtCreateEnd;
	}

	public void setGmtCreateEnd(Date gmtCreateEnd) {
		this.gmtCreateEnd = gmtCreateEnd;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public List<Long> getIdList() {
		return idList;
	}

	public void setIdList(List<Long> idList) {
		this.idList = idList;
	}
	
	
	
}
