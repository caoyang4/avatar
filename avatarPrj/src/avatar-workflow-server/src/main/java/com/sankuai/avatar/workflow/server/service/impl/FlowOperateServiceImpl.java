package com.sankuai.avatar.workflow.server.service.impl;

import com.dianping.cat.Cat;
import com.meituan.mdp.boot.starter.config.annotation.MdpConfig;
import com.sankuai.avatar.common.exception.SupportErrorException;
import com.sankuai.avatar.workflow.core.auditer.chain.AuditOperationType;
import com.sankuai.avatar.workflow.core.auditer.chain.AuditState;
import com.sankuai.avatar.workflow.core.auditer.chain.FlowAuditChainNode;
import com.sankuai.avatar.workflow.core.auditer.chain.FlowAuditorOperation;
import com.sankuai.avatar.workflow.core.context.FlowAudit;
import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.context.FlowState;
import com.sankuai.avatar.workflow.core.context.FlowUserSource;
import com.sankuai.avatar.workflow.core.context.loader.FlowContextLoader;
import com.sankuai.avatar.workflow.core.context.request.FlowCreateRequest;
import com.sankuai.avatar.workflow.core.engine.exception.FlowException;
import com.sankuai.avatar.workflow.core.engine.process.ProcessContext;
import com.sankuai.avatar.workflow.core.engine.process.ProcessContextLoader;
import com.sankuai.avatar.workflow.core.engine.process.ProcessTemplate;
import com.sankuai.avatar.workflow.core.engine.process.response.PreCheckResult;
import com.sankuai.avatar.workflow.core.engine.process.response.Response;
import com.sankuai.avatar.workflow.core.engine.scheduler.Loader;
import com.sankuai.avatar.workflow.core.engine.scheduler.Submit;
import com.sankuai.avatar.workflow.core.engine.scheduler.event.SchedulerEventContext;
import com.sankuai.avatar.workflow.core.engine.scheduler.event.SchedulerEventEnum;
import com.sankuai.avatar.workflow.server.dal.entity.CasType;
import com.sankuai.avatar.workflow.server.dal.entity.CasUser;
import com.sankuai.avatar.workflow.server.dto.flow.*;
import com.sankuai.avatar.workflow.server.service.FlowOperateService;
import com.sankuai.avatar.workflow.server.utils.UserUtils;
import cn.hutool.core.date.DateTime;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

/**
 * 流程操作类接口实现
 *
 * @author zhaozhifan02
 */
@Slf4j
@Service
public class FlowOperateServiceImpl implements FlowOperateService {
    /**
     * 超级管理员
     */
    @MdpConfig("AVATAR_ADMIN:[]")
    private String[] avatarSuperAdmin;

    private final Submit submit;

    private final Loader loader;

    private final FlowContextLoader flowContextLoader;

    private final ProcessContextLoader processContextLoader;

    @Autowired
    public FlowOperateServiceImpl(Submit submit,
                                  Loader loader,
                                  FlowContextLoader flowContextLoader,
                                  ProcessContextLoader processContextLoader) {
        this.submit = submit;
        this.loader = loader;
        this.flowContextLoader = flowContextLoader;
        this.processContextLoader = processContextLoader;
    }

    @Override
    public CreateResponseDTO createFlow(String templateName, String input) {
        // format process template
        ProcessTemplate processTemplate = loader.flowTemplateName(templateName, buildCreateRequest(input));
        // do process
        Future<Response> responseFuture = this.submit.submit(processTemplate);

        try {
            Response response = responseFuture.get(300, TimeUnit.SECONDS);
            if (response != null) {
                // 解析预检结果, 返回接口
                return buildCreateResponse(response, processTemplate);
            }
        } catch (Exception e) {
            Cat.logError(e);
            throw new FlowException("创建流程新增失败" + e);
        }
        return null;
    }

    /**
     * 预检风险确认
     *
     * @param id id
     * @return confirm response
     */
    @Override
    public ConfirmResponseDTO checkConfirm(Integer id) {
        SchedulerEventContext eventContext = SchedulerEventContext.of(SchedulerEventEnum.PRE_CHECK_CONFIRM);
        FlowContext flowContext = flowContextLoader.id(id);
        if (flowContext == null) {
            throw new SupportErrorException(String.format("流程(id: %s)不存在", id));
        }
        // 检查是否有权限操作
        checkOperatePermission(flowContext);
        // 检查操作事件和流程状态是否匹配
        checkFlowStateMatchEvent(flowContext, eventContext.getSchedulerEventEnum());

        Response response = doEvent(flowContext, eventContext);
        return ConfirmResponseDTO.builder().state(Objects.requireNonNull(response).getFlowState()).build();
    }

    /**
     * 预检取消
     *
     * @param id id
     * @return confirm response
     */
    @Override
    public CancelResponseDTO checkCancel(Integer id) {
        SchedulerEventContext eventContext = SchedulerEventContext.of(SchedulerEventEnum.PRE_CHECK_CANCEL);
        FlowContext flowContext = flowContextLoader.id(id);
        if (flowContext == null) {
            throw new SupportErrorException(String.format("流程(id: %s)不存在", id));
        }
        // 检查是否有权限操作
        checkOperatePermission(flowContext);
        // 检查操作事件和流程状态是否匹配
        checkFlowStateMatchEvent(flowContext, eventContext.getSchedulerEventEnum());

        Response response = doEvent(flowContext, eventContext);
        return CancelResponseDTO.builder().state(Objects.requireNonNull(response).getFlowState()).build();
    }

    /**
     * 审核通过
     *
     * @param id         流程ID
     * @param requestDTO {@link FlowAuditOperateRequestDTO}
     * @return {@link AcceptResponseDTO}
     */
    @Override
    public AcceptResponseDTO auditAccept(Integer id, FlowAuditOperateRequestDTO requestDTO) {
        SchedulerEventContext eventContext = SchedulerEventContext.of(SchedulerEventEnum.AUDIT_ACCEPTED);
        FlowContext flowContext = flowContextLoader.id(id);
        if (flowContext == null) {
            throw new SupportErrorException(String.format("流程(id: %s)不存在", id));
        }
        // 检查操作事件和流程状态是否匹配
        checkFlowStateMatchEvent(flowContext, eventContext.getSchedulerEventEnum());

        FlowAuditorOperation auditorOperation = buildFlowAuditorOperation(requestDTO, AuditOperationType.ACCEPT);
        // 是否允许发起审核事件
        canDoAuditEvent(flowContext, auditorOperation);

        eventContext.setEventInput(auditorOperation);
        Response response = doEvent(flowContext, eventContext);
        return AcceptResponseDTO.builder().state(Objects.requireNonNull(response).getFlowState()).build();
    }

    /**
     * 审核拒绝
     *
     * @param id         流程ID
     * @param requestDTO {@link FlowAuditOperateRequestDTO}
     * @return {@link RejectResponseDTO}
     */
    @Override
    public RejectResponseDTO auditReject(Integer id, FlowAuditOperateRequestDTO requestDTO) {
        SchedulerEventContext eventContext = SchedulerEventContext.of(SchedulerEventEnum.AUDIT_REJECTED);
        FlowContext flowContext = flowContextLoader.id(id);
        if (flowContext == null) {
            throw new SupportErrorException(String.format("流程(id: %s)不存在", id));
        }
        // 检查操作事件和流程状态是否匹配
        checkFlowStateMatchEvent(flowContext, eventContext.getSchedulerEventEnum());

        FlowAuditorOperation auditorOperation = buildFlowAuditorOperation(requestDTO, AuditOperationType.ACCEPT);
        // 是否允许发起审核事件
        canDoAuditEvent(flowContext, auditorOperation);

        eventContext.setEventInput(auditorOperation);
        Response response = doEvent(flowContext, eventContext);
        return RejectResponseDTO.builder().state(Objects.requireNonNull(response).getFlowState()).build();
    }

    /**
     * 审核撤销
     *
     * @param id         流程ID
     * @param requestDTO {@link FlowAuditOperateRequestDTO}
     * @return {@link CancelResponseDTO}
     */
    @Override
    public CancelResponseDTO auditCancel(Integer id, FlowAuditOperateRequestDTO requestDTO) {
        SchedulerEventContext eventContext = SchedulerEventContext.of(SchedulerEventEnum.AUDIT_CANCELED);
        FlowContext flowContext = flowContextLoader.id(id);
        if (flowContext == null) {
            throw new SupportErrorException(String.format("流程(id: %s)不存在", id));
        }
        // 检查是否有权限操作
        checkOperatePermission(flowContext);
        // 检查操作事件和流程状态是否匹配
        checkFlowStateMatchEvent(flowContext, eventContext.getSchedulerEventEnum());

        FlowAuditorOperation auditorOperation = buildFlowAuditorOperation(requestDTO, AuditOperationType.ACCEPT);

        // 审核中状态才可撤销
        List<FlowAuditChainNode> auditingNodes = getAuditingNode(flowContext);
        boolean isAuditingNode = auditingNodes.stream()
                .filter(i -> i.getId().equals(auditorOperation.getAuditNodeId())).count() == 1L;
        if (!isAuditingNode) {
            throw new SupportErrorException("当前流程不允许此操作");
        }
        eventContext.setEventInput(auditorOperation);
        Response response = doEvent(flowContext, eventContext);
        return CancelResponseDTO.builder().state(Objects.requireNonNull(response).getFlowState()).build();
    }

    /**
     * 流程事件触发
     *
     * @param flowContext           {@link FlowContext}
     * @param schedulerEventContext {@link SchedulerEventContext}
     * @return {@link Response}
     */
    private Response doEvent(FlowContext flowContext, SchedulerEventContext schedulerEventContext) {
        List<ProcessContext> processContextList = processContextLoader.buildByFlowContext(flowContext);
        ProcessTemplate processTemplate = loader.loadProcessTemplate(processContextList, flowContext);

        Future<Response> responseFuture = this.submit.submit(processTemplate, schedulerEventContext);
        if (responseFuture == null) {
            throw new FlowException("流程操作失败, 获取不到返回值");
        }
        try {
            return responseFuture.get(3, TimeUnit.SECONDS);
        } catch (TimeoutException | InterruptedException | ExecutionException e) {
            Cat.logError(e);
            throw new FlowException(String.format("流程操作 %s 失败", schedulerEventContext.getSchedulerEventEnum().getStatusName()) + e);
        }
    }


    /**
     * 生成流程请求参数
     */
    private FlowCreateRequest buildCreateRequest(String input) {
        CasUser casUser = UserUtils.getCurrentCasUser();
        FlowCreateRequest flowCreateRequest = FlowCreateRequest.builder()
                .input(input)
                .createUser(casUser.getLoginName())
                .createUserName(casUser.getName())
                .createUserSource(FlowUserSource.USER)
                .build();
        // 当前用户
        if (casUser.getCasType() != null && casUser.getCasType().equals(CasType.APPKEY)) {
            flowCreateRequest.setCreateUserSource(FlowUserSource.APPKEY);
        }
        return flowCreateRequest;
    }

    /**
     * 格式化创建流程的输出
     */
    private CreateResponseDTO buildCreateResponse(Response response, ProcessTemplate processTemplate) {
        if (response == null) {
            return null;
        }
        CreateResponseDTO createResponseDTO = CreateResponseDTO.builder()
                .preCheckResult(response.getResult(PreCheckResult.class))
                .state(response.getFlowState())
                .build();
        if (processTemplate != null && processTemplate.getProcesses() != null && (processTemplate.getProcesses().size() > 1)) {
            createResponseDTO.setId(processTemplate.getFlowContext().getId());
            createResponseDTO.setUuid(processTemplate.getFlowContext().getUuid());
        }
        return createResponseDTO;
    }

    /**
     * 构建审核操作对象
     *
     * @param requestDTO    {@link FlowAuditOperateRequestDTO}
     * @param operationType {@link AuditOperationType}
     * @return {@link FlowAuditorOperation}
     */
    private FlowAuditorOperation buildFlowAuditorOperation(FlowAuditOperateRequestDTO requestDTO,
                                                           AuditOperationType operationType) {
        CasUser casUser = UserUtils.getCurrentCasUser();
        return FlowAuditorOperation.builder()
                .auditNodeId(requestDTO.getAuditNodeId())
                .comment(requestDTO.getComment())
                .auditor(casUser.getLoginName())
                .operationType(operationType)
                .operateTime(DateTime.now())
                .build();
    }

    /**
     * 提交事件和流程状态是否匹配
     *
     * @param flowContext        {@link FlowContext}
     * @param schedulerEventEnum {@link SchedulerEventEnum}
     */
    private void checkFlowStateMatchEvent(FlowContext flowContext, SchedulerEventEnum schedulerEventEnum) {
        FlowState flowState = flowContext.getFlowState();
        if (schedulerEventEnum.getFlowState().equals(flowState)) {
            return;
        }
        throw new SupportErrorException("当前流程不允许此操作");
    }

    /**
     * 是否能执行审核动作
     *
     * @param flowContext      {@link FlowContext}
     * @param auditorOperation {@link FlowAuditorOperation}
     */
    private void canDoAuditEvent(FlowContext flowContext, FlowAuditorOperation auditorOperation) {
        Integer auditId = auditorOperation.getAuditNodeId();
        if (auditId == null) {
            // 审核节点ID不存在
            throw new SupportErrorException(String.format("审核节点(id:%s)不存在", auditId));
        }

        // 判断是否全部审核完成，所有节点全部审核完成
        List<FlowAuditChainNode> auditingNode = getAuditingNode(flowContext);
        if (CollectionUtils.isEmpty(auditingNode)) {
            // 没有待审核节点
            throw new SupportErrorException("当前流程不允许此操作");
        }
        FlowAuditChainNode currentAuditNode = auditingNode.get(0);
        if (!auditId.equals(currentAuditNode.getId())) {
            // 非当前审核节点
            throw new SupportErrorException("审核节点(id:%s)不允许此操作");
        }
        if (!currentAuditNode.getAuditors().contains(auditorOperation.getAuditor()) || !isAvatarAdmin()) {
            // 非节点审核人、avatar管理员、SRE
            throw new SupportErrorException("您无权限操作当前流程");
        }
    }

    /**
     * 获取审核链待审核节点
     *
     * @param flowContext {@link FlowContext}
     * @return List<FlowAuditChainNode>
     */
    private List<FlowAuditChainNode> getAuditingNode(FlowContext flowContext) {
        FlowAudit flowAudit = flowContext.getFlowAudit();
        return flowAudit.getAuditNode().stream()
                .filter(i -> AuditState.AUDITING.equals(i.getState())).collect(Collectors.toList());
    }

    /**
     * 登录用户是否为流程发起人
     *
     * @param flowContext {@link FlowContext}
     * @return boolean
     */
    private boolean isFlowCreateUser(FlowContext flowContext) {
        CasUser casUser = UserUtils.getCurrentCasUser();
        return casUser.getLoginName().equals(flowContext.getCreateUser());
    }

    /**
     * 当前用户是否为avatar管理员
     *
     * @return boolean
     */
    private boolean isAvatarAdmin() {
        CasUser casUser = UserUtils.getCurrentCasUser();
        return Arrays.asList(avatarSuperAdmin).contains(casUser.getLoginName());
    }

    /**
     * 检查是否有操作权限
     *
     * @param flowContext {@link FlowContext}
     */
    private void checkOperatePermission(FlowContext flowContext) {
        if (isFlowCreateUser(flowContext) || isAvatarAdmin()) {
            return;
        }
        throw new SupportErrorException("您无权限操作当前流程");
    }

}
