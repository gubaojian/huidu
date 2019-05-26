package net.java.efurture.duke.extractor.article.domain;

import java.io.Serializable;
import java.util.List;

public class SiteArticle implements Serializable{
	private static final long serialVersionUID = 5518436901190356547L;
	

	private String title;
	
	private String author;
	
	private String publishDate;
	
	private String description;
	
	private String content;
	
	private List<String> tags;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

}
