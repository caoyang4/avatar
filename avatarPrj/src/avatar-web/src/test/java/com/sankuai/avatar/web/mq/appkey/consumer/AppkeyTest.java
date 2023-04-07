package com.sankuai.avatar.web.mq.appkey.consumer;

import com.sankuai.avatar.TestBase;
import com.sankuai.avatar.collect.appkey.event.AppkeyCollectEvent;
import com.sankuai.avatar.collect.appkey.event.AppkeyCollectEventData;
import com.sankuai.avatar.collect.appkey.event.CollectEventName;
import com.sankuai.avatar.collect.appkey.model.Appkey;
import com.sankuai.avatar.collect.appkey.scheduer.AppkeyCollectEventScheduler;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * sc appkey消费者测试
 *
 * @author qinwei05
 * @date 2022/11/04
 */
@Slf4j
public class AppkeyTest extends TestBase {

    @Autowired
    AppkeyCollectEventScheduler appkeyCollectEventScheduler;

    /**
     * 测试appkey只存在于OPS系统
     */
    @Test
    public void testAppkeyOnlyInOpsRealCollect() {
        String appkeyName = "com.sankuai.hotel.goods.rssearchshanghai";
        AppkeyCollectEvent appkeyCollectEvent = AppkeyCollectEvent.builder()
                .collectEventData(AppkeyCollectEventData.of(appkeyName))
                .collectEventName(CollectEventName.APPKEY_REFRESH)
                .build();
        Appkey appkey = appkeyCollectEventScheduler.collect(appkeyCollectEvent);
        log.info(appkey.toString());
        assertThat(appkey).isNotNull();
    }

    /**
     * 测试appkey只存在于SC系统
     */
    @Test
    public void testFrontAppkeyRealCollect() {
        // com.sankuai.dx.dx.channel
        String appkeyName = "com.sankuai.dx.dx.channel";
        AppkeyCollectEvent appkeyCollectEvent = AppkeyCollectEvent.builder()
                .collectEventData(AppkeyCollectEventData.of(appkeyName))
                .collectEventName(CollectEventName.SC_NOT_BACKEND_APPKEY_UPDATE)
                .build();
        Appkey appkey = appkeyCollectEventScheduler.collect(appkeyCollectEvent);
        log.info(appkey.toString());
        assertThat(appkey).isNotNull();
    }

    /**
     * 测试appkey存在于OPS+SC系统
     */
    @Test
    public void testAppkeyRealCollect() {
        String appkeyName = "com.sankuai.avatar.develop";
        AppkeyCollectEvent appkeyCollectEvent = AppkeyCollectEvent.builder()
                .collectEventData(AppkeyCollectEventData.of(appkeyName))
                .collectEventName(CollectEventName.APPKEY_REFRESH)
                .build();
        Appkey appkey = appkeyCollectEventScheduler.collect(appkeyCollectEvent);
        log.info(appkey.toString());
        assertThat(appkey).isNotNull();
    }

    /**
     * 测试appkey存在于OPS+SC系统
     */
    @Test
    public void testAppkeyOnlyOpsCollect() {
        String appkeyName = "com.sankuai.avatar.cscscscs";
        AppkeyCollectEvent appkeyCollectEvent = AppkeyCollectEvent.builder()
                .collectEventData(AppkeyCollectEventData.of(appkeyName))
                .collectEventName(CollectEventName.OPS_APPKEY_UPDATE)
                .build();
        Appkey appkey = appkeyCollectEventScheduler.collect(appkeyCollectEvent);
        log.info(appkey.toString());
        assertThat(appkey).isNotNull();
    }

    /**
     * 测试appkey都不存在于OPS+SC系统
     */
    @Test
    public void testNotExistAppkeyRealCollect() {
        String appkeyName = "com.sankuai.avatar.cscscscs123";
        AppkeyCollectEvent appkeyCollectEvent = AppkeyCollectEvent.builder()
                .collectEventData(AppkeyCollectEventData.of(appkeyName))
                .collectEventName(CollectEventName.APPKEY_REFRESH)
                .build();
        Appkey appkey = appkeyCollectEventScheduler.collect(appkeyCollectEvent);
        log.info(appkey.toString());
        assertThat(appkey.getIsOffline()).isEqualTo(Boolean.TRUE);
    }

    @Test
    @Ignore
    public void testFullAppkeyRealCollect() {
        appkeyCollectEventScheduler.fullAppkeyCollect();
    }
}
