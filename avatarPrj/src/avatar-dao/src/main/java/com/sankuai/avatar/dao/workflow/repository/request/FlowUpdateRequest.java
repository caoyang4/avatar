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
public class FlowUpdateRequest {

    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 状态
     */
    private String status;

    /**
     * 公共数据
     */
    private Object publicData;

    /**
     * atom 索引
     */
    private Integer index;

    /**
     * process 索引
     */
    private Integer processIndex;
}
