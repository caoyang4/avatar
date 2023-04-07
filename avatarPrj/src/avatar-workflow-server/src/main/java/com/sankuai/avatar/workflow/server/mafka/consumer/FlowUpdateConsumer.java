package com.sankuai.avatar.workflow.server.mafka.consumer;

import com.dianping.cat.Cat;
import com.meituan.mafka.client.consumer.ConsumeStatus;
import com.meituan.mdp.boot.starter.mafka.consumer.anno.MdpMafkaMsgReceive;
import com.sankuai.avatar.common.utils.GsonUtils;
import com.sankuai.avatar.workflow.server.dto.request.flow.FlowUpdateRequest;
import com.sankuai.avatar.workflow.server.mafka.entity.FlowMessage;
import com.sankuai.avatar.workflow.server.service.FlowDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author zhaozhifan02
 */
@Slf4j
@Component("workFlowUpdateConsumer")
public class FlowUpdateConsumer {

    @Autowired
    private FlowDataService flowDataService;

    @MdpMafkaMsgReceive
    public ConsumeStatus consume(String msg) {
        log.info("Consume flow update msg: {}", msg);
        FlowMessage flowMessage = null;
        try {
            flowMessage = GsonUtils.deserialization(
                    msg, FlowMessage.class);
        } catch (Exception e) {
            log.error("Consume flow update msg: {}, deserialization failed: ",
                    msg, e);
            Cat.logError(e);
            return ConsumeStatus.RECONSUME_LATER;
        }
        if (Objects.isNull(flowMessage) || Objects.isNull(flowMessage.getId())) {
            log.error("Consume flow update msg, flowId is null ");
            return ConsumeStatus.CONSUME_SUCCESS;
        }

        try {
            boolean status = flowDataService.update(FlowUpdateRequest.builder()
                    .id(flowMessage.getId())
                    .type(flowMessage.getType())
                    .data(flowMessage.getData())
                    .build());
            log.info("Consume flow update msg: {}, save es status: {}", msg, status);
        } catch (Exception e) {
            log.error("Consume flow update msg: {}, save es failed: ",
                    msg, e);
            Cat.logError(e);
            return ConsumeStatus.RECONSUME_LATER;
        }
        return ConsumeStatus.CONSUME_SUCCESS;
    }
}
