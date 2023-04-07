package com.sankuai.avatar.dao.resource.repository.request;

import lombok.Builder;
import lombok.Data;

/**
 * UserRelation请求对象
 * @author caoyang
 * @create 2022-11-02 18:48
 */
@Data
@Builder
public class UserRelationRequest {
    /**
     * dx登录 mis
     */
    private String loginName;

    /**
     * 标签
     */
    private String tag;

    /**
     * appkey服务名称
     */
    private String appkey;
}
