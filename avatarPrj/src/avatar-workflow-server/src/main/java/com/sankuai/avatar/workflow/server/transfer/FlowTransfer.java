package com.sankuai.avatar.workflow.server.transfer;

import com.sankuai.avatar.dao.es.entity.FlowSearchEntity;
import com.sankuai.avatar.dao.es.request.FlowSearchQueryRequest;
import com.sankuai.avatar.dao.workflow.repository.entity.FlowEntity;
import com.sankuai.avatar.dao.workflow.repository.request.FlowQueryRequest;
import com.sankuai.avatar.workflow.core.checker.CheckResult;
import com.sankuai.avatar.workflow.core.checker.CheckState;
import com.sankuai.avatar.workflow.core.context.FlowState;
import com.sankuai.avatar.workflow.core.engine.process.response.PreCheckResult;
import com.sankuai.avatar.workflow.server.dto.flow.CreateResponseDTO;
import com.sankuai.avatar.workflow.server.dto.flow.FlowAuditOperateRequestDTO;
import com.sankuai.avatar.workflow.server.dto.flow.FlowDTO;
import com.sankuai.avatar.workflow.server.request.FlowPageRequest;
import com.sankuai.avatar.workflow.server.vo.flow.FlowCreateResponseVO;
import com.sankuai.avatar.workflow.server.vo.flow.FlowHomeVO;
import com.sankuai.avatar.workflow.server.vo.flow.FlowVO;
import com.sankuai.avatar.workflow.server.vo.flow.PreCheckItemVO;
import com.sankuai.avatar.workflow.server.vo.flow.request.FlowAuditOperateRequestVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhaozhifan02
 */
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface FlowTransfer {
    FlowTransfer INSTANCE = Mappers.getMapper(FlowTransfer.class);

    /**
     * FlowEntity 转  FlowDTO
     *
     * @param flowEntity FlowEntity
     * @return FlowDTO
     */
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "approveType", ignore = true)
    @Mapping(target = "flowType", ignore = true)
    @Mapping(target = "objectName", ignore = true)
    @Mapping(target = "objectType", ignore = true)
    @Mapping(target = "reason", expression = "java(\"\")")
    @Mapping(target = "keyword", expression = "java(new ArrayList())")
    FlowDTO toDTO(FlowEntity flowEntity);

    /**
     * 批量 FlowEntity 转  FlowDTO
     *
     * @param flowEntityList flowEntityList
     * @return {@link List}<{@link FlowDTO}>
     */
    @IterableMapping(qualifiedByName = "toDTO")
    List<FlowDTO> toDTOList(List<FlowEntity> flowEntityList);

    @Mapping(target = "templateId", ignore = true)
    @Mapping(target = "objectName", ignore = true)
    @Mapping(target = "objectType", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "input", ignore = true)
    @Mapping(target = "config", ignore = true)
    @Mapping(target = "publicData", ignore = true)
    @Mapping(target = "index", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(source = "cname", target = "cnName")
    @Mapping(source = "appKey", target = "appkey")
    FlowDTO toFlowDTO(FlowSearchEntity flowSearchEntity);

    @IterableMapping(qualifiedByName = "toFlowDTO")
    List<FlowDTO> toFlowDTOList(List<FlowSearchEntity> flowSearchEntities);

    /**
     * FlowDTO 转 FlowVO
     *
     * @param flowDTO FlowDTO
     * @return FlowVO
     */
    FlowVO toVO(FlowDTO flowDTO);

    /**
     * 流程创建VO
     *
     * @param createResponseDTO {@link CreateResponseDTO}
     * @return {@link FlowCreateResponseVO}
     */
    @Mapping(target = "state", expression = "java(createResponseDTO.getState().getCode())")
    @Mapping(target = "stateName", expression = "java(createResponseDTO.getState().getStatusName())")
    @Mapping(source = "preCheckResult", target = "checkList", qualifiedByName = "formatCheckList")
    FlowCreateResponseVO toCreateVO(CreateResponseDTO createResponseDTO);

    /**
     * FlowAuditOperateRequestVO to FlowAuditOperateRequestDTO
     *
     * @param operateRequestVO {@link FlowAuditOperateRequestVO}
     * @return {@link FlowAuditOperateRequestDTO}
     */
    FlowAuditOperateRequestDTO toAuditOperationDTO(FlowAuditOperateRequestVO operateRequestVO);

    /**
     * 显示state名称
     *
     * @param state {@link FlowState}
     * @return state name
     */
    @Named("formatState")
    static String formatState(FlowState state) {
        return state.getEvent();
    }

    /**
     * 显示的检查结果
     *
     * @param preCheckResult 预检结果
     * @return list of preCheckResponse
     */
    @Named("formatCheckList")
    static List<PreCheckItemVO> formatCheckList(PreCheckResult preCheckResult) {
        if (preCheckResult == null || CollectionUtils.isEmpty(preCheckResult.getCheckResults())) {
            return Collections.emptyList();
        }
        List<PreCheckItemVO> checkList = new ArrayList<>();
        for (CheckResult checkResult : preCheckResult.getCheckResults()) {
            // 拦截的展示
            if (checkResult.getCheckState().equals(CheckState.PRE_CHECK_REJECTED)) {
                checkList.add(PreCheckItemVO.of(checkResult.getMsg(), "error"));
            } else if (checkResult.getCheckState().equals(CheckState.PRE_CHECK_WARNING)) {
                checkList.add(PreCheckItemVO.of(checkResult.getMsg(), "warning"));
            }
        }
        return checkList;
    }

    /**
     * DTO转 homeVO
     *
     * @param flowDTO 流dto
     * @return {@link FlowHomeVO}
     */
    @Mapping(source = "uuid", target = "applyId")
    @Mapping(source = "cnName", target = "applyName")
    @Mapping(source = "startTime", target = "applyTime")
    @Mapping(source = "createUser", target = "applyUser")
    @Mapping(target = "applyUserImage", expression = "java(\"\")")
    @Mapping(source = "approveUsers", target = "approveUsers", qualifiedByName = "toApproveUsers")
    FlowHomeVO toHomeVO(FlowDTO flowDTO);

    @IterableMapping(qualifiedByName = "toHomeVO")
    List<FlowHomeVO> toHomeVOList(List<FlowDTO> flowDTOList);

    /**
     * 转换 db 请求
     *
     * @param pageRequest 请求
     * @return {@link FlowQueryRequest}
     */
    FlowQueryRequest toRequest(FlowPageRequest pageRequest);

    /**
     * 转换es请求
     *
     * @param pageRequest 请求
     * @return {@link FlowSearchQueryRequest}
     */
    @Mapping(source = "appkey", target = "appKey")
    @Mapping(source = "templateName", target = "template")
    @Mapping(source = "approveUsers", target = "approveUser")
    @Mapping(source = "startTimeGt", target = "createTimeBegin")
    @Mapping(source = "startTimeLt", target = "createTimeEnd")
    FlowSearchQueryRequest toEsRequest(FlowPageRequest pageRequest);

    @Named("toApproveUsers")
    default List<String> toApproveUsers(String approveUsers){
        if (StringUtils.isEmpty(approveUsers)) {
            return Collections.emptyList();
        }
        String[] users = approveUsers.split(",");
        return Arrays.stream(users).filter(StringUtils::isNotEmpty).distinct().collect(Collectors.toList());
    }

}
