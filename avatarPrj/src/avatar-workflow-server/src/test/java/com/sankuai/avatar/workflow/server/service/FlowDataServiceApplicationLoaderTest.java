package com.sankuai.avatar.workflow.server.service;

import com.sankuai.avatar.common.utils.GsonUtils;
import com.sankuai.avatar.workflow.server.ApplicationLoaderTest;
import com.sankuai.avatar.workflow.server.dto.request.flow.FlowUpdateRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class FlowDataServiceApplicationLoaderTest extends ApplicationLoaderTest {

    @Autowired
    private FlowDataService flowDataService;

    @Autowired
    private FlowService flowService;

    @Test
    public void updateTest() {
        String json = "{\"id\":22721,\"type\":\"search\",\"data\":{\"template_name\":\"org_role_admin_change\",\"create_user\":\"qinwei05\",\"create_user_name\":\"秦伟\",\"id\":22721,\"approve_users\":\",jie.li.sh,zhaozhifan02\",\"uuid\":\"b66fc473-81bc-4198-aca2-f8cb446cbcf5\",\"env\":\"prod\",\"status\":\"SHUTDOWN\",\"approve_type\":1,\"start_time\":\"2022-09-15 21:18:46\",\"cn_name\":\"组织架构负责人配置\",\"fuzzy\":[],\"appkey\":\"\",\"approve_status\":\"SHUTDOWN\",\"keyword\":[],\"srv\":\"\",\"end_time\":\"2022-09-15 21:19:39\",\"flow_type\":2}}";
        FlowUpdateRequest updateRequest = GsonUtils.deserialization(json, FlowUpdateRequest.class);
        boolean status = flowDataService.update(updateRequest);
        Assert.assertTrue(status);
        log.info("Sync update Search flow(id:{}) status {}", updateRequest.getId(), status);
    }

    @Test
    public void updateTestWithNullRequest() {
        boolean status = flowDataService.update(null);
        Assert.assertFalse(status);
    }

    @Test
    public void pushToMafkaTest() {
        String json = "{\"id\":22721,\"type\":\"search\",\"data\":{\"template_name\":\"org_role_admin_change\",\"create_user\":\"qinwei05\",\"create_user_name\":\"秦伟\",\"id\":22721,\"approve_users\":\",jie.li.sh\",\"uuid\":\"b66fc473-81bc-4198-aca2-f8cb446cbcf5\",\"env\":\"prod\",\"status\":\"SHUTDOWN\",\"approve_type\":1,\"start_time\":\"2022-09-15 21:18:46\",\"cn_name\":\"组织架构负责人配置\",\"fuzzy\":[],\"appkey\":\"\",\"approve_status\":\"SHUTDOWN\",\"keyword\":[],\"srv\":\"\",\"end_time\":\"2022-09-15 21:19:39\",\"flow_type\":2}}";
        FlowUpdateRequest updateRequest = GsonUtils.deserialization(json, FlowUpdateRequest.class);
        boolean status = flowDataService.asyncUpdate(updateRequest);
        Assert.assertTrue(status);
        log.info("Async update Search flow(id:{}) status {}", updateRequest.getId(), status);
    }

    @Test
    public void pushToMafkaTestWithNullRequest() {
        boolean status = flowDataService.asyncUpdate(null);
        Assert.assertFalse(status);
    }
}