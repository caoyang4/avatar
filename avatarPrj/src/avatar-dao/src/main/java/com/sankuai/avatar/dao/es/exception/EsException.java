package com.sankuai.avatar.dao.es.exception;

/**
 * @author caoyang
 * @create 2022-12-09 16:56
 */
public class EsException extends RuntimeException{

    public EsException() {
        super();
    }
    public EsException(String message) {
        super(message);
    }

}