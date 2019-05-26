package net.java.mapyou.mybatis.enums;

public enum DeviceStatus {
	
	DELETE((byte)-1, "已删除"),
	NORMAL((byte)0, "正常");
	
	
	private final byte value;
	private final String desc;
	
	private DeviceStatus(byte value, String desc){
		this.value = value;
		this.desc = desc;
	}

	
	public byte getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}
}
