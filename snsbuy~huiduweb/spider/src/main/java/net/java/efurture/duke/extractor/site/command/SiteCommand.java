package net.java.efurture.duke.extractor.site.command;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

public abstract class SiteCommand implements Command{

	@Override
	public boolean execute(Context context) throws Exception {
		this.extract((SiteContext)context);
		return Command.CONTINUE_PROCESSING;
	}
	
	
	/**抽取出文章的内容*/
	public abstract void extract(SiteContext context);

}
