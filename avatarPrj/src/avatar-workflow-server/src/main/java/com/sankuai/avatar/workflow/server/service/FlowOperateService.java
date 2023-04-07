package com.sankuai.avatar.workflow.server.service;

import com.sankuai.avatar.workflow.server.dto.flow.*;

/**
 * 流程操作类业务层接口
 *
 * @author zhaozhifan02
 */
public interface FlowOperateService {
    /**
     * 创建流程
     *
     * @param templateName 模板名称
     * @param input        请求参数
     * @return 是否创建成功
     */
    CreateResponseDTO createFlow(String templateName, String input);

    /**
     * 预检风险确认
     *
     * @param id id
     * @return confirm response
     */
    ConfirmResponseDTO checkConfirm(Integer id);

    /**
     * 预检流程取消
     *
     * @param id id
     * @return confirm response
     */
    CancelResponseDTO checkCancel(Integer id);

    /**
     * 审核通过
     *
     * @param id         流程ID
     * @param requestDTO {@link FlowAuditOperateRequestDTO}
     * @return {@link AcceptResponseDTO}
     */
    AcceptResponseDTO auditAccept(Integer id, FlowAuditOperateRequestDTO requestDTO);

    /**
     * 审核拒绝
     *
     * @param id         流程ID
     * @param requestDTO {@link FlowAuditOperateRequestDTO}
     * @return {@link RejectResponseDTO}
     */
    RejectResponseDTO auditReject(Integer id, FlowAuditOperateRequestDTO requestDTO);

    /**
     * 审核撤销
     *
     * @param id         流程ID
     * @param requestDTO {@link FlowAuditOperateRequestDTO}
     * @return {@link CancelResponseDTO}
     */
    CancelResponseDTO auditCancel(Integer id, FlowAuditOperateRequestDTO requestDTO);
}
