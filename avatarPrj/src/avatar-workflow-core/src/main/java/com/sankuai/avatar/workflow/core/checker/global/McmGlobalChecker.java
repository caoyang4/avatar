package com.sankuai.avatar.workflow.core.checker.global;

import com.meituan.mdp.boot.starter.config.annotation.MdpConfig;
import com.sankuai.avatar.workflow.core.annotation.CheckerMeta;
import com.sankuai.avatar.workflow.core.checker.CheckResult;
import com.sankuai.avatar.workflow.core.checker.CheckState;
import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.context.transfer.FlowContextTransfer;
import com.sankuai.avatar.workflow.core.mcm.McmClient;
import com.sankuai.avatar.workflow.core.mcm.McmEventContext;
import com.sankuai.avatar.workflow.core.mcm.request.McmEventRequest;
import com.sankuai.avatar.workflow.core.mcm.response.McmPreCheckResponse;
import com.sankuai.mcm.client.sdk.dto.common.PreCheckItem;
import com.sankuai.mcm.client.sdk.enums.Decision;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * MCM 全局预检
 *
 * @author zhaozhifan02
 */
@Slf4j
@CheckerMeta
@Component
public class McmGlobalChecker extends AbstractGlobalChecker {

    @MdpConfig("MCM_CHECK_SWITCH:true")
    private boolean mcmCheckSwitch;

    private final McmClient mcmClient;

    @Autowired
    public McmGlobalChecker(McmClient mcmClient) {
        this.mcmClient = mcmClient;
    }


    /**
     * 执行检查
     *
     * @param flowContext 检查请求
     * @return 执行结果
     */
    @Override
    public CheckResult check(FlowContext flowContext) {
        if (!mcmCheckSwitch) {
            return CheckResult.ofAccept();
        }
        McmEventContext eventContext = FlowContextTransfer.INSTANCE.toMcmEventContext(flowContext);
        McmEventRequest eventRequest = McmEventRequest.builder()
                .evenName(eventContext.getEventName())
                .mcmEventContext(eventContext)
                .build();
        McmPreCheckResponse mcmCheckResponse = mcmClient.preCheck(eventRequest);
        if (mcmCheckResponse.getDecision() == null || mcmCheckResponse.getItems() == null) {
            return CheckResult.ofAccept();
        }

        //转换mcm的检查结果
        List<CheckResult> checkResultList = new ArrayList<>();
        for (PreCheckItem mcmItem : mcmCheckResponse.getItems()) {
            checkResultList.add(CheckResult.of(
                    mcmItem.getName(),
                    formatDecision(mcmItem.getDecision()),
                    mcmItem.getPlainDescription()
            ));
        }
        return CheckResult.of(this.getClass().getSimpleName(), formatDecision(mcmCheckResponse.getDecision()), checkResultList);
    }

    /**
     * 转换mcm的决策 -> 检查结果
     *
     * @param decision mcm decision
     * @return avatar check response
     */
    private CheckState formatDecision(Decision decision) {
        switch (decision) {
            case ACCEPT:
                return CheckState.PRE_CHECK_ACCEPTED;
            case REJECT:
                return CheckState.PRE_CHECK_REJECTED;
            case EXCEPTION:
            case TIMEOUT:
            case WARNING:
            default:
                return CheckState.PRE_CHECK_WARNING;
        }
    }
}
