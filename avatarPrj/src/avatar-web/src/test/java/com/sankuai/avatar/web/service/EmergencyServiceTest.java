package com.sankuai.avatar.web.service;

import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.emergency.EmergencyHostResource;
import com.sankuai.avatar.resource.emergency.bo.EmergencyResourceBO;
import com.sankuai.avatar.resource.emergency.bo.OnlineHostBO;
import com.sankuai.avatar.resource.emergency.constant.OperationType;
import com.sankuai.avatar.resource.emergency.request.EmergencyResourceRequestBO;
import com.sankuai.avatar.web.dto.emergency.EmergencyHostDTO;
import com.sankuai.avatar.web.dto.emergency.EmergencyResourceDTO;
import com.sankuai.avatar.web.dto.emergency.OfflineHostDTO;
import com.sankuai.avatar.web.request.EmergencyResourcePageRequest;
import com.sankuai.avatar.web.service.impl.EmergencyServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.UUID;

import static org.mockito.Mockito.*;

/**
 * @author caoyang
 * @create 2023-01-04 15:36
 */
@RunWith(MockitoJUnitRunner.class)
public class EmergencyServiceTest {

    @Mock
    private EmergencyHostResource resource;
    private EmergencyService service;

    static EmergencyResourceBO resourceBO = new EmergencyResourceBO();
    static EmergencyResourceDTO resourceDTO = new EmergencyResourceDTO();
    static {
        resourceBO.setFlowId(123);
        resourceBO.setEnv("prod");
        resourceBO.setFlowUuid(UUID.randomUUID().toString());
        resourceBO.setAppkey("test-appkey");
        resourceBO.setOperationType(OperationType.ECS_ONLINE);
        resourceBO.setCreateUser("zhangzhe");
        resourceBO.setTemplate("service_expand");
        OnlineHostBO onlineHostBO = new OnlineHostBO();
        onlineHostBO.setEnv("prod");
        onlineHostBO.setSet("-");
        onlineHostBO.setCpu(4);
        onlineHostBO.setMemory(8);
        resourceBO.setHostConfig(onlineHostBO);

        resourceDTO.setFlowId(123);
        resourceDTO.setFlowUuid(UUID.randomUUID().toString());
        resourceDTO.setAppkey("test-appkey");
        resourceDTO.setOperationType(OperationType.ECS_OFFLINE);
        resourceDTO.setCreateUser("zhangzhe");
        resourceDTO.setTemplate("reduced_service");
        OfflineHostDTO offlineHostDTO = new OfflineHostDTO();
        offlineHostDTO.setAppkey("test-appkey");
        offlineHostDTO.setEnv("test");
        EmergencyHostDTO hostDTO = new EmergencyHostDTO();
        hostDTO.setIpLan("0.0.0.0");
        hostDTO.setName("localhost");
        offlineHostDTO.setHosts(Collections.singletonList(hostDTO));
        resourceDTO.setOfflineHost(offlineHostDTO);
    }

    @Before
    public void setUp() throws Exception {
        service = new EmergencyServiceImpl(resource);
    }

    @Test
    public void queryPage() {
        PageResponse<EmergencyResourceBO> boPageResponse = new PageResponse<>();
        boPageResponse.setPage(1);
        boPageResponse.setPageSize(10);
        boPageResponse.setTotalPage(1);
        boPageResponse.setTotalCount(1);
        boPageResponse.setItems(Collections.singletonList(resourceBO));
        when(resource.queryPage(Mockito.any(EmergencyResourceRequestBO.class))).thenReturn(boPageResponse);
        PageResponse<EmergencyResourceDTO> dtoPageResponse = service.queryPage(new EmergencyResourcePageRequest());
        Assert.assertEquals(1, dtoPageResponse.getItems().size());
        Assert.assertEquals("prod", dtoPageResponse.getItems().get(0).getEnv());
        verify(resource).queryPage(Mockito.any(EmergencyResourceRequestBO.class));
    }

    @Test
    public void saveEmergencyResource() {
        when(resource.save(Mockito.any(EmergencyResourceBO.class))).thenReturn(true);
        Boolean save = service.saveEmergencyResource(resourceDTO);
        Assert.assertTrue(save);
        verify(resource).save(Mockito.any(EmergencyResourceBO.class));
    }

    @Test
    public void deleteEmergencyResourceByPk() {
        when(resource.deleteByCondition(Mockito.any(EmergencyResourceRequestBO.class))).thenReturn(true);
        Boolean delete = service.deleteEmergencyResourceByPk(1);
        Assert.assertTrue(delete);
        verify(resource).deleteByCondition(Mockito.any(EmergencyResourceRequestBO.class));
    }
}