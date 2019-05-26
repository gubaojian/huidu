package net.java.efurture.duke.extractor.article.feed;

import java.util.List;
import java.util.Map;

import com.google.code.efurture.common.result.Result;

import net.java.efurture.duke.extractor.article.ArticleExtractor;
import net.java.efurture.duke.extractor.article.domain.SiteArticle;
import net.java.efurture.duke.extractor.site.domain.SiteDO;

public class FeedArticleExtractor  implements ArticleExtractor{

	@Override
	public Result<List<SiteArticle>> siteArticles(SiteDO site,
			Map<Object, Object> context) {
		// TODO Auto-generated method stub
		return null;
	}

}
