package com.sankuai.avatar.resource.octo;

import com.sankuai.avatar.client.http.core.EnvEnum;
import com.sankuai.avatar.client.octo.OctoHttpClient;
import com.sankuai.avatar.client.octo.model.OctoProviderGroup;
import com.sankuai.avatar.resource.TestBase;
import com.sankuai.avatar.resource.octo.impl.OctoResourceImpl;
import com.sankuai.avatar.resource.octo.model.OctoProviderGroupBO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * @author caoyang
 * @create 2022-12-13 16:46
 */
public class OctoResourceTest extends TestBase {

    private OctoResource octoResource;

    @Mock
    private OctoHttpClient octoHttpClient;

    private static OctoProviderGroup group1, group2, group3;

    static {
        group1 = new OctoProviderGroup();
        group1.setName("同中心优先");
        group1.setCategory(3);
        group1.setPriority(1);
        group1.setStatus(0);
        group1.setForce(0);

        group2 = new OctoProviderGroup();
        group2.setName("同机房优先");
        group2.setCategory(1);
        group2.setPriority(0);
        group2.setStatus(0);
        group2.setForce(0);

        group3 = new OctoProviderGroup();
        group3.setName("同城市优先");
        group3.setCategory(5);
        group3.setPriority(-1);
        group3.setStatus(0);
        group3.setForce(0);
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        octoResource = new OctoResourceImpl(octoHttpClient);
    }

    @Test
    public void getOctoProviderGroup() {
        when(octoHttpClient.getOctoProviderGroup("x", EnvEnum.TEST, "http"))
                .thenReturn(Arrays.asList(group1, group2, group3));
        List<OctoProviderGroupBO> octoProviderGroupBOList = octoResource.getOctoProviderGroup("x", EnvEnum.TEST, "http");
        Assert.assertTrue(octoProviderGroupBOList.size() > 0);
    }
}