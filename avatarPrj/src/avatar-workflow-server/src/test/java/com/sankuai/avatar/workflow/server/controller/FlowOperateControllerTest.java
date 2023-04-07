package com.sankuai.avatar.workflow.server.controller;

import com.sankuai.avatar.workflow.core.input.jumper.JumperUserUnlockInput;
import com.sankuai.avatar.workflow.server.ApplicationLoaderTest;
import com.sankuai.avatar.workflow.server.vo.flow.FlowCreateResponseVO;
import lombok.Data;
import org.junit.Assert;
import org.junit.Test;


public class FlowOperateControllerTest extends ApplicationLoaderTest {

    /**
     * 模拟创建流程的请求对象
     */
    @Data
    private static class FlowCreateRequestVO {
        private JumperUserUnlockInput input;
    }


    @Test
    public void createFlowTest() throws Exception {
        JumperUserUnlockInput unlockInput = new JumperUserUnlockInput();
        unlockInput.setLoginName("kui.xu");
        unlockInput.setType("user_unlock");

        FlowCreateRequestVO flowCreateRequestVO = new FlowCreateRequestVO();
        flowCreateRequestVO.setInput(unlockInput);

        String url = "/api/v2/avatar/workflow/flow/create/user_unlock";

        FlowCreateResponseVO createResponseVO = this.mockMvcTest.post(url, flowCreateRequestVO, FlowCreateResponseVO.class);

        Assert.assertTrue(createResponseVO.getId() > 0);
    }

    //@Test
    //@Transactional
    //public void confirmFlowTest() throws Exception {
    //    JumperUserUnlockInput unlockInput = new JumperUserUnlockInput();
    //    unlockInput.setLoginName("kui.xu");
    //    unlockInput.setType("user_unlock");
    //
    //    FlowCreateRequestVO flowCreateRequestVO = new FlowCreateRequestVO();
    //    flowCreateRequestVO.setInput(unlockInput);
    //
    //    String url = "/api/v2/avatar/workflow/flow/check/122050/confirm";
    //
    //    this.mockMvcTest.post(url, flowCreateRequestVO, null);
    //}
}