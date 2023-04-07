package com.sankuai.avatar.dao.workflow.repository.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author zhaozhifan02
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class FlowSwitch {
    private boolean mutexResouce;

    @JsonProperty("disable_notice_sre")
    private Integer disableNoticeSre;
}
