package net.java.efurture.duke.extractor.site.domain;

import java.io.Serializable;
import java.util.Set;

public class SiteDO implements Serializable {
	private static final long serialVersionUID = 5292333062489512963L;
	

	private String url;  //网址
	
	private String entry; //抓取入口
	
	private Byte entryType;  //入口类型
	
	private String sign;  //网站标志
	
	private String description;  //网站描述
	
	private String charsetName;  //网站字符编码名称
	
	
	private Set<String> links;
	

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getEntry() {
		return entry;
	}

	public void setEntry(String entry) {
		this.entry = entry;
	}

	

	public Byte getEntryType() {
		return entryType;
	}

	public void setEntryType(Byte entryType) {
		this.entryType = entryType;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCharsetName() {
		return charsetName;
	}

	public void setCharsetName(String charsetName) {
		this.charsetName = charsetName;
	}

	public Set<String> getLinks() {
		return links;
	}

	public void setLinks(Set<String> links) {
		this.links = links;
	}
	
	
}
