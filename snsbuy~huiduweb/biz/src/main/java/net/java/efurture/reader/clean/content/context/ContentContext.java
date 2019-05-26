package net.java.efurture.reader.clean.content.context;

import org.apache.commons.chain.impl.ContextBase;
import org.jsoup.nodes.Document;


public class ContentContext extends ContextBase {
	private static final long serialVersionUID = 1348337781009164975L;
	
	private static final String DOCUMENT_KEY = "DOCUMENT_KEY";
	
	
	private static final String BASE_URI_KEY = "BASE_URI_KEY";
	
	public void setDocument(Document document){
		this.put(DOCUMENT_KEY, document);
	}
	
	public Document getDocument(){
		return (Document) this.get(DOCUMENT_KEY);
	}
	
	
	public void setBaseUri(String uri){
		this.put(BASE_URI_KEY, uri);
	}
	
	public String getBaseUri(){
		return (String) this.get(BASE_URI_KEY);
	}

}
