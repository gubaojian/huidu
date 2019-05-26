package net.java.efurture.reader.clean.synd.context;

import java.util.List;

import org.apache.commons.chain.impl.ContextBase;

import com.sun.syndication.feed.synd.SyndEntry;

public class SyndContext extends ContextBase{

	private static final long serialVersionUID = -3450694557297577764L;
	
	
	private static final String ENTRY_KEY = "ENTRY_KEY";
	
	
	public void setEntryList(List<SyndEntry> entryList){
		this.put(ENTRY_KEY, entryList);
	}
	
	@SuppressWarnings("unchecked")
	public List<SyndEntry> getEntryList(){
		return (List<SyndEntry>) this.get(ENTRY_KEY);
	}

}
