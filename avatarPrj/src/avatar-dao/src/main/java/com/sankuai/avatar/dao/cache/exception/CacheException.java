package com.sankuai.avatar.dao.cache.exception;

/**
 * 缓存异常
 * @author caoyang
 * @create 2022-10-21 17:21
 */
public class CacheException extends RuntimeException{
    public CacheException() {
        super();
    }
    public CacheException(String message) {
        super(message);
    }
}
