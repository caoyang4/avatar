package com.sankuai.avatar.resource.emergency.bo;

import lombok.Data;

/**
 * @author caoyang
 * @create 2022-12-02 21:20
 */
@Data
public class EmergencyHostBO {

    /**
     * set
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

    /**
     * cpu大小
     */
    private Integer cpu;

    /**
     * 内存大小
     */
    private Integer memory;

    /**
     * 磁盘大小
     */
    private Integer disk;

    /**
     * 机器类型
     */
    private String kind;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 所属机房
     */
    private String idc;
}
