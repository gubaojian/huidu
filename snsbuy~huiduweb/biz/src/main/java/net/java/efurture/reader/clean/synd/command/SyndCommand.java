package net.java.efurture.reader.clean.synd.command;

import net.java.efurture.reader.clean.synd.context.SyndContext;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SyndCommand implements Command {
	
	
	protected Logger logger =  LoggerFactory.getLogger(this.getClass());

	@Override
	public boolean execute(Context context) throws Exception {
		SyndContext cleanContext = (SyndContext)context;
		this.clean(cleanContext);
		return Command.CONTINUE_PROCESSING;
	}

	protected abstract void clean(SyndContext cleanContext);

	

}
