package com.sankuai.avatar.collect.appkey.collector;

import com.sankuai.avatar.collect.appkey.annotion.OnFire;
import com.sankuai.avatar.collect.appkey.collector.source.ScAppkeySource;
import com.sankuai.avatar.collect.appkey.event.AppkeyCollectEventResult;
import com.sankuai.avatar.collect.appkey.event.CollectEvent;
import com.sankuai.avatar.collect.appkey.event.CollectEventName;
import com.sankuai.avatar.common.annotation.RaptorTransaction;
import com.sankuai.avatar.resource.appkey.AppkeyResource;
import com.sankuai.avatar.resource.application.ApplicationResource;
import com.sankuai.avatar.dao.cache.CacheClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * sc appkey 采集端
 * @author Jie.li.sh
 * @create 2022-11-17
 **/
@OnFire({CollectEventName.APPKEY_REFRESH})
@Service
@Slf4j
public class ScAppkeyDataCollector extends AbstractCollector {

    public ScAppkeyDataCollector(AppkeyResource appkeyResource, ApplicationResource applicationResource, CacheClient cacheClient) {
        super(appkeyResource, applicationResource, cacheClient);
    }

    /**
     * 触发SC服务采集
     *
     * @param collectEventData 采集事件
     * @return {@link AppkeyCollectEventResult}
     */
    @Override
    @RaptorTransaction(type = "Appkey.Collect", name = "ScAppkeyDataCollector")
    public AppkeyCollectEventResult collect(CollectEvent collectEventData) {
        String appkey = collectEventData.getCollectEventData().getAppkey();
        ScAppkeySource scAppkeySource = getAppkeyBySc(appkey);
        scAppkeySource.setPaas(isPaasAppkey(scAppkeySource.getApplicationName()));

        return AppkeyCollectEventResult.ofSource(scAppkeySource);
    }
}
