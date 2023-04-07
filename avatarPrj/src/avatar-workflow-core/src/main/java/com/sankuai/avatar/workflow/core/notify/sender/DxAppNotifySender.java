package com.sankuai.avatar.workflow.core.notify.sender;

import com.sankuai.avatar.client.dx.DxHttpClient;
import com.sankuai.avatar.workflow.core.notify.NotifyMessageType;
import com.sankuai.avatar.workflow.core.notify.NotifyReceiverType;
import com.sankuai.avatar.workflow.core.notify.NotifyResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 大象应用号消息推送
 *
 * @author zhaozhifan02
 */
@Slf4j
@Component
public class DxAppNotifySender implements NotifySender {

    @Autowired
    private DxHttpClient dxHttpClient;

    @Override
    public void send(NotifyResult notifyResult) {
        if (notifyResult == null || CollectionUtils.isEmpty(notifyResult.getReceiverList())) {
            return;
        }
        // 用户周知
        if (NotifyReceiverType.USER.equals(notifyResult.getReceiverType())) {
            // 普通消息
            if (NotifyMessageType.NORMAL.equals(notifyResult.getNotifyMessageType())) {
                dxHttpClient.pushDxMessage(notifyResult.getReceiverList(), notifyResult.getMsg());
                return;
            }
            // 审核消息
            if (NotifyMessageType.AUDIT.equals(notifyResult.getNotifyMessageType())) {
                dxHttpClient.pushDxAuditMessage(notifyResult.getReceiverList(), notifyResult.getMsg());
                return;
            }
        }
        // 群组周知
        if (NotifyReceiverType.GROUP.equals(notifyResult.getReceiverType())) {
            notifyResult.getReceiverList().forEach(
                    receiver -> dxHttpClient.pushGroupMessage(Long.parseLong(receiver), notifyResult.getMsg())
            );
        }
    }
}
