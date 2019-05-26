package net.java.efurture.duke.extractor.site.domain;

public enum SiteEntryType {
	
	FEED((byte)0, "Feed"),
	SITE((byte)1, "等待同步");
	
	
	private byte value;
	private String desc;
	
	private SiteEntryType(byte value, String desc){
		this.value = value;
		this.desc = desc;
	}

	
	public byte getValue() {
		return value;
	}

	public void setValue(byte value) {
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
