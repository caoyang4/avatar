package com.sankuai.avatar.workflow.server.controller;

import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.workflow.server.ApplicationLoaderTest;
import com.sankuai.avatar.workflow.server.vo.flow.FlowHomeVO;
import com.sankuai.avatar.workflow.server.vo.flow.FlowVO;
import org.junit.Assert;
import org.junit.Test;


/**
 * 流程查询接口单元测试
 *
 * @author kui.xu
 * @date 2023/03/02
 */
public class FlowQueryControllerTest extends ApplicationLoaderTest {

    @Test
    public void getFlowTest() throws Exception {
        String url = "/api/v2/avatar/workflow/flow/73382b83-6c55-4262-97ad-dac57c540361";
        FlowVO flowVO = this.mockMvcTest.get(url, FlowVO.class);
        Assert.assertEquals(flowVO.getUuid(), "73382b83-6c55-4262-97ad-dac57c540361");
    }

    @Test
    public void getAllFlow() throws Exception{
        String url = "/api/v2/avatar/workflow/flow?pageSize=1";
        PageResponse<FlowHomeVO> pageResponse = mockMvcTest.get(url, PageResponse.class);
        Assert.assertNotNull(pageResponse);
    }

    @Test
    public void getMineFlow() throws Exception{
        String url = "/api/v2/avatar/workflow/flow/apply?createUser=caoyang42&pageSize=1";
        PageResponse<FlowHomeVO> pageResponse = mockMvcTest.get(url, PageResponse.class);
        Assert.assertNotNull(pageResponse);
    }

    @Test
    public void getApprovedFlow() throws Exception{
        String url = "/api/v2/avatar/workflow/flow/approve?createUser=caoyang42&pageSize=1";
        PageResponse<FlowHomeVO> pageResponse = mockMvcTest.get(url, PageResponse.class);
        Assert.assertNotNull(pageResponse);
    }
}
