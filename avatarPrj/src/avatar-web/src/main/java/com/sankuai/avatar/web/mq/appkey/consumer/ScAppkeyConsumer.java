package com.sankuai.avatar.web.mq.appkey.consumer;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import com.meituan.mafka.client.consumer.ConsumeStatus;
import com.meituan.mdp.boot.starter.mafka.consumer.anno.MdpMafkaMsgReceive;
import com.sankuai.avatar.collect.appkey.consumer.model.ScAppkeyConsumerData;
import com.sankuai.avatar.collect.appkey.event.AppkeyCollectEvent;
import com.sankuai.avatar.collect.appkey.event.AppkeyCollectEventData;
import com.sankuai.avatar.collect.appkey.event.CollectEventName;
import com.sankuai.avatar.collect.appkey.event.CollectEventTypeEnum;
import com.sankuai.avatar.collect.appkey.model.Appkey;
import com.sankuai.avatar.collect.appkey.scheduer.AppkeyCollectEventScheduler;
import com.sankuai.avatar.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 监听SC服务信息变更
 * @author qinwei05
 * @date 2022/11/3 17:12
 */
@Service("scListener")
@Slf4j
public class ScAppkeyConsumer implements Consumer {

    /**
     * 事件类型
     */
    private static final List<String> operations = Arrays.asList(CollectEventTypeEnum.ADD.name(),
            CollectEventTypeEnum.UPDATE.name(), CollectEventTypeEnum.DELETE.name());

    private final AppkeyCollectEventScheduler appkeyCollectEventScheduler;

    @Autowired
    public ScAppkeyConsumer(AppkeyCollectEventScheduler appkeyCollectEventScheduler) {
        this.appkeyCollectEventScheduler = appkeyCollectEventScheduler;
    }

    /**
     * 消费
     * <a href="https://km.sankuai.com/page/424834682">学城WIKI：Mafka-MDP官方文档</a>
     * 消费配置需要2个步骤：
     * 1、在properties文件中配置消费者相关配置
     * 2、在代码中用 @MdpMafkaMsgReceive 注解消费方法
     * <p>
     * 返回状态说明：①返回CONSUME_SUCCESS，表示消费成功准备消费下一条消息。
     *             ②返回RECONSUME_LATER，表示请求再次消费该消息，默认最多三次，然后跳过此条消息的消费，开始消费下一条。(算上初始最多消费4次）
     *             ③返回CONSUMER_FAILURE，表示请求继续消费，直到消费成功。
     * 注意：如果不想在消费异常时一直进行重试，造成消息积压，可以返回RECONSUME_LATER，详细设置可以看WIKI文档
     * @param msgBody 消费消息体
     * @return {@link ConsumeStatus}
     */
    @MdpMafkaMsgReceive
    @Override
    public ConsumeStatus consume(String msgBody) {
        Transaction t = Cat.newTransaction("Mafka.Appkey.Consumer", "ScAppkeyConsumer");
        try {
            ScAppkeyConsumerData scAppkeyConsumerData = JsonUtil.json2Bean(msgBody, ScAppkeyConsumerData.class);
            if (!operations.contains(scAppkeyConsumerData.getOperation())){
                return ConsumeStatus.CONSUME_SUCCESS;
            }
            log.info("SC消息队列监听: Appkey [{}] 信息发生 [{}] 变更, 详细信息为 {}",
                    scAppkeyConsumerData.getAppKey(), scAppkeyConsumerData.getOperation(), msgBody);
            AppkeyCollectEvent appkeyCollectEvent;
            if (CollectEventTypeEnum.ADD.name().equals(scAppkeyConsumerData.getOperation())){
                appkeyCollectEvent = AppkeyCollectEvent.builder()
                        .collectEventData(AppkeyCollectEventData.of(scAppkeyConsumerData.getAppKey()))
                        .collectEventName(CollectEventName.APPKEY_REFRESH).build();
            } else {
                appkeyCollectEvent = AppkeyCollectEvent.builder()
                        .collectEventData(AppkeyCollectEventData.of(scAppkeyConsumerData.getAppKey(), scAppkeyConsumerData))
                        .collectEventName(CollectEventName.SC_APPKEY_UPDATE).build();
                if (CollectEventTypeEnum.DELETE.name().equals(scAppkeyConsumerData.getOperation())){
                    appkeyCollectEvent.setCollectEventName(CollectEventName.APPKEY_DELETE);
                }
            }
            // 兼容非后端Appkey采集方式
            if (!"BACKEND".equals(scAppkeyConsumerData.getType())){
                appkeyCollectEvent.setCollectEventName(CollectEventName.SC_NOT_BACKEND_APPKEY_UPDATE);
            }
            Appkey appkey = appkeyCollectEventScheduler.collect(appkeyCollectEvent);
            log.info("SC消息队列监听: Appkey信息变更为：{}", appkey.toString());
            return ConsumeStatus.CONSUME_SUCCESS;
        } catch (Exception e) {
            log.error("scListener消费服务变更消息失败", e);
            Cat.logError("scListener消费服务变更消息失败", e);
            t.setStatus(e);
        } finally {
            t.complete();
        }

        return ConsumeStatus.CONSUME_SUCCESS;
    }
}
