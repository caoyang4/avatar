package com.sankuai.avatar.workflow.core.notify;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * Builder生成的通知结果
 * @author Jie.li.sh
 * @create 2022-12-02
 **/
@Builder
@Data
public class NotifyResult {
    /**
     * 接收人 mis / groupId
     */
    Set<String> receiverList;

    /**
     * 接收类型 user / group
     */
    NotifyReceiverType receiverType;

    /**
     * 接收人角色 owner / rd / sre / mark关注 等
     */
    NotifyReceiverRole notifyReceiverRole;

    /**
     * 通知标题
     */
    String title;

    /**
     * 通知内容
     */
    String content;

    /**
     * 通知内的可操作项
     */
    List<NotifyAction> notifyActionList;

    /**
     * 获取推送消息类型：常规提醒、审核提醒
     */
    NotifyMessageType notifyMessageType;

    /**
     * 获取完整周知消息
     */
    String msg;
}
