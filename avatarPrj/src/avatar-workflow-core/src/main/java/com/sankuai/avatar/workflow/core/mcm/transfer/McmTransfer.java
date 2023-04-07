package com.sankuai.avatar.workflow.core.mcm.transfer;

import com.sankuai.avatar.workflow.core.auditer.chain.FlowAuditChain;
import com.sankuai.avatar.workflow.core.auditer.chain.FlowAuditChainNode;
import com.sankuai.avatar.workflow.core.auditer.chain.FlowAuditorOperation;
import com.sankuai.avatar.workflow.core.mcm.McmEventStatus;
import com.sankuai.avatar.workflow.core.mcm.response.McmEventChangeDetailResponse;
import com.sankuai.avatar.workflow.core.mcm.response.McmPreAuditResponse;
import com.sankuai.avatar.workflow.core.mcm.response.McmPreCheckResponse;
import com.sankuai.mcm.client.sdk.context.handler.AuditChain;
import com.sankuai.mcm.client.sdk.context.handler.AuditChainNode;
import com.sankuai.mcm.client.sdk.context.handler.AuditorOperation;
import com.sankuai.mcm.client.sdk.dto.response.ChangeDetailDTO;
import com.sankuai.mcm.client.sdk.dto.response.PreAuditResponseDTO;
import com.sankuai.mcm.client.sdk.dto.response.PreCheckResponseDTO;
import com.sankuai.mcm.client.sdk.enums.AuditOperation;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Date;
import java.util.List;

/**
 * MCM 对象转换器
 *
 * @author zhaozhifan02
 */
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface McmTransfer {
    McmTransfer INSTANCE = Mappers.getMapper(McmTransfer.class);

    /**
     * PreCheckRequestDTO to  McmPreCheckResponse
     *
     * @param responseDTO {@link PreCheckResponseDTO}
     * @return {@link McmPreCheckResponse}
     */
    McmPreCheckResponse toPreCheckResponse(PreCheckResponseDTO responseDTO);

    /**
     * PreAuditResponseDTO to  McmPreAuditResponse
     *
     * @param responseDTO {@link PreAuditResponseDTO}
     * @return {@link McmPreAuditResponse}
     */
    McmPreAuditResponse toPreAuditResponse(PreAuditResponseDTO responseDTO);

    /**
     * AuditChain to FlowAuditChain
     *
     * @param auditChain {@link AuditChain}
     * @return {@link FlowAuditChain}
     */
    @Mapping(source = "chainNodes", target = "chainNodes", qualifiedByName = "toFlowAuditChainNodeList")
    FlowAuditChain toFlowAuditChain(AuditChain auditChain);

    /**
     * AuditChainNode to FlowAuditChainNode
     *
     * @param auditChainNode {@link AuditChainNode}
     * @return {@link FlowAuditChainNode}
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "priority", target = "seq")
    FlowAuditChainNode toFlowAuditChainNode(AuditChainNode auditChainNode);

    /**
     * List<AuditChainNode> to  List<FlowAuditChainNode>
     *
     * @param auditChainNodeList List<AuditChainNode>
     * @return List<FlowAuditChainNode>
     */
    @IterableMapping(qualifiedByName = "toFlowAuditChainNode")
    List<FlowAuditChainNode> toFlowAuditChainNodeList(List<AuditChainNode> auditChainNodeList);

    /**
     * AuditOperation to  FlowAuditOperation
     *
     * @param auditOperation {@link AuditOperation}
     * @return {@link FlowAuditorOperation}
     */
    @Mapping(target = "auditNodeId", ignore = true)
    @Mapping(source = "operateTime", target = "operateTime", qualifiedByName = "timestampToDate")
    FlowAuditorOperation toFlowAuditorOperation(AuditorOperation auditOperation);

    /**
     * List<AuditorOperation> to  List<FlowAuditorOperation>
     *
     * @param auditChainNodeList List<AuditorOperation>
     * @return List<FlowAuditorOperation>
     */
    @IterableMapping(qualifiedByName = "toFlowAuditorOperation")
    List<FlowAuditorOperation> toFlowAuditorOperationList(List<AuditorOperation> auditChainNodeList);

    /**
     * ChangeDetailDTO to McmEventChangeDetailResponse
     *
     * @param changeDetailDTO {@link ChangeDetailDTO}
     * @return {@link McmEventChangeDetailResponse}
     */
    @Mapping(source = "status", target = "status", qualifiedByName = "toEventStatus")
    McmEventChangeDetailResponse toEventChangeDetailResponse(ChangeDetailDTO changeDetailDTO);

    /**
     * 时间戳转时间格式
     *
     * @param timestamp 时间戳
     * @return 时间格式
     */
    @Named("timestampToDate")
    static Date timestampToDate(Long timestamp) {
        return new Date(timestamp);
    }

    /**
     * 状态转换
     *
     * @param status 状态
     * @return 状态枚举
     */
    @Named("toEventStatus")
    static McmEventStatus toEventStatus(String status) {
        return McmEventStatus.getEventStatus(status);
    }
}
