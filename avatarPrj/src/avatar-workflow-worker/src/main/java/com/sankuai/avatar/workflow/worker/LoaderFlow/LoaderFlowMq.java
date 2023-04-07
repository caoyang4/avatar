package com.sankuai.avatar.workflow.worker.LoaderFlow;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 从mq消息队列中加载流程id
 *
 * @author xk
 */
@Slf4j
@Component
public class LoaderFlowMq extends AbstractLoaderFlow {

    @Override
    public boolean loadFlowId(int flowId) {
        try {
            return this.submitProcessScheduler(flowId);
        } catch (Exception e) {
            log.error("LoaderFlowMQ error", e);
        }
        // 从mq加载总是返回true, 避免不确认死循环
        return true;
    }
}
