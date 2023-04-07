package com.sankuai.avatar.workflow.core.context;

import com.google.common.collect.ImmutableMap;
import com.sankuai.avatar.common.utils.JsonUtil;
import com.sankuai.avatar.dao.workflow.repository.FlowRepository;
import com.sankuai.avatar.dao.workflow.repository.FlowTemplateRepository;
import com.sankuai.avatar.dao.workflow.repository.entity.FlowTemplateEntity;
import com.sankuai.avatar.workflow.core.context.loader.FlowContextLoaderImpl;
import com.sankuai.avatar.workflow.core.context.loader.FlowInputNameLoader;
import com.sankuai.avatar.workflow.core.context.request.FlowCreateRequest;
import com.sankuai.avatar.workflow.core.engine.listener.PushEvent;
import com.sankuai.avatar.workflow.core.input.FlowInput;
import com.sankuai.avatar.workflow.core.input.host.HostsRebootInput;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.Map;


@RunWith(MockitoJUnitRunner.class)
public class FlowContextLoaderTest {
    @Mock
    private FlowRepository flowRepository;

    @Mock
    private FlowInputNameLoader flowInputNameLoader;

    @Mock
    private FlowTemplateRepository flowTemplateRepository;

    @Mock
    private PushEvent pushEvent;

    @InjectMocks
    private FlowContextLoaderImpl flowContextLoader;

    @Before
    public void setUp() {
    }

    @Test
    public void testBuildByTemplateName() {
        // mock input class
        Class<? extends FlowInput> clazz = HostsRebootInput.class;
        doReturn(clazz).when(flowInputNameLoader).load(any());
        // mock add
        when(flowRepository.addFlow(any())).thenReturn(true);
        // mock search template
        FlowTemplateEntity flowTemplateEntity = FlowTemplateEntity.builder().build();
        when(flowTemplateRepository.getFlowTemplateByName(any())).thenReturn(flowTemplateEntity);

        String templateName = "deliver_activity_resource";
        String createUser = "zhaozhifan02";
        Map<String, Object> inputParams = ImmutableMap.of(
                "appkey", "appkey",
                "agent", Collections.singletonList(createUser),
                "end_time", "2023-01-13 00:00:00",
                "env", "test",
                "user", createUser);
        FlowCreateRequest flowCreateRequest = FlowCreateRequest.builder()
                .input(JsonUtil.bean2Json(inputParams))
                .createUser(createUser)
                .createUserSource(FlowUserSource.USER)
                .build();
        FlowContext flowContext = flowContextLoader.buildByTemplateName(templateName, flowCreateRequest);
        Assert.assertNotNull(flowContext);
        Assert.assertEquals(flowContext.getEnv(), "test");
        Assert.assertEquals(flowContext.getResource().getAppkey(), "appkey");
        Assert.assertEquals(flowContext.createUser, createUser);
    }
}
