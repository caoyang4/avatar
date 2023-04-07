package com.sankuai.avatar.resource.mcm;

import com.sankuai.avatar.client.mcm.ComponentHttpClient;
import com.sankuai.avatar.resource.TestBase;
import com.sankuai.avatar.resource.mcm.bo.PeriodConfigBO;
import com.sankuai.avatar.resource.mcm.impl.ComponentResourceImpl;
import com.sankuai.avatar.resource.mcm.request.CreateBusyPeriodRequestBO;
import com.sankuai.avatar.resource.mcm.transfer.BusyPeriodTransfer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

public class ComponentResourceTest extends TestBase {

    @Mock
    private ComponentHttpClient componentHttpClient;

    private ComponentResource componentResource;

    @Override
    @Before
    public void setUp() {
        componentResource = new ComponentResourceImpl(componentHttpClient);
    }

    @Test
    public void testCreateBusyPeriod() {
        CreateBusyPeriodRequestBO request = CreateBusyPeriodRequestBO.builder()
                .configKey("com.sankuai.avatar.workflow.server")
                .configType("APPKEY")
                .status("ONLINE")
                .covered(true)
                .actionType("OPEN")
                .periodConfig(getPeriodConfig())
                .createUser("zhaozhifan02")
                .description("测试")
                .build();
        when(componentHttpClient.createBusyPeriod(BusyPeriodTransfer.INSTANCE.boToRequest(request))).thenReturn(true);
        final boolean result = componentResource.createBusyPeriod(request);
        assertThat(result).isTrue();
    }

    @Test
    public void testCreateBusyPeriodWithEmptyRequest() {
        final boolean result = componentResource.createBusyPeriod(null);
        assertThat(result).isFalse();
    }

    private PeriodConfigBO getPeriodConfig() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        PeriodConfigBO periodConfig = new PeriodConfigBO();
        periodConfig.setWeekTime(Collections.emptyMap());
        periodConfig.setStartDate(now.format(formatter));
        periodConfig.setEndDate(now.plusHours(2).format(formatter));
        return periodConfig;
    }
}