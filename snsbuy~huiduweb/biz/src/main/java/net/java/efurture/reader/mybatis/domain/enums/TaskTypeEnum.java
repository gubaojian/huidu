package net.java.efurture.reader.mybatis.domain.enums;

public enum TaskTypeEnum {
	
	SYN_NORMAL((byte)0, "普通消息"),
	SYN_HOT((byte)1, "热门博客"),
	SYN_NEWS((byte)2, "新闻");
	
	private byte value;
	private String desc;
	
	private TaskTypeEnum(byte value, String desc){
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
