package com.sankuai.avatar.dao.workflow.repository;

import com.sankuai.avatar.common.utils.JsonUtil;
import com.sankuai.avatar.dao.workflow.repository.entity.FlowDataEntity;
import com.sankuai.avatar.dao.config.DataSourceConfig;
import com.sankuai.avatar.dao.workflow.repository.request.FlowCheckResultAddRequest;
import com.sankuai.avatar.dao.workflow.repository.request.FlowDataAddRequest;
import com.sankuai.avatar.dao.workflow.repository.request.FlowDataUpdateRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class FlowDataRepositoryTest {

    private final FlowDataRepository repository;

    public FlowDataRepositoryTest() {
        final ApplicationContext ctx = new AnnotationConfigApplicationContext(DataSourceConfig.class);
        repository = (FlowDataRepository) ctx.getBean("flowDataRepositoryImpl");
    }

    @Test
    public void testAddFlowData() {
        FlowDataAddRequest request = FlowDataAddRequest.builder()
                .flowId(1)
                .input("{}")
                .resource("{}")
                .build();
        boolean status = repository.addFlowData(request);
        log.info("flow(id:{}) save data(id:{}) status:{}", request.getFlowId(), request.getId(), status);
        Assert.assertTrue(status);
    }

    @Test(expected = NullPointerException.class)
    public void testAddFlowDataByEmptyFlowId() {
        FlowDataAddRequest request = FlowDataAddRequest.builder()
                .input("{}")
                .resource("{}")
                .build();
        boolean status = repository.addFlowData(request);
        log.info("flow(id:{}) save data(id:{}) status:{}", request.getFlowId(), request.getId(), status);
        Assert.assertFalse(status);
    }

    @Test
    public void testUpdateFlowData() {
        FlowDataUpdateRequest request = FlowDataUpdateRequest.builder()
                .id(5)
                .flowId(1)
                .input("")
                .build();
        boolean status = repository.updateFlowData(request);
        log.info("flow(id:{}) update data(id:{}) status:{}", request.getFlowId(), request.getId(), status);
        Assert.assertTrue(status);
    }

    @Test(expected = NullPointerException.class)
    public void testUpdateFlowDataByEmptyPK() {
        FlowDataUpdateRequest request = FlowDataUpdateRequest.builder()
                .flowId(1)
                .input("")
                .build();
        boolean status = repository.updateFlowData(request);
        log.info("flow(id:{}) update data(id:{}) status:{}", request.getFlowId(), request.getId(), status);
        Assert.assertFalse(status);
    }

    @Test
    public void testUpdateFlowDataByFlowId() throws Exception {
        Map<String, Object> publicData = new HashMap<>();
        publicData.put("login_name", "zhaozhifan02");
        publicData.put("type", "user_unlock");
        FlowDataUpdateRequest request = FlowDataUpdateRequest.builder()
                .flowId(10)
                .publicData(JsonUtil.mapToJson(publicData))
                .build();
        boolean status = repository.updateFlowDataByFlowId(request);
        log.info("flow(id:{}) update data(id:{}) status:{}", request.getFlowId(), request.getId(), status);
        Assert.assertTrue(status);
    }

    @Test(expected = NullPointerException.class)
    public void testUpdateFlowDataByEmptyFlowId() throws Exception {
        Map<String, Object> publicData = new HashMap<>();
        publicData.put("login_name", "zhaozhifan02");
        publicData.put("type", "user_unlock");
        FlowDataUpdateRequest request = FlowDataUpdateRequest.builder()
                .publicData(JsonUtil.mapToJson(publicData))
                .build();
        boolean status = repository.updateFlowDataByFlowId(request);
        log.info("flow(id:{}) update data(id:{}) status:{}", request.getFlowId(), request.getId(), status);
        Assert.assertFalse(status);
    }

    @Test
    public void testGetFlowDataByFlowId() {
        Integer flowId = 10;
        FlowDataEntity flowDataEntity = repository.getFlowDataByFlowId(flowId);
        log.info("get flow(id:{}) data {}", flowId, flowDataEntity);
        Assert.assertNotNull(flowDataEntity);
    }

    @Test
    public void testGetFlowDataByNotExistFlowId() {
        Integer flowId = 0;
        FlowDataEntity flowDataEntity = repository.getFlowDataByFlowId(flowId);
        log.info("get flow(id:{}) data {}", flowId, flowDataEntity);
        Assert.assertNull(flowDataEntity);
    }

    @Test(expected = NullPointerException.class)
    public void testGetFlowDataByEmptyFlowId() {
        Integer flowId = null;
        FlowDataEntity flowDataEntity = repository.getFlowDataByFlowId(flowId);
        log.info("get flow(id:{}) data {}", flowId, flowDataEntity);
        Assert.assertNull(flowDataEntity);
    }

    @Test
    public void testAddFlowCheckResult() {
        Integer flowId = 10;
        FlowCheckResultAddRequest request = FlowCheckResultAddRequest.builder()
                .flowId(flowId)
                .checkerResult("{\"checkState\":\"PRE_CHECK_ACCEPTED\",\"checkResults\":null}")
                .build();
        boolean status = repository.addFlowCheckResult(request);
        log.info("update flow(id:{}) checkerResult {}", flowId, status);
        Assert.assertTrue(status);
    }

    @Test(expected = NullPointerException.class)
    public void testAddFlowCheckResultByEmptyFlowId() {
        FlowCheckResultAddRequest request = FlowCheckResultAddRequest.builder()
                .checkerResult("{\"checkState\":\"PRE_CHECK_ACCEPTED\",\"checkResults\":null}")
                .build();
        boolean status = repository.addFlowCheckResult(request);
        Assert.assertFalse(status);
    }
}