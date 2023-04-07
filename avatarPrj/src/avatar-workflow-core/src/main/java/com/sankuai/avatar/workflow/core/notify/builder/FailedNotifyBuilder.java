package com.sankuai.avatar.workflow.core.notify.builder;

import com.meituan.mdp.boot.starter.config.annotation.MdpConfig;
import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.notify.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 流程执行失败，发起人/失败群
 *
 * @author zhaozhifan02
 */
@Component
public class FailedNotifyBuilder extends AbstractNotifyBuilder {
    /**
     * 流程失败标题
     */
    private static final String FLOW_FAIL_TITLE = "❌您有一个变更执行失败，请关注";

    /**
     * 流程失败通知群id
     */
    @MdpConfig("FAILED_GROUP_ID:66765549986")
    private String failedGroupId;

    /**
     * 生成通知内容及角色等
     *
     * @param request 请求
     * @return 通知内容
     */
    @Override
    public List<NotifyResult> build(NotifyRequest request) {
        return Arrays.asList(buildCreateUserResult(request), buildFailedGroupResult(request));
    }

    /**
     * 创建人的通知
     * @param notifyRequest 请求
     * @return 通知内容
     */
    private NotifyResult buildCreateUserResult(NotifyRequest notifyRequest) {
        FlowContext flowContext = notifyRequest.getFlowContext();
        NotifyResult notifyResult =  NotifyResult.builder()
                .receiverList(Collections.singleton(flowContext.getCreateUser()))
                .receiverType(NotifyReceiverType.USER)
                .notifyReceiverRole(NotifyReceiverRole.CREATE_USER)
                .title(FLOW_FAIL_TITLE)
                .notifyMessageType(NotifyMessageType.NORMAL)
                .build();
        return defaultBuild(notifyResult, notifyRequest);
    }

    /**
     * 失败群的通知
     * @param notifyRequest {@link NotifyRequest}
     * @return {@link NotifyResult}
     */
    private NotifyResult buildFailedGroupResult(NotifyRequest notifyRequest) {
        NotifyResult notifyResult = NotifyResult.builder()
                .receiverList(Collections.singleton(failedGroupId))
                .receiverType(NotifyReceiverType.GROUP)
                .title(FLOW_FAIL_TITLE)
                .notifyMessageType(NotifyMessageType.NORMAL)
                .build();
        return defaultBuild(notifyResult, notifyRequest);
    }
}
