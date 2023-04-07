package com.sankuai.avatar.web.service;

import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.emergency.EmergencySreResource;
import com.sankuai.avatar.resource.emergency.bo.EmergencySreBO;
import com.sankuai.avatar.resource.emergency.request.EmergencySreRequestBO;
import com.sankuai.avatar.web.dto.emergency.EmergencySreDTO;
import com.sankuai.avatar.web.request.EmergencySrePageRequest;
import com.sankuai.avatar.web.service.impl.EmergencySreServiceImpl;
import org.apache.commons.collections.CollectionUtils;
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
 * @create 2023-02-02 10:49
 */
@RunWith(MockitoJUnitRunner.class)
public class EmergencySreServiceTest {

    private EmergencySreService service;

    @Mock
    private EmergencySreResource resource;

    static EmergencySreBO emergencySreBO = new EmergencySreBO();
    static EmergencySreDTO emergencySreDTO = new EmergencySreDTO();
    static PageResponse<EmergencySreBO> pageResponse = new PageResponse<>();
    static {
        emergencySreBO.setId(1);
        emergencySreBO.setSourceId(10086);
        emergencySreBO.setTime(1);
        emergencySreBO.setState("FINISH");
        emergencySreBO.setAppkey("appkey");
        emergencySreBO.setOpAdmin("avatar");
        emergencySreBO.setAttachAdmin("mcm");
        emergencySreBO.setCreateUser("长者");

        emergencySreDTO.setId(1);
        emergencySreDTO.setSourceId(10086);
        emergencySreDTO.setTime(1);
        emergencySreDTO.setState("FINISH");
        emergencySreDTO.setAppkey("appkey");
        emergencySreDTO.setOpAdmin("avatar");
        emergencySreDTO.setAttachAdmin("mcm");
        emergencySreDTO.setCreateUser("长者");

        pageResponse.setPage(1);
        pageResponse.setPageSize(10);
        pageResponse.setTotalCount(1);
        pageResponse.setTotalPage(1);
        pageResponse.setItems(Collections.singletonList(emergencySreBO));
    }

    @Before
    public void setUp() throws Exception {
        service = new EmergencySreServiceImpl(resource);
    }

    @Test
    public void getPageEmergencySre() {
        when(resource.queryPage(Mockito.any(EmergencySreRequestBO.class))).thenReturn(pageResponse);
        PageResponse<EmergencySreDTO> dtoPageResponse = service.getPageEmergencySre(new EmergencySrePageRequest());
        Assert.assertTrue(CollectionUtils.isNotEmpty(dtoPageResponse.getItems()));
        Assert.assertEquals("FINISH", dtoPageResponse.getItems().get(0).getState());
        verify(resource).queryPage(Mockito.any(EmergencySreRequestBO.class));
    }

    @Test
    public void getEmergencySreByPk() {
        when(resource.queryPage(Mockito.any(EmergencySreRequestBO.class))).thenReturn(pageResponse);
        EmergencySreDTO dto = service.getEmergencySreByPk(1);
        Assert.assertEquals("FINISH", dto.getState());
        verify(resource).queryPage(Mockito.any(EmergencySreRequestBO.class));
    }

    @Test
    public void saveEmergencySre() {
        when(resource.save(Mockito.any(EmergencySreBO.class))).thenReturn(true);
        Boolean save = service.saveEmergencySre(emergencySreDTO);
        Assert.assertTrue(save);
        verify(resource).save(Mockito.any(EmergencySreBO.class));
    }

    @Test
    public void deleteEmergencySreByPk() {
        when(resource.deleteByCondition(Mockito.any(EmergencySreRequestBO.class))).thenReturn(true);
        Boolean delete = service.deleteEmergencySreByPk(1);
        Assert.assertTrue(delete);
        verify(resource).deleteByCondition(Mockito.any(EmergencySreRequestBO.class));
    }
}