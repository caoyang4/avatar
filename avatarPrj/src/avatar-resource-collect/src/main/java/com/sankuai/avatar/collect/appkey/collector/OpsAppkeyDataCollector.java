package com.sankuai.avatar.collect.appkey.collector;

import com.sankuai.avatar.collect.appkey.annotion.OnFire;
import com.sankuai.avatar.collect.appkey.collector.source.OpsAppkeySource;
import com.sankuai.avatar.collect.appkey.event.AppkeyCollectEventResult;
import com.sankuai.avatar.collect.appkey.event.CollectEvent;
import com.sankuai.avatar.collect.appkey.event.CollectEventName;
import com.sankuai.avatar.common.annotation.RaptorTransaction;
import com.sankuai.avatar.dao.cache.CacheClient;
import com.sankuai.avatar.resource.appkey.AppkeyResource;
import com.sankuai.avatar.resource.application.ApplicationResource;
import com.sankuai.avatar.resource.tree.AppkeyTreeResource;
import com.sankuai.avatar.resource.tree.bo.AppkeyTreeBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * ops appkey 采集端
 * @author Jie.li.sh
 * @create 2022-11-17
 **/
@OnFire({CollectEventName.APPKEY_REFRESH, CollectEventName.OPS_APPKEY_UPDATE})
@Service
@Slf4j
public class OpsAppkeyDataCollector extends AbstractCollector {

    private final AppkeyTreeResource appkeyTreeResource;

    public OpsAppkeyDataCollector(AppkeyResource appkeyResource, AppkeyTreeResource appkeyTreeResource, ApplicationResource applicationResource, CacheClient cacheClient) {
        super(appkeyResource, applicationResource, cacheClient);
        this.appkeyTreeResource = appkeyTreeResource;
    }

    /**
     * 触发OPS数据采集
     *
     * @param collectEventData 采集事件
     * @return {@link AppkeyCollectEventResult}
     */
    @Override
    @RaptorTransaction(type = "Appkey.Collect", name = "OpsAppkeyDataCollector")
    public AppkeyCollectEventResult collect(CollectEvent collectEventData) {
        String appkey = collectEventData.getCollectEventData().getAppkey();
        OpsAppkeySource opsAppkeySource = getAppkeyByOps(appkey);
        // 补充服务树节点数据
        AppkeyTreeBO appkeyTreeBO = appkeyTreeResource.getAppkeyTreeByKey(opsAppkeySource.getSrv());
        opsAppkeySource.setBusinessGroup(appkeyTreeBO.getOwt().getBusinessGroup());
        opsAppkeySource.setOwtName(appkeyTreeBO.getOwt().getName());
        opsAppkeySource.setPdlName(appkeyTreeBO.getPdl().getName());
        return AppkeyCollectEventResult.ofSource(opsAppkeySource);
    }
}
