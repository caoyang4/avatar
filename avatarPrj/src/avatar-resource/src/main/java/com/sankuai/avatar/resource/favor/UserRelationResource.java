package com.sankuai.avatar.resource.favor;

import com.sankuai.avatar.resource.favor.bo.UserRelationBO;

import java.util.List;

/**
 * 用户服务置顶资源管理
 * @author caoyang
 * @create 2022-12-27 15:59
 */
public interface UserRelationResource {

    /**
     * 获取用户置顶服务
     *
     * @param mis 管理信息系统
     * @return {@link List}<{@link String}>
     */
    List<String> getUserTopAppkey(String mis);

    /**
     * 保存用户置顶服务信息
     *
     * @param userRelationBO 博用户关系
     * @return {@link Boolean}
     */
    Boolean saveUserRelation(UserRelationBO userRelationBO);

    /**
     * 取消服务置顶
     *
     * @param user   用户
     * @param appkey appkey
     * @return {@link Boolean}
     */
    Boolean cancelUserRelation(String user, String appkey);
}
