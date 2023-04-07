package com.sankuai.avatar.capacity.mafka;

import com.meituan.mafka.client.MafkaClient;
import com.meituan.mafka.client.consumer.ConsumerConstants;
import com.meituan.mafka.client.producer.*;
import com.sankuai.avatar.capacity.dto.CapacityDTO;
import com.sankuai.avatar.capacity.dto.UtilizationOptimizeDTO;
import com.sankuai.avatar.capacity.util.GsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Properties;


/**
 * @author chenxinli
 */
@Slf4j
@Service
public class Producer {

    private static IProducerProcessor producer;

    public Producer() throws Exception {
        Properties properties = new Properties();
        // 设置业务所在BG的namespace，此参数必须配置且请按照demo正确配置
        properties.setProperty(ConsumerConstants.MafkaBGNamespace, "common");
        // 设置生产者appkey，此参数必须配置且请按照demo正确配置
        properties.setProperty(ConsumerConstants.MafkaClientAppkey, "com.sankuai.avatar.web");

        // 创建topic对应的producer对象（注意每次build调用会产生一个新的实例），此处配置topic名称，请按照demo正确配置
        // 请注意：若调用MafkaClient.buildProduceFactory()创建实例抛出有异常，请重点关注并排查异常原因，不可频繁调用该方法给服务端带来压力。
        producer = MafkaClient.buildProduceFactory(properties, "avatar-capacity");
    }

    public void sendMsg(CapacityDTO capacityDTO) throws Exception {
        producer.sendAsyncMessage(GsonUtils.serialization(capacityDTO), new FutureCallback() {
            @Override
            public void onSuccess(AsyncProducerResult result) {
                log.info(String.format("[%s]容灾等级、资源利用率信息推送成功", capacityDTO.getAppkey()));
            }

            @Override
            public void onFailure(Throwable t) {
                log.error(String.format("[%s]容灾等级、资源利用率信息推送失败", capacityDTO.getAppkey()));
            }
        });
    }

    public void sendMsg(List<UtilizationOptimizeDTO> utilizationOptimizeDTOS) throws Exception {
        producer.sendAsyncMessage(GsonUtils.serialization(utilizationOptimizeDTOS), new FutureCallback() {
            @Override
            public void onSuccess(AsyncProducerResult result) {
                log.info(String.format("[%s]全链路容灾等级、资源利用率信息推送成功", utilizationOptimizeDTOS.get(0).getAppkey()));
            }

            @Override
            public void onFailure(Throwable t) {
                log.error(String.format("[%s]全链路容灾等级、资源利用率信息推送失败", utilizationOptimizeDTOS.get(0).getAppkey()));
            }
        });
    }

}
