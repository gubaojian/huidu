package net.java.efurture.reader.mybatis.query;

import java.util.Date;

import com.google.code.efurture.common.pagination.PageQuery;

public class TaskQuery extends PageQuery {
	
    private Long relateId;
	
	private Byte type;
	
	private Byte status;
	
	private Date scheduleTimeEnd;
	
	private Date scheduleTimeStart;

	public Long getRelateId() {
		return relateId;
	}

	public void setRelateId(Long relateId) {
		this.relateId = relateId;
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

	public Date getScheduleTimeEnd() {
		return scheduleTimeEnd;
	}

	public void setScheduleTimeEnd(Date scheduleTimeEnd) {
		this.scheduleTimeEnd = scheduleTimeEnd;
	}

	public Date getScheduleTimeStart() {
		return scheduleTimeStart;
	}

	public void setScheduleTimeStart(Date scheduleTimeStart) {
		this.scheduleTimeStart = scheduleTimeStart;
	}
}
