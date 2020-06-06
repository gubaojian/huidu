package com.google.code.efurture.kvdb;

import java.io.Serializable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;

public class KVDBSqliteStoreHelper extends SQLiteOpenHelper implements KVDB {
    
	private static final String TABLE_NAME = "kv_store";
	
	private int mExpireTime;

	public KVDBSqliteStoreHelper(Context context, String dbName, int expireTime, int version){
		super(context, dbName, null, version);
		this.mExpireTime = expireTime;
		this.cleanOldData();
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE '" +  TABLE_NAME + "' ("
				 + "'key' TEXT NOT NULL PRIMARY KEY UNIQUE,"
				 + "'value' BLOB NULL, "
				 + "'gmt_create' INTEGER NULL"+
				   ");");
		db.execSQL("CREATE INDEX 'gmt_create_index' ON '"+ TABLE_NAME +"' ('gmt_create' ASC);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS '" + TABLE_NAME + "';");
		this.onCreate(db);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public  <T extends Serializable> T get(String key){
		SQLiteDatabase db = null; 
		try{
			db = this.getReadableDatabase();
			Cursor cursor = db.query(TABLE_NAME, new String[]{"value", "gmt_create"}, "key = ? ", new String[]{key}, null, null, null);
		    if(cursor == null){
		    	   return null;
		    }
		    
		    if(!cursor.moveToFirst()){
		    	   return null;
		    }
		    byte[] bts = cursor.getBlob(0);
		    int gmtCreate = cursor.getInt(1);
		    int validGmtCreate =  this.currentSeconds() - mExpireTime;
		    if (gmtCreate > validGmtCreate) { //数据有效
				return  (T) KVDBCoder.deserialize(bts);
			} 
		    //数据已过期，清除此条数据
		    this.remove(key);
		}catch(Exception e){
			Log.e("KVDB", "KVDB get error key = " + key, e);
		}finally{
			if(db != null){
				db.close();
			}
		}
		return null;
	}
	
	@Override
	public <T extends Serializable> void asncPut(final String key, final T value){
		AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>(){
			@Override
			protected Void doInBackground(Void... params) {
				put(key, value);
				return null;
			}
		};
		task.execute();
	}

	public <T extends Serializable> void put(String key, T value){
		SQLiteDatabase db = null;
		try{
		 	db = this.getWritableDatabase();
			byte[] bts = KVDBCoder.serialize(value);
			ContentValues  values = new ContentValues();
			values.put("key", key);
			values.put("value", bts);
			values.put("gmt_create", this.currentSeconds());
			db.replace(TABLE_NAME,  null,  values);
		}catch(Exception e){
			Log.e("KVDB", "KVDB Put error key = " + key, e);
		}finally{
			if(db != null){
				db.close();
			}
		}
	}



	@Override
	public void remove(String key) {
		SQLiteDatabase db = null;
		try{
		 	db = this.getWritableDatabase();
			db.delete(TABLE_NAME,  " key = ? ",  new String[]{key});
		}catch(Exception e){
			Log.e("KVDB", "KVDB Put error key = " + key, e);
		}finally{
			if(db != null){
				db.close();
			}
		}
	}
	
	/**
	 * 清除过期的数据
	 * */
	private void cleanOldData(){
		int validGmtCreate = this.currentSeconds() - mExpireTime;
		SQLiteDatabase db = null;
		try{
		 	db = this.getWritableDatabase();
		 	if (db == null) {
				return;
			}
			db.delete(TABLE_NAME,  " gmt_create < ? ",  new String[]{String.valueOf(validGmtCreate)});
		}catch(Exception e){
			Log.e("KVDB", "KVDB cleanOldData error expireTime = " + mExpireTime, e);
		}finally{
			if(db != null){
				db.close();
			}
		}
	}
	
	/**
	 * 返回当前时间，以秒为单位。
	 * */
	private int currentSeconds(){
		return (int) (System.currentTimeMillis()/1000);
	}

	
	

}
