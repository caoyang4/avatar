package com.sankuai.avatar.resource.capacity.constant;

import lombok.Getter;

/**
 * 机房所属地域类型
 * @author caoyang
 * @create 2022-11-03 16:10
 */
@Getter
public enum IdcRegionType {
    // 北京
    BJ("BJ"),
    // 上海
    SH("SH"),
    // 其他
    OTHERS("OTHERS");

    private final String desc;

    IdcRegionType(String desc){
        this.desc = desc;
    }
}
