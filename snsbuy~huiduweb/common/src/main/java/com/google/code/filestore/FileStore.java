package com.google.code.filestore;

public interface FileStore {



    /**
     * 保存文件系统
     * */
    public String save(byte[] bts);



    /**
     * 获取文件系统
     * */
    public byte[] get(String key);

    /**
     * 获取内容文件
     * */
    public String getString(String key);

}
