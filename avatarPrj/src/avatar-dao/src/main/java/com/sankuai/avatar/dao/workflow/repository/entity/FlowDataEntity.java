package com.sankuai.avatar.dao.workflow.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author zhaozhifan02
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class FlowDataEntity {
    /**
     * 主键 id
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
    private Map<String, Object> publicData;
}
