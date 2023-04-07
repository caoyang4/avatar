package com.sankuai.avatar.resource.appkey.request;

import com.sankuai.avatar.common.vo.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author caoyang
 * @create 2023-01-12 11:56
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AppkeySrvsQueryRequest extends PageRequest {

    /**
     * type
     */
    private String type;

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
