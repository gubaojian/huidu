package net.java.efurture.reader.admin.web.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


/**参考文章： http://docs.oracle.com/javaee/6/api/javax/validation/constraints/package-summary.html 
 * */
public class FeedForm {
	

	private static final String regexpPattern = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]$";
	
	private Long id;

	@NotNull
	@Size(min = 1, max = 1024, message="网站主页地址必须介于{min}-{max}之间")
	@Pattern(regexp = regexpPattern, message="site必须为正确的URL格式")
    private String site;
	
	
	@NotNull
	@Size(min = 1, max = 1024, message="url长度值必须介于{min}-{max}之间")
	@Pattern(regexp=regexpPattern, message="url必须为正确的URL格式")
    private String url;
	
	
	@Size(max = 128, message="描述长度值必须介于0-{max}之间")
    private String shortDesc;
	
	
	
    
	@NotNull(message="请选择类型")
    private Byte type;
	
	

	@Size(max = 128, message="tags必须介于0-{max}之间")
    private String tags;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	

	public String getShortDesc() {
		return shortDesc;
	}

	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}
    
    
    

}
