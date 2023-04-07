package com.sankuai.avatar.workflow.server.dto.flow;

import com.sankuai.avatar.workflow.core.context.FlowState;
import com.sankuai.avatar.workflow.core.engine.process.response.PreCheckResult;
import lombok.Builder;
import lombok.Data;

/**
 * 创建接口返回数据
 * @author Jie.li.sh
 * @create 2023-02-28
 **/
@Data
@Builder
public class CreateResponseDTO {
    /**
     * 主键ID
     */
    private Integer id;

    /**
     * uuid
     */
    private String uuid;

    /**
     * 流程状态
     */
    private FlowState state;

    /**
     * 预检结果
     */
    private PreCheckResult preCheckResult;
}
