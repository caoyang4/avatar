package com.sankuai.avatar.dao.workflow.repository.request;

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
public class FlowDataUpdateRequest {
    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 流程ID
     */
    private Integer flowId;

    /**
     * 流程申请信息
     */
    private String input;

    /**
     * 流程操作的资源对象
     */
    private String resource;

    /**
     * 预检结果
     */
    private String checkerResult;

    /**
     * 公共数据
     */
    private String publicData;
}
