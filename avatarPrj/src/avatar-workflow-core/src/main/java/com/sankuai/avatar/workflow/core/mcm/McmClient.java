package com.sankuai.avatar.workflow.core.mcm;

import com.sankuai.avatar.workflow.core.mcm.exception.McmErrorException;
import com.sankuai.avatar.workflow.core.mcm.request.McmEventRequest;
import com.sankuai.avatar.workflow.core.mcm.response.McmEventChangeDetailResponse;
import com.sankuai.avatar.workflow.core.mcm.response.McmPreAuditResponse;
import com.sankuai.avatar.workflow.core.mcm.response.McmPreCheckResponse;

/**
 * MCM 客户端
 *
 * @author zhaozhifan02
 */
public interface McmClient {
    /**
     * 执行预检
     *
     * @param mcmEventRequest 预检请求参数
     * @return 预检结果
     * @throws McmErrorException mcm执行异常
     */
    McmPreCheckResponse preCheck(McmEventRequest mcmEventRequest) throws McmErrorException;

    /**
     * 根据时事件ID查询预检状态
     *
     * @param eventUuid 事件ID
     * @return 预检结果
     * @throws McmErrorException mcm执行异常
     */
    McmEventChangeDetailResponse getEventChangeDetail(String eventUuid) throws McmErrorException;

    /**
     * 预检确认
     *
     * @param mcmEventRequest 预检确认请求参数
     * @throws McmErrorException mcm执行异常
     */
    void preCheckConfirm(McmEventRequest mcmEventRequest) throws McmErrorException;

    /**
     * 预检撤销
     *
     * @param mcmEventRequest 预检撤销请求参数
     * @throws McmErrorException mcm执行异常
     */
    void preCheckCancel(McmEventRequest mcmEventRequest) throws McmErrorException;

    /**
     * 发起审核
     *
     * @param mcmEventRequest 审核请求参数
     * @return PreAuditResponse
     * @throws McmErrorException mcm执行异常
     */
    McmPreAuditResponse audit(McmEventRequest mcmEventRequest) throws McmErrorException;

    /**
     * 撤销审核
     *
     * @param mcmEventRequest 撤销审核请求参数
     * @throws McmErrorException mcm执行异常
     */
    void auditCancel(McmEventRequest mcmEventRequest) throws McmErrorException;

    /**
     * 审核驳回，MCM客户端暂不支持
     *
     * @param mcmEventRequest 审核驳回请求参数
     * @throws McmErrorException mcm执行异常
     */
    void auditReject(McmEventRequest mcmEventRequest) throws McmErrorException;

    /**
     * 审核通过，MCM客户端暂不支持
     *
     * @param mcmEventRequest 审核通过请求参数
     * @throws McmErrorException mcm执行异常
     */
    void auditAccept(McmEventRequest mcmEventRequest) throws McmErrorException;
}
