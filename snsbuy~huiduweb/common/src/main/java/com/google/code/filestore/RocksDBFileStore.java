package com.google.code.filestore;

import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;

import java.util.UUID;

public class RocksDBFileStore implements FileStore {


    static {
        RocksDB.loadLibrary();
    }

    /**
     * 数据库存储路径
     * */
    private String rocksdbFilePath;

    /**
     * 数据库存储
     * */
    private RocksDB fileStoreDB;

    /**
     * LRUCache
     * */
    private LruCache<String, String> lruCache;


    public RocksDBFileStore(String rocksdbFilePath) {
        this.rocksdbFilePath = rocksdbFilePath;
        this.lruCache = new LruCache(256);
        initRocksDb();
    }





    private void initRocksDb(){
        try {
            Options options = new Options().setCreateIfMissing(true);
            fileStoreDB = RocksDB.open(options, rocksdbFilePath);
        } catch (RocksDBException e) {
           throw new RuntimeException(e);
        }

    }


    @Override
    public String save(byte[] bts) {
        String uuid = UUID.randomUUID().toString();
        try {
            fileStoreDB.put(uuid.getBytes(), bts);
        } catch (RocksDBException e) {
            throw new RuntimeException(e);
        }
        return uuid;
    }

    @Override
    public byte[] get(String key) {
        try {
            return fileStoreDB.get(key.getBytes());
        } catch (RocksDBException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getString(String key) {
        String result = lruCache.get(key);
        if(result != null){
            return result;
        }
        byte[] bts = get(key);
        if(bts !=  null){
            result = new String(bts);
            lruCache.put(key, result);
        }
        return result;
    }
}
