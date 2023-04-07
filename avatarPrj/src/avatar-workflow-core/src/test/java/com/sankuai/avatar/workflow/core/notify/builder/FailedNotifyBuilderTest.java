package com.sankuai.avatar.workflow.core.notify.builder;

import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.context.FlowResource;
import com.sankuai.avatar.workflow.core.notify.NotifyReceiverRole;
import com.sankuai.avatar.workflow.core.notify.NotifyRequest;
import com.sankuai.avatar.workflow.core.notify.NotifyResult;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FailedNotifyBuilderTest {
    @Spy
    private TemplateParser templateParser;

    @InjectMocks
    private FailedNotifyBuilder failedNotifyBuilder;


    @Before
    public void setUp() {
        templateParser.init();
        ReflectionTestUtils.setField(failedNotifyBuilder, "avatarDomain", "test.com");
    }

    @Test
    public void testBuild() {
        // mock flow context
        FlowContext flowContext = FlowContext.builder()
                .id(12)
                .cnName("新增机器")
                .resource(FlowResource.builder().appkey("com.sankuai.avatar.web").build())
                .createUser("jie")
                .createUserName("中文名")
                .uuid("uuid")
                .build();
        NotifyRequest notifyRequest = mock(NotifyRequest.class);
        when(notifyRequest.getFlowContext()).thenReturn(flowContext);
        List<NotifyResult> notifyResultList = failedNotifyBuilder.build(notifyRequest);
        Assert.assertEquals(2, notifyResultList.size());
        Assert.assertNotNull(notifyResultList.get(0).getMsg());
        Assert.assertEquals(NotifyReceiverRole.CREATE_USER, notifyResultList.get(0).getNotifyReceiverRole());
    }

}
