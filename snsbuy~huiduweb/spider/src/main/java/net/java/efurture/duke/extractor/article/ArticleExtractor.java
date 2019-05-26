package net.java.efurture.duke.extractor.article;

import java.util.List;
import java.util.Map;

import com.google.code.efurture.common.result.Result;

import net.java.efurture.duke.extractor.article.domain.SiteArticle;
import net.java.efurture.duke.extractor.site.domain.SiteDO;

public interface ArticleExtractor {
	
	Result<List<SiteArticle>> siteArticles(SiteDO site, Map<Object,Object> context);

}
