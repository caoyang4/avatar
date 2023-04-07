package com.sankuai.avatar.dao.resource.repository.request;

import lombok.Builder;
import lombok.Data;

/**
 * @author caoyang
 * @create 2022-11-03 14:22
 */
@Data
@Builder
public class AppkeyCapacityRequest {

    /**
     * 是否返回全部字段
     */
    private Boolean isFullField;

    /**
     * 是否只返回 distinct appkey
     */
    private boolean onlyAppkey;

    /**
     * 服务名称
     */
    private String appkey;

    /**
     * set 名称：包含（set/liteset）
     */
    private String setName;

    /**
     * 容灾是否达标
     */
    private Boolean isCapacityStandard;

    /**
     * 根据 org 模糊搜索
     */
    private String org;
}
