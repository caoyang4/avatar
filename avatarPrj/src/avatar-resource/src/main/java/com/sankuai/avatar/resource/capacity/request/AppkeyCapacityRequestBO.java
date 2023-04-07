package com.sankuai.avatar.resource.capacity.request;

import com.sankuai.avatar.common.vo.PageRequest;
import lombok.*;

/**
 * @author caoyang
 * @create 2022-11-03 18:30
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppkeyCapacityRequestBO extends PageRequest {

    /**
     * 是否返回全部字段
     */
    private Boolean isFullField;

    /**
     * 是否仅查询 appkey
     */
    private Boolean onlyAppkey;

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
