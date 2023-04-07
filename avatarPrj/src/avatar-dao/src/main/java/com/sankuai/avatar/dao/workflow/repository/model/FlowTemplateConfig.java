package com.sankuai.avatar.dao.workflow.repository.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author zhaozhifan02
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FlowTemplateConfig {

    private FlowSettings settings;

    @JsonProperty("switch")
    private FlowSwitch switchs;

    @JsonProperty("output_display")
    private boolean outputDisplay;

    @JsonProperty("output_display_Field")
    private String outputDisplayField;

    @JsonProperty("output_display_Rows")
    List<String> outputDisplayRows;
}
