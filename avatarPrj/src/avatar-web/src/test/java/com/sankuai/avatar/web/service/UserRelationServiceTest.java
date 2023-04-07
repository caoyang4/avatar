package com.sankuai.avatar.web.service;

import com.sankuai.avatar.resource.favor.UserRelationResource;
import com.sankuai.avatar.resource.favor.bo.UserRelationBO;
import com.sankuai.avatar.web.service.impl.UserRelationServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

/**
 * @author caoyang
 * @create 2022-12-27 17:22
 */
@RunWith(MockitoJUnitRunner.class)
public class UserRelationServiceTest  {

    @Mock
    private UserRelationResource resource;

    private UserRelationService service;

    @Before
    public void setUp() throws Exception {
        service = new UserRelationServiceImpl(resource);
    }

    @Test
    public void saveUserTopAppkey() {
        when(resource.saveUserRelation(Mockito.any(UserRelationBO.class))).thenReturn(true);
        Boolean save = service.saveUserTopAppkey("x", "z");
        Assert.assertTrue(save);
        verify(resource).saveUserRelation(Mockito.any(UserRelationBO.class));
    }

    @Test
    public void cancelUserTopAppkey() {
        when(resource.cancelUserRelation(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
        Boolean cancel = service.cancelUserTopAppkey("x", "b");
        Assert.assertTrue(cancel);
        verify(resource).cancelUserRelation(Mockito.anyString(),Mockito.anyString());
    }
}