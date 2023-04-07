package com.sankuai.avatar.workflow.server.dto.flow;

import com.sankuai.avatar.workflow.core.context.FlowState;
import lombok.Builder;
import lombok.Data;

/**
 * 审核通过响应对象
 *
 * @author zhaozhifan02
 */
@Data
@Builder
public class AcceptResponseDTO {
    /**
     * 流程状态
     */
    private FlowState state;
}
