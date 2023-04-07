package com.sankuai.avatar.web.mq.capacity.consumer.impl;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import com.meituan.mafka.client.consumer.ConsumeStatus;
import com.meituan.mdp.boot.starter.mafka.consumer.anno.MdpMafkaMsgReceive;
import com.sankuai.avatar.common.utils.JsonUtil;
import com.sankuai.avatar.web.dto.capacity.AppkeyPaasCapacityReportDTO;
import com.sankuai.avatar.web.mq.capacity.consumer.PaasCapacityReportConsumer;
import com.sankuai.avatar.web.service.AppkeyPaasCapacityService;
import com.sankuai.avatar.web.transfer.capacity.AppkeyPaasCapacityReportVOTransfer;
import com.sankuai.avatar.web.vo.capacity.AppkeyPaasCapacityReportVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * @author caoyang
 * @create 2023-02-24 14:58
 */
@Service("paasCapacityListener")
@Slf4j
public class PaasCapacityReportConsumerImpl implements PaasCapacityReportConsumer {

    private final AppkeyPaasCapacityService appkeyPaasCapacityService;

    @Autowired
    public PaasCapacityReportConsumerImpl(AppkeyPaasCapacityService appkeyPaasCapacityService){
        this.appkeyPaasCapacityService = appkeyPaasCapacityService;
    }

    @MdpMafkaMsgReceive
    @Override
    public ConsumeStatus consume(String msgBody) {
        Transaction t = Cat.newTransaction("Mafka.PaasCapacity.Consumer", "PaasCapacityConsumer");
        try {
            AppkeyPaasCapacityReportVO reportVO = JsonUtil.json2Bean(msgBody, AppkeyPaasCapacityReportVO.class);
            AppkeyPaasCapacityReportDTO appkeyPaasCapacityReportDTO = new AppkeyPaasCapacityReportDTO();
            appkeyPaasCapacityReportDTO.setPaasName(reportVO.getPaasName());
            appkeyPaasCapacityReportDTO.setAppkeyPaasCapacityDTOList(AppkeyPaasCapacityReportVOTransfer.INSTANCE.toAppkeyPaasCapacityDTOList(reportVO));
            appkeyPaasCapacityReportDTO.setAppkeyPaasStandardClientDTOList(AppkeyPaasCapacityReportVOTransfer.INSTANCE.toAppkeyPaasStandardClientDTOList(reportVO));
            appkeyPaasCapacityReportDTO.setAppkeyPaasClientDTOList(AppkeyPaasCapacityReportVOTransfer.INSTANCE.toAppkeyPaasClientDTOList(reportVO));
            appkeyPaasCapacityService.reportAppkeyPaasCapacity(Collections.singletonList(appkeyPaasCapacityReportDTO));
        } catch (Exception e) {
            log.error("paasCapacityListener消费paas容灾消息失败", e);
            Cat.logError("paasCapacityListener消费paas容灾消息失败", e);
            t.setStatus(e);
        } finally {
            t.complete();
        }
        return ConsumeStatus.CONSUME_SUCCESS;
    }
}
