package net.java.mapyou.mybatis.query;

import java.util.Date;
import java.util.List;

import com.google.code.efurture.common.pagination.PageQuery;

public class LocationQuery  extends PageQuery{

	 private Long deviceId;
	 
	 private List<Long> deviceIds;

	 private Byte type;

	 private Byte status;
	 
	 private Date gmtCreateStart;
	 
	 private Date gmtCreateEnd;

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

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

	public List<Long> getDeviceIds() {
		return deviceIds;
	}

	public void setDeviceIds(List<Long> deviceIds) {
		this.deviceIds = deviceIds;
	}
	
	
	
}
