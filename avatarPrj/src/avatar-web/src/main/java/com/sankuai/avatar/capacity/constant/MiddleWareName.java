package com.sankuai.avatar.capacity.constant;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 中间件名称
 */
public enum MiddleWareName {
    /**
     *
     */
    OCTO_TRIFT("octo_thrift"),
    OCTO_HTTP("octo_http"),
    OCEANUS_HTTP("oceanus_http"),
    MQ("mafka"),
    CRANE("crane"),
    MGW("mgw");

    MiddleWareName(String name) { }
    public static MiddleWareName[] getMiddleWareNameList() {
        return MiddleWareName.class.getEnumConstants();
    }

    /**
     * @return 判断自动迁移的中间件列表
     */
    public static MiddleWareName[] getAutoMigrationMiddleWareNameList() {
        return MiddleWareName.class.getEnumConstants();
    }

    public static String formatMiddleWareNames(List<MiddleWareName> middleWareNames) {
        return middleWareNames.stream().map(MiddleWareName::name).collect(Collectors.joining(","));
    }

    public static String formatMiddleWareNames(MiddleWareName[] middleWareNames) {
        return Arrays.stream(middleWareNames).map(MiddleWareName::name).collect(Collectors.joining(","));
    }
}
