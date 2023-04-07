package com.sankuai.avatar.workflow.core.notify;

import com.sankuai.avatar.workflow.core.context.FlowContext;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 通知请求数据
 * @author Jie.li.sh
 * @create 2022-11-29
 **/
@Data
@Builder
public class NotifyRequest {
    /**
     * 流程上下文
     */
    FlowContext flowContext;

    /**
     * 流程失败数据
     */
    List<String> errorMsgList;


    public static NotifyRequest of(FlowContext flowContext) {
        return NotifyRequest.builder().flowContext(flowContext).build();
    }

    public static NotifyRequest of(FlowContext flowContext, List<String> errorMsgList) {
        return NotifyRequest.builder()
                .flowContext(flowContext)
                .errorMsgList(errorMsgList)
                .build();
    }
}
