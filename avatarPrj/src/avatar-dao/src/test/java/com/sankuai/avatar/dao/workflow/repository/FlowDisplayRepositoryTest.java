package com.sankuai.avatar.dao.workflow.repository;

import com.sankuai.avatar.common.utils.JsonUtil;
import com.sankuai.avatar.dao.workflow.repository.entity.FlowDisplayEntity;
import com.sankuai.avatar.dao.config.DataSourceConfig;
import com.sankuai.avatar.dao.workflow.repository.request.FlowDisplayAddRequest;
import com.sankuai.avatar.dao.workflow.repository.request.FlowDisplayUpdateRequest;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Slf4j
public class FlowDisplayRepositoryTest {
    private final FlowDisplayRepository repository;

    public FlowDisplayRepositoryTest() {
        final ApplicationContext ctx = new AnnotationConfigApplicationContext(DataSourceConfig.class);
        repository = (FlowDisplayRepository) ctx.getBean("flowDisplayRepositoryImpl");
    }

    @Test
    public void testAddFlowDisplay() {
        FlowDisplayAddRequest request = FlowDisplayAddRequest.builder()
                .flowId(1)
                .input(getDisplayInput())
                .build();
        boolean status = repository.addFlowDisplay(request);
        log.info("flow(id:{}) save display(id:{}) status:{}", request.getFlowId(), request.getId(), status);
        Assert.assertTrue(status);
    }

    @Test
    public void testUpdateFlowDisplay() {
        FlowDisplayUpdateRequest request = FlowDisplayUpdateRequest.builder()
                .id(12)
                .flowId(1)
                .input(getDisplayInput())
                .text(getDisplayText())
                .build();
        boolean status = repository.updateFlowDisplay(request);
        log.info("flow(id:{}) update display(id:{}) status:{}", request.getFlowId(), request.getId(), status);
        Assert.assertTrue(status);
    }

    @Test
    public void testGetFlowDisplayByFlowId() {
        Integer flowId = 1;
        FlowDisplayEntity flowDisplayEntity = repository.getFlowDisplayByFlowId(flowId);
        log.info("get flow(id:{}) display data {}", flowId, flowDisplayEntity);
        Assert.assertNotNull(flowDisplayEntity);
    }

    @Test(expected = NullPointerException.class)
    public void testGetFlowDisplayByEmptyFlowId() {
        Integer flowId = null;
        FlowDisplayEntity flowDisplayEntity = repository.getFlowDisplayByFlowId(flowId);
        log.info("get flow(id:{}) display data {}", flowId, flowDisplayEntity);
        Assert.assertNull(flowDisplayEntity);
    }

    private String getDisplayInput() {
        InputDisplay input = new InputDisplay();
        input.setKey("服务");
        input.setValue("com.sankuai.avatar.web");
        return JsonUtil.bean2Json(input);
    }

    private String getDisplayText() {
        TextDisplay text = new TextDisplay();
        text.setContent("风险检查不通过");
        text.setType("error");
        return JsonUtil.bean2Json(text);
    }

    @Data
    public static class InputDisplay {
        /**
         * 名称
         */
        String key;
        /**
         * 值
         */
        String value;
    }

    @Data
    public static class TextDisplay {
        /**
         * 风险内容
         */
        String content;

        /**
         * 风险提示等级
         */
        String type;
    }
}