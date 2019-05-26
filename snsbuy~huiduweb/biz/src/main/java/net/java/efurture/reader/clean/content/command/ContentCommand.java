package net.java.efurture.reader.clean.content.command;

import net.java.efurture.reader.clean.content.context.ContentContext;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 清理文章内容执行的基类
 * */
public abstract class ContentCommand implements Command{
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public boolean execute(Context context) throws Exception {
		ContentContext cleanContext = (ContentContext)context;
		this.clean(cleanContext);
		return Command.CONTINUE_PROCESSING;
	}
	
	
	protected abstract void clean(ContentContext context);

}
