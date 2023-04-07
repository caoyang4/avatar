package com.sankuai.avatar.client.mcm;

import com.sankuai.avatar.client.TestBase;
import com.sankuai.avatar.client.mcm.impl.ComponentHttpClientImpl;
import com.sankuai.avatar.client.mcm.model.PeriodConfig;
import com.sankuai.avatar.client.mcm.request.CreateBusyPeriodRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

public class ComponentHttpClientTest extends TestBase {

    private ComponentHttpClient componentHttpClient;

    public ComponentHttpClientTest() {
        this.componentHttpClient = new ComponentHttpClientImpl();
    }

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(componentHttpClient, "componentDomain", "http://mcm.tbd.test.sankuai.com");
        logger.info("=============" + getClass().getName() + "============");
    }

    @Test
    public void testCreateBusyPeriod() {
        CreateBusyPeriodRequest request = CreateBusyPeriodRequest.builder()
                .configKey(testAppkey)
                .configType("APPKEY")
                .status("ONLINE")
                .covered(true)
                .actionType("OPEN")
                .periodConfig(getPeriodConfig())
                .createUser("zhaozhifan02")
                .description("测试")
                .build();
        boolean status = componentHttpClient.createBusyPeriod(request);
        Assert.assertTrue(status);
    }

    private PeriodConfig getPeriodConfig() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        PeriodConfig periodConfig = new PeriodConfig();
        periodConfig.setWeekTime(Collections.emptyMap());
        periodConfig.setStartDate(now.format(formatter));
        periodConfig.setEndDate(now.plusHours(2).format(formatter));
        return periodConfig;
    }
}