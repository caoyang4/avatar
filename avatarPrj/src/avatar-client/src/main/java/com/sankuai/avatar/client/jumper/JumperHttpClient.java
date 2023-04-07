package com.sankuai.avatar.client.jumper;

import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;

/**
 * Jumper http 客户端
 *
 * @author zhaozhifan02
 */
public interface JumperHttpClient {
    /**
     * 账户解锁
     *
     * @param userName 用户名
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    void userUnlock(String userName) throws SdkCallException, SdkBusinessErrorException;


    /**
     * 跳板机密码重置
     *
     * @param userName 用户名
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    void passwordReset(String userName) throws SdkCallException, SdkBusinessErrorException;
}
