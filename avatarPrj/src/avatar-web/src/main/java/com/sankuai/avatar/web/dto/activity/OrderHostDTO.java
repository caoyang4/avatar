package com.sankuai.avatar.web.dto.activity;

import lombok.Data;

/**
 * @author caoyang
 * @create 2023-02-13 14:42
 */
@Data
public class OrderHostDTO {

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
