package net.java.efurture.duke.extractor.article;

import java.util.List;
import java.util.Map;

import net.java.efurture.duke.extractor.article.domain.SiteArticle;
import net.java.efurture.duke.extractor.site.domain.SiteDO;

import com.google.code.efurture.common.result.Result;

public class DefaultArticleExtractor implements ArticleExtractor{

	@Override
	public Result<List<SiteArticle>> siteArticles(SiteDO site, Map<Object, Object> context) {
	
		return null;
	}

}
