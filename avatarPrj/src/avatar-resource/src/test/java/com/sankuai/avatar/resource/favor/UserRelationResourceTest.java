package com.sankuai.avatar.resource.favor;

import com.sankuai.avatar.resource.TestBase;
import com.sankuai.avatar.dao.resource.repository.UserRelationRepository;
import com.sankuai.avatar.dao.resource.repository.model.UserRelationDO;
import com.sankuai.avatar.dao.resource.repository.request.UserRelationRequest;
import com.sankuai.avatar.resource.favor.bo.UserRelationBO;
import com.sankuai.avatar.resource.favor.impl.UserRelationResourceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
/**
 * @author caoyang
 * @create 2022-12-27 16:53
 */
public class UserRelationResourceTest extends TestBase {

    @Mock
    private UserRelationRepository repository;

    private UserRelationResource resource;

    static UserRelationDO userRelationDO = new UserRelationDO();
    static {
        userRelationDO.setAppkey("test-appkey");
        userRelationDO.setLoginName("unit");
        userRelationDO.setTag("top");
        userRelationDO.setId(1);
        userRelationDO.setUpdateTime(new Date());
    }
    static UserRelationBO userRelationBO;
    static {
        userRelationBO = UserRelationBO.builder()
                .appkey("test-appkey")
                .loginName("unit")
                .tag("top")
                .build();
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        resource = new UserRelationResourceImpl(repository);
    }

    @Test
    public void getUserTopAppkey() {
        when(repository.query(Mockito.any(UserRelationRequest.class))).thenReturn(Collections.singletonList(userRelationDO));
        List<String> topAppkey = resource.getUserTopAppkey("x");
        Assert.assertEquals(1, topAppkey.size());
        Assert.assertEquals("test-appkey", topAppkey.get(0));
    }

    @Test
    public void saveUserRelationForInsert() {
        Assert.assertFalse(resource.saveUserRelation(null));
        when(repository.query(Mockito.any(UserRelationRequest.class))).thenReturn(Collections.emptyList());
        when(repository.insert(Mockito.any(UserRelationDO.class))).thenReturn(true);
        Boolean save = resource.saveUserRelation(userRelationBO);
        Assert.assertTrue(save);
        verify(repository).insert(Mockito.any(UserRelationDO.class));
        verify(repository,times(0)).update(Mockito.any(UserRelationDO.class));
    }

    @Test
    public void saveUserRelationForUpdate() {
        when(repository.query(Mockito.any(UserRelationRequest.class))).thenReturn(Collections.singletonList(userRelationDO));
        when(repository.update(Mockito.any(UserRelationDO.class))).thenReturn(true);
        Boolean save = resource.saveUserRelation(userRelationBO);
        Assert.assertTrue(save);
        verify(repository, times(0)).insert(Mockito.any(UserRelationDO.class));
        verify(repository).update(Mockito.any(UserRelationDO.class));
    }

    @Test
    public void cancelUserRelation() {
        when(repository.query(Mockito.any(UserRelationRequest.class))).thenReturn(Collections.singletonList(userRelationDO));
        when(repository.delete(Mockito.anyInt())).thenReturn(true);
        Boolean cancel = resource.cancelUserRelation("x", "z");
        Assert.assertTrue(cancel);
        verify(repository).delete(Mockito.anyInt());
    }
}