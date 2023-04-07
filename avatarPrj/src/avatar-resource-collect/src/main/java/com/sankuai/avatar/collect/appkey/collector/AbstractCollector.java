package com.sankuai.avatar.collect.appkey.collector;

import com.dianping.cat.Cat;
import com.sankuai.avatar.collect.appkey.annotion.OnFire;
import com.sankuai.avatar.collect.appkey.collector.source.OpsAppkeySource;
import com.sankuai.avatar.collect.appkey.collector.source.ScAppkeySource;
import com.sankuai.avatar.collect.appkey.collector.source.ScNotBackendAppkeySource;
import com.sankuai.avatar.collect.appkey.collector.transfer.CollectorDataTransfer;
import com.sankuai.avatar.collect.appkey.event.AppkeyCollectEventResult;
import com.sankuai.avatar.collect.appkey.event.CollectEvent;
import com.sankuai.avatar.collect.appkey.event.CollectEventName;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;
import com.sankuai.avatar.resource.appkey.AppkeyResource;
import com.sankuai.avatar.resource.appkey.bo.OpsSrvBO;
import com.sankuai.avatar.resource.appkey.bo.ScAppkeyBO;
import com.sankuai.avatar.resource.application.ApplicationResource;
import com.sankuai.avatar.dao.cache.CacheClient;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Jie.li.sh
 * @create 2022-11-17
 **/
public abstract class AbstractCollector implements Collector {

    private final AppkeyResource appkeyResource;

    private final ApplicationResource applicationResource;

    private final CacheClient cacheClient;

    @Autowired
    protected AbstractCollector(AppkeyResource appkeyResource,
                                ApplicationResource applicationResource,
                                CacheClient cacheClient) {
        this.appkeyResource = appkeyResource;
        this.applicationResource = applicationResource;
        this.cacheClient = cacheClient;
    }

    private static final String CATEGORY = "avatar-web";

    /**
     * 采集
     *
     * @param collectEventData 收集事件数据
     * @return {@link AppkeyCollectEventResult}
     */
    public abstract AppkeyCollectEventResult collect(CollectEvent collectEventData);

    public AppkeyCollectEventResult run(CollectEvent collectEventData) {
        AppkeyCollectEventResult collectEventResult = null;
        try {
            collectEventResult = collect(collectEventData);
        } catch (Exception e) {
            Cat.logError(e);
        }
        return collectEventResult;
    }

    /**
     * 是否采集
     *
     * @param collectEventName 事件名称
     * @return true/false
     */
    @Override
    public boolean onFire(CollectEventName collectEventName) {
        OnFire onFire = this.getClass().getAnnotation(OnFire.class);
        if (onFire == null)  {
            return false;
        }
        return Arrays.asList(onFire.value()).contains(collectEventName);
    }

    @SuppressWarnings("unchecked")
    private List<String> getPaasApplications() throws SdkCallException, SdkBusinessErrorException {
        try {
            List<String> cachedApplications = cacheClient.get(CATEGORY, "PaasApplications", List.class);
            if (CollectionUtils.isNotEmpty(cachedApplications)){
                return cachedApplications;
            }
            List<String> paasApplications = applicationResource.getPaasApplications();
            cacheClient.set(CATEGORY, "PaasApplications", paasApplications, 60 * 60);
            return paasApplications;
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public Boolean isPaasAppkey(String applicationName) throws SdkCallException, SdkBusinessErrorException{
        if (StringUtils.isBlank(applicationName)){
            return Boolean.FALSE;
        }
        List<String> paasApplications = getPaasApplications();
        return paasApplications.contains(applicationName);
    }

    public OpsAppkeySource getAppkeyByOps(String appkey) throws SdkCallException, SdkBusinessErrorException {
        OpsSrvBO opsSrvBO = appkeyResource.getAppkeyByOps(appkey);
        return CollectorDataTransfer.INSTANCE.toSource(opsSrvBO);
    }

    public ScAppkeySource getAppkeyBySc(String appkey) throws SdkCallException, SdkBusinessErrorException {
        ScAppkeyBO scAppkeyBO = appkeyResource.getAppkeyBySc(appkey);
        return CollectorDataTransfer.INSTANCE.toSource(scAppkeyBO);
    }

    public ScNotBackendAppkeySource getNotBackendAppkeyBySc(String appkey) throws SdkCallException, SdkBusinessErrorException {
        ScAppkeyBO scAppkeyBO = appkeyResource.getAppkeyBySc(appkey);
        return CollectorDataTransfer.INSTANCE.toScNotBackendAppkeySource(scAppkeyBO);
    }
}
