package com.sankuai.avatar.workflow.core.context.config;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 流程配置信息：资源锁相关字段
 *
 * @author zhaozhifan02
 */
@Data
@Builder
public class FlowMutexResource {
    /**
     * 资源锁字段
     * 举例：
     * [
     * "$.template_name;$.input.fullname",
     * "$.input.vs_list..vip;$.input.vs_list..vport;$.input.vs_list..protocol"
     * ]
     */
    private List<String> mutexFields;
}
