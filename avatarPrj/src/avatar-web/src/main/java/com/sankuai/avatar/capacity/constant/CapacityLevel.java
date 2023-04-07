package com.sankuai.avatar.capacity.constant;

import lombok.Getter;

/**
 * @author chenxinli
 */

@Getter
public enum CapacityLevel {
    /**
     * 容灾等级
     */
    FIVE(5),
    FOUR(4),
    THREE(3),
    TWO(2),
    ONE(1),
    ZERO(0),
    NO(-1);
    private int num;

    CapacityLevel(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return String.format("容灾等级： %d", num);
    }
}
