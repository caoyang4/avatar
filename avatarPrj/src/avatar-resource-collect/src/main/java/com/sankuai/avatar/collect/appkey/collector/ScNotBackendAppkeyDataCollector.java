package com.sankuai.avatar.collect.appkey.collector;

import com.sankuai.avatar.collect.appkey.annotion.OnFire;
import com.sankuai.avatar.collect.appkey.collector.source.ScNotBackendAppkeySource;
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
 * sc 非后端类型appkey 数据采集端
 *
 * @author qinwei05
 * @date 2022/12/14
 */
@OnFire({CollectEventName.SC_NOT_BACKEND_APPKEY_UPDATE})
@Service
@Slf4j
public class ScNotBackendAppkeyDataCollector extends AbstractCollector {

    public ScNotBackendAppkeyDataCollector(AppkeyResource appkeyResource, ApplicationResource applicationResource, CacheClient cacheClient) {
        super(appkeyResource, applicationResource, cacheClient);
    }

    /**
     * 触发采集
     *
     * @param collectEventData 采集事件
     * @return {@link AppkeyCollectEventResult}
     */
    @Override
    @RaptorTransaction(type = "Appkey.Collect", name = "ScNotBackendAppkeyDataCollector")
    public AppkeyCollectEventResult collect(CollectEvent collectEventData) {
        String appkey = collectEventData.getCollectEventData().getAppkey();
        ScNotBackendAppkeySource scNotBackendAppkeySource = getNotBackendAppkeyBySc(appkey);
        scNotBackendAppkeySource.setPaas(isPaasAppkey(scNotBackendAppkeySource.getApplicationName()));

        return AppkeyCollectEventResult.ofSource(scNotBackendAppkeySource);
    }
}
