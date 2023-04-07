package com.sankuai.avatar.workflow.core.notify.builder.templates;

import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.context.FlowResource;
import com.sankuai.avatar.workflow.core.notify.builder.TemplateParser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TemplateParserTest {
    private TemplateParser templateParser;

    @Before
    public void setUp() throws Exception {
        templateParser = new TemplateParser();
        templateParser.init();
    }

    @Test
    public void testParse(){
        FlowContext flowContext = FlowContext.builder()
                .id(12)
                .cnName("新增机器")
                .resource(FlowResource.builder().appkey("com.sankuai.avatar.web").build())
                .createUser("jie")
                .uuid("uuid")
                .build();
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("title", "您有一个变更已发起");
        params.put("flowContext", flowContext);
        params.put("avatarDomain", "https://avatar.mws.sankuai.com");
        params.put("errorMsgList", Arrays.asList(
                "HostsReboot 执行发生了异常: 私有云主机状态错误",
                "HostsReboot 执行发生了异常: 遇到错误:超时导致重启失败"
        ));
        String content = templateParser.parse("flow_default.ftl", params);
        Assert.assertNotNull(content);
    }

    @Test
    public void testParseNoAppkey(){
        FlowContext flowContext = FlowContext.builder()
                .id(12)
                .cnName("新增机器")
                .createUser("jie")
                .createUserName("中文名")
                .uuid("uuid")
                .build();
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("title", "您有一个变更已发起");
        params.put("flowContext", flowContext);
        params.put("avatarDomain", "https://avatar.mws.sankuai.com");
        String content = templateParser.parse("flow_default.ftl", params);
        Assert.assertNotNull(content);
    }

}
