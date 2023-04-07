package com.sankuai.avatar.web.crane;

import com.sankuai.avatar.collect.appkey.event.AppkeyCollectEvent;
import com.sankuai.avatar.collect.appkey.event.AppkeyCollectEventData;
import com.sankuai.avatar.collect.appkey.event.CollectEventName;
import com.sankuai.avatar.collect.appkey.model.Appkey;
import com.sankuai.avatar.collect.appkey.scheduer.AppkeyCollectEventScheduler;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.resource.dxMessage.DxMessageResource;
import com.sankuai.avatar.web.dto.appkey.AppkeyDTO;
import com.sankuai.avatar.web.service.AppkeyService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class CraneAppkeyInfoCheckDiffTest {

    @Mock
    private AppkeyCollectEventScheduler mockAppkeyCollectEventScheduler;
    @Mock
    private AppkeyService mockAppkeyService;
    @Mock
    private DxMessageResource mockDxMessageResource;

    private CraneAppkeyInfoCheckDiff craneAppkeyInfoCheckDiffUnderTest;

    @Before
    public void setUp() throws Exception {
        craneAppkeyInfoCheckDiffUnderTest = new CraneAppkeyInfoCheckDiff(mockAppkeyCollectEventScheduler,
                mockAppkeyService, mockDxMessageResource);
    }

    @Test
    public void testAppkeyInfoCheckDiff() {
        // Setup
        // Configure AppkeyService.getByAppkeyRandom(...).
        final AppkeyDTO appkeyDTO = new AppkeyDTO();
        appkeyDTO.setAppkey("appkey");
        appkeyDTO.setIsOffline(Boolean.FALSE);
        appkeyDTO.setType("BACKEND");
        final List<AppkeyDTO> appkeyDTOList = Collections.singletonList(appkeyDTO);
        when(mockAppkeyService.getByAppkeyRandom(20)).thenReturn(appkeyDTOList);

        // Configure AppkeyCollectEventScheduler.collect(...).
        final Appkey appkey = new Appkey("appkey");
        appkey.setIsOffline(Boolean.FALSE);
        when(mockAppkeyCollectEventScheduler.collect(AppkeyCollectEvent.builder()
                .collectEventName(CollectEventName.APPKEY_REFRESH)
                .collectEventData(AppkeyCollectEventData.of("appkey"))
                .build())).thenReturn(appkey);

        when(mockDxMessageResource.pushDxMessage(Collections.singletonList("value"), "message")).thenReturn(false);

        // Run the test
        craneAppkeyInfoCheckDiffUnderTest.appkeyInfoCheckDiff();

        // Verify the results
        Mockito.verify(mockDxMessageResource, times(1)).pushDxMessage(any(), any());
    }
}
