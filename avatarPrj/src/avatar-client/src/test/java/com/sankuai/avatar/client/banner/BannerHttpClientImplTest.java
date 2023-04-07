package com.sankuai.avatar.client.banner;

import com.sankuai.avatar.client.TestBase;
import com.sankuai.avatar.client.banner.impl.BannerHttpClientImpl;
import com.sankuai.avatar.client.banner.response.ElasticTip;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class BannerHttpClientImplTest extends TestBase {

    private final BannerHttpClient bannerHttpClient;

    public BannerHttpClientImplTest() {
        this.bannerHttpClient = new BannerHttpClientImpl();
    }

    @Before
    public void setUp() throws Exception {
        ReflectionTestUtils.setField(bannerHttpClient, "bannerApiDomain", "http://bannerapi.inf.test.sankuai.com");
        ReflectionTestUtils.setField(bannerHttpClient, "bannerApiClientToken", "a8e520b90f8f4611bc32bc05943de325");
        ReflectionTestUtils.setField(bannerHttpClient, "bannerApiClientId", "avatarNew");
    }

    @Test
    public void testGetElasticTips() {
        final ElasticTip elasticTip = bannerHttpClient.getElasticTips();
        Assert.assertTrue(elasticTip.getRefuseActiveReasonEnum().size() > 0);
    }


    @Test
    public void testGetElasticGrayScale() {
        final Boolean gray = bannerHttpClient.getElasticGrayScale("meituan.avatar");
        Assert.assertEquals(Boolean.TRUE, gray);
    }
}
