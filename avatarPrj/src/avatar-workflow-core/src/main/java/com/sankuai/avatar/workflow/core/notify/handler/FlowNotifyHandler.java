package com.sankuai.avatar.workflow.core.notify.handler;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import com.google.common.collect.Sets;
import com.sankuai.avatar.workflow.core.notify.NotifyRequest;
import com.sankuai.avatar.workflow.core.notify.NotifyResult;
import com.sankuai.avatar.workflow.core.notify.builder.CompleteNotifyBuilder;
import com.sankuai.avatar.workflow.core.notify.builder.FailedNotifyBuilder;
import com.sankuai.avatar.workflow.core.notify.builder.StartNotifyBuilder;
import com.sankuai.avatar.workflow.core.notify.sender.NotifySender;
import de.danielbechler.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 * 流程周知消息处理器
 *
 * @author zhaozhifan02
 */
@Slf4j
@Component
public class FlowNotifyHandler implements NotifyHandler {
    @Autowired
    NotifySender dxAppNotifySender;

    @Autowired
    StartNotifyBuilder startNotifyBuilder;

    @Autowired
    CompleteNotifyBuilder completeNotifyBuilder;

    @Autowired
    FailedNotifyBuilder failedNotifyBuilder;

    @Override
    public void handle(FlowTemplateType templateType, NotifyRequest notifyRequest) {
        // build msg
        List<NotifyResult> notifyResultList;
        switch (templateType) {
            case START:
                notifyResultList = startNotifyBuilder.build(notifyRequest);
                break;
            case COMPLETE:
                notifyResultList = completeNotifyBuilder.build(notifyRequest);
                break;
            case FAILED:
                notifyResultList = failedNotifyBuilder.build(notifyRequest);
                break;
            default:
                return;
        }
        if (Collections.isEmpty(notifyResultList)) {
            return;
        }
        // 优化发送
        formatNotifyResult(notifyResultList);
        // do send
        notifyResultList.forEach(this::doSendNotify);
    }


    /**
     * 执行dx发送
     * @param notifyResult  {@link NotifyResult}
     */
    private  void doSendNotify(NotifyResult notifyResult) {
        Transaction transaction = Cat.newTransaction("FlowNotify", notifyResult.getNotifyMessageType() == null ? "Default" : notifyResult.getNotifyMessageType().name());
        try {
            dxAppNotifySender.send(notifyResult);
            log.debug("流程通知完成: {}", notifyResult);
            transaction.setSuccessStatus();
        } catch (Exception e) {
            Cat.logError(e);
            log.error("流程通知失败: {}, {}", notifyResult, e);
            transaction.setStatus(e);
        } finally {
            transaction.complete();
        }
    }

    /**
     * 按role优先级排序发送，并去除重复的用户
     */
    private void formatNotifyResult(List<NotifyResult> notifyResultList) {
        notifyResultList.sort(Comparator.comparingInt(o -> o.getNotifyReceiverRole().getPriority()));
        Set<String> sendUsers = null;
        for (NotifyResult notifyResult : notifyResultList) {
            if(Collections.isEmpty(notifyResult.getReceiverList())){
                return;
            }
            if (sendUsers == null) {
                sendUsers = Sets.newHashSet(notifyResult.getReceiverList());
                continue;
            }
            // 去除已发送的用户
            notifyResult.getReceiverList().removeAll(sendUsers);
            // 记录已发送用户
            sendUsers.addAll(notifyResult.getReceiverList());
        }
    }
}
