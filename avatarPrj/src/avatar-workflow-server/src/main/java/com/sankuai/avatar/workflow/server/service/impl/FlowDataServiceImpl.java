package com.sankuai.avatar.workflow.server.service.impl;

import com.dianping.cat.Cat;
import com.meituan.mdp.boot.starter.config.annotation.MdpConfig;
import com.sankuai.avatar.workflow.server.dto.es.EsFlowType;
import com.sankuai.avatar.workflow.server.dto.request.flow.FlowUpdateRequest;
import com.sankuai.avatar.workflow.server.mafka.entity.FlowMessage;
import com.sankuai.avatar.workflow.server.mafka.producer.Producer;
import com.sankuai.avatar.workflow.server.repository.handler.FlowUpdateHandler;
import com.sankuai.avatar.workflow.server.service.FlowDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author zhaozhifan02
 */
@Slf4j
@Service
public class FlowDataServiceImpl implements FlowDataService {


    @Resource(name = "flowUpdateProducer")
    private Producer<FlowMessage> producer;

    @MdpConfig("ASYNC_WRITE_ES_SWITCH:true")
    private static boolean asyncWriteEsSwitch;

    @Autowired
    private List<FlowUpdateHandler> handlers;

    private Map<EsFlowType, FlowUpdateHandler> flowUpdateHandlerMap;

    @PostConstruct
    public void init() {
        flowUpdateHandlerMap = handlers.stream().collect(Collectors.toMap(FlowUpdateHandler::getType,
                Function.identity()));
    }

    @Override
    public boolean asyncUpdate(FlowUpdateRequest request) {
        // 异步写入 mafka
        if (asyncWriteEsSwitch) {
            return pushToMafka(request);
        }
        // 同步写入
        return update(request);
    }

    @Override
    public boolean update(FlowUpdateRequest request) {
        if (Objects.isNull(request)) {
            return false;
        }
        EsFlowType flowType = EsFlowType.getByName(request.getType());
        if (Objects.isNull(flowType)) {
            return false;
        }
        return flowUpdateHandlerMap.get(flowType).update(request);
    }

    private boolean pushToMafka(FlowUpdateRequest request) {
        if (Objects.isNull(request)) {
            return false;
        }
        FlowMessage flowMessage = FlowMessage.builder()
                .id(request.getId())
                .type(request.getType())
                .data(request.getData())
                .build();
        try {
            // 推送 Mafka 消息，异步写入 ES
            producer.produce(flowMessage);
            log.info("Produce flowMessage: {} succeed", flowMessage);
        } catch (Throwable t) {
            log.error("FlowProcess flowMessage: {} caught exception",
                    flowMessage, t);
            Cat.logError("FlowProcess flowMessage caught exception", t);
            return false;
        }
        return true;
    }

}
