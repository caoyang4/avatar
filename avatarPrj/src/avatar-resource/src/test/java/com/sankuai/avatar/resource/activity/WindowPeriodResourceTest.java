package com.sankuai.avatar.resource.activity;

import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.TestBase;
import com.sankuai.avatar.resource.activity.bo.WindowPeriodResourceBO;
import com.sankuai.avatar.resource.activity.impl.WindowPeriodResourceImpl;
import com.sankuai.avatar.resource.activity.request.WindowPeriodRequestBO;
import com.sankuai.avatar.dao.resource.repository.WindowPeriodRepository;
import com.sankuai.avatar.dao.resource.repository.model.ResourceWindowPeriodDO;
import com.sankuai.avatar.dao.resource.repository.request.WindowPeriodRequest;
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
 * @create 2023-03-15 17:00
 */
public class WindowPeriodResourceTest extends TestBase {

    private WindowPeriodResource resource;

    @Mock
    private WindowPeriodRepository repository;

    @Before
    public void setUp() throws Exception {
        resource = new WindowPeriodResourceImpl(repository);
    }

    @Test
    public void queryPage() {
        when(repository.query(Mockito.any(WindowPeriodRequest.class))).thenReturn(Collections.singletonList(new ResourceWindowPeriodDO()));
        PageResponse<WindowPeriodResourceBO> pageResponse = resource.queryPage(new WindowPeriodRequestBO());
        Assert.assertNotNull(pageResponse);
        verify(repository).query(Mockito.any(WindowPeriodRequest.class));
    }

    @Test
    public void query() {
        when(repository.query(Mockito.any(WindowPeriodRequest.class))).thenReturn(Collections.singletonList(new ResourceWindowPeriodDO()));
        List<WindowPeriodResourceBO> boList = resource.query(new WindowPeriodRequestBO());
        Assert.assertEquals(1, boList.size());
        verify(repository).query(Mockito.any(WindowPeriodRequest.class));
    }

    @Test
    public void getMaxWindowId(){
        when(repository.getMaxWindowId()).thenReturn(1);
        Integer windowId = resource.getMaxWindowId();
        Assert.assertEquals(1, windowId.intValue());
        verify(repository).getMaxWindowId();
    }

    @Test
    public void insert() {
        when(repository.insert(Mockito.any(ResourceWindowPeriodDO.class))).thenReturn(true);
        Boolean save = resource.save(new WindowPeriodResourceBO());
        Assert.assertTrue(save);
        verify(repository).insert(Mockito.any(ResourceWindowPeriodDO.class));
        verify(repository, times(0)).update(Mockito.any(ResourceWindowPeriodDO.class));
    }

    @Test
    public void update() {
        when(repository.update(Mockito.any(ResourceWindowPeriodDO.class))).thenReturn(true);
        WindowPeriodResourceBO bo = new WindowPeriodResourceBO();
        bo.setId(1);
        Boolean save = resource.save(bo);
        Assert.assertTrue(save);
        verify(repository).update(Mockito.any(ResourceWindowPeriodDO.class));
        verify(repository, times(0)).insert(Mockito.any(ResourceWindowPeriodDO.class));
    }

    @Test
    public void deleteByPk() {
        when(repository.delete(Mockito.anyInt())).thenReturn(true);
        Boolean delete = resource.deleteByPk(1);
        Assert.assertTrue(delete);
        verify(repository).delete(Mockito.anyInt());
    }
}