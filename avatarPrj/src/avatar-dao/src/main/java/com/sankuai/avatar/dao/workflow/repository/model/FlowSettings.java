package com.sankuai.avatar.dao.workflow.repository.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author zhaozhifan02
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class FlowSettings {

    @JsonProperty("rollback_url")
    private String rollbackUrl;

    @JsonProperty("mutexCommonFields")
    private String mutexCommonFields;
}
