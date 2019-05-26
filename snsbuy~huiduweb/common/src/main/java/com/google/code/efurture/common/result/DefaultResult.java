package com.google.code.efurture.common.result;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnore;

public class DefaultResult<T> implements Result<T>{
    private static final long serialVersionUID = 3790077632310919529L;

        /**
         * 业务逻辑是否执行成功
         * */
        private boolean isSuccess;
        
        /**
         * 业务逻辑结果对象
         * */
        private T  result;
        
        /**
         * 业务逻辑视图模型
         * */
        private Map<Object, Object>  models;

        /**
         * 业务逻辑执行的结果代码
         * */
        private ResultCode resultCode;

        /**
         * 异常消息 
         * */
        @JsonIgnore
        private Throwable throwable;
        
        /**
         * 构造参数，默认是否成功标志是false
         * */
        public DefaultResult(){
                this(false);
        }

        
        public DefaultResult(boolean isSuccess){
                this.isSuccess = isSuccess;
        }

        public boolean isSuccess() {
                return isSuccess;
        }

        public void setSuccess(boolean isSuccess) {
                this.isSuccess = isSuccess;
        }

        public T getResult() {
                return result;
        }


        
        public Result<T> setResult(T result) {
                this.result = result;
                return this;
        }


        /**
         * 懒加载，节约内存
         * */
        public Map<Object,Object> getModels() {
                if(models == null){
                        models = new HashMap<Object,Object>(4);
                }
                return models;
        }




        public ResultCode getResultCode() {
                if(resultCode == null){
                        resultCode = ResultCode.create("unset empty ResultCode");
                }
                return resultCode;
        }


        public Result<T> setResultCode(ResultCode resultCode) {
                this.resultCode = resultCode;
                return this;
                
        }

        
        public Throwable getThrowable() {
                return throwable;
        }


        public Result<T> setThrowable(Throwable throwable) {
                this.throwable = throwable;
                return this;
        }

}
