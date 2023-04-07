package com.sankuai.avatar.web.service;

import com.sankuai.avatar.web.vo.DXUserVO;

import java.util.List;
import java.util.Map;

/**
 * @author Jie.li.sh
 * @create 2020-02-18
 **/
public interface DXUserService {
    /**
     * 根据mis获取用户大象头像/姓名/性别
     *
     * @param mis mis
     * @return 大象头像url
     */
    DXUserVO getUserVo(String mis);

    /**
     * 缓存逻辑: 根据mis批量获取缓存的用户大象头像
     * @param mis mis
     * @return 大象头像url
     */
    Map<String, String> batchGetUserAvatarUrlCache(List<String> mis);

    /**
     * 查询用户大象头像[带缓存]
     * @param mis mis
     * @param userUrlMap 缓存Map
     * @return 大象头像url
     */
    String getUserAvatarUrlByCache(String mis, Map<String, String> userUrlMap);


    /**
     * 根据misList获取用户大象头像 mis->url
     *
     * @param misList mis列表
     * @return List<UserVO>
     */
    List<DXUserVO> getUserVo(List<String> misList);
}
