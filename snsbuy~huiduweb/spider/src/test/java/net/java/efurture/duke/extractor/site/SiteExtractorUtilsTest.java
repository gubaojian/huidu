package net.java.efurture.duke.extractor.site;

import org.junit.Test;

import com.google.code.efurture.common.result.Result;

import net.java.efurture.duke.extractor.site.domain.SiteDO;
import net.java.efurture.duke.extractor.site.domain.SiteEntryType;
import net.java.efurture.duke.extractor.site.feed.FeedClient;
import junit.framework.Assert;
import junit.framework.TestCase;

public class SiteExtractorUtilsTest extends TestCase {
	
	
	@Test
	public void testExtractFeedUrl(){
		Result<SiteDO> result = SiteExtractor.extraceSite("http://99jty.com/");
		SiteDO site = result.getResult();
		Assert.assertTrue(result.isSuccess());
		Assert.assertEquals("http://99jty.com/?feed=rss2", site.getEntry());
		Assert.assertTrue(site.getEntryType() == SiteEntryType.FEED.getValue());
		Assert.assertEquals("serendipity的技术小宅", site.getSign());
		Assert.assertNotNull(site.getDescription());
	}
	
	
	@Test
	public void testExtractFeedTwo(){
		Result<SiteDO> result = SiteExtractor.extraceSite("http://www.cssha.com/");
		SiteDO site = result.getResult();
		Assert.assertTrue(result.isSuccess());
		Assert.assertEquals("http://www.cssha.com/feed/atom", site.getEntry());
		Assert.assertTrue(site.getEntryType() == SiteEntryType.FEED.getValue());
		Assert.assertEquals("前端手记", site.getSign());
		Assert.assertNotNull(site.getDescription());
	}

	@Test
	public void testExtractFeedThree(){
		Result<SiteDO> result = SiteExtractor.extraceSite("http://wentao.me/");
		SiteDO site = result.getResult();
		Assert.assertTrue(result.isSuccess());
		Assert.assertEquals("http://wentao.me/feeds/", site.getEntry());
		Assert.assertTrue(site.getEntryType() == SiteEntryType.FEED.getValue());
		Assert.assertEquals("文韬的BLOG", site.getSign());
		Assert.assertNotNull(site.getDescription());
	}
	
	@Test
	public void testTaobaoUed(){		
		Result<SiteDO> result = SiteExtractor.extraceSite("http://ued.taobao.com/blog");
		SiteDO site = result.getResult();
		Assert.assertTrue(result.isSuccess());
		Assert.assertEquals("http://ued.taobao.com/blog/feed/", site.getEntry());
		Assert.assertTrue(site.getEntryType() == SiteEntryType.FEED.getValue());
		Assert.assertEquals("TaoBaoUED", site.getSign());
		Assert.assertNotNull(site.getDescription());
	
	}
	
	
}
