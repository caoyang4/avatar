package com.sankuai.avatar.workflow.core.mcm.impl;

import com.dianping.lion.Environment;
import com.google.common.base.Preconditions;
import com.sankuai.avatar.workflow.core.mcm.McmClient;
import com.sankuai.avatar.workflow.core.mcm.McmEventContext;
import com.sankuai.avatar.workflow.core.mcm.McmEventResource;
import com.sankuai.avatar.workflow.core.mcm.exception.McmErrorException;
import com.sankuai.avatar.workflow.core.mcm.request.McmEventRequest;
import com.sankuai.avatar.workflow.core.mcm.response.McmEventChangeDetailResponse;
import com.sankuai.avatar.workflow.core.mcm.response.McmPreAuditResponse;
import com.sankuai.avatar.workflow.core.mcm.response.McmPreCheckResponse;
import com.sankuai.avatar.workflow.core.mcm.transfer.McmTransfer;
import com.sankuai.mcm.client.sdk.context.handler.client.EventChangeClient;
import com.sankuai.mcm.client.sdk.context.handler.client.PreAuditClient;
import com.sankuai.mcm.client.sdk.context.handler.client.PreCheckClient;
import com.sankuai.mcm.client.sdk.dto.common.*;
import com.sankuai.mcm.client.sdk.dto.request.*;
import com.sankuai.mcm.client.sdk.dto.response.EventChangeResponseDTO;
import com.sankuai.mcm.client.sdk.dto.response.PreAuditResponseDTO;
import com.sankuai.mcm.client.sdk.dto.response.PreCheckResponseDTO;
import com.sankuai.mcm.client.sdk.enums.NotificationTargetType;
import com.sankuai.mcm.client.sdk.enums.OnEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * MCM调度接口实现
 *
 * @author zhaozhifan02
 */
@Slf4j
@Service
public class McmClientImpl implements McmClient {

    /**
     * 请求参数提示信息
     */
    private static final String REQUEST_PARAM_NULL_ERROR_MESSAGE = "请求参数不能为空";

    /**
     * MCM管理端注册的系统名
     */
    private static final String SYSTEM_NAME = "avatar";

    /**
     * 时间格式化
     */
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Autowired
    private PreCheckClient preCheckClient;

    @Autowired
    private PreAuditClient preAuditClient;

    @Autowired
    private EventChangeClient eventChangeClient;

    @Override
    public McmPreCheckResponse preCheck(McmEventRequest mcmEventRequest) throws McmErrorException {
        Preconditions.checkNotNull(mcmEventRequest, REQUEST_PARAM_NULL_ERROR_MESSAGE);
        PreCheckRequestDTO preCheckRequestDTO = new PreCheckRequestDTO();
        EventContext eventContext = getEventContext(mcmEventRequest.getEvenName(),
                mcmEventRequest.getMcmEventContext());
        if (eventContext == null) {
            return null;
        }
        preCheckRequestDTO.setEventContext(eventContext);
        PreCheckResponseDTO preCheckResponseDTO;
        try {
            preCheckResponseDTO = preCheckClient.preCheck(preCheckRequestDTO);
        } catch (Exception e) {
            String errorMsg = "Do Mcm preCheck catch error:" + e.getMessage();
            log.error(errorMsg);
            throw new McmErrorException(errorMsg);
        }
        return McmTransfer.INSTANCE.toPreCheckResponse(preCheckResponseDTO);
    }

    @Override
    public McmEventChangeDetailResponse getEventChangeDetail(String eventUuid) throws McmErrorException {
        Preconditions.checkNotNull(eventUuid, "EventUuid must not be null");
        EventChangeResponseDTO eventChangeResponseDTO;
        try {
            eventChangeResponseDTO = eventChangeClient.getChangeDetail(SYSTEM_NAME, eventUuid);
        } catch (Exception e) {
            String errorMsg = "Do get Mcm change detail catch error:" + e.getMessage();
            log.error(errorMsg);
            throw new McmErrorException(errorMsg);
        }
        if (eventChangeResponseDTO == null || eventChangeResponseDTO.getChangeDetail() == null) {
            return null;
        }
        return McmTransfer.INSTANCE.toEventChangeDetailResponse(eventChangeResponseDTO.getChangeDetail());
    }


    @Override
    public void preCheckConfirm(McmEventRequest mcmEventRequest) throws McmErrorException {
        Preconditions.checkNotNull(mcmEventRequest, REQUEST_PARAM_NULL_ERROR_MESSAGE);
        PreCheckConfirmRequestDTO preCheckConfirmRequestDTO = new PreCheckConfirmRequestDTO();
        EventContext eventContext = getEventContext(mcmEventRequest.getEvenName(),
                mcmEventRequest.getMcmEventContext());
        preCheckConfirmRequestDTO.setEventContext(eventContext);
        try {
            preCheckClient.confirm(preCheckConfirmRequestDTO);
        } catch (Exception e) {
            String errorMsg = "Do Mcm preCheckConfirm catch error:" + e.getMessage();
            log.error(errorMsg);
            throw new McmErrorException(errorMsg);
        }
    }

    @Override
    public void preCheckCancel(McmEventRequest mcmEventRequest) throws McmErrorException {
        Preconditions.checkNotNull(mcmEventRequest, REQUEST_PARAM_NULL_ERROR_MESSAGE);
        PreCheckCancelRequestDTO preCheckCancelRequestDTO = new PreCheckCancelRequestDTO();
        preCheckCancelRequestDTO.setEventContext(getEventContext(mcmEventRequest.getEvenName(),
                mcmEventRequest.getMcmEventContext()));
        try {
            preCheckClient.cancel(preCheckCancelRequestDTO);
        } catch (Exception e) {
            String errorMsg = "Do Mcm preCheckCancel catch error:" + e.getMessage();
            log.error(errorMsg);
            throw new McmErrorException(errorMsg);
        }
    }

    @Override
    public McmPreAuditResponse audit(McmEventRequest mcmEventRequest) throws McmErrorException {
        Preconditions.checkNotNull(mcmEventRequest, REQUEST_PARAM_NULL_ERROR_MESSAGE);
        PreAuditRequestDTO requestDTO = new PreAuditRequestDTO();
        requestDTO.setEventContext(getEventContext(mcmEventRequest.getEvenName(),
                mcmEventRequest.getMcmEventContext()));
        requestDTO.setCustomConfig(buildAuditCustomConfig());
        PreAuditResponseDTO responseDTO;
        try {
            responseDTO = preAuditClient.preAudit(requestDTO);
        } catch (Exception e) {
            String errorMsg = "Do Mcm audit catch error:" + e.getMessage();
            log.error(errorMsg);
            throw new McmErrorException(errorMsg);
        }
        if (responseDTO == null) {
            return null;
        }
        return McmTransfer.INSTANCE.toPreAuditResponse(responseDTO);
    }

    @Override
    public void auditCancel(McmEventRequest mcmEventRequest) throws McmErrorException {
        Preconditions.checkNotNull(mcmEventRequest, REQUEST_PARAM_NULL_ERROR_MESSAGE);
        PreAuditCancelRequestDTO requestDTO = new PreAuditCancelRequestDTO();
        requestDTO.setEventContext(getEventContext(mcmEventRequest.getEvenName(),
                mcmEventRequest.getMcmEventContext()));
        try {
            preAuditClient.cancel(requestDTO);
        } catch (Exception e) {
            String errorMsg = "Do Mcm auditCancel catch error:" + e.getMessage();
            log.error(errorMsg);
            throw new McmErrorException(errorMsg);
        }
    }

    @Override
    public void auditReject(McmEventRequest mcmEventRequest) throws McmErrorException {
        Preconditions.checkNotNull(mcmEventRequest, REQUEST_PARAM_NULL_ERROR_MESSAGE);
        PreAuditRejectRequestDTO requestDTO = new PreAuditRejectRequestDTO();
        requestDTO.setEventContext(getEventContext(mcmEventRequest.getEvenName(),
                mcmEventRequest.getMcmEventContext()));
        requestDTO.setOperatorRequest(buildOperatorRequest(mcmEventRequest));
        try {
            preAuditClient.reject(requestDTO);
        } catch (Exception e) {
            String errorMsg = "Do Mcm auditReject catch error:" + e.getMessage();
            log.error(errorMsg);
            throw new McmErrorException(errorMsg);
        }
    }

    @Override
    public void auditAccept(McmEventRequest mcmEventRequest) throws McmErrorException {
        Preconditions.checkNotNull(mcmEventRequest, REQUEST_PARAM_NULL_ERROR_MESSAGE);
        PreAuditAcceptRequestDTO requestDTO = new PreAuditAcceptRequestDTO();
        requestDTO.setEventContext(getEventContext(mcmEventRequest.getEvenName(),
                mcmEventRequest.getMcmEventContext()));
        requestDTO.setOperatorRequest(buildOperatorRequest(mcmEventRequest));
        try {
            preAuditClient.accept(requestDTO);
        } catch (Exception e) {
            String errorMsg = "Do Mcm auditAccept catch error:" + e.getMessage();
            log.error(errorMsg);
            throw new McmErrorException(errorMsg);
        }
    }

    /**
     * 获取事件上下文
     *
     * @param eventName       事件名称
     * @param mcmEventContext Mcm事件请求上下文
     * @return 事件上下文
     */
    private EventContext getEventContext(String eventName, McmEventContext mcmEventContext) {
        if (eventName == null || mcmEventContext == null) {
            return null;
        }
        // 组装事件参数
        Resource resource = new Resource();
        McmEventResource eventResource = mcmEventContext.getResource();
        if (eventResource != null) {
            App app = new App();
            app.setAppkey(eventResource.getAppkey());
            resource.setApp(app);
        }
        // 构造默认上下文
        EventContext eventContext = buildDefaultEventContext(eventName, mcmEventContext);
        eventContext.setResources(resource);
        return eventContext;
    }

    /**
     * 构建默认事件上下文
     *
     * @param eventName       事件名
     * @param mcmEventContext {@link McmEventContext}
     * @return EventContext {@link EventContext}
     */
    private EventContext buildDefaultEventContext(String eventName, McmEventContext mcmEventContext) {
        String appKey = Environment.getAppName();
        EventContext eventContext = new EventContext();
        eventContext.setEventName(eventName);
        eventContext.setEnv(mcmEventContext.getEnv() == null ? Environment.getEnvironment() : mcmEventContext.getEnv());
        eventContext.setEventUuid(mcmEventContext.getUuid());
        eventContext.setAccountName(SYSTEM_NAME);
        eventContext.setEventSourceAppkey(appKey);

        eventContext.setUserIdentity(UserIdentity.builder().name(mcmEventContext.getOperator()).build());
        eventContext.setRequestParameters(mcmEventContext.getParams());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        String startTime = mcmEventContext.getStartTime() == null ? simpleDateFormat.format(new Date()) :
                simpleDateFormat.format(mcmEventContext.getStartTime());
        String endTime = mcmEventContext.getEndTime() == null ? null :
                simpleDateFormat.format(mcmEventContext.getStartTime());
        eventContext.setEventStartTime(startTime);
        eventContext.setEventEndTime(endTime);
        return eventContext;
    }


    /**
     * 构造审核操作请求
     *
     * @param mcmEventRequest {@link McmEventRequest}
     * @return {@link OperatorRequest}
     */
    private OperatorRequest buildOperatorRequest(McmEventRequest mcmEventRequest) {
        OperatorRequest operatorRequest = new OperatorRequest();
        operatorRequest.setOperator(mcmEventRequest.getMcmEventContext().getOperator());
        return operatorRequest;
    }

    /**
     * 构建自定义配置，审核默认不周知
     *
     * @return CustomConfig
     */
    private CustomConfig buildAuditCustomConfig() {
        NoticeCloseConfigItem noticeCloseConfigItem = NoticeCloseConfigItem.builder()
                .target(Arrays.asList(NotificationTargetType.LAUNCHER, NotificationTargetType.AUDITOR))
                .on(getIgnoreNoticeEvent())
                .build();
        NoticeConfig noticeConfig = NoticeConfig.builder().closeConfig(
                NoticeCloseConfig.builder().configItems(Collections.singletonList(noticeCloseConfigItem)).build())
                .build();
        CustomConfig customConfig = new CustomConfig();
        customConfig.setNoticeConfig(noticeConfig);
        return customConfig;
    }

    /**
     * 获取忽略 MCM 周知的事件
     *
     * @return List<OnEvent>
     */
    private List<OnEvent> getIgnoreNoticeEvent() {
        return Arrays.asList(OnEvent.AFTER_AUDIT_ACCEPT,
                OnEvent.AFTER_AUDIT_FINALLY_ACCEPT,
                OnEvent.AFTER_AUDIT_REJECT,
                OnEvent.AFTER_AUDIT_CANCEL,
                OnEvent.AFTER_AUDIT_LAUNCH,
                OnEvent.AFTER_AUDIT_IGNORE);
    }
}
