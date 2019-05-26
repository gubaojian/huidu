package net.java.efurture.reader.clean.content.command;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.CollectionUtils;

import net.java.efurture.reader.clean.content.context.ContentContext;


/**
 * 清理文章中的代码行数，防止table中的行数造成代码难看: 动态下载苹果提供的多种字体
 * 
 * <div class="highlight"><table><tr><td class="gutter"><pre class="line-numbers"><span class='line-number'>1</span>
<span class='line-number'>2</span>
<span class='line-number'>3</span>
<span class='line-number'>4</span>
</pre></td><td class='code'><pre><code class='bash'><span class='line'>git submodule add  https://github.com/gmarik/vundle.git .vim/bundle/vundle
</span><span class='line'>
</span><span class='line'>git submodule status <span class="c"># 查看子模块状态</span>
</span><span class='line'> 59bff0c457f68c3d52bcebbf6068ea01ac8f5dac .vim/bundle/vundle <span class="o">(</span>0.9.1-18-g59bff0c<span class="o">)</span>
</span></code></pre></td></tr></table></div>
 * */
public class CleanCodeLineNumber extends ContentCommand {

	
	private static final List<String> CLASS_NAME_LIST = new ArrayList<String>();
	static{
		CLASS_NAME_LIST.add("gutter");
		CLASS_NAME_LIST.add("line_numbers");
	}
	
	@Override
	protected void clean(ContentContext context) {
		Element body = context.getDocument().body();
			Elements tables = body.getElementsByTag("table");
			if(tables == null || tables.size() == 0){
				return;
			}
			
			for(Element table : tables){
				for(String className : CLASS_NAME_LIST){
					Elements lineNumbers = table.getElementsByClass(className);
					if(CollectionUtils.isEmpty(lineNumbers)){
						continue;
					}
					for(Element lineNumber :lineNumbers){
						if(lineNumber.parent() != null){
							lineNumber.remove();
						}
					}
				}
		   }
	}
}
