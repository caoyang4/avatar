package com.sankuai.avatar.dao.workflow.repository.request;

import com.sankuai.avatar.dao.workflow.repository.model.ExecuteResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhaozhifan02
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class FlowAtomRecordAddRequest {
    /**
     * 流程ID
     */
    private Integer flowId;

    /**
     * 原子化步骤名称
     */
    private String atomName;

    /**
     * 状态
     */
    private String status;

    /**
     * 重试次数
     */
    private Integer retryTimes;

    /**
     * 超时时间
     */
    private Integer timeout;

    /**
     * 执行结果
     */
    private ExecuteResult result;
}
