package com.sankuai.avatar.resource.appkey.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 服务分页查询请求对象
 *
 * @author qinwei05
 * @date 2023/02/07
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IsoltAppkeyRequestBO {

    /**
     * 页码
     */
    private Integer page;

    /**
     * 页码数
     */
    private Integer pageSize;

    /**
     * 检索关键字
     */
    private String query;

    /**
     * 服务等级
     */
    private String rank;

    /**
     * 服务状态
     */
    private Boolean stateful;

    /**
     * 服务容灾等级:支持多个以逗号分隔
     */
    private String capacity;

    /**
     * 用户MIS
     */
    private String mis;
}

