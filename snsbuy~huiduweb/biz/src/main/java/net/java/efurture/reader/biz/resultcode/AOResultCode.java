package net.java.efurture.reader.biz.resultcode;

import com.google.code.efurture.common.result.ResultCode;
import com.google.code.efurture.common.resultcode.BaseResultCode;

public class AOResultCode extends BaseResultCode{

	private static final long serialVersionUID = 7625237818324389773L;
	
	
	
	public static final ResultCode TASK_ARGS_MUST_HAVE = create("type、relateId参数不能为空");
	
	
	public static final ResultCode TASK_MUST_BE_VALID = create("无效task");
	
	public static final ResultCode TASK_NOT_EXISTD = create("Task不存在");
	
	
	public static final ResultCode FEED_ARGS_MUST_NOT_NULL = create("type、url、site三个参数不能为空");
	
	

	public static final ResultCode FEED_ALREADY_EXISTL = create("Feed记录已存在");
	
	public static final ResultCode FEED_ID_ILLEGAL = create("Feed ID不合法");
	

	public static final ResultCode FEED_NOT_EXIST = create("Feed 不存在");
	

	public static final ResultCode FEED_STATUS_NOT_NORMAL = create("Feed 状态异常，Feed已被删除");
	
	
	
	public static final ResultCode ARTICLE_ARGS_ILLEGAL = create("article数据非法");
	
	public static final ResultCode ARTICLE_ALREADY_EXIST = create("article已存在");
	
	public static final ResultCode ARTICLE_STORE_ERROR = create("存储文件错误");
	
	
	
	
	
	

}
