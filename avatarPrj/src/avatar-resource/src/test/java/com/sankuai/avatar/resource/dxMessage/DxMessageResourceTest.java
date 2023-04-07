package com.sankuai.avatar.resource.dxMessage;

import com.sankuai.avatar.client.dx.DxClient;
import com.sankuai.avatar.client.org.OrgClient;
import com.sankuai.avatar.client.org.model.OrgUser;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.resource.TestBase;
import com.sankuai.avatar.resource.dxMessage.impl.DxMessageResourceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * @author caoyang
 * @create 2022-11-25 13:19
 */
public class DxMessageResourceTest extends TestBase {

    @Mock
    private DxClient dxClient;
    @Mock
    private OrgClient orgClient;

    private DxMessageResource dxResource;

    @Before
    public void setUp() throws Exception {
        dxResource = new DxMessageResourceImpl(dxClient, orgClient);
    }

    @Test
    public void testPushDxMessage() {
        OrgUser orgUser = new OrgUser();
        orgUser.setJobStatus("在职");
        when(orgClient.getOrgUserByMis(Mockito.anyString())).thenReturn(orgUser);
        when(dxClient.pushDxMessage(Mockito.anyList(),Mockito.anyString())).thenReturn(true);
        final Boolean result = dxResource.pushDxMessage(Collections.singletonList("x"), "message");
        verify(orgClient).getOrgUserByMis(Mockito.anyString());
        verify(dxClient).pushDxMessage(Mockito.anyList(), Mockito.anyString());
        assertThat(result).isTrue();
    }

    @Test
    public void testPushDxMessageThrowsSdkBusinessErrorException() {
        OrgUser orgUser = new OrgUser();
        orgUser.setJobStatus("在职");
        when(orgClient.getOrgUserByMis(Mockito.anyString())).thenThrow(SdkBusinessErrorException.class);
        final Boolean result = dxResource.pushDxMessage(Collections.singletonList("x"), "message");
        verify(orgClient).getOrgUserByMis(Mockito.anyString());
        verify(dxClient, times(0)).pushDxMessage(Mockito.anyList(), Mockito.anyString());
        assertThat(result).isFalse();
    }
}