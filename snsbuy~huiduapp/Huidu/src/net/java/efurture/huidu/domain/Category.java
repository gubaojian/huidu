package net.java.efurture.huidu.domain;

import java.io.Serializable;

public class Category implements Serializable{
	private static final long serialVersionUID = 289895143905822923L;

	private Long id;
	
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
