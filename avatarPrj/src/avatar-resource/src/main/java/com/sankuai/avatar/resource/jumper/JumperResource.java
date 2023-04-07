package com.sankuai.avatar.resource.jumper;

/**
 * @author zhaozhifan02
 */
public interface JumperResource {
    /**
     * 跳板机账户解锁
     *
     * @param userName 用户名
     */
    void userUnlock(String userName);

    /**
     * 跳板机密码重置
     *
     * @param userName 用户名
     */
    void passwordReset(String userName);

}

