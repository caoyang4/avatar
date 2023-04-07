package com.sankuai.avatar.resource.emergency;

import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.TestBase;
import com.sankuai.avatar.dao.resource.repository.EmergencySreRepository;
import com.sankuai.avatar.dao.resource.repository.model.EmergencySreDO;
import com.sankuai.avatar.dao.resource.repository.request.EmergencySreRequest;
import com.sankuai.avatar.resource.emergency.bo.EmergencySreBO;
import com.sankuai.avatar.resource.emergency.impl.EmergencySreResourceImpl;
import com.sankuai.avatar.resource.emergency.request.EmergencySreRequestBO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Collections;

import static org.mockito.Mockito.*;

/**
 * @author caoyang
 * @create 2023-01-20 18:13
 */
public class EmergencySreResourceTest extends TestBase {

    private EmergencySreResource resource;

    @Mock
    private EmergencySreRepository repository;

    static EmergencySreDO emergencySreDO = new EmergencySreDO();
    static EmergencySreBO emergencySreBO = new EmergencySreBO();
    static {
        emergencySreDO.setId(1);
        emergencySreDO.setSourceId(10086);
        emergencySreDO.setTime(1);
        emergencySreDO.setState("FINISH");
        emergencySreDO.setAppkey("appkey");
        emergencySreDO.setOpAdmin("avatar");
        emergencySreDO.setAttachAdmin("mcm");
        emergencySreDO.setCreateUser("长者");

        emergencySreBO.setId(1);
        emergencySreBO.setSourceId(10086);
        emergencySreBO.setTime(1);
        emergencySreBO.setState("FINISH");
        emergencySreBO.setAppkey("appkey");
        emergencySreBO.setOpAdmin("avatar");
        emergencySreBO.setAttachAdmin("mcm");
        emergencySreBO.setCreateUser("长者");
    }

    @Before
    public void setUp() throws Exception {
        resource = new EmergencySreResourceImpl(repository);
    }

    @Test
    public void queryPage() {
        when(repository.query(Mockito.any(EmergencySreRequest.class))).thenReturn(Collections.singletonList(emergencySreDO));
        EmergencySreRequestBO requestBO = new EmergencySreRequestBO();
        PageResponse<EmergencySreBO> pageResponse = resource.queryPage(requestBO);
        Assert.assertEquals(10, pageResponse.getPageSize());
        verify(repository).query(Mockito.any(EmergencySreRequest.class));
    }

    @Test
    public void saveByInsert() {
        when(repository.query(Mockito.any(EmergencySreRequest.class))).thenReturn(Collections.emptyList());
        when(repository.insert(Mockito.any(EmergencySreDO.class))).thenReturn(true);
        Boolean save = resource.save(emergencySreBO);
        Assert.assertTrue(save);
        verify(repository).insert(Mockito.any(EmergencySreDO.class));
        verify(repository, times(0)).update(Mockito.any(EmergencySreDO.class));
    }

    @Test
    public void saveByUpdate() {
        when(repository.query(Mockito.any(EmergencySreRequest.class))).thenReturn(Collections.singletonList(emergencySreDO));
        when(repository.update(Mockito.any(EmergencySreDO.class))).thenReturn(true);
        Boolean save = resource.save(emergencySreBO);
        Assert.assertTrue(save);
        verify(repository).update(Mockito.any(EmergencySreDO.class));
        verify(repository, times(0)).insert(Mockito.any(EmergencySreDO.class));
    }

    @Test
    public void deleteByCondition() {
        EmergencySreRequestBO requestBO = new EmergencySreRequestBO();
        Assert.assertFalse(resource.deleteByCondition(null));
        when(repository.query(Mockito.any(EmergencySreRequest.class))).thenReturn(Collections.singletonList(emergencySreDO));
        when(repository.delete(Mockito.anyInt())).thenReturn(true);
        requestBO.setAppkey("appkey");
        Boolean delete = resource.deleteByCondition(requestBO);
        Assert.assertTrue(delete);
        verify(repository).delete(Mockito.anyInt());
    }
}