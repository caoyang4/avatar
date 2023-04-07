package com.sankuai.avatar.dao.es.request;

import lombok.Builder;
import lombok.Data;

/**
 * @author caoyang
 * @create 2023-01-12 14:48
 */
@Data
@Builder
public class UserAppkeyRequest {

    /**
     * 页码
     */
    private Integer page;

    /**
     * 每页大小
     */
    private Integer pageSize;

    /**
     * 用户MIS
     */
    private String user;

    /**
     * 服务等级
     */
    private String rank;

    /**
     * SET化（Set、LiteSet）
     */
    private String cell;

    /**
     * paas服务
     */
    private Boolean paas;

    /**
     * 检索关键字
     */
    private String query;

    /**
     * 服务状态
     */
    private Boolean stateful;

    /**
     * 服务容灾等级:支持多个以逗号分隔
     */
    private String capacity;

}
