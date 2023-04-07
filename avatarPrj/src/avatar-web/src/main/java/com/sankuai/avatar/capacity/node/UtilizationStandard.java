package com.sankuai.avatar.capacity.node;

/**
 * @author chenxinli
 */

public enum UtilizationStandard {

    /**
     * 资源利用率达标
     */
    STANDARD(1, "已达标"),
    UN_STANDARD(0, "未达标"),
    SKIP_STANDARD(-1, "免达标");


    private Integer code;
    private String desc;

    UtilizationStandard(Integer code, String desc){
        this.code = code;
        this.desc = desc;
    }

}
