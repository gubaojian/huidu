package net.java.efurture.reader.mybatis.domain.enums;

public enum CategoryStatusEnum {
	
	
	DELETE((byte)-1, "已删除"),
	WAIT((byte)-2,  "待升级状态"),
	NORMAL((byte)0, "正常状态");
	
	
	private byte value;
	private String desc;
	
	private CategoryStatusEnum(byte value, String desc){
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
