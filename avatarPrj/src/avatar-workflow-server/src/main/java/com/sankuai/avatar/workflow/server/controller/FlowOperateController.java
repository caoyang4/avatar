package com.sankuai.avatar.workflow.server.controller;

import com.sankuai.avatar.workflow.server.dto.flow.CreateResponseDTO;
import com.sankuai.avatar.workflow.server.dto.flow.FlowAuditOperateRequestDTO;
import com.sankuai.avatar.workflow.server.service.FlowOperateService;
import com.sankuai.avatar.workflow.server.transfer.FlowTransfer;
import com.sankuai.avatar.workflow.server.vo.flow.FlowCreateResponseVO;
import com.sankuai.avatar.workflow.server.vo.flow.request.FlowAuditOperateRequestVO;
import com.sankuai.avatar.workflow.server.vo.flow.request.FlowCreateRequestVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 流程操作类型接口, 发起、确认、审批、关闭等操作
 *
 * @author zhaozhifan02
 */
@Slf4j
@RequestMapping("/api/v2/avatar/workflow/flow")
@RestController
public class FlowOperateController {

    private final FlowOperateService flowOperateService;

    @Autowired
    public FlowOperateController(FlowOperateService flowOperateService) {
        this.flowOperateService = flowOperateService;
    }

    /**
     * 创建流程
     *
     * @param templateName 模板名称
     * @return create workflow response
     */
    @PostMapping("/create/{templateName}")
    public FlowCreateResponseVO createFlow(@PathVariable String templateName, @Validated @RequestBody FlowCreateRequestVO request) {
        CreateResponseDTO createResponseDTO = flowOperateService.createFlow(templateName, request.getInput());
        return FlowTransfer.INSTANCE.toCreateVO(createResponseDTO);
    }

    /**
     * 预检风险确认
     * @param id 流程id
     */
    @PostMapping("/check/{id}/confirm")
    public void confirmFlow(@PathVariable Integer id) {
        flowOperateService.checkConfirm(id);
    }

    /**
     * 预检取消
     *
     * @param id 流程id
     */
    @PostMapping("/check/{id}/cancel")
    public void cancelFlow(@PathVariable Integer id) {
        flowOperateService.checkCancel(id);
    }

    /**
     * 审核通过
     *
     * @param id      流程ID
     * @param request {@link FlowAuditOperateRequestVO}
     */
    @PostMapping("/audit/{id}/accept")
    public void auditAccept(@PathVariable Integer id, @Validated @RequestBody FlowAuditOperateRequestVO request) {
        FlowAuditOperateRequestDTO requestDTO = FlowTransfer.INSTANCE.toAuditOperationDTO(request);
        flowOperateService.auditAccept(id, requestDTO);
    }

    /**
     * 审核驳回
     *
     * @param id      流程ID
     * @param request {@link FlowAuditOperateRequestVO}
     */
    @PostMapping("/audit/{id}/reject")
    public void auditReject(@PathVariable Integer id, @Validated @RequestBody FlowAuditOperateRequestVO request) {
        FlowAuditOperateRequestDTO requestDTO = FlowTransfer.INSTANCE.toAuditOperationDTO(request);
        flowOperateService.auditReject(id, requestDTO);
    }

    /**
     * 审核撤销
     *
     * @param id      流程ID
     * @param request {@link FlowAuditOperateRequestVO}
     */
    @PostMapping("/audit/{id}/cancel")
    public void auditCancel(@PathVariable Integer id, @Validated @RequestBody FlowAuditOperateRequestVO request) {
        FlowAuditOperateRequestDTO requestDTO = FlowTransfer.INSTANCE.toAuditOperationDTO(request);
        flowOperateService.auditCancel(id, requestDTO);
    }
}
