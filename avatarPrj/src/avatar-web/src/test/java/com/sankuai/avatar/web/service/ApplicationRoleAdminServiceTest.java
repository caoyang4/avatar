package com.sankuai.avatar.web.service;

import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.application.ApplicationRoleAdminResource;
import com.sankuai.avatar.resource.application.bo.ApplicationRoleAdminBO;
import com.sankuai.avatar.resource.application.request.ApplicationRoleAdminRequestBO;
import com.sankuai.avatar.web.dto.application.ApplicationRoleAdminDTO;
import com.sankuai.avatar.web.request.ApplicationRoleAdminPageRequest;
import com.sankuai.avatar.web.service.impl.ApplicationRoleAdminServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
/**
 * @author caoyang
 * @create 2023-01-16 19:19
 */
@RunWith(MockitoJUnitRunner.class)
public class ApplicationRoleAdminServiceTest {

    private ApplicationRoleAdminService service;
    @Mock
    private ApplicationRoleAdminResource resource;

    static ApplicationRoleAdminBO bo = new ApplicationRoleAdminBO();
    static ApplicationRoleAdminDTO dto = new ApplicationRoleAdminDTO();
    static PageResponse<ApplicationRoleAdminBO> boPageResponse = new PageResponse<>();
    static {
        bo.setApplicationId(1);
        bo.setApplicationName("UnitTest");
        bo.setApplicationCnName("单测");
        bo.setEpAdmin("avatar");
        bo.setOpAdmin("avatar");
        bo.setCreateUser("avatar");
        bo.setUpdateUser("avatar");

        dto.setApplicationId(1);
        dto.setApplicationName("UnitTest");
        dto.setApplicationCnName("单测");
        dto.setEpAdmin("avatar");
        dto.setOpAdmin("avatar");
        dto.setCreateUser("avatar");
        dto.setUpdateUser("avatar");

        boPageResponse.setPage(1);
        boPageResponse.setPageSize(10);
        boPageResponse.setTotalPage(1);
        boPageResponse.setTotalCount(1);
    }

    @Before
    public void setUp() throws Exception {
        service = new ApplicationRoleAdminServiceImpl(resource);
    }

    @Test
    public void getPageApplicationRoleAdmin() {
        boPageResponse.setItems(Collections.singletonList(bo));
        when(resource.queryPage(Mockito.any(ApplicationRoleAdminRequestBO.class))).thenReturn(boPageResponse);
        PageResponse<ApplicationRoleAdminDTO> dtoPageResponse = service.getPageApplicationRoleAdmin(new ApplicationRoleAdminPageRequest());
        Assert.assertEquals("UnitTest", dtoPageResponse.getItems().get(0).getApplicationName());
        verify(resource).queryPage(Mockito.any(ApplicationRoleAdminRequestBO.class));
    }

    @Test
    public void getByApplicationId() {
        when(resource.query(Mockito.any(ApplicationRoleAdminRequestBO.class))).thenReturn(Collections.singletonList(bo));
        ApplicationRoleAdminDTO adminDTO = service.getByApplicationId(1);
        Assert.assertEquals("UnitTest", adminDTO.getApplicationName());
        verify(resource).query(Mockito.any(ApplicationRoleAdminRequestBO.class));
    }

    @Test
    public void getByApplicationName() {
        when(resource.query(Mockito.any(ApplicationRoleAdminRequestBO.class))).thenReturn(Collections.singletonList(bo));
        ApplicationRoleAdminDTO adminDTO = service.getByApplicationName("unit");
        Assert.assertEquals("UnitTest", adminDTO.getApplicationName());
        verify(resource).query(Mockito.any(ApplicationRoleAdminRequestBO.class));
    }

    @Test
    public void deleteApplicationRoleAdminByPk() {
        when(resource.deleteByCondition(Mockito.any(ApplicationRoleAdminRequestBO.class))).thenReturn(true);
        Boolean delete = service.deleteApplicationRoleAdminByPk(1);
        Assert.assertTrue(delete);
        verify(resource).deleteByCondition(Mockito.any(ApplicationRoleAdminRequestBO.class));
    }

    @Test
    public void saveApplicationRoleAdmin() {
        when(resource.save(Mockito.any(ApplicationRoleAdminBO.class))).thenReturn(true);
        Boolean save = service.saveApplicationRoleAdmin(dto);
        Assert.assertTrue(save);
        verify(resource).save(Mockito.any(ApplicationRoleAdminBO.class));
    }

}