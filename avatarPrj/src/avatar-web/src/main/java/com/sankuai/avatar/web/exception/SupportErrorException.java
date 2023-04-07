package com.sankuai.avatar.web.exception;

/**
 * @author Jie.li.sh
 * @create 2020-03-19
 **/
public class SupportErrorException extends RuntimeException{
    public SupportErrorException() { super(); }

    public SupportErrorException(String message) {
        super(message);
    }
}
