package com.sankuai.avatar.collect.appkey.collector;

import com.sankuai.avatar.client.http.core.HttpStatusEnum;
import com.sankuai.avatar.collect.appkey.annotion.OnFire;
import com.sankuai.avatar.collect.appkey.collector.source.OfflineAppkeySource;
import com.sankuai.avatar.collect.appkey.event.AppkeyCollectEventResult;
import com.sankuai.avatar.collect.appkey.event.AppkeyStatusEnum;
import com.sankuai.avatar.collect.appkey.event.CollectEvent;
import com.sankuai.avatar.collect.appkey.event.CollectEventName;
import com.sankuai.avatar.common.annotation.RaptorTransaction;
import com.sankuai.avatar.common.exception.ResourceNotFoundErrorException;
import com.sankuai.avatar.resource.appkey.AppkeyResource;
import com.sankuai.avatar.resource.appkey.bo.ScAppkeyBO;
import com.sankuai.avatar.resource.application.ApplicationResource;
import com.sankuai.avatar.dao.cache.CacheClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/**
 * appkey 下线事件采集端
 * @author qinwei05
 * @create 2022-12-17
 **/
@OnFire({CollectEventName.APPKEY_DELETE, CollectEventName.APPKEY_REFRESH, CollectEventName.SC_NOT_BACKEND_APPKEY_UPDATE})
@Service
@Slf4j
public class OffLineAppkeyDataCollector extends AbstractCollector {

    private final AppkeyResource appkeyResource;

    public OffLineAppkeyDataCollector(AppkeyResource appkeyResource, ApplicationResource applicationResource, CacheClient cacheClient) {
        super(appkeyResource, applicationResource, cacheClient);
        this.appkeyResource = appkeyResource;
    }

    /**
     * 触发服务是否存在信息判定采集
     *
     * @param collectEventData 采集事件
     * @return {@link AppkeyCollectEventResult}
     */
    @Override
    @RaptorTransaction(type = "Appkey.Collect", name = "OffLineAppkeyDataCollector")
    public AppkeyCollectEventResult collect(CollectEvent collectEventData) {
        String appkey = collectEventData.getCollectEventData().getAppkey();
        OfflineAppkeySource offlineAppkeySource = new OfflineAppkeySource();
        if (collectEventData.getCollectEventName() == CollectEventName.APPKEY_DELETE) {
            offlineAppkeySource.setAppkey(appkey);
            offlineAppkeySource.setIsOffline(Boolean.TRUE);
            offlineAppkeySource.setOfflineTime(new Date());
            return AppkeyCollectEventResult.ofSource(offlineAppkeySource);
        }
        // 主动判断服务是否存在
        OfflineAppkeySource collectOfflineAppkeySource = getOfflineAppkeySource(appkey);
        if (collectOfflineAppkeySource == null){
            return null;
        }
        return AppkeyCollectEventResult.ofSource(collectOfflineAppkeySource);
    }

    private OfflineAppkeySource getOfflineAppkeySource(String appkey){
        OfflineAppkeySource offlineAppkeySource = new OfflineAppkeySource();
        offlineAppkeySource.setAppkey(appkey);
        AppkeyStatusEnum appkeyStatusEnum = getAppkeyStatusEnum(appkey);
        if (AppkeyStatusEnum.ONLINE.equals(appkeyStatusEnum)){
            offlineAppkeySource.setIsOffline(Boolean.FALSE);
            offlineAppkeySource.setOfflineTime(null);
        } else if (AppkeyStatusEnum.OFFLINE.equals(appkeyStatusEnum)){
            offlineAppkeySource.setIsOffline(Boolean.TRUE);
            offlineAppkeySource.setOfflineTime(new Date());
        } else {
            return null;
        }
        return offlineAppkeySource;
    }

    private AppkeyStatusEnum getAppkeyStatusEnum(String appkey) {
        try {
            String srv = appkeyResource.getSrvKeyByAppkey(appkey);
            if (StringUtils.isNotBlank(srv)) {
                return AppkeyStatusEnum.ONLINE;
            } else {
                ScAppkeyBO scAppkeyBO = appkeyResource.getAppkeyBySc(appkey);
                if (scAppkeyBO != null && Objects.equals(scAppkeyBO.getAppKey(), appkey)) {
                    return AppkeyStatusEnum.ONLINE;
                }
            }
        } catch (ResourceNotFoundErrorException e){
            if (e.getCode() == HttpStatusEnum.NOT_FOUND.code()){
                return AppkeyStatusEnum.OFFLINE;
            }
        } catch (Exception e) {
            return AppkeyStatusEnum.UNKNOWN;
        }
        return AppkeyStatusEnum.UNKNOWN;
    }
}
