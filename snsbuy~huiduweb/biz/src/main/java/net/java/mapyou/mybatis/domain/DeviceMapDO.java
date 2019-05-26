package net.java.mapyou.mybatis.domain;

import java.util.Date;

public class DeviceMapDO {
    private Long id;

    private Long deviceId;

    private Long trackDeviceId;

    private Byte status;
    
    

    private Date gmtCreate;

    private Date gmtModified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Long getTrackDeviceId() {
        return trackDeviceId;
    }

    public void setTrackDeviceId(Long trackDeviceId) {
        this.trackDeviceId = trackDeviceId;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
}