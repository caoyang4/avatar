package com.sankuai.avatar.workflow.core.mcm.response;

import com.sankuai.avatar.workflow.core.mcm.McmEventStatus;
import lombok.Data;

import java.util.Date;

/**
 * MCM 事件变更详情
 *
 * @author zhaozhifan02
 */
@Data
public class McmEventChangeDetailResponse {
    /**
     * 事件ID
     */
    private Integer id;

    /**
     * 事件状态
     */
    private McmEventStatus status;

    /**
     * 链接
     */
    private String url;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    private Date createTime;
}
