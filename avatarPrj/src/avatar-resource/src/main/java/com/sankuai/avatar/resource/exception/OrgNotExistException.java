package com.sankuai.avatar.resource.exception;

/**
 * org不存在异常
 * @author caoyang
 * @create 2022-11-13 20:41
 */
public class OrgNotExistException extends RuntimeException{
    public OrgNotExistException() {
        super();
    }
    public OrgNotExistException(String message) {
        super(message);
    }
}

