package net.java.mapyou.mybatis.domain;

import java.util.Date;

public class DeviceDO {
	
	
	
    private Long id;  //设备id

    private String token; //访问token，类似于session

    private String trackToken; //二维码标示，跟踪定位token

    private String name;
    
    private String pushToken;

    private Byte os;

    private String lang;

    private Byte status;

    private Date gmtCreate;

    private Date gmtModify;
    
   
	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTrackToken() {
        return trackToken;
    }

    public void setTrackToken(String trackToken) {
        this.trackToken = trackToken == null ? null : trackToken.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPushToken() {
        return pushToken;
    }

    public void setPushToken(String pushToken) {
        this.pushToken = pushToken == null ? null : pushToken.trim();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    public Byte getOs() {
        return os;
    }

    public void setOs(Byte os) {
        this.os = os;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang == null ? null : lang.trim();
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

    public Date getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }
}