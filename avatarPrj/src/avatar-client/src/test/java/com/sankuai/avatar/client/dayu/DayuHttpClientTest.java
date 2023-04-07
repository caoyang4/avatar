package com.sankuai.avatar.client.dayu;

import com.sankuai.avatar.client.TestBase;
import com.sankuai.avatar.client.dayu.impl.DayuHttpClientImpl;
import com.sankuai.avatar.client.dayu.model.DayuGroupTag;
import com.sankuai.avatar.client.dayu.model.GroupTagQueryRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

public class DayuHttpClientTest extends TestBase {

    private final DayuHttpClient dayuHttpClient;

    public DayuHttpClientTest() {
        this.dayuHttpClient = new DayuHttpClientImpl();
    }

    @Before
    public void setUp() throws Exception {
        ReflectionTestUtils.setField(dayuHttpClient, "dayuApiDomain", "http://dayu.fetc.test.sankuai.com");
        ReflectionTestUtils.setField(dayuHttpClient, "dayuClientId", "avatar");
        ReflectionTestUtils.setField(dayuHttpClient, "dayuClientSecret", "1017088D4C0B11ECB5BF0A580A247DD6");
    }

    @Test
    public void testGetVsList() {
        // Setup
        final GroupTagQueryRequest request = GroupTagQueryRequest.builder().owt("meituan.inf").isHidden(false).build();

        // Run the test
        final List<DayuGroupTag> grouptags = dayuHttpClient.getGrouptags(request);
        logger.info(grouptags.toString());
        // Verify the results
        Assert.assertTrue(grouptags.size() > 0);
    }
}
