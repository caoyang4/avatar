package com.sankuai.avatar.web.crane;

import com.sankuai.avatar.web.dto.capacity.AppkeyCapacitySummaryDTO;
import com.sankuai.avatar.web.service.AppkeyCapacityService;
import com.sankuai.avatar.web.service.AppkeyPaasCapacityService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CraneAppkeyPaasCapacityTest {

    @Mock
    private AppkeyPaasCapacityService paasCapacityService;
    @Mock
    private AppkeyCapacityService appkeyCapacityService;

    private CraneAppkeyPaasCapacity crane;

    @Before
    public void setUp() {
        crane = new CraneAppkeyPaasCapacity(paasCapacityService, appkeyCapacityService);
    }

    @Test
    public void testCleanAppkeyPaasCapacity() {
        when(paasCapacityService.deleteAppkeyPaasCapacityByUpdateDate(Mockito.any(LocalDate.class))).thenReturn(true);
        crane.cleanAppkeyPaasCapacity();
        verify(paasCapacityService).deleteAppkeyPaasCapacityByUpdateDate(Mockito.any(LocalDate.class));
    }

    @Test
    public void testUpdateOpsPaasCapacity() {
        AppkeyCapacitySummaryDTO summaryDTO = new AppkeyCapacitySummaryDTO();
        summaryDTO.setCapacityLevel(3);
        summaryDTO.setStandardTips("x");
        when(paasCapacityService.getValidPaasAppkeys()).thenReturn(Arrays.asList("x","y"));
        when(paasCapacityService.getAppkeyPaasCapacitySummaryByPaasAppkeyNoCache(Mockito.anyString())).thenReturn(summaryDTO);
        when(appkeyCapacityService.updateOpsCapacity(Mockito.anyString(),Mockito.anyString(),Mockito.anyInt())).thenReturn(true);
        crane.updateOpsPaasCapacity();
        verify(paasCapacityService).getValidPaasAppkeys();
        verify(paasCapacityService,times(2)).getAppkeyPaasCapacitySummaryByPaasAppkeyNoCache(Mockito.anyString());
        verify(appkeyCapacityService,times(2)).updateOpsCapacity(Mockito.anyString(),Mockito.anyString(),Mockito.anyInt());
    }


    @Test
    public void testCacheCapacitySummary() {
        when(paasCapacityService.getValidPaasAppkeys()).thenReturn(Arrays.asList("a","b"));
        when(paasCapacityService.getValidClientAppkeys()).thenReturn(Arrays.asList("x","y"));
        when(paasCapacityService.cacheAppkeyCapacitySummary(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
        crane.cacheCapacitySummary();
        verify(paasCapacityService).getValidPaasAppkeys();
        verify(paasCapacityService).getValidClientAppkeys();
        verify(paasCapacityService,times(4)).cacheAppkeyCapacitySummary(Mockito.anyString(),Mockito.anyBoolean());
    }

}
