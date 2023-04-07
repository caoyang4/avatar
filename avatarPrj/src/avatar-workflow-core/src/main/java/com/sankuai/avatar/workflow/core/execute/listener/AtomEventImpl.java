package com.sankuai.avatar.workflow.core.execute.listener;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import com.sankuai.avatar.workflow.core.execute.atom.AtomContext;
import com.sankuai.avatar.workflow.core.execute.listener.atomListen.AtomListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Consumer;

/**
 * atom事件推送器，atom状态发生变化时触发触发推送器
 *
 * @author xk
 */
@Slf4j
@Component
public class AtomEventImpl implements AtomEvent{

    @Autowired
    private List<AtomListener> listeners;

    @Override
    public void pushEvent(AtomContext atomContext) {
        listeners.forEach(listener -> this.doPushEvent(listener, listener::atomAll, atomContext));
        switch (atomContext.getAtomStatus()) {
            case NEW:
                listeners.forEach(listener -> this.doPushEvent(listener, listener::atomNew, atomContext));
                break;
            case SCHEDULER:
                listeners.forEach(listener -> this.doPushEvent(listener, listener::atomAtomScheduler, atomContext));
                break;
            case RUNNING:
                listeners.forEach(listener -> this.doPushEvent(listener, listener::atomRunning, atomContext));
                break;
            case SUCCESS:
                listeners.forEach(listener -> this.doPushEvent(listener, listener::atomSuccess, atomContext));
                break;
            case PENDING:
                listeners.forEach(listener -> this.doPushEvent(listener, listener::atomPending, atomContext));
                break;
            case FAIL:
                listeners.forEach(listener -> this.doPushEvent(listener, listener::atomFail, atomContext));
                break;
            default:
                break;
        }
    }

    /**
     * 执行推送事件, 加入监控打点
     *
     * @param consumer    触发函数
     * @param listener    监听器
     * @param atomContext atom上下文
     */
    private void doPushEvent(AtomListener listener, Consumer<AtomContext> consumer, AtomContext atomContext) {
        Transaction transaction = Cat.newTransaction("AtomListener", listener.getClass().getSimpleName());
        try {
            consumer.accept(atomContext);
            transaction.setSuccessStatus();
        } catch (Exception e) {
            log.error("监听器执行失败 ", e);
            transaction.setStatus(e);
            throw e;
        } finally {
            transaction.complete();
        }
    }
}
