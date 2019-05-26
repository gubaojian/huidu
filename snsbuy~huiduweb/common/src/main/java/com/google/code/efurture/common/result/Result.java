package com.google.code.efurture.common.result;

import java.io.Serializable;
import java.util.Map;

public interface Result<T> extends Serializable{
    /**
     * 业务是否成功
     * */
    public boolean isSuccess();
    
    /**
     * 设置业务操作是否成功
     * */
    public void setSuccess(boolean isSuccess);

    /**
     * 业务操作，单一返回数据模型
     * */
    public T getResult();
    
    /**
     * 业务操作，单一返回数据模型
     * */
    public Result<T> setResult(T result);

    /**
     * 多参数时模型的上下文
     * */
    public Map<Object,Object> getModels();
    
    /**
     * 业务结果代码, 返回值，必须不为空
     * */
    public ResultCode getResultCode();

    /**
     * 业务结果代码
     * */
    public Result<T> setResultCode(ResultCode resultCode) ;

    /**
     * 业务发生异常时的异常信息
     * */
    public Throwable getThrowable();
    
    /**
     * 业务发生异常时的异常信息
     * */
    public Result<T> setThrowable(Throwable throwable);
}

