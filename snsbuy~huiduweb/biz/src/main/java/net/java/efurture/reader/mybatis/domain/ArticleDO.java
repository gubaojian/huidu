package net.java.efurture.reader.mybatis.domain;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class ArticleDO {
    private Long id;

    private String title;

    @JsonIgnore
    private Long feedId;

    private String author;
    
    private String shortDesc;

    @JsonProperty("publishTime")
    private Date publishDate;

    @JsonIgnore
    private Byte status;

    @JsonIgnore
    private Integer sort;

    private String url;

    @JsonIgnore
    private Date gmtCreate;
    
    @JsonIgnore
    private Date gmtModified;
    
    //文章内容，非持久化字段，通过fileKey由k-v存储中取出
    private String content;
    
    @JsonIgnore
    private String fileKey;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
    	if(title != null && title.length() > 128){
			title = title.substring(0, 128);
		}
        this.title = title == null ? null : title.trim();
    }


    public Long getFeedId() {
		return feedId;
	}

	public void setFeedId(Long feedId) {
		this.feedId = feedId;
	}

	public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
    	if(author != null && author.length() > 32){
    		author = author.substring(0, 32);
		}
        this.author = author == null ? null : author.trim();
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

	public String getShortDesc() {
		return shortDesc;
	}

	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc == null ? null : shortDesc.trim();
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public String getFileKey() {
		return fileKey;
	}

	public void setFileKey(String fileKey) {
		this.fileKey = fileKey;
	}
	
	
}