package com.sankuai.avatar.dao.workflow.repository;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableMap;
import com.sankuai.avatar.common.utils.JsonUtil;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.dao.config.DataSourceConfig;
import com.sankuai.avatar.dao.workflow.repository.entity.FlowEntity;
import com.sankuai.avatar.dao.workflow.repository.entity.FlowTemplateEntity;
import com.sankuai.avatar.dao.workflow.repository.request.FlowPublicDataUpdateRequest;
import com.sankuai.avatar.dao.workflow.repository.request.FlowQueryRequest;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.*;

@Slf4j
public class FlowRepositoryTest {

    private static final String TEST_APP_KEY = "avatar-workflow-web";

    private static final String CREATE_USER = "zhaozhifan02";

    private final FlowRepository repository;

    private final FlowTemplateRepository flowTemplateRepository;

    public FlowRepositoryTest() {
        final ApplicationContext ctx = new AnnotationConfigApplicationContext(DataSourceConfig.class);
        repository = (FlowRepository) ctx.getBean("flowRepositoryImpl");
        flowTemplateRepository = (FlowTemplateRepository) ctx.getBean("flowTemplateRepositoryImpl");
    }

    @Test
    public void testGetFlowEntityById() {
        Integer flowId = 109107;
        FlowEntity flowEntity = repository.getFlowEntityById(flowId);
        Assert.assertNotNull(flowEntity);
        log.info("Flow id:{} data {}", flowId, flowEntity);
    }

    @Test
    public void testGetFlowEntityByNoExistId() {
        Integer flowId = 0;
        FlowEntity flowEntity = repository.getFlowEntityById(flowId);
        Assert.assertNull(flowEntity);
        log.info("Flow id:{} data {}", flowId, flowEntity);
    }

    @Test
    public void testGetFlowEntityByUuid() {
        String uuid = "c18beb2e-e752-465b-a85e-6618f211fdf2";
        FlowEntity flowEntity = repository.getFlowEntityByUuid(uuid);
        Assert.assertNotNull(flowEntity);
        log.info("Flow uuid:{} data {}", uuid, flowEntity);
    }

    @Test
    public void testGetFlowEntityByEmptyUuid() {
        String uuid = "";
        FlowEntity flowEntity = repository.getFlowEntityByUuid(uuid);
        Assert.assertNull(flowEntity);
        log.info("Flow uuid:{} data {}", uuid, flowEntity);
    }

    @Test
    public void testAddFlowWithEmptyEntity() {
        boolean status = repository.addFlow(null);
        Assert.assertFalse(status);
    }

    @Test
    public void testAddFlow() {
        String templateName = "delegate_work";
        FlowTemplateEntity flowTemplateEntity = flowTemplateRepository.getFlowTemplateByName(templateName);
        FlowEntity flowEntity = FlowEntity.builder()
                .status("SUCCESS")
                .processIndex(0)
                .uuid(UUID.randomUUID().toString())
                .index(0)
                .createUser(CREATE_USER)
                .templateId(flowTemplateEntity.getId())
                .templateName(flowTemplateEntity.getTemplateName())
                .cnName(flowTemplateEntity.getCnName())
                .tasks(flowTemplateEntity.getTasks())
                .config(flowTemplateEntity.getConfig())
                .input(getDelegateWorkInput())
                .version(flowTemplateEntity.getVersion())
                .build();
        boolean status = repository.addFlow(flowEntity);
        Assert.assertTrue(status);
    }

    @Test
    public void testUpdateFlow() {
        FlowPublicData publicData = new FlowPublicData();
        publicData.setDisplay(getFlowDisplay());
        FlowEntity flowEntity = FlowEntity.builder()
                .id(110850)
                .status("SUCCESS")
                .build();
        boolean status = repository.updateFlow(flowEntity);
        Assert.assertTrue(status);
    }

    @Test
    public void testUpdateFlowByEmptyPrimaryKey() {
        FlowPublicData publicData = new FlowPublicData();
        publicData.setDisplay(getFlowDisplay());
        FlowEntity flowEntity = FlowEntity.builder()
                .status("SUCCESS")
                .build();
        boolean status = repository.updateFlow(flowEntity);
        Assert.assertFalse(status);
    }


    @Test
    public void testUpdatePublicData() {
        FlowPublicData publicData = new FlowPublicData();
        publicData.setDisplay(getFlowDisplay());
        FlowPublicDataUpdateRequest request = FlowPublicDataUpdateRequest.builder().flowId(110850).publicData(publicData).build();
        boolean status = repository.updatePublicData(request);
        Assert.assertTrue(status);
    }

    @Test(expected = NullPointerException.class)
    public void testUpdatePublicDataByEmptyFlowId() {
        FlowPublicData publicData = new FlowPublicData();
        publicData.setDisplay(getFlowDisplay());
        FlowPublicDataUpdateRequest request = FlowPublicDataUpdateRequest.builder().publicData(publicData).build();
        boolean status = repository.updatePublicData(request);
        Assert.assertFalse(status);
    }

    private FlowDisplay getFlowDisplay() {
        InputDisplay displayKv1 = new InputDisplay();
        displayKv1.setKey("委托人");
        displayKv1.setValue("zhaozhifan02");
        InputDisplay displayKv2 = new InputDisplay();
        displayKv2.setKey("结束时间");
        displayKv2.setValue("2023-01-29 00:00:00");
        List<InputDisplay> inputDisplayList = Arrays.asList(
                displayKv1, displayKv2
        );
        FlowDisplay flowDisplay = new FlowDisplay();
        flowDisplay.setInput(inputDisplayList);
        return flowDisplay;
    }

    private DelegateWorkInput getDelegateWorkInput() {
        Map<String, Object> input = ImmutableMap.of(
                "agent", Collections.singletonList(CREATE_USER),
                "end_time", "2023-01-13 00:00:00",
                "comment", "test",
                "user", CREATE_USER);
        try {
            return JsonUtil.mapToBean(input, DelegateWorkInput.class);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Test
    public void queryPage() {
        FlowQueryRequest request = new FlowQueryRequest();
        request.setAppkey("com.sankuai.avatar.web");
        PageResponse<FlowEntity> entityList = repository.queryPage(request);
        Assert.assertEquals(1, entityList.getPage());
    }


    @Data
    public class UpdateServiceTestPrincipalInput {
        /**
         * 测试负责人
         */
        @NotBlank
        String epAdmin;

        /**
         * 服务名称
         */
        String serviceName;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class DelegateWorkInput {
        /**
         * 委托人
         */
        @NotEmpty(message = "委托人不能为空")
        List<String> agent;

        /**
         * 备注
         */
        String comment;

        /**
         * 委托时间
         */
        @JsonProperty("end_time")
        String endTime;

        String user;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    private class FlowPublicData {
        private FlowDisplay display;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class FlowDisplay {
        /**
         * 申请信息
         */
        List<InputDisplay> input = Collections.emptyList();

        /**
         * 风险提示信息
         */
        List<TextDisplay> text = Collections.emptyList();
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class InputDisplay {
        /**
         * field
         */
        String fieldName;
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
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class TextDisplay {
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
