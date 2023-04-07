package com.sankuai.avatar.workflow.core.notify.builder;

import com.dianping.cat.Cat;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;
import com.sankuai.avatar.resource.appkey.AppkeyResource;
import com.sankuai.avatar.resource.appkey.bo.AppkeyBO;
import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.notify.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 流程执行成功，变更完成通知
 *
 * @author zhaozhifan02
 */
@Component
public class CompleteNotifyBuilder extends AbstractNotifyBuilder {
    /**
     * 流程发起周知消息标题
     */
    private static final String FLOW_TITLE = "✅您%s有一个变更已完成";

    @Autowired
    AppkeyResource appkeyResource;

    /**
     * 生成通知内容及角色等
     *
     * @param notifyRequest 请求
     * @return 通知内容
     */
    @Override
    public List<NotifyResult> build(NotifyRequest notifyRequest) {
        if (notifyRequest.getFlowContext() == null) {
            return null;
        }
        List<NotifyResult> notifyResultList = new ArrayList<>();
        // 发起人
        notifyResultList.add(buildCreateUserResult(notifyRequest));
        // 服务负责人相关
        List<NotifyResult> appkeyNotifyResultList = buildAppkeyResult(notifyRequest);
        if (!CollectionUtils.isEmpty(appkeyNotifyResultList)) {
            notifyResultList.addAll(appkeyNotifyResultList);
        }
        return notifyResultList;
    }

    /**
     * 给创建人的通知
     * @param notifyRequest 请求
     * @return 通知内容
     */
    private NotifyResult buildCreateUserResult(NotifyRequest notifyRequest) {
        FlowContext flowContext = notifyRequest.getFlowContext();
        NotifyResult notifyResult = NotifyResult.builder()
                .receiverList(Collections.singleton(flowContext.getCreateUser()))
                .receiverType(NotifyReceiverType.USER)
                .notifyReceiverRole(NotifyReceiverRole.CREATE_USER)
                .title(String.format(FLOW_TITLE, ""))
                .notifyMessageType(NotifyMessageType.NORMAL)
                .build();
        return defaultBuild(notifyResult, notifyRequest);
    }

    /**
     * 批量构建appkey干系人通知
     *
     * @param notifyRequest 通知请求
     * @return {@link List}<{@link NotifyResult}>
     */
    private List<NotifyResult> buildAppkeyResult(NotifyRequest notifyRequest) {
        if (notifyRequest.getFlowContext().getResource() == null || notifyRequest.getFlowContext().getResource().getAppkey() == null) {
            return null;
        }
        // 获取服务相关信息
        AppkeyBO appkeyBO;
        try {
            appkeyBO  = appkeyResource.getByAppkey(notifyRequest.getFlowContext().getResource().getAppkey());
        }catch (SdkCallException | SdkBusinessErrorException e) {
            Cat.logError(e);
            return null;
        }
        if (appkeyBO == null) {
            return null;
        }
        return Arrays.asList(
                buildAppkeyRdResult(notifyRequest, appkeyBO),
                buildAppkeyOpResult(notifyRequest, appkeyBO),
                buildAppkeyEpResult(notifyRequest, appkeyBO)
        );
    }

    /**
     * 给服务负责人的通知
     * @param notifyRequest 请求
     * @return 通知内容
     */
    private NotifyResult buildAppkeyRdResult(NotifyRequest notifyRequest, AppkeyBO appkeyBO) {
        NotifyResult notifyResult = NotifyResult.builder()
                .receiverList(new HashSet<>(Arrays.asList(appkeyBO.getRdAdmin().split(","))))
                .receiverType(NotifyReceiverType.USER)
                .notifyReceiverRole(NotifyReceiverRole.RD_ADMIN)
                .title(String.format(FLOW_TITLE, "负责的服务"))
                .notifyMessageType(NotifyMessageType.NORMAL)
                .build();
        return defaultBuild(notifyResult, notifyRequest);
    }

    /**
     * 给服务运维的通知
     * @param notifyRequest 请求
     * @return 通知内容
     */
    private NotifyResult buildAppkeyOpResult(NotifyRequest notifyRequest, AppkeyBO appkeyBO) {
        NotifyResult notifyResult = NotifyResult.builder()
                .receiverList(new HashSet<>(Arrays.asList(appkeyBO.getOpAdmin().split(","))))
                .receiverType(NotifyReceiverType.USER)
                .notifyReceiverRole(NotifyReceiverRole.OP_ADMIN)
                .title(String.format(FLOW_TITLE, "维护的服务"))
                .notifyMessageType(NotifyMessageType.NORMAL)
                .build();
        return defaultBuild(notifyResult, notifyRequest);
    }


    /**
     * 给服务测试的通知
     * @param notifyRequest 请求
     * @return 通知内容
     */
    private NotifyResult buildAppkeyEpResult(NotifyRequest notifyRequest, AppkeyBO appkeyBO) {
        NotifyResult notifyResult = NotifyResult.builder()
                .receiverList(new HashSet<>(Arrays.asList(appkeyBO.getEpAdmin().split(","))))
                .receiverType(NotifyReceiverType.USER)
                .notifyReceiverRole(NotifyReceiverRole.EP_ADMIN)
                .title(String.format(FLOW_TITLE, "测试的服务"))
                .notifyMessageType(NotifyMessageType.NORMAL)
                .build();
        return defaultBuild(notifyResult, notifyRequest);
    }

}
