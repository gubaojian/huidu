package net.java.efurture.reader.clean.title.command;

import org.apache.commons.chain.impl.ContextBase;

public class CleanTitleContext extends ContextBase{
	private static final long serialVersionUID = -1944657584300279562L;
	
	private static final String TITLE_KEY = "TITLE_KEY";
	
	
	public void setTitle(String title){
		this.put(TITLE_KEY, title);
	}
	
	
	public String getTitle(){
		return (String) this.get(TITLE_KEY);
	}

}
