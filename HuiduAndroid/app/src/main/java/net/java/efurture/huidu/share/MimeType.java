package net.java.efurture.huidu.share;

public enum MimeType {
	
	TEXT("text/plain");
	
	private String desc;
	
	private MimeType(String desc){
		this.desc = desc;
	}
	

	public String getDesc() {
		return desc;
	}


	public void setDesc(String desc) {
		this.desc = desc;
	}


	public String toString(){
		return desc;
	}
}
