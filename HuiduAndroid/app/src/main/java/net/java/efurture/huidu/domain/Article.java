package net.java.efurture.huidu.domain;

import java.io.Serializable;
import java.util.Date;

import net.java.efurture.huidu.util.DateFormatUtils;

public class Article implements Serializable{
	private static final long serialVersionUID = -2919394296448035781L;
	
	private Long id;
	
	private String author;
	
	private String title;
	
	private String shortDesc;
	
	private String content;
	
	private String url;
	
	private Date publishTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getShortDesc() {
		return shortDesc;
	}

	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	
	public String getFormatPublishDate(){
	    return DateFormatUtils.format(publishTime);
	}

	
}
