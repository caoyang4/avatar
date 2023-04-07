package com.sankuai.avatar.dao.workflow.repository.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author zhaozhifan02
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FlowTemplateTask {

    @JsonProperty("atom_list")
    List<Atom> atomList;

    @JsonProperty("task_name")
    String taskName;
}
