package net.java.efurture.reader.admin.web.controller.me;

import java.util.List;

import javax.annotation.Resource;

import net.java.efurture.reader.admin.web.constants.ControllerName;
import net.java.efurture.reader.admin.web.form.FeedQueryForm;
import net.java.efurture.reader.biz.ao.FeedAO;
import net.java.efurture.reader.mybatis.domain.FeedDO;
import net.java.efurture.reader.mybatis.domain.enums.FeedStatusEnum;
import net.java.efurture.reader.mybatis.query.FeedQuery;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.code.efurture.common.result.Result;


@Controller
@RequestMapping(ControllerName.ME_FEED_LIST)
public class FeedList {
	
	@Resource
	FeedAO feedAO;
	
	
	@RequestMapping(method = RequestMethod.GET)
	public String doGet(@ModelAttribute("formQuery") FeedQueryForm formQuery, Model model){
		FeedQuery query = new FeedQuery();
		
		if(formQuery != null){
			formQuery.setTargetValue(StringUtils.trimToNull(formQuery.getTargetValue()));
			if(formQuery.getType() != null && formQuery.getType() == 1){
				query.setUrl(formQuery.getTargetValue());
			}else{
			    query.setSite(formQuery.getTargetValue());	
			}
			query.setCurrentPageNum(formQuery.getPageNum());
		}
		query.setStatus(FeedStatusEnum.NORMAL.getValue());
		
		Result<List<FeedDO>> result = feedAO.findFeedListByQuery(query);
		if(!result.isSuccess()){
			model.addAttribute("message", result.getResultCode().getMessage());
			return ControllerName.ME_TIPS;
		}
		
		model.addAttribute("query", query);
		model.addAttribute("feedList", result.getResult());
		
		return ControllerName.ME_FEED_LIST;
	}

}
