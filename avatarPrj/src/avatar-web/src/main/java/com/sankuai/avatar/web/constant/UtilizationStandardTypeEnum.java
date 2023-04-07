package com.sankuai.avatar.web.constant;

import lombok.Getter;

/**
 * 资源利用率枚举类型
 *
 * @author qinwei05
 * @date 2023/01/04
 */

@Getter
public enum UtilizationStandardTypeEnum {
    /**
     * 忽略
     */
    SKIP_STANDARD(0, "", "免达标"),

    /**
     * 已达标
     */
    STANDARD(1, "success", "已达标"),

    /**
     * 未达标
     */
    UN_STANDARD(2, "danger", "未达标");

    private final int code;

    private final String name;

    private final String cnName;

    UtilizationStandardTypeEnum(int code, String name, String cnName) {
        this.code = code;
        this.name = name;
        this.cnName = cnName;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getCnName() {
        return cnName;
    }
}
