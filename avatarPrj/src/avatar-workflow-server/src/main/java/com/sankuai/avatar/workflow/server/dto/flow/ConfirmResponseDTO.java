package com.sankuai.avatar.workflow.server.dto.flow;

import com.sankuai.avatar.workflow.core.context.FlowState;
import lombok.Builder;
import lombok.Data;

/**
 * 确认结果
 * @author Jie.li.sh
 * @create 2023-03-02
 **/
@Data
@Builder
public class ConfirmResponseDTO {
    /**
     * 流程状态
     */
    private FlowState state;
}
