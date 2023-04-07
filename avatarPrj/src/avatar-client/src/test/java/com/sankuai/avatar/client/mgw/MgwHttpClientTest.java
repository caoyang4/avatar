package com.sankuai.avatar.client.mgw;

import com.sankuai.avatar.client.TestBase;
import com.sankuai.avatar.client.http.core.EnvEnum;
import com.sankuai.avatar.client.mgw.impl.MgwHttpClientImpl;
import com.sankuai.avatar.client.mgw.request.MgwVsRequest;
import com.sankuai.avatar.client.mgw.response.MgwVs;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

public class MgwHttpClientTest extends TestBase {

    private final MgwHttpClient mgwHttpClientImplUnderTest;

    public MgwHttpClientTest() {
        this.mgwHttpClientImplUnderTest = new MgwHttpClientImpl();
    }

    @Before
    public void setUp() throws Exception {
        ReflectionTestUtils.setField(mgwHttpClientImplUnderTest, "mgwProdDomain", "http://mip.sankuai.com");
        ReflectionTestUtils.setField(mgwHttpClientImplUnderTest, "mgwTestDomain", "http://mip.ep.test.sankuai.com");
        ReflectionTestUtils.setField(mgwHttpClientImplUnderTest, "mgwToken", "token e71a4680d97cafe941f217c511f0250f");
    }

    @Test
    public void testGetVsList() {
        // Setup
        final MgwVsRequest mgwVsRequest = MgwVsRequest.builder().appkey(testAppkey).build();

        // Run the test
        final List<MgwVs> mgwVsList = mgwHttpClientImplUnderTest.getVsList(mgwVsRequest, EnvEnum.PROD);
        logger.info(mgwVsList.toString());
        // Verify the results
        Assert.assertTrue(mgwVsList.size() > 0);
    }


    @Test
    public void testGetNotExistVsList() {
        // Setup
        final MgwVsRequest mgwVsRequest = MgwVsRequest.builder().appkey(testAppkey+"123").build();

        // Run the test
        final List<MgwVs> mgwVsList = mgwHttpClientImplUnderTest.getVsList(mgwVsRequest, EnvEnum.PROD);
        logger.info(mgwVsList.toString());
        // Verify the results
        Assert.assertEquals(0, mgwVsList.size());
    }
}
