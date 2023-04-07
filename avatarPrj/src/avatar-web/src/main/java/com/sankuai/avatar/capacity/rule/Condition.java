package com.sankuai.avatar.capacity.rule;

import com.sankuai.avatar.capacity.constant.CompareType;
import com.sankuai.avatar.capacity.property.AbstractProperty;
import lombok.Getter;

/**
 * @author Jie.li.sh
 * @create 2020-04-05
 **/

@Getter
public class Condition <T>{
    private Class<? extends AbstractProperty> clazz;
    private T expect;
    private CompareType compareType;

    public Condition(Class<? extends AbstractProperty> clazz, T expect) {
        this.clazz = clazz;
        this.expect = expect;
        this.compareType = CompareType.EQUALS;
    }

    public Condition(Class<? extends AbstractProperty> clazz, T expect, CompareType compareType) {
        this.clazz = clazz;
        this.expect = expect;
        this.compareType = compareType;
    }
}
