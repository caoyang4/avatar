package com.sankuai.avatar.workflow.core.notify.builder;

import com.sankuai.avatar.resource.appkey.AppkeyResource;
import com.sankuai.avatar.resource.appkey.bo.AppkeyBO;
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
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CompleteNotifyBuilderTest {
    @Spy
    private TemplateParser templateParser;

    @InjectMocks
    private CompleteNotifyBuilder completeNotifyBuilder;

    @Mock
    private AppkeyResource appkeyResource;


    @Before
    public void setUp() {
        templateParser.init();
        ReflectionTestUtils.setField(completeNotifyBuilder, "avatarDomain", "test.com");
    }

    @Test
    public void testBuild() {
        // mock flow context
        FlowContext flowContext = FlowContext.builder()
                .id(12)
                .cnName("新增机器")
                .createUser("jie")
                .createUserName("中文名")
                .uuid("uuid")
                .build();
        NotifyRequest notifyRequest = mock(NotifyRequest.class);
        when(notifyRequest.getFlowContext()).thenReturn(flowContext);
        List<NotifyResult> notifyResultList = completeNotifyBuilder.build(notifyRequest);
        Assert.assertEquals(1, notifyResultList.size());
        Assert.assertNotNull(notifyResultList.get(0).getMsg());
        Assert.assertEquals(NotifyReceiverRole.CREATE_USER, notifyResultList.get(0).getNotifyReceiverRole());
    }

    @Test
    public void testAppkeyBuild() {
        // mock flow context
        FlowContext flowContext = FlowContext.builder()
                .id(12)
                .cnName("新增机器")
                .resource(FlowResource.builder().appkey("com.sankuai.avatar.web").build())
                .createUser("jie")
                .createUserName("中文名")
                .uuid("uuid")
                .build();
        // mock appkey
        AppkeyBO appkeyBO = mock(AppkeyBO.class);
        when(appkeyResource.getByAppkey(anyString())).thenReturn(appkeyBO);
        when(appkeyBO.getRdAdmin()).thenReturn("a,b,c");
        when(appkeyBO.getOpAdmin()).thenReturn("c,d");
        when(appkeyBO.getEpAdmin()).thenReturn("c");

        NotifyRequest notifyRequest = mock(NotifyRequest.class);
        when(notifyRequest.getFlowContext()).thenReturn(flowContext);
        List<NotifyResult> notifyResultList = completeNotifyBuilder.build(notifyRequest);
        Assert.assertEquals(4, notifyResultList.size());
        // verify create
        Assert.assertNotNull(notifyResultList.get(0).getMsg());
        Assert.assertEquals(NotifyReceiverRole.CREATE_USER, notifyResultList.get(0).getNotifyReceiverRole());
        // verify rd
        Assert.assertEquals(appkeyBO.getRdAdmin().split(",").length, notifyResultList.get(1).getReceiverList().size());
    }
}
