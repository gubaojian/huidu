package net.java.efurture.reader.admin.web.form;

import javax.validation.constraints.NotNull;

public class BaseForm {
	
	@NotNull
	private String csrf;

	public String getCsrf() {
		return csrf;
	}

	public void setCsrf(String csrf) {
		this.csrf = csrf;
	}
}
