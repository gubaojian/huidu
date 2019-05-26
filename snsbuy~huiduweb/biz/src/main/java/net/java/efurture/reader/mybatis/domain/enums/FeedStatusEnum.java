package net.java.efurture.reader.mybatis.domain.enums;

public enum FeedStatusEnum {
	
	DELETE((byte)-1, "已删除"),
	NORMAL((byte)0, "等待同步");
	

	
	private byte value;
	private String desc;
	
	private FeedStatusEnum(byte value, String desc){
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
