package net.java.efurture.reader.clean.title.command;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class TitleCommand implements Command {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public boolean execute(Context context) throws Exception {
		CleanTitleContext cleanContext = (CleanTitleContext)context;
		this.clean(cleanContext);
		return Command.CONTINUE_PROCESSING;
	}
	
	
	protected abstract void clean(CleanTitleContext context);
}
