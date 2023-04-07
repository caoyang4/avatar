package com.sankuai.avatar.web.service;

/**
 * @author caoyang
 * @create 2022-12-27 17:12
 */
public interface UserRelationService {

    /**
     * 置顶服务
     *
     * @param user   用户
     * @param appkey appkey
     * @return {@link Boolean}
     */
    Boolean saveUserTopAppkey(String user, String appkey);

    /**
     * 取消服务置顶
     *
     * @param user   用户
     * @param appkey appkey
     * @return {@link Boolean}
     */
    Boolean cancelUserTopAppkey(String user, String appkey);
}
