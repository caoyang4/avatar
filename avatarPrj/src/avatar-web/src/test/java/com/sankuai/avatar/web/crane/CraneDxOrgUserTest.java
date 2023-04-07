package com.sankuai.avatar.web.crane;

import com.sankuai.avatar.common.utils.JsonUtil;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.user.UserResource;
import com.sankuai.avatar.resource.user.bo.UserBO;
import com.sankuai.avatar.resource.user.request.UserRequestBO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CraneDxOrgUserTest {

    @Mock
    private UserResource userResource;

    private CraneDxOrgUser crane;

    static UserBO userBO1 = new UserBO();
    static UserBO userBO2 = new UserBO();
    static PageResponse<UserBO> pageResponse1 = new PageResponse<>();
    static {
        userBO1.setNumber(666);
        userBO1.setName("unit-test");
        userBO1.setMis("unit-mis1");
        userBO1.setSource("MT");
        userBO1.setOrganization("公司/美团/基础研发平台/基础技术部/服务运维部/运维工具开发组/变更管理开发组");
        userBO1.setRole("rd");
        userBO1.setOrgPath("0-1-2-100046-150042-1573-150044-1021866");
        userBO1.setOrgId("1021866");
        userBO1.setJobStatus("在职");
        userBO1.setLeader("xxx");
        userBO1.setUserImage("xxx");
        userBO2 = JsonUtil.json2Bean(JsonUtil.bean2Json(userBO1), UserBO.class);
        userBO2.setMis("unit-mis2");
        userBO2.setSource("ORG");
        userBO2.setJobStatus("离职");

        pageResponse1.setPage(1);
        pageResponse1.setTotalPage(1);
        pageResponse1.setPageSize(10);
        pageResponse1.setTotalCount(1);
        pageResponse1.setItems(Arrays.asList(userBO1, userBO2));
    }


    @Before
    public void setUp() {
        crane = new CraneDxOrgUser(userResource);
    }

    @Test
    public void testSupplementUser() {
        userBO2.setSource("ORG");
        userBO2.setJobStatus("离职");
        UserRequestBO requestBO1 = UserRequestBO.builder().build();
        requestBO1.setPageSize(200);
        when(userResource.queryPage(requestBO1)).thenReturn(pageResponse1);
        when(userResource.getUserByOrgDx("unit-mis1")).thenReturn(userBO1);
        when(userResource.getUserByOrgDx("unit-mis2")).thenReturn(userBO2);
        when(userResource.save(Mockito.any(UserBO.class))).thenReturn(true);
        when(userResource.deleteByCondition(Mockito.any(UserRequestBO.class))).thenReturn(false);
        crane.supplementUser();
        verify(userResource,times(2)).queryPage(Mockito.any());
        verify(userResource, times(2)).getUserByOrgDx(Mockito.anyString());
        verify(userResource,times(1)).save(Mockito.any());
        verify(userResource,times(1)).deleteByCondition(Mockito.any());
    }

    @Test
    public void testCacheUser() {
        UserRequestBO requestBO1 = UserRequestBO.builder().build();
        requestBO1.setPageSize(200);
        when(userResource.queryPage(requestBO1)).thenReturn(pageResponse1);
        when(userResource.cacheUserBO(Mockito.anyList())).thenReturn(true);
        when(userResource.getUserByOrgDx(Mockito.anyString())).thenReturn(userBO1);
        crane.cacheUser();
        verify(userResource,times(1)).cacheUserBO(Mockito.anyList());
    }
}
