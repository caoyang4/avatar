package com.sankuai.avatar.collect.appkey.event;

/**
 * 收集事件名
 *
 * @author qinwei05
 * @date 2022/12/14
 */
public enum CollectEventName {

    /**
     * 后端类型 appkey 全量刷新
     */
    APPKEY_REFRESH("appkey_refresh"),

    /**
     * ops 服务数据更新
     */
    OPS_APPKEY_UPDATE("ops_appkey_update"),

    /**
     * 服务数据删除
     */
    APPKEY_DELETE("appkey_delete"),

    /**
     * sc 更新
     */
    SC_APPKEY_UPDATE("sc_appkey_update"),

    /**
     * sc 更新(非后端类型服务)
     */
    SC_NOT_BACKEND_APPKEY_UPDATE("sc_not_backend_appkey_update"),

    /**
     * rocket 主机上下线
     */
    ROCKET_HOST_UPDATE("rocket_host_update"),

    /**
     * 服务容灾等级更新
     */
    APPKEY_CAPACITY_UPDATE("appkey_capacity_update"),

    /**
     * dom资源利用率更新
     */
    DOM_UTIL_UPDATE("dom_util_update");

    private final String value;

    CollectEventName(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
