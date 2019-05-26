package com.google.code.efurture.common.result;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnore;

public class ResultCode implements Serializable {

    private static final long serialVersionUID = 8642966485247603481L;

    /**
     * 错误代码
     * */
    private int code;
    
    /**
     * 格式化message
     * */
    private String message;
    
    
   
    /**
     * 采用string的hashcode作为代码
     * */
    public static final <T extends ResultCode> T create(String message) {
            return create(message.hashCode(), message);
    }

    /**
     * 错误代码的标志符号, 每个错误代码都不同
     * */
    @SuppressWarnings("unchecked")
    public static final <T extends ResultCode> T create(int code, String message) {
            try {
                    String className = getCallerClassName();

                    Class<T> classType = null;

                    ClassLoader classLoader = Thread.currentThread()
                                    .getContextClassLoader();
                    if (classLoader == null) {
                            classType = (Class<T>) Class.forName(className);
                    } else {
                            classType = (Class<T>) Class.forName(className, true,
                                            classLoader);
                    }

                    T instance = classType.newInstance();
                    instance.setCode(code);
                    instance.setMessage(message);
                    return instance;
            } catch (Exception e) {
                    throw new RuntimeException("Instant ResultCode instance  error", e);
            }
    }

    /**
     * 从apache common-lang中的Enum源代码中copy过来。
     * */
    private static String getCallerClassName() {
            StackTraceElement[] callers = new Throwable().getStackTrace();
            String enumClass = ResultCode.class.getName();

            for (int i = 0; i < callers.length; i++) {
                    StackTraceElement caller = callers[i];
                    String className = caller.getClassName();
                    String methodName = caller.getMethodName();

                    if (!enumClass.equals(className) && "<clinit>".equals(methodName)) {
                            return className;
                    }
            }

          return ResultCode.class.getName();
    }

    

    public int getCode() {
            return code;
    }



    public ResultCode setCode(int code) {
            this.code = code;
            return this;
    }

    public String getMessage() {
            return message;
    }
    
    @JsonIgnore
    public Message getFmtMessage() {
        return new Message(message);
}

    public void setMessage(String message) {
            this.message = message;
    }

    @Override
    public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + code;
            return result;
    }

    @Override
    public boolean equals(Object obj) {
            if (this == obj)
                    return true;
            if (obj == null)
                    return false;
            ResultCode other = (ResultCode) obj;
            if (code != other.code)
                    return false;
            return true;
    }
    
    
    public String toString(){
         return getMessage();
    }
}