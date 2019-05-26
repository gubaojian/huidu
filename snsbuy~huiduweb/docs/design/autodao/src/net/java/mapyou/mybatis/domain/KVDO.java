package net.java.mapyou.mybatis.domain;

import java.util.Date;

public class KVDO {
    private Long id;

    private Long kvKey;

    private Byte type;

    private String kvValue;

    private Date gmtCreate;

    private Date gmtModify;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getKvKey() {
        return kvKey;
    }

    public void setKvKey(Long kvKey) {
        this.kvKey = kvKey;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getKvValue() {
        return kvValue;
    }

    public void setKvValue(String kvValue) {
        this.kvValue = kvValue == null ? null : kvValue.trim();
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