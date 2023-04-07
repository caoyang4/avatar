package com.sankuai.avatar.web.mq.appkey.consumer;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import com.meituan.mafka.client.consumer.ConsumeStatus;
import com.meituan.mdp.boot.starter.mafka.consumer.anno.MdpMafkaMsgReceive;
import com.sankuai.avatar.collect.appkey.event.AppkeyCollectEvent;
import com.sankuai.avatar.collect.appkey.scheduer.AppkeyCollectEventScheduler;
import com.sankuai.avatar.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 监听Avatar服务信息变更
 * @author qinwei05
 * @date 2022/11/30 17:12
 */
@Service("appkeyListener")
@Slf4j
public class AvatarCollectAppkeyConsumer implements Consumer {

    private final AppkeyCollectEventScheduler appkeyCollectEventScheduler;

    @Autowired
    public AvatarCollectAppkeyConsumer(AppkeyCollectEventScheduler appkeyCollectEventScheduler) {
        this.appkeyCollectEventScheduler = appkeyCollectEventScheduler;
    }

    /**
     * 消费Avatar服务采集信息
     * @param msgBody 消费消息体
     * @return {@link ConsumeStatus}
     */
    @MdpMafkaMsgReceive
    @Override
    public ConsumeStatus consume(String msgBody) {
        Transaction t = Cat.newTransaction("Mafka.Appkey.Consumer", "AvatarCollectAppkeyConsumer");
        AppkeyCollectEvent appkeyCollectEvent;
        try {
            appkeyCollectEvent = JsonUtil.json2Bean(msgBody, AppkeyCollectEvent.class);
            appkeyCollectEventScheduler.collect(appkeyCollectEvent);
        } catch (Exception e) {
            log.error("appkeyListener服务变更消息消费失败", e);
            Cat.logError("appkeyListener服务变更消息消费失败", e);
            t.setStatus(e);
        } finally {
            t.complete();
        }
        return ConsumeStatus.CONSUME_SUCCESS;
    }
}
