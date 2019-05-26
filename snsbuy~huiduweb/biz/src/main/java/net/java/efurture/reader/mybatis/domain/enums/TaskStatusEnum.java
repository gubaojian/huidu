package net.java.efurture.reader.mybatis.domain.enums;

public enum TaskStatusEnum {

	DELETE((byte)-1, "已删除"),
	SYN_CIRCLE((byte)0, "正常同步周期"),
	SYN_END((byte)1, "同步结束");

	
	private byte value;
	private String desc;
	
	private TaskStatusEnum(byte value, String desc){
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
