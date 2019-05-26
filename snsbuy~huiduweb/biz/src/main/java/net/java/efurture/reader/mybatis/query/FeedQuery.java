package net.java.efurture.reader.mybatis.query;

import com.google.code.efurture.common.pagination.PageQuery;

public class FeedQuery  extends PageQuery{
	
	
	private Byte status;
	
	private String site;
	
	private String url;


	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		if(url != null){
			url = url.trim();
		}
		this.url = url;
	}

	



	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}
	
	

}
