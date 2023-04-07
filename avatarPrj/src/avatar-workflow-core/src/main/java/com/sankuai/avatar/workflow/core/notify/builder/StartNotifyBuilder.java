package com.sankuai.avatar.workflow.core.notify.builder;

import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.notify.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * 预检通过后, 发起人发送通知
 * @author Jie.li.sh
 * @create 2022-12-02
 **/
@Component
public class StartNotifyBuilder extends AbstractNotifyBuilder {
    @Autowired
    TemplateParser templateParser;

    /**
     * 流程发起周知消息标题
     */
    private static final String FLOW_START_TITLE = "🕐您有一个变更已发起";

    /**
     * 生成通知内容及角色等
     *
     * @param request 请求
     * @return 通知内容
     */
    @Override
    public List<NotifyResult> build(NotifyRequest request) {
        return Collections.singletonList(buildCreateUserResult(request));
    }

    /**
     * 创建人的通知
     * @param notifyRequest 请求
     * @return 通知内容
     */
    private NotifyResult buildCreateUserResult(NotifyRequest notifyRequest) {
        FlowContext flowContext = notifyRequest.getFlowContext();
        NotifyResult notifyResult = NotifyResult.builder()
                .receiverList(Collections.singleton(flowContext.getCreateUser()))
                .receiverType(NotifyReceiverType.USER)
                .notifyReceiverRole(NotifyReceiverRole.CREATE_USER)
                .title(FLOW_START_TITLE)
                .content(null)
                .notifyActionList(null)
                .notifyMessageType(NotifyMessageType.NORMAL)
                .build();
        return defaultBuild(notifyResult, notifyRequest);
    }
}
