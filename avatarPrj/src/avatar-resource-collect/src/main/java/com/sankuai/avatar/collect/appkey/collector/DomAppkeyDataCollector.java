package com.sankuai.avatar.collect.appkey.collector;

import com.sankuai.avatar.collect.appkey.annotion.OnFire;
import com.sankuai.avatar.collect.appkey.collector.source.DomAppkeySource;
import com.sankuai.avatar.collect.appkey.collector.transfer.CollectorDataTransfer;
import com.sankuai.avatar.collect.appkey.event.AppkeyCollectEventResult;
import com.sankuai.avatar.collect.appkey.event.CollectEvent;
import com.sankuai.avatar.collect.appkey.event.CollectEventName;
import com.sankuai.avatar.common.annotation.RaptorTransaction;
import com.sankuai.avatar.resource.appkey.AppkeyResource;
import com.sankuai.avatar.resource.appkey.bo.AppkeyResourceUtilBO;
import com.sankuai.avatar.resource.application.ApplicationResource;
import com.sankuai.avatar.dao.cache.CacheClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;

/**
 * dom appkey 资源利用率采集端
 * @author Jie.li.sh
 * @create 2022-11-17
 **/
@OnFire({CollectEventName.APPKEY_REFRESH, CollectEventName.DOM_UTIL_UPDATE})
@Service
@Slf4j
public class DomAppkeyDataCollector extends AbstractCollector {

    private final AppkeyResource appkeyResource;

    public DomAppkeyDataCollector(AppkeyResource appkeyResource, ApplicationResource applicationResource, CacheClient cacheClient) {
        super(appkeyResource, applicationResource, cacheClient);
        this.appkeyResource = appkeyResource;
    }

    /**
     * 触发DOM资源利用率信息采集
     *
     * @param collectEventData 采集事件
     * @return {@link AppkeyCollectEventResult}
     */
    @Override
    @RaptorTransaction(type = "Appkey.Collect", name = "DomAppkeyDataCollector")
    public AppkeyCollectEventResult collect(CollectEvent collectEventData) {
        String appkey = collectEventData.getCollectEventData().getAppkey();
        NumberFormat fmt = NumberFormat.getPercentInstance();
        fmt.setMaximumFractionDigits(3);

        AppkeyResourceUtilBO appkeyResourceUtilBO = appkeyResource.getAppkeyResourceUtil(appkey);
        DomAppkeySource domAppkeySource = CollectorDataTransfer.INSTANCE.toSource(appkeyResourceUtilBO);
        domAppkeySource.setWeekResourceUtil(fmt.format(appkeyResourceUtilBO.getResourceUtil().getLastWeekValue()));
        return AppkeyCollectEventResult.ofSource(domAppkeySource);
    }
}
