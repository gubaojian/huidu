package net.java.mapyou.mybatis.enums;

public enum LocationStatus {
	
	DELETE((byte)-1, "已删除"),
	NORMAL((byte)0, "正常"),
	AD_ENABLED((byte)1, "启用"),
	AD_DEVICE_NOT_SUPPORT((byte)-2, "对方设备不支持GPS定位"),
	AD_DENY((byte)-3, "对方拒绝GPS定位请求"),
	AD_TIMEOUT((byte)-4, "远程定位超时"),
	AD_FAILED((byte)-5, "远程定位失败"),
	AD_SUCCESS((byte)2, "辅助定位成功");
	
	
	private final byte value;
	private final String desc;
	
	private LocationStatus(byte value, String desc){
		this.value = value;
		this.desc = desc;
	}

	
	public byte getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}
	
	
	public static LocationStatus type(Byte code){
		if (code == null) {
			return null;
		}
		LocationStatus[] types  = LocationStatus.values();
		for(LocationStatus type : types){
			if(type.getValue() == code){
			   return type;
			}
		}
		return null;
	}
}
