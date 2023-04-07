package com.sankuai.avatar.client.soa.model;

import lombok.*;

/**
 * SC接口返回的Org模型对象
 *
 * @author zhangxiaoning07
 * @create 2022/11/10
 **/
@Data
@Builder
public class ScOrg {
    /**
     * 服务数
     */
    private Integer appKeyCount;

    /**
     * 应用数
     */
    private Integer applicationCount;

    /**
     * 负责人
     */
    private ScUser leader;
}
