package net.java.efurture.reader.mybatis.domain.enums;

public enum FeedTypeEnum {

	
	NOTMAL((byte)0, "汇读"),
	HOT((byte)1, "汇读国外版本"),
	NEWS((byte)2, "其它版本");
	
	private byte value;
	private String desc;
	
	private FeedTypeEnum(byte value, String desc){
		this.value = value;
		this.desc = desc;
	}

	public static TaskTypeEnum valueOf(Byte bt){
		if(bt == null){
			return null;
		}
		for(TaskTypeEnum type : TaskTypeEnum.values()){
			if(type.getValue() == bt){
				return type;
			}
		}
		return null;
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
