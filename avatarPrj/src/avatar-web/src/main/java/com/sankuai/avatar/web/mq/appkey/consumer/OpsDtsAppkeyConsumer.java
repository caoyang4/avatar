package com.sankuai.avatar.web.mq.appkey.consumer;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import com.meituan.dbus.MQ.MQUtils;
import com.meituan.dbus.common.DbusUtils;
import com.meituan.dbus.common.StaticUtils;
import com.meituan.mafka.client.consumer.ConsumeStatus;
import com.meituan.mdp.boot.starter.mafka.consumer.anno.MdpMafkaMsgReceive;
import com.sankuai.avatar.collect.appkey.event.AppkeyCollectEvent;
import com.sankuai.avatar.collect.appkey.event.AppkeyCollectEventData;
import com.sankuai.avatar.collect.appkey.event.CollectEventName;
import com.sankuai.avatar.collect.appkey.model.Appkey;
import com.sankuai.avatar.collect.appkey.scheduer.AppkeyCollectEventScheduler;
import com.sankuai.avatar.common.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * DTS订阅监听OPS服务信息变更
 * <a href="https://km.sankuai.com/page/391844612">DTS订阅通过Mafka方式接入</a>
 * @author qinwei05
 * @date 2022/11/3 17:12
 */
@Service("opsDtsListener")
@Slf4j
public class OpsDtsAppkeyConsumer implements Consumer {

    private static final String APPKEY_NAME = "appkey";

    private final AppkeyCollectEventScheduler appkeyCollectEventScheduler;

    @Autowired
    public OpsDtsAppkeyConsumer(AppkeyCollectEventScheduler appkeyCollectEventScheduler) {
        this.appkeyCollectEventScheduler = appkeyCollectEventScheduler;
    }

    /**
     * 消费
     * 1）、delete的变更前Map有值，变更后为空
     * 2）、insert的变更前Map为空，变更后有值
     * <a href="https://km.sankuai.com/page/392339055">DbusUtils对象获取相应值</a>
     * @param msgBody 消费消息体
     * @return {@link ConsumeStatus}
     */
    @MdpMafkaMsgReceive
    @Override
    public ConsumeStatus consume(String msgBody) {
        Transaction t = Cat.newTransaction("Mafka.Appkey.Consumer", "OpsDtsAppkeyConsumer");
        try {
            // 获取变更前数据，可通过DbusUtils类实现
            DbusUtils dbusUtils = MQUtils.newInstanceForMQ(msgBody);
            StaticUtils.EventType eventType = dbusUtils.getEventType();
            List<StaticUtils.EventType> eventTypes = Arrays.asList(StaticUtils.EventType.insert, StaticUtils.EventType.delete, StaticUtils.EventType.update);
            if (!eventTypes.contains(eventType)){
                return ConsumeStatus.CONSUME_SUCCESS;
            }
            // 容灾等级更新兼容,无需触发更新,每日定时任务同步一次即可
            List<String> ignoreCols = dbusUtils.getDiffCols().stream()
                    .filter(col -> !Arrays.asList("capacity_update_by", "capacity_update_at", "capacity_reason", "capacity").contains(col))
                    .collect(Collectors.toList());
            if (CollectionUtils.isEmpty(ignoreCols)) {
                log.info("DTS订阅OPS消息队列监听: Appkey信息忽略更新, 已忽略列：{}", dbusUtils.getDiffCols());
                return ConsumeStatus.CONSUME_SUCCESS;
            }
            // after变更之后的数据/pre变更前的数据
            Map<String, Object> after = dbusUtils.getAftMap();
            Map<String, Object> pre = dbusUtils.getPreMap();

            String appkeyName = getRealAppkeyByMsgBody(after, pre);
            if (StringUtils.isBlank(appkeyName)){
                return ConsumeStatus.CONSUME_SUCCESS;
            }
            log.info("DTS订阅OPS消息队列监听: Appkey [{}] 信息发生 [{}] 变更, 详细信息为 {}", appkeyName, eventType, dbusUtils);

            AppkeyCollectEvent appkeyCollectEvent = AppkeyCollectEvent.builder()
                    .collectEventData(AppkeyCollectEventData.of(appkeyName, after))
                    .collectEventName(CollectEventName.APPKEY_REFRESH).build();
            // 服务下线标记
            if (StaticUtils.EventType.delete.equals(eventType)){
                appkeyCollectEvent.setCollectEventName(CollectEventName.APPKEY_DELETE);
            }
            Appkey appkey = appkeyCollectEventScheduler.collect(appkeyCollectEvent);
            log.info("DTS订阅OPS消息队列监听: Appkey信息变更为：{}", appkey.toString());
        } catch (Exception e) {
            log.error("OpsDtsAppkeyConsumer Appkey负责人变更消息采集失败", e);
            Cat.logError("OpsDtsAppkeyConsumer Appkey负责人采集失败", e);
            t.setStatus(e);
        } finally {
            t.complete();
        }
        return ConsumeStatus.CONSUME_SUCCESS;
    }

    /**
     * 获取真实服务名
     * 新增：先insert数据，此时无Appkey，但是接着会update数据，添加Appkey值
     * 删除：先update数据，删除了Appkey的字段值，然后在触发Delete操作，此时消息体中无Appkey数据
     *
     * @param after 变更后信息
     * @param pre   变更前信息
     * @return {@link String}
     */
    private String getRealAppkeyByMsgBody(Map<String, Object> after, Map<String, Object> pre){
        String afterAppkey = ObjectUtils.objectNull2Empty(after.get(APPKEY_NAME), "");
        String preAppkey = ObjectUtils.objectNull2Empty(pre.get(APPKEY_NAME), "");
        return StringUtils.isNotBlank(afterAppkey) ? afterAppkey : preAppkey;
    }
}
