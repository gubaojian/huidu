package net.java.efurture.duke.extractor.site;

import net.java.efurture.duke.extractor.site.command.ExtractSiteEntryCommand;
import net.java.efurture.duke.extractor.site.command.SignDescriptionCommand;
import net.java.efurture.duke.extractor.site.command.SiteContext;
import net.java.efurture.duke.extractor.site.domain.SiteDO;
import net.java.efurture.reader.biz.spider.client.GrapClient;

import org.apache.commons.chain.impl.ChainBase;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.google.code.efurture.common.result.DefaultResult;
import com.google.code.efurture.common.result.Result;
import com.google.code.efurture.common.resultcode.BaseResultCode;

public class SiteExtractor {

	
	private  static final ChainBase CHAIN = new ChainBase();
	static{
		CHAIN.addCommand(new ExtractSiteEntryCommand());
		CHAIN.addCommand(new SignDescriptionCommand());	
	}
	
	public static Result<SiteDO> extraceFromSource(String url, String htmlSource) {
		Document document = Jsoup.parse(htmlSource, url);
		SiteDO site = new SiteDO();
		site.setUrl(url);
		SiteContext context = new SiteContext();
		context.setSiteDO(site);
		context.setSiteDocument(document);
		Result<SiteDO> result = new DefaultResult<SiteDO>();
		try {
			CHAIN.execute(context);
			result.setResult(context.getSiteDO());
			result.setResultCode(BaseResultCode.SUCCESS);
			result.setSuccess(true);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.setThrowable(e);
			result.setResultCode(BaseResultCode.SYSTEM_EXCEPTION);
			result.setSuccess(false);
			return result;
		}
	}
	
	

	public static Result<SiteDO> extraceSite(String url) {
		Result<String> grapResult = GrapClient.grapSite(url);
		if(!grapResult.isSuccess() || grapResult.getResult() == null){
			Result<SiteDO> result = new DefaultResult<SiteDO>();
			result.setThrowable(grapResult.getThrowable());
			result.setResultCode(BaseResultCode.SYSTEM_EXCEPTION);
			result.setSuccess(false);
			return result;
		}
		return extraceFromSource(url, grapResult.getResult());
	}

}
