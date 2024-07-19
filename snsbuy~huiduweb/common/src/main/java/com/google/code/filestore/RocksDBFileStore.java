package com.google.code.filestore;

import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;

import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class RocksDBFileStore implements FileStore {




    /**
     * 数据库存储路径
     * */
    private String rocksdbFilePath;



    /**
     * single thread 单线程存储
     * */
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
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
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                initRocksDb();
            }
        });
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
        final String uuid = UUID.randomUUID().toString();
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    fileStoreDB.put(uuid.getBytes(), bts);
                } catch (RocksDBException e) {
                    e.printStackTrace();
                }
            }
        });
        return uuid;
    }

    @Override
    public byte[] get(String key) {
        try {
            Future< byte[]> future = executorService.submit(new Callable<byte[]>(){
                @Override
                public byte[] call() throws Exception {
                    return fileStoreDB.get(key.getBytes());
                }
            });
            return future.get();
        } catch (Exception e) {
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
