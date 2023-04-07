package com.sankuai.avatar.workflow.server.mafka.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhaozhifan02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class FlowMessage {
    /**
     * 流程Id
     */
    private Integer id;
    /**
     * 流程类型
     */
    private String type;
    /**
     * 流程内容
     */
    private Object data;
}
