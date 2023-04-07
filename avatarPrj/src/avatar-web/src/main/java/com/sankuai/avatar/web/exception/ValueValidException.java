package com.sankuai.avatar.web.exception;

/**
 * @author caoyang
 * @create 2022-09-22 16:50
 */
public class ValueValidException extends RuntimeException{
    public ValueValidException(){
        super();
    }
    public ValueValidException(String msg){
        super(msg);
    }
}
