package com.sankuai.avatar.workflow.server.dto.flow;

import com.sankuai.avatar.workflow.core.context.FlowState;
import lombok.Builder;
import lombok.Data;

/**
 * 流程取消结果
 * @author Jie.li.sh
 * @create 2023-03-02
 **/
@Data
@Builder
public class CancelResponseDTO {
    /**
     * 流程状态
     */
    private FlowState state;
}
