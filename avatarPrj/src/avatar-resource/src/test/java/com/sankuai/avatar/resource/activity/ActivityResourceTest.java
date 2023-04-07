package com.sankuai.avatar.resource.activity;

import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.TestBase;
import com.sankuai.avatar.resource.activity.bo.ActivityHostBO;
import com.sankuai.avatar.resource.activity.bo.ActivityResourceBO;
import com.sankuai.avatar.resource.activity.constant.ResourceStatusType;
import com.sankuai.avatar.resource.activity.impl.ActivityResourceImpl;
import com.sankuai.avatar.resource.activity.request.ActivityResourceRequestBO;
import com.sankuai.avatar.dao.resource.repository.ActivityResourceRepository;
import com.sankuai.avatar.dao.resource.repository.model.ActivityResourceDO;
import com.sankuai.avatar.dao.resource.repository.request.ActivityResourceRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

/**
 * @author caoyang
 * @create 2023-03-08 15:24
 */
public class ActivityResourceTest extends TestBase {

    private ActivityResource resource;

    @Mock
    private ActivityResourceRepository repository;

    static ActivityResourceDO resourceDO = new ActivityResourceDO();
    static ActivityResourceBO resourceBO = new ActivityResourceBO();
    static {
        resourceDO.setId(666);
        resourceDO.setFlowId(666);
        resourceDO.setFlowUuid(UUID.randomUUID().toString());
        resourceDO.setAppkey("test-appkey");
        resourceDO.setHostConfig("{\"env\":\"prod\",\"channel\":\"hulk\",\"channelCn\":\"HULK\",\"cluster\":\"hulk_10g\",\"clusterCn\":\"通用集群\",\"city\":\"北京\",\"region\":\"beijing\",\"count\":1,\"deliverCount\":0,\"cpu\":2,\"memory\":4,\"disk\":150,\"diskType\":\"system\",\"diskTypeCn\":\"本地磁盘\",\"idcs\":\"\",\"os\":\"CentOS 6\",\"nic\":\"nic_10g\",\"nicType\":\"common_nic\",\"set\":\"\",\"parallel\":null,\"deploy\":false,\"configExtraInfo\":\"\"}");
        resourceDO.setCreateUser("x");
        resourceDO.setStatus("CLOSE");

        resourceBO.setId(666);
        resourceBO.setFlowId(666);
        resourceBO.setFlowUuid(UUID.randomUUID().toString());
        resourceBO.setAppkey("test-appkey");
        resourceBO.setHostConfig(new ActivityHostBO());
        resourceBO.setStatus(ResourceStatusType.CLOSE);
    }



    @Before
    public void setUp() throws Exception {
        resource = new ActivityResourceImpl(repository);
    }

    @Test
    public void query() {
        when(repository.query(Mockito.any(ActivityResourceRequest.class))).thenReturn(Collections.singletonList(resourceDO));
        List<ActivityResourceBO> boList = resource.query(new ActivityResourceRequestBO());
        Assert.assertEquals("test-appkey", boList.get(0).getAppkey());
        verify(repository).query(Mockito.any(ActivityResourceRequest.class));
    }

    @Test
    public void queryPage() {
        when(repository.query(Mockito.any(ActivityResourceRequest.class))).thenReturn(Collections.singletonList(resourceDO));
        PageResponse<ActivityResourceBO> response = resource.queryPage(new ActivityResourceRequestBO());
        Assert.assertEquals(1, response.getPage());
        verify(repository).query(Mockito.any(ActivityResourceRequest.class));
    }

    @Test
    public void insert() {
        resourceBO.setId(null);
        when(repository.insert(Mockito.any(ActivityResourceDO.class))).thenReturn(true);
        Boolean save = resource.save(resourceBO);
        Assert.assertTrue(save);
        verify(repository).insert(Mockito.any(ActivityResourceDO.class));
        verify(repository, times(0)).update(Mockito.any(ActivityResourceDO.class));
    }

    @Test
    public void update() {
        resourceBO.setId(1);
        when(repository.update(Mockito.any(ActivityResourceDO.class))).thenReturn(true);
        Boolean save = resource.save(resourceBO);
        Assert.assertTrue(save);
        verify(repository).update(Mockito.any(ActivityResourceDO.class));
        verify(repository, times(0)).insert(Mockito.any(ActivityResourceDO.class));
    }

    @Test
    public void deleteByPk() {
        when(repository.delete(Mockito.anyInt())).thenReturn(true);
        Boolean delete = resource.deleteByPk(1);
        Assert.assertTrue(delete);
    }
}