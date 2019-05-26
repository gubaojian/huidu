package net.java.mapyou.mybatis.enums;

public enum LocationType {
	
	ME((byte)0, "主动位置上报"),
	AD((byte)2, "辅助远程定位请求");
	
	
	private final byte value;
	private final String desc;
	
	private LocationType(byte value, String desc){
		this.value = value;
		this.desc = desc;
	}

	
	public byte getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}
	
	public static LocationType type(Byte code){
		if (code == null) {
			return null;
		}
		LocationType[] types  = LocationType.values();
		for(LocationType type : types){
			if(type.getValue() == code){
				   return type;
			}
		}
		return null;
	}
	
}
