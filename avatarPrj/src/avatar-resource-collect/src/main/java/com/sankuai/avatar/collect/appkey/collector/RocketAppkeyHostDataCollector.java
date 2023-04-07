package com.sankuai.avatar.collect.appkey.collector;

import com.sankuai.avatar.client.rocket.RocketHttpClient;
import com.sankuai.avatar.client.rocket.model.HostQueryRequest;
import com.sankuai.avatar.client.rocket.model.RocketHost;
import com.sankuai.avatar.client.rocket.response.RocketHostResponseData;
import com.sankuai.avatar.collect.appkey.annotion.OnFire;
import com.sankuai.avatar.collect.appkey.collector.source.RocketAppkeyHostSource;
import com.sankuai.avatar.collect.appkey.event.AppkeyCollectEventResult;
import com.sankuai.avatar.collect.appkey.event.CollectEvent;
import com.sankuai.avatar.collect.appkey.event.CollectEventName;
import com.sankuai.avatar.common.annotation.RaptorTransaction;
import com.sankuai.avatar.resource.appkey.AppkeyResource;
import com.sankuai.avatar.resource.application.ApplicationResource;
import com.sankuai.avatar.dao.cache.CacheClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * appkey 机器信息
 *
 * @author qinwei05
 * @date 2022/11/30
 */
@OnFire({CollectEventName.APPKEY_REFRESH, CollectEventName.ROCKET_HOST_UPDATE})
@Service
@Slf4j
public class RocketAppkeyHostDataCollector extends AbstractCollector {

    private final RocketHttpClient rocketHttpClient;

    @Autowired
    public RocketAppkeyHostDataCollector(RocketHttpClient rocketHttpClient,
                                         AppkeyResource appkeyResource,
                                         ApplicationResource applicationResource,
                                         CacheClient cacheClient) {
        super(appkeyResource, applicationResource, cacheClient);
        this.rocketHttpClient = rocketHttpClient;
    }

    /**
     * 触发采集
     *
     * @param collectEventData 采集事件
     * @return {@link AppkeyCollectEventResult}
     */
    @Override
    @RaptorTransaction(type = "Appkey.Collect", name = "RocketAppkeyHostDataCollector")
    public AppkeyCollectEventResult collect(CollectEvent collectEventData) {
        String appkey = collectEventData.getCollectEventData().getAppkey();
        RocketAppkeyHostSource rocketAppkeyHostSource = new RocketAppkeyHostSource(appkey);

        // 服务机器总数目
        RocketHostResponseData<RocketHost> rocketHostResponseData = rocketHttpClient.getAppkeyHosts(appkey);
        rocketAppkeyHostSource.setHostCount(!rocketHostResponseData.getData().isEmpty() ? rocketHostResponseData.getTotal() : 0);

        // 服务线上机器总数目
        HostQueryRequest hostQueryRequest = HostQueryRequest.builder().appkey(appkey).env("prod").build();
        RocketHostResponseData<RocketHost> rocketProdHostResponseData = rocketHttpClient.getAppkeyHostsByQueryRequest(hostQueryRequest);
        rocketAppkeyHostSource.setProdHostCount(!rocketProdHostResponseData.getData().isEmpty() ? rocketProdHostResponseData.getTotal() : 0);

        return AppkeyCollectEventResult.ofSource(rocketAppkeyHostSource);
    }
}
