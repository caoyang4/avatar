package com.sankuai.avatar.web.crane;

import com.sankuai.avatar.TestBase;
import com.sankuai.avatar.common.utils.JsonUtil;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.tree.AppkeyTreeResource;
import com.sankuai.avatar.resource.tree.bo.AppkeyTreePdlBO;
import com.sankuai.avatar.resource.tree.bo.UserBgBO;
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
import java.util.Collections;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CraneAppkeyTreeTest extends TestBase {

    @Mock
    private AppkeyTreeResource treeResource;
    @Mock
    private UserResource userResource;

    private CraneAppkeyTree crane;

    static UserBO userBO1 = new UserBO();
    static UserBO userBO2 = new UserBO();
    static PageResponse<UserBO> pageResponse = new PageResponse<>();
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

        pageResponse.setPage(1);
        pageResponse.setTotalPage(1);
        pageResponse.setPageSize(10);
        pageResponse.setTotalCount(2);
        pageResponse.setItems(Arrays.asList(userBO1, userBO2));
    }

    @Before
    public void setUp() throws Exception {
        crane = new CraneAppkeyTree(treeResource, userResource);
    }

    @Test
    public void testCacheUserBg() {
        UserRequestBO requestBO = UserRequestBO.builder().build();
        requestBO.setPageSize(200);
        when(userResource.queryPage(requestBO)).thenReturn(pageResponse);
        when(treeResource.getUserBgNoCache(Mockito.anyString())).thenReturn(Arrays.asList("服务运维部", "基础架构部"));
        when(treeResource.cacheUserBg(Mockito.anyString(), Mockito.anyList())).thenReturn(true);
        crane.cacheUserBg();
        verify(userResource, times(2)).queryPage(Mockito.any(UserRequestBO.class));
        verify(treeResource, times(2)).cacheUserBg(Mockito.anyString(), Mockito.anyList());
    }


    @Test
    public void testCacheUserBgTree() {
        UserRequestBO requestBO = UserRequestBO.builder().build();
        requestBO.setPageSize(200);
        UserBgBO bgBO = UserBgBO.builder().bgName("服务运维部").build();
        when(userResource.queryPage(requestBO)).thenReturn(pageResponse);
        when(treeResource.getUserBgTreeNoCache(Mockito.anyString())).thenReturn(Collections.singletonList(bgBO));
        when(treeResource.cacheUserBgTree(Mockito.anyString(), Mockito.anyList())).thenReturn(true);
        crane.cacheUserBgTree();
        verify(userResource, times(2)).queryPage(Mockito.any(UserRequestBO.class));
        verify(treeResource, times(2)).cacheUserBgTree(Mockito.anyString(), Mockito.anyList());
    }

    @Test
    public void cachePdlSrvCount() {
        when( treeResource.getPdlList()).thenReturn(Arrays.asList(AppkeyTreePdlBO.builder().key("pdl1").build()
                ,AppkeyTreePdlBO.builder().key("pdl2").build()));
        when(treeResource.getPdlSrvCountNoCache(Mockito.anyString())).thenReturn(1);
        when(treeResource.cachePdlSrvCount(Mockito.anyString(), Mockito.anyInt())).thenReturn(true);
        crane.cachePdlSrvCount();
        verify(treeResource).getPdlList();
        verify(treeResource,times(2)).getPdlSrvCountNoCache(Mockito.anyString());
        verify(treeResource, times(2)).cachePdlSrvCount(Mockito.anyString(), Mockito.anyInt());
    }
}
