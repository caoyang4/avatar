package com.sankuai.avatar.workflow.core.context.loader;

import com.sankuai.avatar.common.utils.HttpServletUtils;
import com.sankuai.avatar.common.utils.JsonUtil;
import com.sankuai.avatar.dao.workflow.repository.*;
import com.sankuai.avatar.dao.workflow.repository.entity.*;
import com.sankuai.avatar.workflow.core.auditer.chain.AuditState;
import com.sankuai.avatar.workflow.core.auditer.chain.FlowAuditChainNode;
import com.sankuai.avatar.workflow.core.auditer.chain.FlowAuditChainType;
import com.sankuai.avatar.workflow.core.auditer.chain.FlowAuditorOperation;
import com.sankuai.avatar.workflow.core.context.*;
import com.sankuai.avatar.workflow.core.context.request.FlowCreateRequest;
import com.sankuai.avatar.workflow.core.context.transfer.FlowContextTransfer;
import com.sankuai.avatar.workflow.core.engine.exception.FlowException;
import com.sankuai.avatar.workflow.core.engine.listener.PushEvent;
import com.sankuai.avatar.workflow.core.input.FlowInput;
import cn.hutool.core.date.DateTime;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xk
 */
@Slf4j
@Component
public class FlowContextLoaderImpl implements FlowContextLoader {

    @Autowired
    private PushEvent pushEvent;

    @Autowired
    FlowRepository flowRepository;

    @Autowired
    FlowDataRepository flowDataRepository;

    @Autowired
    FlowTemplateRepository flowTemplateRepository;

    @Autowired
    FlowInputNameLoader flowInputNameLoader;

    @Autowired
    FlowAuditNodeRepository flowAuditNodeRepository;

    @Autowired
    FlowAuditRecordRepository flowAuditRecordRepository;

    /**
     * 获取指定 id 的流程上下文
     *
     * @param flowId 流程id
     * @return {@link FlowContext}
     */
    @Override
    public FlowContext id(Integer flowId) {
        FlowEntity flowEntity = flowRepository.getFlowEntityById(flowId);
        if (flowEntity == null) {
            return null;
        }
        FlowContext flowContext = FlowContextTransfer.INSTANCE.toFlowContext(flowEntity);
        FlowDataEntity flowDataEntity = flowDataRepository.getFlowDataByFlowId(flowId);
        // load resource
        if (flowDataEntity.getResource() != null) {
            flowContext.setResource(JsonUtil.json2Bean(flowDataEntity.getResource(), FlowResource.class));
        }
        // load input
        flowContext.setFlowInput(this.loadInput(flowContext.getTemplateName(), flowDataEntity.getInput()));
        // load audit
        flowContext.setFlowAudit(loadAudit(flowContext.getId()));
        return flowContext;
    }

    /**
     * 创建指定模版名称的流程上下文
     *
     * @param templateName 名字
     * @return {@link FlowContext}
     */
    @Override
    public FlowContext buildByTemplateName(String templateName, FlowCreateRequest flowCreateRequest) {
        FlowContext flowContext = FlowContext.builder()
                .uuid(UUID.randomUUID().toString())
                .processIndex(0)
                .atomIndex(0)
                .flowState(FlowState.NEW)
                .startTime(DateTime.now())
                .createUser(flowCreateRequest.getCreateUser())
                .createUserName(flowCreateRequest.getCreateUserName())
                .flowInput(loadInput(templateName, flowCreateRequest.getInput()))
                .build();

        // 解析环境
        flowContext.setEnv(loadEnv(flowContext.getFlowInput()));
        // 解析资源类型
        flowContext.setResource(loadResources(flowContext.getFlowInput()));
        // 解析用户请求信息，用于CT事件上报
        flowContext.setFlowEvent(loadFlowEvent());
        // 流程写入数据库
        createFlow(flowContext, loadTemplate(flowContext, templateName));
        // 推送创建流程事件
        pushEvent.pushEvent(flowContext, FlowState.NEW);
        return flowContext;
    }

    /**
     * 创建流程
     *
     * @param flowContext {@link FlowContext}
     */
    private void createFlow(FlowContext flowContext, FlowTemplateEntity flowTemplateEntity) {
        FlowEntity flowEntity = FlowContextTransfer.INSTANCE.toFlowEntity(flowContext);
        // 兼容 V1 数据
        flowEntity.setConfig(flowTemplateEntity.getConfig());
        flowEntity.setTasks(flowTemplateEntity.getTasks());
        flowEntity.setLogs(Collections.emptyMap());
        flowEntity.setInput(flowContext.getFlowInput());
        if (flowRepository.addFlow(flowEntity)) {
            flowContext.setId(flowEntity.getId());
            log.info("创建流程成功, {}:{}", flowContext.getId(), flowContext.getCnName());
            return;
        }
        throw new FlowException("创建流程DB新增失败");
    }

    /**
     * 根据input string加载input class
     *
     * @param input        json string
     * @param templateName 模板名称
     * @return {@link FlowInput}
     */
    private FlowInput loadInput(String templateName, String input) {
        Class<? extends FlowInput> flowInputClass = flowInputNameLoader.load(templateName);
        if (flowInputClass == null) {
            throw new FlowException("流程获取不到有效的input对象");
        }
        return JsonUtil.json2Bean(input, flowInputClass);
    }

    /**
     * 加载流程模版数据
     *
     * @param flowContext  {@link FlowContext}
     * @param templateName name
     * @return {@link FlowTemplateEntity}
     */
    private FlowTemplateEntity loadTemplate(FlowContext flowContext, String templateName) {
        FlowTemplateEntity flowTemplateEntity = flowTemplateRepository.getFlowTemplateByName(templateName);
        if (flowTemplateEntity == null) {
            throw new FlowException("创建流程获取不到对应的模版");
        }
        flowContext.setTemplateId(flowTemplateEntity.getId());
        flowContext.setCnName(flowTemplateEntity.getCnName());
        flowContext.setVersion(flowTemplateEntity.getVersion());
        flowContext.setTemplateName(templateName);
        return flowTemplateEntity;
    }

    /**
     * 从input加载流程resource; 相同field进行copy
     *
     * @param flowInput 流输入
     * @return {@link FlowResource}
     */
    private static FlowResource loadResources(FlowInput flowInput) {
        FlowResource flowResource = FlowResource.builder().build();
        BeanUtils.copyProperties(flowInput, flowResource);
        return flowResource;
    }

    /**
     * 从input加载流程env
     *
     * @param flowInput 流输入
     * @return {@link String}
     */
    private static String loadEnv(FlowInput flowInput) {
        try {
            Field field = flowInput.getClass().getDeclaredField("env");
            field.setAccessible(true);
            return (String) field.get(flowInput);
        } catch (NoSuchFieldException | IllegalAccessException ignored) {}
        return null;
    }

    /**
     * 加载审核信息到上下文
     *
     * @param flowId 流id
     * @return {@link FlowAudit}
     */
    private FlowAudit loadAudit(Integer flowId) {
        List<FlowAuditNodeEntity> entityList = flowAuditNodeRepository.queryAuditNode(flowId);
        if (CollectionUtils.isEmpty(entityList)) {
            return null;
        }
        // 审核节点列表
        List<FlowAuditChainNode> flowAuditChainNodeList = new ArrayList<>();

        // 已审核的人员列表
        List<String> auditors = new ArrayList<>();
        for (FlowAuditNodeEntity auditNodeEntity : entityList) {
            List<FlowAuditorOperation> operationList = getFlowAuditorOperation(auditNodeEntity.getId());
            FlowAuditChainNode flowAuditChainNode = FlowAuditChainNode.builder()
                    .id(auditNodeEntity.getId())
                    .name(auditNodeEntity.getName())
                    .seq(auditNodeEntity.getSeq())
                    .auditors(getAuditNodeAuditors(auditNodeEntity))
                    .state(AuditState.getCodeOf(auditNodeEntity.getState()))
                    .build();
            if (CollectionUtils.isNotEmpty(operationList)) {
                auditors.addAll(operationList.stream().map(FlowAuditorOperation::getAuditor).collect(Collectors.toSet()));
                flowAuditChainNode.setAuditorOperations(operationList);
            }
            flowAuditChainNodeList.add(flowAuditChainNode);
        }

        return FlowAudit.builder()
                .auditor(auditors)
                .auditChainType(FlowAuditChainType.getCodeOf(entityList.get(0).getAuditChainType()))
                .auditNode(flowAuditChainNodeList)
                .build();
    }

    /**
     * 获取审核节点操作记录
     *
     * @param auditNodeId 审核节点ID
     * @return List<FlowAuditorOperation>
     */
    private List<FlowAuditorOperation> getFlowAuditorOperation(Integer auditNodeId) {
        List<FlowAuditRecordEntity> auditRecordEntityList = flowAuditRecordRepository.queryAuditRecord(auditNodeId);
        if (CollectionUtils.isEmpty(auditRecordEntityList)) {
            return Collections.emptyList();
        }
        List<FlowAuditorOperation> auditorOperationList = new ArrayList<>();
        for (FlowAuditRecordEntity recordEntity : auditRecordEntityList) {
            FlowAuditorOperation auditorOperation = FlowAuditorOperation.builder()
                    .auditor(recordEntity.getAuditor())
                    .comment(recordEntity.getComment())
                    .build();
            auditorOperationList.add(auditorOperation);
        }
        return auditorOperationList;
    }

    /**
     * 获取审核节点审核人
     *
     * @param auditNodeEntity {@link FlowAuditNodeEntity}
     * @return List<String>
     */
    private List<String> getAuditNodeAuditors(FlowAuditNodeEntity auditNodeEntity) {
        if (StringUtils.isEmpty(auditNodeEntity.getAuditor())) {
            return Collections.emptyList();
        }
        String[] auditNodeAuditors = auditNodeEntity.getAuditor().split(",");
        return Arrays.asList(auditNodeAuditors);
    }

    /**
     * 获取流程上报CT事件基本信息
     *
     * @return {@link FlowEvent}
     */
    private FlowEvent loadFlowEvent() {
        return FlowEvent.builder().sourceDomain(HttpServletUtils.getDomainName())
                .sourceIp(HttpServletUtils.getSourceIp()).build();
    }
}
