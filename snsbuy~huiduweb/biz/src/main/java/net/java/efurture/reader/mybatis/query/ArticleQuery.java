package net.java.efurture.reader.mybatis.query;

import com.google.code.efurture.common.pagination.PageQuery;

public class ArticleQuery extends PageQuery{
	
	private Long feedId;
	
	private String title;
	
	private Byte status;
	
	//文件内容存储到KV中，此属性仅在ArticleAO中生效
	private Boolean includeContent = Boolean.FALSE;
	

	public Long getFeedId() {
		return feedId;
	}

	public void setFeedId(Long feedId) {
		this.feedId = feedId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Boolean getIncludeContent() {
		return includeContent;
	}

	public void setIncludeContent(Boolean includeContent) {
		this.includeContent = includeContent;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((feedId == null) ? 0 : feedId.hashCode());
		result = prime * result
				+ ((includeContent == null) ? 0 : includeContent.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ArticleQuery other = (ArticleQuery) obj;
		if (feedId == null) {
			if (other.feedId != null)
				return false;
		} else if (!feedId.equals(other.feedId))
			return false;
		if (includeContent == null) {
			if (other.includeContent != null)
				return false;
		} else if (!includeContent.equals(other.includeContent))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	
	
	
	
	
}
