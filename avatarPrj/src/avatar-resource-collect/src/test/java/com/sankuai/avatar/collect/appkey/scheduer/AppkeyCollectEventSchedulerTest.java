package com.sankuai.avatar.collect.appkey.scheduer;

import com.meituan.mafka.client.producer.ProducerResult;
import com.meituan.mafka.client.producer.ProducerStatus;
import com.sankuai.avatar.client.ops.OpsHttpClient;
import com.sankuai.avatar.client.ops.model.OpsSrv;
import com.sankuai.avatar.client.soa.ScHttpClient;
import com.sankuai.avatar.client.soa.model.ScAppkey;
import com.sankuai.avatar.collect.appkey.collector.Collector;
import com.sankuai.avatar.collect.appkey.collector.OpsAppkeyDataCollector;
import com.sankuai.avatar.collect.appkey.event.AppkeyCollectEvent;
import com.sankuai.avatar.collect.appkey.event.AppkeyCollectEventData;
import com.sankuai.avatar.collect.appkey.event.CollectEventName;
import com.sankuai.avatar.collect.appkey.model.Appkey;
import com.sankuai.avatar.collect.appkey.producer.AppkeyChangeProducer;
import com.sankuai.avatar.common.exception.CollectErrorException;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;
import com.sankuai.avatar.dao.cache.CacheClient;
import com.sankuai.avatar.dao.resource.repository.model.AppkeyDO;
import com.sankuai.avatar.resource.appkey.AppkeyResource;
import com.sankuai.avatar.resource.appkey.bo.OpsSrvBO;
import com.sankuai.avatar.resource.application.ApplicationResource;
import com.sankuai.avatar.resource.tree.AppkeyTreeResource;
import com.sankuai.avatar.resource.tree.bo.AppkeyTreeBO;
import com.sankuai.avatar.resource.tree.bo.AppkeyTreeOwtBO;
import com.sankuai.avatar.resource.tree.bo.AppkeyTreePdlBO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class AppkeyCollectEventSchedulerTest {

    @Mock
    private OpsHttpClient mockOpsHttpClient;
    @Mock
    private ScHttpClient mockScHttpClient;
    @Mock
    private AppkeyChangeProducer mockAppkeyChangeProducer;

    @Mock
    private AppkeyResource mockAppkeyResource;
    @Mock
    private ApplicationResource applicationResource;
    @Mock
    private AppkeyTreeResource mockAppkeyTreeResource;
    @Mock
    private CacheClient cacheClient;

    @Spy
    private List<Collector> allRegisterCollectorList = new ArrayList<>();

    @Spy
    @InjectMocks
    private AppkeyCollectEventScheduler appkeyCollectEventSchedulerUnderTest;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        OpsAppkeyDataCollector opsAppkeyDataCollector = new OpsAppkeyDataCollector(mockAppkeyResource, mockAppkeyTreeResource, applicationResource, cacheClient);
        allRegisterCollectorList.add(opsAppkeyDataCollector);
        appkeyCollectEventSchedulerUnderTest = new AppkeyCollectEventScheduler(Collections.emptyList(), mockOpsHttpClient,
                mockScHttpClient, mockAppkeyChangeProducer, allRegisterCollectorList);
    }

    @Test
    public void testFullAppkeyCollect() {
        // Setup
        // Configure OpsHttpClient.getAllAppkeyInfo(...).
        final List<OpsSrv> opsSrvs = Collections.singletonList(OpsSrv.builder().appkey("appkey").build());
        when(mockOpsHttpClient.getAllAppkeyInfo()).thenReturn(opsSrvs);

        // Configure AppkeyChangeProducer.appkeyChange(...).
        final ProducerResult producerResult = new ProducerResult(ProducerStatus.SEND_OK, "clusterName", "traceId");
        when(mockAppkeyChangeProducer.appkeyChange(any())).thenReturn(producerResult);

        when(mockScHttpClient.getAllAppkeysByPage(any(), any())).thenReturn(Collections.singletonList("value"));
        when(mockScHttpClient.getAllAppkeysByPage(2, 100)).thenReturn(Collections.emptyList());

        // Configure ScHttpClient.getAppkeysInfo(...).
        final ScAppkey scAppkey = new ScAppkey();
        scAppkey.setAppKey("appKey");
        final List<ScAppkey> scAppkeys = Collections.singletonList(scAppkey);
        when(mockScHttpClient.getAppkeysInfo(Collections.singletonList("value"))).thenReturn(scAppkeys);

        // Run the test
        appkeyCollectEventSchedulerUnderTest.fullAppkeyCollect();

        // Verify the results
        Mockito.verify(mockAppkeyChangeProducer, Mockito.times(2)).appkeyChange(any());
    }

    @Test
    public void testPushToMafkaProducer() {
        // Setup
        // Configure AppkeyChangeProducer.appkeyChange(...).
        final ProducerResult producerResult = new ProducerResult(ProducerStatus.SEND_OK, "clusterName", "traceId");
        when(mockAppkeyChangeProducer.appkeyChange(any())).thenReturn(producerResult);

        // Run the test
        final ProducerResult result = appkeyCollectEventSchedulerUnderTest.pushToMafkaProducer("appkey",
                CollectEventName.APPKEY_REFRESH);

        // Verify the results
        assertThat(result.getProducerStatus()).isEqualTo(ProducerStatus.SEND_OK);
    }

    @Test
    public void testCollect() {
        // Setup
        final AppkeyCollectEvent appkeyCollectEvent = AppkeyCollectEvent.builder()
                .collectEventName(CollectEventName.APPKEY_REFRESH)
                .collectEventData(AppkeyCollectEventData.of("appkey", null))
                .build();
        final Appkey expectedResult = new Appkey("appkey");

        // Configure AppkeyResource.getAppkeyByOps(...).
        final OpsSrvBO opsSrvBO = new OpsSrvBO();
        opsSrvBO.setAppkey("appkey");
        opsSrvBO.setKey("meituan.owt.pdl.srv");
        when(mockAppkeyResource.getAppkeyByOps("appkey")).thenReturn(opsSrvBO);

        AppkeyTreeBO appkeyTreeBO = new AppkeyTreeBO();
        AppkeyTreeOwtBO appkeyTreeOwtBO = AppkeyTreeOwtBO.builder().key("owtKey").name("owtName").businessGroup("bg").build();
        AppkeyTreePdlBO appkeyTreePdlBO = AppkeyTreePdlBO.builder().key("pdlKey").name("pdlName").build();
        appkeyTreeBO.setOwt(appkeyTreeOwtBO);
        appkeyTreeBO.setPdl(appkeyTreePdlBO);
        PowerMockito.when(mockAppkeyTreeResource.getAppkeyTreeByKey(opsSrvBO.getKey())).thenReturn(appkeyTreeBO);


        // Configure AppkeyRepository.query(...).
        final AppkeyDO appkeyDO = new AppkeyDO();
        appkeyDO.setAppkey("appkey");

        // Run the test
        final Appkey result = appkeyCollectEventSchedulerUnderTest.collect(appkeyCollectEvent);

        // Verify the results
        assertThat(result.getAppkey()).isEqualTo(expectedResult.getAppkey());
    }

    @Test
    public void testCollect_AppkeyResourceGetAppkeyByOpsThrowsSdkCallException() {
        // Setup
        final AppkeyCollectEvent appkeyCollectEvent = AppkeyCollectEvent.builder()
                .collectEventName(CollectEventName.APPKEY_REFRESH)
                .collectEventData(AppkeyCollectEventData.of("appkey", null))
                .build();

        when(mockAppkeyResource.getSrvKeyByAppkey("appkey")).thenThrow(SdkCallException.class);
        when(mockAppkeyResource.getAppkeyByOps("appkey")).thenThrow(SdkCallException.class);

        // Run the test
        assertThatThrownBy(() -> appkeyCollectEventSchedulerUnderTest.collect(appkeyCollectEvent))
                .isInstanceOf(CollectErrorException.class);
    }

    @Test
    public void testCollect_AppkeyResourceGetAppkeyByOpsThrowsSdkBusinessErrorException() {
        // Setup
        final AppkeyCollectEvent appkeyCollectEvent = AppkeyCollectEvent.builder()
                .collectEventName(CollectEventName.APPKEY_REFRESH)
                .collectEventData(AppkeyCollectEventData.of("appkey", null))
                .build();

        when(mockAppkeyResource.getAppkeyByOps("appkey")).thenThrow(new SdkBusinessErrorException(404, "Appkey not found"));

        // Run the test
        assertThatThrownBy(() -> appkeyCollectEventSchedulerUnderTest.collect(appkeyCollectEvent))
                .isInstanceOf(CollectErrorException.class);
    }
}
