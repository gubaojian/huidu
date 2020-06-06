package com.google.code.efurture.kvdb;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

/**
 * 模仿iOS的KVDB项目，在Android平台上利用sqlite实现Key-Value数据库。
 * 增加过期失效控制的功能。
 * */
public abstract class KVDBManager {

	private static Map<String, KVDB> kvdbsMap = new HashMap<String, KVDB>(2);
	private static Context context;
	/**
	 * 应用的Context，需要在Application初始化时设置该值
	 * @param context Context
	 * */
	public static void setContext(Context mContext){
		context = mContext;
	}
	
    /**
     * 以某个文件初始化KVDB,如果数据库已经初始化，返回已经初始化的对象。
     * @param fileName 存储文件名， 默认永久储存
     * */
	public static KVDB with(String fileName) {
		return with(fileName, Integer.MAX_VALUE/2);
	}

	/**
	 * 以某个文件初始化KVDB,如果数据库已经初始化，返回已经初始化的对象。
	 * @param fileName   存储文件名，
	 * @param expireTime 存储有效时间，单位秒
	 */ 
	public static KVDB with(String fileName, int expireTime){
		return with(fileName, expireTime, 1);
	}
	
	/**
	 * 以某个文件初始化KVDB,如果数据库已经初始化，返回已经初始化的对象。
	 * @param fileName   存储文件名，
	 * @param expireTime 存储有效时间，单位秒
	 * @param version    数据库版本, 默认值1，必须为>=1 的正数
	 */ 
	public static KVDB with(String fileName, int expireTime, int version){
		KVDB kvdb  = kvdbsMap.get(fileName);
		if (kvdb != null) {
			return kvdb;
		}
		synchronized (kvdbsMap) {
			 kvdb  = kvdbsMap.get(fileName);
			if (kvdb != null) {
				return kvdb;
			}
			kvdb = new KVDBSqliteStoreHelper(context, fileName, expireTime, version);
			kvdbsMap.put(fileName, kvdb);
			return kvdb;
		}
	}
}
