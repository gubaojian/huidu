package net.java.mapyou.mybatis.enums;

public enum OS {

	IOS((byte)0, "IOS"),
	ANDROID((byte)1, "Android");
	
	
	private final byte value;
	private final String desc;
	
	private OS(byte value, String desc){
		this.value = value;
		this.desc = desc;
	}

	
	public byte getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}
	
	
	public  static OS  type(Byte value){
		if(value == null){
			return null;
		}
		OS[] values = OS.values();
		for(OS os : values){
			if (os.getValue() == value) {
				return os;
			}
		}
		return null;
	}
	
}
