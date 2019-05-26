package com.google.code.efurture.common.result;

import java.io.Serializable;
import java.text.MessageFormat;

public class Message implements Serializable{
    private static final long serialVersionUID = 7466881182946883213L;

    /**
     * 配置的信息
     * */
    private String message;
    
    /**
     * 格式化信息
     * */
    private Object[] args;
    
    
    public Message(String message){
            this.message = message;
    }
    
    public String format(Object... args){
            return MessageFormat.format(message, args);
    }
    
    public  void setMessage(String message) {
            this.message = message;
    }
    
    public String getMessage() {
            return message;
    }

public String getFmtMessage(){
    if(args == null){
            return message;
    }else{
            return  MessageFormat.format(message, args);
    }
}

    public Object[] getArgs() {
            return args;
    }


    public void setArgs(Object[] args) {
            this.args = args;
    }

	@Override
	public String toString() {
		return  this.getFmtMessage();
	}
    
    
}