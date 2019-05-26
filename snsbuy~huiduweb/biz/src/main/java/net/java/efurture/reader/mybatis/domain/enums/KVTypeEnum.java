package net.java.efurture.reader.mybatis.domain.enums;

public enum KVTypeEnum {

	LAST_LOCATION((byte)0, "设备最近位置");

	
	private byte value;
	private String desc;
	
	private KVTypeEnum(byte value, String desc){
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
