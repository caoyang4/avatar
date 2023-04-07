package com.sankuai.avatar.resource.application;

import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.TestBase;
import com.sankuai.avatar.resource.application.bo.ApplicationRoleAdminBO;
import com.sankuai.avatar.resource.application.impl.ApplicationRoleAdminResourceImpl;
import com.sankuai.avatar.resource.application.request.ApplicationRoleAdminRequestBO;
import com.sankuai.avatar.dao.resource.repository.ApplicationRoleAdminRepository;
import com.sankuai.avatar.dao.resource.repository.model.ApplicationRoleAdminDO;
import com.sankuai.avatar.dao.resource.repository.request.ApplicationRoleAdminRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * @author caoyang
 * @create 2023-01-16 18:19
 */
public class ApplicationRoleAdminResourceTest extends TestBase {

    private ApplicationRoleAdminResource resource;

    @Mock
    private ApplicationRoleAdminRepository repository;

    static ApplicationRoleAdminDO applicationRoleAdminDO = new ApplicationRoleAdminDO();
    static ApplicationRoleAdminBO applicationRoleAdminBO = new ApplicationRoleAdminBO();
    static {
        applicationRoleAdminDO.setId(1);
        applicationRoleAdminDO.setApplicationId(1);
        applicationRoleAdminDO.setApplicationName("UnitTest");
        applicationRoleAdminDO.setApplicationCnName("单测");
        applicationRoleAdminDO.setEpAdmin("avatar");
        applicationRoleAdminDO.setOpAdmin("avatar");
        applicationRoleAdminDO.setCreateUser("avatar");
        applicationRoleAdminDO.setUpdateUser("avatar");

        applicationRoleAdminBO.setApplicationId(1);
        applicationRoleAdminBO.setApplicationName("UnitTest");
        applicationRoleAdminBO.setApplicationCnName("单测");
        applicationRoleAdminBO.setEpAdmin("avatar");
        applicationRoleAdminBO.setOpAdmin("avatar");
        applicationRoleAdminBO.setCreateUser("avatar");
        applicationRoleAdminBO.setUpdateUser("avatar");
    }


    @Before
    public void setUp() throws Exception {
        resource = new ApplicationRoleAdminResourceImpl(repository);
    }

    @Test
    public void queryPage() {
        when(repository.query(Mockito.any(ApplicationRoleAdminRequest.class))).thenReturn(Collections.singletonList(applicationRoleAdminDO));
        PageResponse<ApplicationRoleAdminBO> pageResponse = resource.queryPage(new ApplicationRoleAdminRequestBO());
        Assert.assertEquals(10, pageResponse.getPageSize());
        verify(repository).query(Mockito.any(ApplicationRoleAdminRequest.class));
    }

    @Test
    public void query(){
        when(repository.query(Mockito.any(ApplicationRoleAdminRequest.class))).thenReturn(Collections.singletonList(applicationRoleAdminDO));
        List<ApplicationRoleAdminBO> boList = resource.query(new ApplicationRoleAdminRequestBO());
        Assert.assertEquals("UnitTest", boList.get(0).getApplicationName());
        verify(repository).query(Mockito.any(ApplicationRoleAdminRequest.class));
    }

    @Test
    public void saveByUpdate() {
        when(repository.query(Mockito.any(ApplicationRoleAdminRequest.class))).thenReturn(Collections.singletonList(applicationRoleAdminDO));
        when(repository.update(Mockito.any(ApplicationRoleAdminDO.class))).thenReturn(true);
        Boolean save = resource.save(applicationRoleAdminBO);
        Assert.assertTrue(save);
        verify(repository).query(Mockito.any(ApplicationRoleAdminRequest.class));
        verify(repository).update(Mockito.any(ApplicationRoleAdminDO.class));
        verify(repository, times(0)).insert(Mockito.any(ApplicationRoleAdminDO.class));
    }

    @Test
    public void saveByInsert() {
        when(repository.query(Mockito.any(ApplicationRoleAdminRequest.class))).thenReturn(Collections.emptyList());
        when(repository.insert(Mockito.any(ApplicationRoleAdminDO.class))).thenReturn(true);
        Boolean save = resource.save(applicationRoleAdminBO);
        Assert.assertTrue(save);
        verify(repository).query(Mockito.any(ApplicationRoleAdminRequest.class));
        verify(repository).insert(Mockito.any(ApplicationRoleAdminDO.class));
        verify(repository, times(0)).update(Mockito.any(ApplicationRoleAdminDO.class));
    }

    @Test
    public void deleteByCondition() {
        Assert.assertFalse(resource.deleteByCondition(null));
        when(repository.query(Mockito.any(ApplicationRoleAdminRequest.class))).thenReturn(Collections.singletonList(applicationRoleAdminDO));
        when(repository.delete(Mockito.anyInt())).thenReturn(true);
        ApplicationRoleAdminRequestBO requestBO = new ApplicationRoleAdminRequestBO();
        requestBO.setApplicationId(1);
        Boolean delete = resource.deleteByCondition(requestBO);
        Assert.assertTrue(delete);
        verify(repository).query(Mockito.any(ApplicationRoleAdminRequest.class));
        verify(repository).delete(Mockito.anyInt());
    }
}