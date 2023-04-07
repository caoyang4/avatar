package com.sankuai.avatar.capacity.constant;

import lombok.Getter;

/**
 * @author jie.li.sh
 */
@Getter
public enum CompareType {
    /**
     * 大于
     */
    GREATER_THAN("gt", ">"),
    LESS_THAN("lt", "<"),
    /**
     * 是否包含
     */
    CONTAINS("contains", "包含"),
    EQUALS("EQUALS", "为");
    private String compare;
    private String name;

    CompareType(String compare, String name) {
        this.compare = compare;
        this.name = name;
    }
}
