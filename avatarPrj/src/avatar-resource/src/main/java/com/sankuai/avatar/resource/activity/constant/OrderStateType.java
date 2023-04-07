package com.sankuai.avatar.resource.activity.constant;

import lombok.Getter;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.Objects;

/**
 * 订单状态
 * @author caoyang
 * @create 2023-02-13 11:21
 */
@Getter
public enum OrderStateType {

    /**
     * 进行中
     */
    HOLDING(0, "进行中"),

    /**
     * 已结束
     */
    FINISH(1, "已结束"),

    /**
     * 已关闭
     */
    CLOSE(2, "已关闭");

    private final int code;

    private final String name;

    OrderStateType(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static OrderStateType getInstance(String state){
        if (StringUtils.isEmpty(state)) {
            return null;
        }
        return Arrays.stream(OrderStateType.values())
                .filter(type -> Objects.equals(state, type.name())).findFirst().orElse(null);
    }
}
