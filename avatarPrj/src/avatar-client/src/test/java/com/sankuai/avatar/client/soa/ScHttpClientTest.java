package com.sankuai.avatar.client.soa;

import com.sankuai.avatar.client.TestBase;
import com.sankuai.avatar.client.soa.impl.ScHttpClientImpl;
import com.sankuai.avatar.client.soa.model.*;
import com.sankuai.avatar.client.soa.request.IsoltAppkeyRequest;
import com.sankuai.avatar.client.soa.request.ApplicationPageRequest;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;


/**
 * ScHttpClientImpl的测试类
 */
public class ScHttpClientTest extends TestBase {

    private ScHttpClientImpl scHttpClientImplUnderTest;

    @Before
    public void setUp() {
        scHttpClientImplUnderTest = new ScHttpClientImpl();
        ReflectionTestUtils.setField(scHttpClientImplUnderTest, "serviceCatalogDomain", "http://service.fetc.test.sankuai.com");
    }

    /**
     * 测试getOrg功能逻辑
     */
    @Test
    public void testGetOrg() {
        // Run the test
        final ScOrg result = scHttpClientImplUnderTest.getOrg("100046,150042,1573,150044,1021866");

        // Verify the results
        Assert.assertTrue(result.getAppKeyCount() > 0);
    }

    /**
     * 测试getOrgTreeByUser功能逻辑
     */
    @Test
    public void testGetOrgTreeByUser() {
        // Run the test
        final List<ScOrgTreeNode> result = scHttpClientImplUnderTest.getOrgTreeByUser("zhangxiaoning07");

        // Verify the results
        Assert.assertTrue(result.size() > 0);

    }

    @Test
    public void testGetApplications() {
        // Setup
        final ApplicationPageRequest request = new ApplicationPageRequest();
        request.setPage(1);
        request.setPageSize(10);
        request.setIsPublic(true);
        request.setMember("qinwei05");

        // Run the test
        final ScPageResponse<ScApplication> result = scHttpClientImplUnderTest.getApplications(request);

        // Verify the results
        Assert.assertTrue(result.getItems().size() > 0);
    }

    @Test
    public void testGetUserOwnerApplications() {
        // Setup
        final ApplicationPageRequest request = new ApplicationPageRequest();
        request.setPage(1);
        request.setPageSize(10);
        request.setMis("qinwei05");

        // Run the test
        final ScPageResponse<ScUserOwnerApplication> result = scHttpClientImplUnderTest.getUserOwnerApplications(request);

        // Verify the results
        Assert.assertTrue(result.getItems().size() > 0);
    }

    @Test
    public void testQueryApplications() {
        // Setup
        final ApplicationPageRequest request = new ApplicationPageRequest();
        request.setPage(1);
        request.setPageSize(10);
        request.setQuery("Avatar");

        // Run the test
        final ScPageResponse<ScQueryApplication> result = scHttpClientImplUnderTest.queryApplications(request);

        // Verify the results
        Assert.assertTrue(result.getItems().size() > 0);
    }

    @Test
    public void testGetApplicationsThatQueryByOrgIds() {
        // Setup
        final ApplicationPageRequest request = new ApplicationPageRequest();
        request.setPage(1);
        request.setPageSize(10);
        request.setOrgIds("100046,150042,1573,150044,1021866");
        request.setStartTime(new GregorianCalendar(2021, Calendar.JANUARY, 1).getTime());
        request.setEndTime(new GregorianCalendar(2022, Calendar.JANUARY, 1).getTime());

        // Run the test
        final ScPageResponse<ScApplication> result = scHttpClientImplUnderTest.getApplications(request);
        System.out.println(result.getItems().size());
        // Verify the results
        Assert.assertTrue(result.getItems().size() > 0);
    }

    @Test
    public void testGetApplication() {
        // Run the test
        final ScApplicationDetail result = scHttpClientImplUnderTest.getApplication("Avatar");

        // Verify the results
        Assert.assertTrue(result.getAppKeyTotal() > 0);

    }

    @Test
    public void testGetAppkeyInfoByV1() {
        ScV1Appkey scAppkey = scHttpClientImplUnderTest.getAppkeyInfoByV1(testAppkey);
        logger.info(scAppkey.toString());
        Assert.assertNotNull(scAppkey);
    }

    @Test
    public void testGetNotExistAppkeyInfoByV1() {
        try {
            ScV1Appkey scAppkey = scHttpClientImplUnderTest.getAppkeyInfoByV1(testAppkey + "test123");
        } catch (SdkBusinessErrorException e){
            logger.info(e.getMessage());
            Assert.assertNotNull(e.getMessage());
        }

    }

    @Test
    public void testGetAppkeyInfo() {
        List<String> appKes = Collections.singletonList(testAppkey);
        List<ScAppkey> scAppkeys = scHttpClientImplUnderTest.getAppkeysInfo(appKes);
        logger.info(scAppkeys.toString());
        Assert.assertEquals(1, scAppkeys.size());
    }

    @Test
    public void testGetNotExistAppkeyInfo() {
        List<String> appKes = Collections.singletonList(testAppkey + "test123");
        List<ScAppkey> scAppkeys = scHttpClientImplUnderTest.getAppkeysInfo(appKes);
        Assert.assertEquals(0, scAppkeys.size());
    }

    @Test
    public void testGetAllAppkeysByPage() {
        List<String> scAppkeys = scHttpClientImplUnderTest.getAllAppkeysByPage(1, 100);
        Assert.assertEquals(100, scAppkeys.size());
    }

    @Test
    @Ignore
    public void testGetAllAppkeys() {
        List<String> scAppkeys = scHttpClientImplUnderTest.getAllAppkeys();
        logger.info(String.valueOf(scAppkeys.size()));
        Assert.assertTrue(scAppkeys.size() > 0);
    }

    @Test
    public void testGetAllPaasApplications() {
        List<String> paasApplications = scHttpClientImplUnderTest.getAllPaasApplications();
        Assert.assertTrue(paasApplications.size() > 0);
    }

    @Test
    public void testGetIsoltAppkeys() {
        final IsoltAppkeyRequest isoltAppkeyRequest = IsoltAppkeyRequest.builder()
                .page(1)
                .pageSize(10)
                .capacity("1,2")
                .rank("CORE")
                .query("sankuai")
                .build();
        ScPageResponse<ScIsoltAppkey> scAppkeyScPageResponse = scHttpClientImplUnderTest.getIsoltAppkeys(isoltAppkeyRequest);
        Assert.assertTrue(scAppkeyScPageResponse.getItems().size() > 0);

        final IsoltAppkeyRequest mineIsoltAppkeyRequest = IsoltAppkeyRequest.builder()
                .page(1)
                .pageSize(10)
                .mis("qinwei05")
                .query("")
                .build();
        ScPageResponse<ScIsoltAppkey> scMineAppkeyScPageResponse = scHttpClientImplUnderTest.getIsoltAppkeys(mineIsoltAppkeyRequest);
        Assert.assertTrue(scMineAppkeyScPageResponse.getItems().size() > 0);

    }
}
