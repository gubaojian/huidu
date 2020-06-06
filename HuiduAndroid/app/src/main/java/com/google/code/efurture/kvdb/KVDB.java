package com.google.code.efurture.kvdb;

import java.io.Serializable;


/**
 * KV数据库接口
 * */
public interface KVDB {
	
	public abstract <T extends Serializable> void put(String key, T value);
	
	public abstract <T extends Serializable> void asncPut(String key, T value);
	
	public abstract <T extends Serializable> T get(String key);
	

	public abstract void remove(String key);
}
