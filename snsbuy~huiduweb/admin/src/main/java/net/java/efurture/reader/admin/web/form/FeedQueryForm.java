package net.java.efurture.reader.admin.web.form;

public class FeedQueryForm {
	
	private String targetValue;
	
	private Byte type;
	
	private Integer pageNum;

	public String getTargetValue() {
		return targetValue;
	}

	public void setTargetValue(String targetValue) {
		this.targetValue = targetValue;
	}

	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
	}

	public Integer getPageNum() {
		if(pageNum == null || pageNum < 1){
			pageNum = 1;
		}
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	
	
	

}
