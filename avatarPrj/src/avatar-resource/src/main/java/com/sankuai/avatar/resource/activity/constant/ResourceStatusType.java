package com.sankuai.avatar.resource.activity.constant;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * 活动资源状态枚举
 * @author caoyang
 * @create 2023-03-08 13:52
 */
@Getter
public enum ResourceStatusType {

    /**
     * status
     */
    HOLDING(0, "待交付"),

    WAITING(1, "交付中"),

    FINISH(2, "已交付"),

    CLOSE(3, "已撤销");

    private final int code;

    private final String name;

    ResourceStatusType(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static ResourceStatusType getResourceStatusType(String type){
        return Arrays.stream(values()).filter(status -> Objects.equals(status.name(), type)).findFirst().orElse(null);
    }

}
