package net.java.efurture.reader.mybatis.domain.enums;

public enum TaskPriorityEnum {

	
	DEFAULT_PRIORITY((byte)0, "默认优先级"),
	HIGH_PRIORITY((byte)5, "优先级高"),
	LOW_PRIORITY((byte)-5, "优先级低");

	
	private byte value;
	private String desc;
	
	private TaskPriorityEnum(byte value, String desc){
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

