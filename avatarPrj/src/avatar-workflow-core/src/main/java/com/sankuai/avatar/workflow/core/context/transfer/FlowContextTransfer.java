package com.sankuai.avatar.workflow.core.context.transfer;

import com.sankuai.avatar.common.utils.DateUtils;
import com.sankuai.avatar.common.utils.JsonUtil;
import com.sankuai.avatar.dao.es.request.FlowSearchUpdateRequest;
import com.sankuai.avatar.dao.workflow.repository.entity.FlowEntity;
import com.sankuai.avatar.workflow.core.context.FlowAudit;
import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.context.FlowResource;
import com.sankuai.avatar.workflow.core.context.FlowState;
import com.sankuai.avatar.workflow.core.context.resource.Host;
import com.sankuai.avatar.workflow.core.input.FlowInput;
import com.sankuai.avatar.workflow.core.mcm.McmEventContext;
import com.sankuai.avatar.workflow.core.mcm.McmEventResource;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * flowContext DO层转换
 *
 * @author Jie.li.sh
 * @create 2023-02-23
 **/
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface FlowContextTransfer {
    FlowContextTransfer INSTANCE = Mappers.getMapper(FlowContextTransfer.class);

    /**
     * flowEntity转context
     *
     * @param flowEntity DO对象
     * @return {@link FlowContext}
     */
    @Mapping(target = "resource", ignore = true)
    @Mapping(target = "atomIndex", source = "index")
    @Mapping(target = "flowState", source = "status", qualifiedByName = "toStatus")
    FlowContext toFlowContext(FlowEntity flowEntity);

    /**
     * do层转换
     *
     * @param flowContext {@link FlowContext}
     * @return {@link FlowEntity}
     */
    @Mapping(target = "status", expression = "java(flowContext.getFlowState().getEvent())")
    @Mapping(source = "atomIndex", target = "index")
    @Mapping(target = "appkey", qualifiedByName = "toResourceAppkey")
    FlowEntity toFlowEntity(FlowContext flowContext);

    /**
     * FlowContext转FlowSearchUpdateRequest
     *
     * @param flowContext {@link FlowContext}
     * @return {@link FlowSearchUpdateRequest}
     */
    @Mapping(target = "status", expression = "java(flowContext.getFlowState().getEvent())")
    @Mapping(source = "resource", target = "appkey", qualifiedByName = "toResourceAppkey")
    @Mapping(source = "resource", target = "srv", qualifiedByName = "toResourceSrv")
    @Mapping(source = "resource", target = "host", qualifiedByName = "toResourceHost")
    @Mapping(source = "resource", target = "domain", qualifiedByName = "toResourceDomain")
    @Mapping(source = "flowInput", target = "reason", qualifiedByName = "toInputReason")
    @Mapping(source = "startTime", target = "startTime", qualifiedByName = "toDateStr")
    @Mapping(source = "endTime", target = "endTime", qualifiedByName = "toDateStr")
    @Mapping(source = "flowAudit", target = "approveUsers", qualifiedByName = "toApproveUsers")
    FlowSearchUpdateRequest toFlowSearchUpdateRequest(FlowContext flowContext);

    /**
     * FlowContext to McmEventContext
     *
     * @param flowContext {@link FlowContext}
     * @return {@link McmEventContext}
     */
    @Mapping(source = "createUser", target = "operator")
    @Mapping(source = "templateName", target = "eventName", qualifiedByName = "toEventName")
    @Mapping(source = "resource", target = "resource", qualifiedByName = "toResourceBO")
    @Mapping(source = "flowInput", target = "params", qualifiedByName = "toInputParams")
    McmEventContext toMcmEventContext(FlowContext flowContext);

    /**
     * state 转换为class
     *
     * @param status 状态字符
     * @return flow state
     */
    @Named("toStatus")
    static FlowState toStatus(String status) {
        return FlowState.getEventOf(status);
    }

    /**
     * resource 转换为class
     * @param resource string
     * @return {@link FlowResource}
     */
    @Named("toResourceClass")
    static FlowResource toResourceClass(String resource) {
        if (resource == null) {
            return null;
        }
        return JsonUtil.json2Bean(resource, FlowResource.class);
    }

    @Named("toInput")
    static String toInput(FlowInput flowInput) {
        return JsonUtil.bean2Json(flowInput);
    }

    /**
     * 转换appkey
     * @param resource {@link FlowResource}
     * @return string
     */
    @Named("toResourceAppkey")
    static String toResourceAppkey(FlowResource resource) {
        if (resource == null || resource.getAppkey() == null) {
            return null;
        }
        return resource.getAppkey();
    }

    /**
     * 转换srv
     *
     * @param resource {@link FlowResource}
     * @return string
     */
    @Named("toResourceSrv")
    static String toResourceSrv(FlowResource resource) {
        if (resource == null || StringUtils.isEmpty(resource.getSrv())) {
            return null;
        }
        return resource.getSrv();
    }

    /**
     * 主机资源
     *
     * @param resource {@link FlowResource}
     * @return String
     */
    @Named("toResourceHost")
    static String toResourceHost(FlowResource resource) {
        if (resource == null || CollectionUtils.isEmpty(resource.getHostList())) {
            return null;
        }
        Set<String> hosts = resource.getHostList().stream().map(Host::getIpLan).collect(Collectors.toSet());
        return String.join(",", hosts);
    }

    /**
     * 域名资源
     *
     * @param resource {@link FlowResource}
     * @return String
     */
    @Named("toResourceDomain")
    static String toResourceDomain(FlowResource resource) {
        if (resource == null || StringUtils.isEmpty(resource.getDomain())) {
            return null;
        }
        return resource.getDomain();
    }

    /**
     * 获取原因
     *
     * @param input {@link FlowInput}
     * @return string
     */
    @Named("toInputReason")
    static String toResourceSrv(FlowInput input) {
        if (input == null) {
            return null;
        }
        String reason;
        try {
            Field field = input.getClass().getDeclaredField("reason");
            field.setAccessible(true);
            reason = (String) field.get(input);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return null;
        }
        return reason;
    }

    /**
     * 时间格式化
     *
     * @param date Date
     * @return String
     */
    @Named("toDateStr")
    static String toDateStr(Date date) {
        return date == null ? null : DateUtils.dateToStr(date);
    }

    /**
     * 审核人
     *
     * @param flowAudit {@link FlowAudit}
     * @return String
     */
    @Named("toApproveUsers")
    static String toApproveUsers(FlowAudit flowAudit) {
        if (flowAudit == null || CollectionUtils.isEmpty(flowAudit.getAuditor())) {
            return "";
        }
        return String.join(",", flowAudit.getAuditor());
    }

    /**
     * FlowInput to Map
     *
     * @param flowInput {@link FlowInput}
     * @return Map<String, Object>
     */
    @Named("toInputParams")
    static Map<String, Object> toInputParams(FlowInput flowInput) {
        return JsonUtil.beanToMap(flowInput);
    }

    /**
     * 获取 EventResourceBO 对象
     *
     * @param resource {@link FlowResource}
     * @return {@link McmEventResource}
     */
    @Named("toResourceBO")
    static McmEventResource toResourceBO(FlowResource resource) {
        if (resource == null) {
            return null;
        }
        McmEventResource mcmEventResource = new McmEventResource();
        mcmEventResource.setAppkey(resource.getAppkey());
        mcmEventResource.setDomain(resource.getDomain());
        if (CollectionUtils.isNotEmpty(resource.getHostList())) {
            mcmEventResource.setHost(resource.getHostList().stream().map(Host::getIpLan).collect(Collectors.toList()));
        }
        return mcmEventResource;
    }

    /**
     * 模板名转事件名
     *
     * @param templateName 流程模板名称
     * @return 事件名
     */
    @Named("toEventName")
    static String toEventName(String templateName) {
        List<String> tmp = Stream.of(templateName.split("_")).map(StringUtils::capitalize)
                .collect(Collectors.toList());
        return StringUtils.join(tmp, "");
    }
}
