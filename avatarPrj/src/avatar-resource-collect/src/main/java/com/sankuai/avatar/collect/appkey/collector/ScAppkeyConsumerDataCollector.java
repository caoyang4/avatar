package com.sankuai.avatar.collect.appkey.collector;

import com.sankuai.avatar.collect.appkey.annotion.OnFire;
import com.sankuai.avatar.collect.appkey.collector.source.ScAppkeySource;
import com.sankuai.avatar.collect.appkey.collector.transfer.CollectorConsumerDataTransfer;
import com.sankuai.avatar.collect.appkey.collector.transfer.CollectorDataTransfer;
import com.sankuai.avatar.collect.appkey.consumer.model.ScAppkeyConsumerData;
import com.sankuai.avatar.collect.appkey.event.AppkeyCollectEventResult;
import com.sankuai.avatar.collect.appkey.event.CollectEvent;
import com.sankuai.avatar.collect.appkey.event.CollectEventName;
import com.sankuai.avatar.collect.appkey.event.consumer.ScAppkeyConsumerEventData;
import com.sankuai.avatar.common.annotation.RaptorTransaction;
import com.sankuai.avatar.resource.appkey.AppkeyResource;
import com.sankuai.avatar.resource.application.ApplicationResource;
import com.sankuai.avatar.dao.cache.CacheClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * sc appkey 消费者数据接收端
 * @author qinwei05
 * @create 2022-11-17
 **/
@OnFire({CollectEventName.SC_APPKEY_UPDATE})
@Service
@Slf4j
public class ScAppkeyConsumerDataCollector extends AbstractCollector {

    public ScAppkeyConsumerDataCollector(AppkeyResource appkeyResource, ApplicationResource applicationResource, CacheClient cacheClient) {
        super(appkeyResource, applicationResource, cacheClient);
    }

    /**
     * 原地更新采集信息
     *
     * @param collectEventData 采集事件
     * @return {@link AppkeyCollectEventResult}
     */
    @Override
    @RaptorTransaction(type = "Appkey.Collect", name = "ScAppkeyConsumerDataCollector")
    public AppkeyCollectEventResult collect(CollectEvent collectEventData) {
        ScAppkeyConsumerEventData scAppkeyConsumerEventData = CollectorConsumerDataTransfer.INSTANCE.toConsumerData(
                (ScAppkeyConsumerData) collectEventData.getCollectEventData().getEventData());

        // 直接使用消息队列中数据,并从接口补充其余数据
        ScAppkeySource scAppkeySource = CollectorDataTransfer.INSTANCE.toSource(scAppkeyConsumerEventData);
        scAppkeySource.setPaas(isPaasAppkey(scAppkeySource.getApplicationName()));

        ScAppkeySource scAppkeyNewSource = getAppkeyBySc(collectEventData.getCollectEventData().getAppkey());
        scAppkeySource.setTenant(scAppkeyNewSource.getTenant());
        scAppkeySource.setIsBoughtExternal(scAppkeyNewSource.getIsBoughtExternal());
        scAppkeySource.setFrameworks(scAppkeyNewSource.getFrameworks());

        return AppkeyCollectEventResult.ofSource(scAppkeySource);

    }
}
