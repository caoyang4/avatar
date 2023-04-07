package com.sankuai.avatar.resource.capacity.constant;

/**
 * 资源利用率达标类型
 * @author caoyang
 * @create 2022-11-03 15:38
 */
public enum UtilizationStandardType {

    /**
     * 已达标
     */
    STANDARD(1, "已达标"),
    /**
     * 未达标
     */
    UN_STANDARD(0, "未达标"),
    /**
     * 免达标
     */
    SKIP_STANDARD(-1, "免达标");


    private Integer code;
    private String desc;

    UtilizationStandardType(Integer code, String desc){
        this.code = code;
        this.desc = desc;
    }
}
