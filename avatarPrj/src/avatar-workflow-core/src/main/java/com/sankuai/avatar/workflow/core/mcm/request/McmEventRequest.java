package com.sankuai.avatar.workflow.core.mcm.request;

import com.sankuai.avatar.workflow.core.mcm.McmEventContext;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * MCM事件请求对象
 *
 * @author zhaozhifan02
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class McmEventRequest {
    /**
     * 事件名称
     */
    String evenName;

    /**
     * 事件上下文
     */
    McmEventContext mcmEventContext;
}
