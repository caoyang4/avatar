package com.sankuai.avatar.capacity.constant;

import lombok.*;

@Getter
public enum WhiteApp {
    /**
     *
     */
    AUTO_MIGRATE("auto-migration", "自动迁移"),
    CAPACITY("capacity", "容灾等级"),
    BJ_READONLY("bj-readonly", "北京侧只读"),
    IDC("idc", "多机房"),
    UTILIZATION("utilization", "资源利用率"),
    ST_SINGLE("st-single-relieve", "staging单机限制解除"),
    SERVICE_DRILL("service_drill", "非核心演习"),
    ALTERATION("alteration", "高峰期解禁");

    private String appName;
    private String cname;

    WhiteApp(String appName, String cname) {
        this.appName = appName;
        this.cname = cname;
    }
}
