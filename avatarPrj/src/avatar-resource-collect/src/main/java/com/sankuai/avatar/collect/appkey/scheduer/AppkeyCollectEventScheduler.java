package com.sankuai.avatar.collect.appkey.scheduer;

import com.meituan.mafka.client.producer.ProducerResult;
import com.sankuai.avatar.client.ops.OpsHttpClient;
import com.sankuai.avatar.client.ops.model.OpsSrv;
import com.sankuai.avatar.client.soa.ScHttpClient;
import com.sankuai.avatar.client.soa.model.ScAppkey;
import com.sankuai.avatar.collect.appkey.collector.Collector;
import com.sankuai.avatar.collect.appkey.collector.source.AppkeySource;
import com.sankuai.avatar.collect.appkey.collector.source.Source;
import com.sankuai.avatar.collect.appkey.event.AppkeyCollectEvent;
import com.sankuai.avatar.collect.appkey.event.AppkeyCollectEventData;
import com.sankuai.avatar.collect.appkey.event.CollectEventName;
import com.sankuai.avatar.collect.appkey.event.CollectEventResult;
import com.sankuai.avatar.collect.appkey.model.Appkey;
import com.sankuai.avatar.collect.appkey.process.Processor;
import com.sankuai.avatar.collect.appkey.producer.AppkeyChangeProducer;
import com.sankuai.avatar.common.exception.CollectErrorException;
import com.sankuai.avatar.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * appkey的事件监听与采集处理
 * 1. 主动采集单个
 * 2. 主动采集全部
 * 3. 被动接受消息触发,不采集,以接受到的信息做处理
 * @author Jie.li.sh
 * @create 2022-11-17
 **/
@Slf4j
@Service
public class AppkeyCollectEventScheduler extends AbstractCollectEventScheduler {

    private final List<Processor> processors;

    private final OpsHttpClient opsHttpClient;

    private final ScHttpClient scHttpClient;

    private final AppkeyChangeProducer appkeyChangeProducer;

    @Autowired
    public AppkeyCollectEventScheduler(List<Processor> processors,
                                       OpsHttpClient opsHttpClient,
                                       ScHttpClient scHttpClient,
                                       AppkeyChangeProducer appkeyChangeProducer,
                                       List<Collector> allRegisterCollectorList) {
        super(allRegisterCollectorList);
        this.processors = processors;
        this.opsHttpClient = opsHttpClient;
        this.scHttpClient = scHttpClient;
        this.appkeyChangeProducer = appkeyChangeProducer;
    }

    public void fullAppkeyCollect(){
        fullBackEndAppkeyCollect();
        fullNotBackEndAppkeyCollect();
    }

    /**
     * 后端类型appkey收集
     */
    public void fullBackEndAppkeyCollect(){
        // 从OPS获取全量后端类型Appkey信息 -> 采集信息并存储
        List<OpsSrv> opsSrvs = opsHttpClient.getAllAppkeyInfo();
        List<String> opsAppkeys = opsSrvs.stream()
                .distinct()
                .map(OpsSrv::getAppkey)
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.toList());
        for (String opsAppkey: opsAppkeys){
            pushToMafkaProducer(opsAppkey, CollectEventName.APPKEY_REFRESH);
        }
        log.info("opsAppkeys size: " + opsAppkeys.size());
    }

    /**
     * 非后端类型appkey收集
     */
    public void fullNotBackEndAppkeyCollect(){
        int page = 1;
        int pageSize = 100;
        List<String> curPageAppkeys = scHttpClient.getAllAppkeysByPage(page, pageSize);
        while (CollectionUtils.isNotEmpty(curPageAppkeys)){
            List<ScAppkey> appkeysInfo = scHttpClient.getAppkeysInfo(curPageAppkeys);
            List<String> scNotBackendAppkeys = appkeysInfo.stream()
                    .distinct()
                    .filter(i -> !"BACKEND".equals(i.getType()))
                    .map(ScAppkey::getAppKey)
                    .filter(StringUtils::isNotBlank)
                    .collect(Collectors.toList());
            for (String scNotBackendAppkey: scNotBackendAppkeys){
                pushToMafkaProducer(scNotBackendAppkey, CollectEventName.SC_NOT_BACKEND_APPKEY_UPDATE);
            }
            page += 1;
            curPageAppkeys = scHttpClient.getAllAppkeysByPage(page, pageSize);
        }
    }

    /**
     * 发送采集消息至Mafka消息队列：以Appkey名称采集
     *
     * @param appkey           appkey
     * @param collectEventName 收集事件名
     * @return {@link Appkey}
     */
    public ProducerResult pushToMafkaProducer(String appkey, CollectEventName collectEventName) {
        // 初始化采集事件
        AppkeyCollectEvent appkeyCollectEvent = AppkeyCollectEvent.builder()
                .collectEventData(AppkeyCollectEventData.of(appkey))
                .collectEventName(collectEventName)
                .build();
        String appkeyCollectEventString = JsonUtil.bean2Json(appkeyCollectEvent);
        return appkeyChangeProducer.appkeyChange(appkeyCollectEventString);
    }

    /**
     * 采集:
     * 1.支持携带初始数据：不再主动采集，以初始数据更新
     * 2.支持指定采集器
     *
     * @param appkeyCollectEvent appkey收集事件
     * @return {@link Appkey}
     */
    public Appkey collect(AppkeyCollectEvent appkeyCollectEvent) {
        String appkey = appkeyCollectEvent.getCollectEventData().getAppkey();
        if (StringUtils.isBlank(appkey)){
            throw new CollectErrorException("服务资源采集异常：未传入Appkey信息！");
        }
        // 1、采集数据
        List<CollectEventResult> collectEventResultList = fireEvent(appkeyCollectEvent);
        // 2、数据格式化处理
        Appkey appkeyResourceData = transToAppkeyResourceData(appkey, collectEventResultList);
        // 3、数据持久化存储(DB更新失败时,不更新ES数据)
        save(appkeyResourceData);
        return appkeyResourceData;
    }

    private Appkey transToAppkeyResourceData(String appkey, List<CollectEventResult> collectEventResultList){
        Appkey appkeyResourceData = new Appkey(appkey);
        for (CollectEventResult collectEventResult : collectEventResultList) {
            for (Source source : collectEventResult.getSourceList()) {
                if (!(source instanceof AppkeySource)) {
                    continue;
                }
                appkeyResourceData = ((AppkeySource) source).transToAppkey(appkeyResourceData);
            }
        }
        return appkeyResourceData;
    }

    private void save(Appkey appkeyResourceData){
        // 二个数据源数据需要保持一致
        for (Processor processor: processors){
            Boolean result = processor.process(appkeyResourceData);
            if (Boolean.FALSE.equals(result)){
                break;
            }
        }
    }
}
