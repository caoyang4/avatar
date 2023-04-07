package com.sankuai.avatar.resource.plus;

import com.sankuai.avatar.client.plus.PlusHttpClient;
import com.sankuai.avatar.client.plus.model.PlusRelease;
import com.sankuai.avatar.resource.plus.bo.PlusReleaseBO;
import com.sankuai.avatar.resource.plus.impl.PlusResourceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

import static junit.framework.TestCase.assertEquals;


public class PlusResourceTest {
    private PlusResource plusResource;
    private final String testAppkey = "com.sankuai.avatar.cscscscs";

    @Before
    public void setUp() {
        PlusHttpClient plusHttpClient = Mockito.mock(PlusHttpClient.class);
        PlusRelease plusRelease = PlusRelease.builder().name(testAppkey).id(1212).build();
        Mockito.when(plusHttpClient.getBindPlusByAppkey(ArgumentMatchers.anyString())).thenReturn(Collections.singletonList(plusRelease));
        Mockito.when(plusHttpClient.getAppliedPlusByAppkey(ArgumentMatchers.anyString())).thenReturn(Collections.singletonList(plusRelease));
        this.plusResource = new PlusResourceImpl(plusHttpClient);
    }

    @Test
    public void getBindPlusByAppkey() {
        List<PlusReleaseBO> plusReleaseBOList = plusResource.getBindPlusByAppkey(testAppkey);
        assertEquals(plusReleaseBOList.get(0).getName(), testAppkey);
    }

    @Test
    public void getAppliedPlusByAppkey() {
        List<PlusReleaseBO> plusReleaseBOList = plusResource.getAppliedPlusByAppkey(testAppkey);
        assertEquals(plusReleaseBOList.get(0).getName(), testAppkey);
    }
}