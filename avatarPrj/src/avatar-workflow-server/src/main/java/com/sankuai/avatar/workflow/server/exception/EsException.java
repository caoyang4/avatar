package com.sankuai.avatar.workflow.server.exception;

/**
 * @author caoyang
 * @create 2023-02-21 15:39
 */
public class EsException extends RuntimeException{

    public EsException() {
        super();
    }
    public EsException(String message) {
        super(message);
    }

}
