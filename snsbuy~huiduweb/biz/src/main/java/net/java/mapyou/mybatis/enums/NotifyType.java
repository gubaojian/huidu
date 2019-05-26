package net.java.mapyou.mybatis.enums;

public enum NotifyType {
	
	NONE((byte)0, "无任何提示"),
	BRADGE((byte)1, "红点提示"),
	PUSH((byte)2, "Push消息提示");
	
	
	private final byte value;
	private final String desc;
	
	private NotifyType(byte value, String desc){
		this.value = value;
		this.desc = desc;
	}

	
	public byte getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}
	
	
	public  static NotifyType type(Byte value){
		if(value == null){
			return NONE;
		}
		NotifyType[] values = NotifyType.values();
		for(NotifyType os : values){
			if (os.getValue() == value) {
				return os;
			}
		}
		return NONE;
	}
}
