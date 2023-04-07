package com.sankuai.avatar.dao.workflow.repository.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @author zhaozhifan02
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class Atom {
    String name;
}
