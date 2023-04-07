package com.sankuai.avatar.workflow.core.context;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @author zhaozhifan02
 */
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class FlowPublicData {
    /**
     * 展示信息
     */
    private FlowDisplay display;

    /**
     * 时间开始时间
     */
    @JsonProperty("event_start_time")
    private Long eventStartTime;

    /**
     * 登录用户
     */
    @JsonProperty("login_name")
    private String loginName;

    /**
     * 域名
     */
    @JsonProperty("source_domain")
    private String sourceDomain;

    /**
     * 源IP
     */
    @JsonProperty("source_ip")
    private String sourceIp;

    /**
     * 扩展信息
     */
    @JsonProperty("extra_info")
    private Object extraInfo;
}
