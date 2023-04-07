package com.sankuai.avatar.resource.activity.bo;

import lombok.Data;

/**
 * 订单机器配置
 * @author caoyang
 * @create 2023-02-13 11:19
 */
@Data
public class OrderHostBO {

    /**
     * 链路
     */
    private String cell;

    /**
     * ip地址
     */
    private String ipLan;

    /**
     * 机器名称
     */
    private String name;

    private Integer cpu;

    private Integer memory;

    private Integer disk;

    private String kind;

    private String os;

    /**
     * 机房
     */
    private String idc;

}
