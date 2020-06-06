package com.google.code.efurture.kvdb;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * KV数据库序列化实现
 * */
public class KVDBCoder {
	
	 public static byte[] serialize(Serializable o) {
	        if (o == null) {
	            throw new NullPointerException("Can't serialize null");
	        }

	        byte[] rv = null;

	        try {
	            ByteArrayOutputStream bos = new ByteArrayOutputStream();
	            ObjectOutputStream    os  = new ObjectOutputStream(bos);

	            os.writeObject(o);
	            os.close();
	            bos.close();
	            rv = bos.toByteArray();
	        } catch (IOException e) {
	            throw new IllegalArgumentException("Non-serializable object", e);
	        }

	        return rv;
	    }

	    public static Serializable deserialize(byte[] in) {
	       	Serializable rv = null;

	        try {
	            if (in != null) {
	                ByteArrayInputStream bis = new ByteArrayInputStream(in);
	                ObjectInputStream    is  = new ObjectInputStream(bis);
	                rv                       = (Serializable) is.readObject();
	                is.close();
	                bis.close();
	            }
	        } catch (Exception e) {
	            throw new RuntimeException("deserialize failed", e);
	        }
	        return rv;
	    }
	
	
	

}
