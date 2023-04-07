package com.sankuai.avatar.resource.capacity.constant;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 中间件类型
 * @author caoyang
 * @create 2022-11-03 15:55
 */
@Getter
public enum MiddleWareType {

    /**
     * octo thrift
     */
    OCTO_TRIFT("octo_thrift"),
    /**
     * octo http
     */
    OCTO_HTTP("octo_http"),
    /**
     * oceanus http
     */
    OCEANUS_HTTP("oceanus_http"),
    /**
     * mafka
     */
    MQ("mafka"),
    /**
     * crane
     */
    CRANE("crane"),
    /**
     * mgw
     */
    MGW("mgw");

    private final String wareName;
    MiddleWareType(String wareName) {
        this.wareName = wareName;
    }

    /**
     * 返回所有中间件类型
     *
     * @return {@link MiddleWareType[]}
     */
    public static MiddleWareType[] getAutoMigrationMiddleWareNameList() {
        return MiddleWareType.class.getEnumConstants();
    }

    /**
     * 串联指定的中间件
     *
     * @param middleWareNames 中间件列表
     * @return {@link String}
     */
    public static String formatMiddleWares(List<MiddleWareType> middleWareNames) {
        return middleWareNames.stream().map(MiddleWareType::name).collect(Collectors.joining(","));
    }

    /**
     * 串联指定的中间件
     *
     * @param middleWareNames 中间件列表
     * @return {@link String}
     */
    public static String formatMiddleWares(MiddleWareType[] middleWareNames) {
        return Arrays.stream(middleWareNames).map(MiddleWareType::name).collect(Collectors.joining(","));
    }
}
