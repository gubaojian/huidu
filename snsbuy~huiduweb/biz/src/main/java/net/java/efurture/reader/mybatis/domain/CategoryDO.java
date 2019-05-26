package net.java.efurture.reader.mybatis.domain;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;


/**
 * name 统一采用小写
 * pin 拼音搜索的功能
 * */
public class CategoryDO {
	
    private Integer id;

    private String name;
    
    @JsonIgnore
    private String pinyin;

    @JsonIgnore
    private Byte status;

    @JsonIgnore
    private Integer sort;
    
    @JsonIgnore
    private Date gmtModified;

    @JsonIgnore
    private Date gmtCreate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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