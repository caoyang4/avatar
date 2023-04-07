package com.sankuai.avatar.workflow.core.mcm;

import lombok.Data;

import java.util.Date;
import java.util.Map;

/**
 * MCM事件上下文
 *
 * @author zhaozhifan02
 */
@Data
public class McmEventContext {

    /**
     * 事件uuid
     */
    private String uuid;

    /**
     * 事件名称
     */
    private String eventName;

    /**
     * 环境
     */
    private String env;

    /**
     * 操作人
     */
    private String operator;

    /**
     * 入参
     */
    private Map<String, Object> params;

    /**
     * 资源
     */
    private McmEventResource resource;

    /**
     * 创建时间
     */
    Date startTime;

    /**
     * 结束时间
     */
    Date endTime;

}
