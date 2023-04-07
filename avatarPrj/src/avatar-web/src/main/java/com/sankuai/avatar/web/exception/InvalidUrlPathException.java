package com.sankuai.avatar.web.exception;

/**
 * @author caoyang
 */
public class InvalidUrlPathException extends RuntimeException{
    public InvalidUrlPathException(){
        super("请求路径不正确...");
    }

    public InvalidUrlPathException(String msg){
        super(msg);
    }
}
