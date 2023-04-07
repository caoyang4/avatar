package com.sankuai.avatar.dao.workflow.repository.request;

import com.sankuai.avatar.dao.workflow.repository.model.FlowTemplateTask;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

/**
 * @author zhaozhifan02
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class FlowPublicDataUpdateRequest {
    /**
     * 流程ID
     */
    private Integer flowId;
    /**
     * 公共数据
     */
    private Object publicData;

    /**
     * 任务列表
     */
    private List<FlowTemplateTask> tasks;

    /**
     * 配置
     */
    private Object config;

    /**
     * 日志
     */
    private Object logs = Collections.emptyMap();
}
