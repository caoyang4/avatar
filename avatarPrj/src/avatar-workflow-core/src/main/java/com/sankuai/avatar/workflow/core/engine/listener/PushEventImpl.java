package com.sankuai.avatar.workflow.core.engine.listener;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.context.FlowState;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 推送flow状态流转事件
 *
 * @author xk
 */
@Log4j
@Component
public class PushEventImpl implements PushEvent{

    @Autowired
    private ThreadPoolExecutor taskThreadPool;

    @Autowired
    private List<FlowListen> flowListens;

    @Override
    public void pushEvent(FlowContext flowContext, FlowState flowState) {
        for (FlowListen flowListen : flowListens) {
            // 只推送订阅事件
            ListenFlowState listenFlowState = flowListen.getClass().getAnnotation(ListenFlowState.class);
            if (listenFlowState != null && Arrays.stream(listenFlowState.value()).noneMatch(flowState::equals)) {
                continue;
            }

            // 把监听任务封装为Runnable对象
            Runnable runnable = ()-> flowListen.receiveEvents(flowContext, flowState);

            if (flowListen.getClass().getAnnotation(SyncListen.class) != null) {
                // 同步执行
                this.doPushEvent(runnable, flowListen.getClass().getSimpleName());
            } else {
                // 默认异步执行
                this.taskThreadPool.execute(() -> this.doPushEvent(runnable, flowListen.getClass().getSimpleName()));
            }
        }
    }

    private void doPushEvent(Runnable runnable, String transactionName) {
        Transaction transaction = Cat.newTransaction("scheduler.listen", transactionName);
        try {
            runnable.run();
            transaction.setSuccessStatus();
        } catch (Exception e) {
            transaction.setStatus(e);
            log.error("PushEvent.FlowEvents error", e);
        } finally {
            transaction.complete();
        }
    }
}
