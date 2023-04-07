package com.sankuai.avatar.workflow.worker.LoaderFlow;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 从db队列中加载流程id
 *
 * @author xk
 */
@Slf4j
@Component
public class LoaderFlowDb extends  AbstractLoaderFlow {

    @Override
    public boolean loadFlowId(int flowId) {
        try {
            return this.submitProcessScheduler(flowId);
        } catch (Exception e) {
            this.clearId(flowId);
            log.error("LoaderFlowDb error", e);
        }
        return false;
    }

    /**
     * 每0.2秒尝试从db队列读取流程id, 提交到加载器
     */
    @Scheduled(fixedRate = 200)
    private void readQueueDb() {
        /*
        TODO
             实现从db队列加载逻辑
             this.loadFlowId(id)
         */
        log.warn("Load flow from db");
    }

    /**
     * 清除不存在id
     *
     * @param flowId 流id
     */
    private void clearId(int flowId) {
        // TODO 删除db队列中的错误id
    }
}
