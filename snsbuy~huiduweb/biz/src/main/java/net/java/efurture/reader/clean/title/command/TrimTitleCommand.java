package net.java.efurture.reader.clean.title.command;

import net.java.efurture.reader.utils.TrimUtils;

public class TrimTitleCommand  extends TitleCommand{

	@Override
	protected void clean(CleanTitleContext context) {
		 String title = context.getTitle();
		  title = TrimUtils.trim(title);
		 context.setTitle(title);
	}

}
