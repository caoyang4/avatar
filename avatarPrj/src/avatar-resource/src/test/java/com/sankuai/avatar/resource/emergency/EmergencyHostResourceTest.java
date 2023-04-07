package com.sankuai.avatar.resource.emergency;

import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.TestBase;
import com.sankuai.avatar.dao.resource.repository.EmergencyResourceRepository;
import com.sankuai.avatar.dao.resource.repository.model.EmergencyResourceDO;
import com.sankuai.avatar.dao.resource.repository.request.EmergencyResourceRequest;
import com.sankuai.avatar.resource.emergency.bo.EmergencyResourceBO;
import com.sankuai.avatar.resource.emergency.constant.OperationType;
import com.sankuai.avatar.resource.emergency.impl.EmergencyHostResourceImpl;
import com.sankuai.avatar.resource.emergency.request.EmergencyResourceRequestBO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.UUID;
import static org.mockito.Mockito.*;
/**
 * @author caoyang
 * @create 2022-12-02 23:26
 */
public class EmergencyHostResourceTest extends TestBase {

    @Mock
    private EmergencyResourceRepository repository;
    private EmergencyHostResource resource;

    static EmergencyResourceDO resourceDO = new EmergencyResourceDO();
    static EmergencyResourceBO resourceBO = new EmergencyResourceBO();
    static {
        resourceDO.setId(1);
        resourceDO.setFlowId(123);
        resourceDO.setFlowUuid(UUID.randomUUID().toString());
        resourceDO.setAppkey("test-appkey");
        resourceDO.setOperationType("ECS_ONLINE");
        resourceDO.setCreateUser("zhangzhe");
        resourceDO.setTemplate("service_expand");

        resourceBO.setFlowId(123);
        resourceBO.setFlowUuid(UUID.randomUUID().toString());
        resourceBO.setAppkey("test-appkey");
        resourceBO.setOperationType(OperationType.ECS_ONLINE);
        resourceBO.setCreateUser("zhangzhe");
        resourceBO.setTemplate("service_expand");
    }


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        resource = new EmergencyHostResourceImpl(repository);
    }

    @Test
    public void queryPage() {
        when(repository.query(Mockito.any(EmergencyResourceRequest.class))).thenReturn(Collections.singletonList(resourceDO));
        PageResponse<EmergencyResourceBO> response = resource.queryPage(EmergencyResourceRequestBO.builder().build());
        Assert.assertEquals(1, response.getPage());
        verify(repository).query(Mockito.any(EmergencyResourceRequest.class));
    }

    @Test
    public void save() {
        when(repository.insert(Mockito.any(EmergencyResourceDO.class))).thenReturn(true);
        Boolean save = resource.save(resourceBO);
        Assert.assertTrue(save);
        verify(repository).insert(Mockito.any(EmergencyResourceDO.class));
    }

    @Test
    public void deleteByCondition() {
        when(repository.query(Mockito.any(EmergencyResourceRequest.class))).thenReturn(Collections.singletonList(resourceDO));
        when(repository.delete(Mockito.anyInt())).thenReturn(true);
        Boolean delete = resource.deleteByCondition(EmergencyResourceRequestBO.builder().id(1).build());
        Assert.assertTrue(delete);
        verify(repository).delete(Mockito.anyInt());
    }

}