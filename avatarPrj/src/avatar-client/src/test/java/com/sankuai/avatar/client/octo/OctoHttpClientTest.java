package com.sankuai.avatar.client.octo;

import com.sankuai.avatar.client.TestBase;
import com.sankuai.avatar.client.http.core.EnvEnum;
import com.sankuai.avatar.client.octo.model.OctoProviderGroup;
import com.sankuai.avatar.client.octo.request.OctoNodeStatusQueryRequest;
import com.sankuai.avatar.client.octo.response.OctoNodeStatusResponse;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author caoyang
 * @create 2022-12-13 15:34
 */
public class OctoHttpClientTest extends TestBase {

    @Autowired
    private OctoHttpClient octoHttpClient;

    @Test
    public void getHttpOctoProviderGroup() {
        List<OctoProviderGroup> providerGroupListWithTestEnv = octoHttpClient.getOctoProviderGroup(testAppkey, EnvEnum.TEST, "http");
        logger.info(providerGroupListWithTestEnv.toString());
        Assert.assertTrue(CollectionUtils.isNotEmpty(providerGroupListWithTestEnv));

        List<OctoProviderGroup> providerGroupListWithProdEnv = octoHttpClient.getOctoProviderGroup(testAppkey, EnvEnum.PROD, "http");
        logger.info(providerGroupListWithProdEnv.toString());
        Assert.assertTrue(CollectionUtils.isNotEmpty(providerGroupListWithProdEnv));
    }

    @Test
    public void getThriftOctoProviderGroup() {
        List<OctoProviderGroup> providerGroupList = octoHttpClient.getOctoProviderGroup(testAppkey, EnvEnum.TEST, "thrift");
        logger.info(providerGroupList.toString());
        Assert.assertTrue(CollectionUtils.isNotEmpty(providerGroupList));

        List<OctoProviderGroup> providerGroupListWithProdEnv = octoHttpClient.getOctoProviderGroup(testAppkey, EnvEnum.PROD, "thrift");
        logger.info(providerGroupListWithProdEnv.toString());
        Assert.assertTrue(CollectionUtils.isNotEmpty(providerGroupListWithProdEnv));
    }

    @Test
    public void getAppkeyOctoNodeStatus() {
        OctoNodeStatusQueryRequest request = OctoNodeStatusQueryRequest.builder()
                .appkey("com.sankuai.avatar.develop")
                .pageSize(-1).build();
        OctoNodeStatusResponse octoNodeStatusResponse = octoHttpClient.getAppkeyOctoNodeStatus(request, EnvEnum.PROD);
        logger.info(octoNodeStatusResponse.toString());
        Assert.assertNotNull(octoNodeStatusResponse);

        OctoNodeStatusQueryRequest request2 = OctoNodeStatusQueryRequest.builder().appkey("com.sankuai.avatar.develop").build();
        OctoNodeStatusResponse octoNodeStatusResponse2 = octoHttpClient.getAppkeyOctoNodeStatus(request2, EnvEnum.TEST);
        logger.info(octoNodeStatusResponse2.toString());
        Assert.assertNotNull(octoNodeStatusResponse2);
    }
}