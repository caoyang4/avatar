package com.sankuai.avatar.resource.tree;

import com.google.common.collect.ImmutableMap;
import com.sankuai.avatar.client.ops.OpsHttpClient;
import com.sankuai.avatar.client.ops.model.*;
import com.sankuai.avatar.client.ops.response.OpsPdlResponse;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;
import com.sankuai.avatar.resource.TestBase;
import com.sankuai.avatar.dao.cache.AppkeyTreeCacheRepository;
import com.sankuai.avatar.dao.cache.model.UserBg;
import com.sankuai.avatar.resource.tree.bo.*;
import com.sankuai.avatar.resource.tree.impl.AppkeyTreeResourceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;


/**
 * 测试AppkeyTreeResource逻辑，OpsHttpClient使用Mock数据，根据不同入参设计Case，测试Resource的不同分支逻辑
 *
 * @author zhangxiaoning07
 * @Date 2022-11-01
 */
public class AppkeyTreeResourceTest extends TestBase {

    @Mock
    private OpsHttpClient mockOpsHttpClient;
    
    @Mock
    private AppkeyTreeCacheRepository cacheRepository;

    private AppkeyTreeResourceImpl resource;

    static OpsOwt opsOwt;
    
    static OpsPdl opsPdl;
    
    static {
        opsOwt = OpsOwt.builder()
                .id(1)
                .key("dianping.tbd")
                .name("服务运维部")
                .businessGroup("服务运维部")
                .kind("business")
                .build();
        opsPdl = OpsPdl.builder()
                .id(1)
                .key("dianping.tbd.change")
                .name("变更管理工具")
                .rdAdmin("caoyang42,qinwei05")
                .build();
    }

    @Before
    public void setUp() {
        resource = new AppkeyTreeResourceImpl(mockOpsHttpClient, cacheRepository);
    }

    @Test
    public void testGetAppkeyTreeByKey() {
        // Setup

        // Configure OpsHttpClient.getAppkeyTreeByKey(...).
        final OpsTree opsTree = new OpsTree();
        opsTree.setOwt(OpsOwt.builder()
                .key("key")
                .build());
        opsTree.setPdl(OpsPdl.builder().build());
        opsTree.setSrv(OpsSrv.builder().build());
        opsTree.setCorp(OpsBg.builder().build());
        when(mockOpsHttpClient.getSrvTreeByKey("srv")).thenReturn(opsTree);

        // Run the test
        final AppkeyTreeBO result = resource.getAppkeyTreeByKey("srv");

        // Verify the results
        Mockito.verify(mockOpsHttpClient, times(1)).getSrvTreeByKey("srv");
        assertThat(result).isNotNull();
    }

    @Test
    public void testGetAppkeyTreeByKeyThatThrowsSdkCallException() {
        // Setup
        // Configure OpsHttpClient.getAppkeyTreeByKey(...).
        final OpsTree opsTree = new OpsTree();
        opsTree.setOwt(OpsOwt.builder()
                .key("key")
                .build());
        opsTree.setPdl(OpsPdl.builder().build());
        opsTree.setSrv(OpsSrv.builder().build());
        opsTree.setCorp(OpsBg.builder().build());
        when(mockOpsHttpClient.getSrvTreeByKey("srv")).thenThrow(SdkCallException.class);

        // Run the test
        assertThatThrownBy(() -> resource.getAppkeyTreeByKey("srv"))
                .isInstanceOf(SdkCallException.class);
        verify(mockOpsHttpClient).getSrvTreeByKey(Mockito.anyString());
    }

    @Test
    public void testGetAppkeyTreeByKeyThatThrowsSdkBusinessErrorException() {
        // Setup
        // Configure OpsHttpClient.getAppkeyTreeByKey(...).
        final OpsTree opsTree = new OpsTree();
        opsTree.setOwt(OpsOwt.builder()
                .key("key")
                .build());
        opsTree.setPdl(OpsPdl.builder().build());
        opsTree.setSrv(OpsSrv.builder().build());
        opsTree.setCorp(OpsBg.builder().build());
        when(mockOpsHttpClient.getSrvTreeByKey("srv")).thenThrow(SdkBusinessErrorException.class);

        // Run the test
        assertThatThrownBy(() -> resource.getAppkeyTreeByKey("srv"))
                .isInstanceOf(SdkBusinessErrorException.class);
        verify(mockOpsHttpClient).getSrvTreeByKey(Mockito.anyString());
    }

    @Test
    public void testGetAppkeyDetailByKey() {
        // Setup
        final AppkeyTreeSrvDetailBO expectedResult = new AppkeyTreeSrvDetailBO();
        expectedResult.setHostCount(0);
        expectedResult.setAppkeys(Collections.singletonList("value"));

        // Configure OpsHttpClient.getAppkeyTreeSrvDetailByKey(...).
        final OpsSrvDetail opsSrvDetail = new OpsSrvDetail();
        opsSrvDetail.setHostCount(0);
        opsSrvDetail.setAppkeys(Collections.singletonList("value"));
        when(mockOpsHttpClient.getSrvDetailByKey("srv")).thenReturn(opsSrvDetail);

        // Run the test
        final AppkeyTreeSrvDetailBO result = resource.getAppkeyTreeSrvDetailByKey("srv");

        // Verify the results
        Mockito.verify(mockOpsHttpClient, times(1)).getSrvDetailByKey("srv");
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testGetAppkeyDetailByKeyThatThrowsSdkCallException() {
        // Setup
        // Configure OpsHttpClient.getAppkeyTreeSrvDetailByKey(...).
        final OpsSrvDetail opsSrvDetail = new OpsSrvDetail();
        opsSrvDetail.setHostCount(0);
        opsSrvDetail.setAppkeys(Collections.singletonList("value"));
        opsSrvDetail.setSrv(OpsSrv.builder().build());
        when(mockOpsHttpClient.getSrvDetailByKey("srv")).thenThrow(SdkCallException.class);

        // Run the test
        assertThatThrownBy(() -> resource.getAppkeyTreeSrvDetailByKey("srv"))
                .isInstanceOf(SdkCallException.class);
        verify(mockOpsHttpClient).getSrvDetailByKey(Mockito.anyString());
    }

    @Test
    public void testGetAppkeyDetailByKeyThatThrowsSdkBusinessErrorException() {
        // Setup
        // Configure OpsHttpClient.getAppkeyTreeSrvDetailByKey(...).
        final OpsSrvDetail opsSrvDetail = new OpsSrvDetail();
        opsSrvDetail.setHostCount(0);
        opsSrvDetail.setAppkeys(Collections.singletonList("value"));
        opsSrvDetail.setSrv(OpsSrv.builder().build());
        when(mockOpsHttpClient.getSrvDetailByKey("srv")).thenThrow(SdkBusinessErrorException.class);

        // Run the test
        assertThatThrownBy(() -> resource.getAppkeyTreeSrvDetailByKey("srv"))
                .isInstanceOf(SdkBusinessErrorException.class);
        verify(mockOpsHttpClient).getSrvDetailByKey(Mockito.anyString());
    }

    @Test
    public void testGetSrvSubscribers() {
        // Setup
        when(mockOpsHttpClient.getSrvSubscribers("srv")).thenReturn(Collections.singletonList("value"));

        // Run the test
        final List<String> result = resource.getSrvSubscribers("srv");

        // Verify the results
        Mockito.verify(mockOpsHttpClient, times(1)).getSrvSubscribers("srv");
        assertThat(result).isEqualTo(Collections.singletonList("value"));
    }

    @Test
    public void testGetSrvSubscribersThatReturnsNoItems() {
        // Setup
        when(mockOpsHttpClient.getSrvSubscribers("srv")).thenReturn(Collections.emptyList());

        // Run the test
        final List<String> result = resource.getSrvSubscribers("srv");

        // Verify the results
        Mockito.verify(mockOpsHttpClient, times(1)).getSrvSubscribers("srv");
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    public void testGetSrvSubscribersThatThrowsSdkCallException() {
        // Setup
        when(mockOpsHttpClient.getSrvSubscribers("srv")).thenThrow(SdkCallException.class);

        // Run the test
        assertThatThrownBy(() -> resource.getSrvSubscribers("srv"))
                .isInstanceOf(SdkCallException.class);
        verify(mockOpsHttpClient).getSrvSubscribers(Mockito.anyString());
    }

    @Test
    public void testGetSrvSubscribersThatThrowsSdkBusinessErrorException() {
        // Setup
        when(mockOpsHttpClient.getSrvSubscribers("srv")).thenThrow(SdkBusinessErrorException.class);

        // Run the test
        assertThatThrownBy(() -> resource.getSrvSubscribers("srv"))
                .isInstanceOf(SdkBusinessErrorException.class);
        verify(mockOpsHttpClient).getSrvSubscribers(Mockito.anyString());
    }

    /**
     * 测试GetBgList功能
     */
    @Test
    public void testGetBgList() {
        final List<AppkeyTreeBgBO> expectedResult = Arrays.asList(AppkeyTreeBgBO.builder().name("test").build());
        when(mockOpsHttpClient.getBgList()).thenReturn(Arrays.asList(OpsBg.builder().name("test").build()));
        final List<AppkeyTreeBgBO> result = resource.getBgList();
        assertThat(result).isEqualTo(expectedResult);
    }

    /**
     * 测试getBgList边界Case: Client接口返回空列表
     */
    @Test
    public void testGetBgListThatOpsHttpClientReturnsNoItems() {
        when(mockOpsHttpClient.getBgList()).thenReturn(Collections.emptyList());
        final List<AppkeyTreeBgBO> result = resource.getBgList();
        assertThat(result).isEqualTo(Collections.emptyList());
    }


    @Test
    public void getBgList() {
        OpsBg opsBg = OpsBg.builder().id(1).name("服务运维部").build();
        when(mockOpsHttpClient.getBgList()).thenReturn(Collections.singletonList(opsBg));
        List<AppkeyTreeBgBO> bgList = resource.getBgList();
        Assert.assertEquals(1, bgList.size());
        verify(mockOpsHttpClient).getBgList();
    }

    @Test
    public void getBgListByUser() {
        when(mockOpsHttpClient.getBgListByUser(Mockito.anyString())).thenReturn(Arrays.asList("服务运维部","基础架构部"));
        List<AppkeyTreeBgBO> boList = resource.getBgListByUser("x");
        Assert.assertEquals(2, boList.size());
        verify(mockOpsHttpClient).getBgListByUser(Mockito.anyString());
    }


    @Test
    public void getOwtList() {
        when(mockOpsHttpClient.getOwtList()).thenReturn(Collections.singletonList(opsOwt));
        List<AppkeyTreeOwtBO> boList = resource.getOwtList();
        Assert.assertEquals(1, boList.size());
        verify(mockOpsHttpClient).getOwtList();
    }

    @Test
    public void getOwtByKey() {
        Assert.assertNull(resource.getOwtByKey(null));
        OpsOrg opsOrg = OpsOrg.builder().id(1).orgID(10086).path("公司/美团/基础研发平台/基础技术部/服务运维部").build();
        when(mockOpsHttpClient.getOwtByKey(Mockito.anyString())).thenReturn(opsOwt);
        when(mockOpsHttpClient.getOrgListByKey(Mockito.anyString())).thenReturn(Collections.singletonList(opsOrg));
        AppkeyTreeOwtBO owtBO = resource.getOwtByKey("x");
        Assert.assertEquals("dianping.tbd", owtBO.getKey());
        Assert.assertEquals("公司/美团/基础研发平台/基础技术部/服务运维部", owtBO.getOrg().get(0).getPath());
        verify(mockOpsHttpClient).getOwtByKey(Mockito.anyString());
        verify(mockOpsHttpClient).getOrgListByKey(Mockito.anyString());
    }

    @Test
    public void getPdlListByOwt() {
        Assert.assertTrue(CollectionUtils.isEmpty(resource.getPdlListByOwt(null)));
        when(mockOpsHttpClient.getPdlListByOwtKey(Mockito.anyString())).thenReturn(Collections.singletonList(opsPdl));
        when(cacheRepository.multiGetPdlSrvCount(Mockito.anyList())).thenReturn(ImmutableMap.of("dianping.tbd.change", 1));
        List<AppkeyTreePdlBO> boList = resource.getPdlListByOwt("x");
        Assert.assertEquals(1, boList.size());
        Assert.assertEquals(1, boList.get(0).getSrvCount().intValue());
        verify(mockOpsHttpClient).getPdlListByOwtKey(Mockito.anyString());
        verify(mockOpsHttpClient, times(0)).getSrvListByPdl(Mockito.anyString());
    }

    @Test
    public void getPdlList() {
        when(mockOpsHttpClient.getPdlList()).thenReturn(Collections.singletonList(opsPdl));
        List<AppkeyTreePdlBO> boList = resource.getPdlList();
        Assert.assertEquals(1, boList.size());
        verify(mockOpsHttpClient).getPdlList();
    }

    @Test
    public void getPdlByKey() {
        OpsPdlResponse response = new OpsPdlResponse();
        response.setOwt(opsOwt);
        response.setPdl(opsPdl);
        when(mockOpsHttpClient.getPdlByKey(Mockito.anyString())).thenReturn(response);
        when(mockOpsHttpClient.getSrvListByPdl(Mockito.anyString())).thenReturn(Collections.singletonList(OpsSrv.builder().build()));
        when(cacheRepository.getPdlSrvCount(Mockito.anyString())).thenReturn(null);
        AppkeyTreePdlBO pdlBO = resource.getPdlByKey("pdl");
        Assert.assertEquals("dianping.tbd.change", pdlBO.getKey());
        Assert.assertEquals("dianping.tbd", pdlBO.getOwt().getKey());
        Assert.assertEquals(1, pdlBO.getSrvCount().intValue());
        verify(mockOpsHttpClient).getPdlByKey(Mockito.anyString());
        verify(mockOpsHttpClient).getSrvListByPdl(Mockito.anyString());
    }

    @Test
    public void getUserBgWithCache() {
        when(cacheRepository.getUserBg(Mockito.anyString())).thenReturn(Arrays.asList("服务运维部", "基础架构部"));
        List<String> userBg = resource.getUserBg("x");
        Assert.assertEquals(2, userBg.size());
        verify(cacheRepository).getUserBg(Mockito.anyString());
    }

    @Test
    public void getUserBgNoCache() {
        OpsSrv opsSrv = OpsSrv.builder().key("dianping.tbd.change.xxx").build();
        when(mockOpsHttpClient.getOwtList()).thenReturn(Collections.singletonList(opsOwt));
        when(mockOpsHttpClient.getUserAppkeyInfo(Mockito.anyString())).thenReturn(Collections.singletonList(opsSrv));
        when(mockOpsHttpClient.getBgListByUser(Mockito.anyString())).thenReturn(Arrays.asList("点评搜索智能中心","基础架构部"));
        List<String> list = resource.getUserBgNoCache("x");
        Assert.assertEquals(3, list.size());
        verify(mockOpsHttpClient).getOwtList();
        verify(mockOpsHttpClient).getUserAppkeyInfo(Mockito.anyString());
        verify(mockOpsHttpClient).getBgListByUser(Mockito.anyString());
    }

    @Test
    public void cacheUserBg() {
        when(cacheRepository.setUserBg(Mockito.anyString(), Mockito.anyList(), Mockito.anyInt())).thenReturn(true);
        Boolean success = resource.cacheUserBg("x", Arrays.asList("服务运维部", "基础架构部"));
        Assert.assertTrue(success);
        verify(cacheRepository).setUserBg(Mockito.anyString(), Mockito.anyList(), Mockito.anyInt());
    }

    @Test
    public void getUserBgTreeWithCache() {
        UserBg userBg = new UserBg();
        when(cacheRepository.getUserBgTree(Mockito.anyString())).thenReturn(Collections.singletonList(userBg));
        List<UserBgBO> userBgTree = resource.getUserBgTree("x");
        Assert.assertEquals(1, userBgTree.size());
        verify(cacheRepository).getUserBgTree(Mockito.anyString());
    }

    @Test
    public void getUserBgTreeNoCache() {
        OpsSrv opsSrv = OpsSrv.builder().key("dianping.tbd.change.xxx").build();
        when(mockOpsHttpClient.getOwtList()).thenReturn(Collections.singletonList(opsOwt));
        when(mockOpsHttpClient.getPdlList()).thenReturn(Collections.singletonList(opsPdl));
        when(mockOpsHttpClient.getUserAppkeyInfo(Mockito.anyString())).thenReturn(Collections.singletonList(opsSrv));
        List<UserBgBO> bgTree = resource.getUserBgTreeNoCache("x");
        Assert.assertTrue(CollectionUtils.isNotEmpty(bgTree));
        verify(mockOpsHttpClient).getOwtList();
        verify(mockOpsHttpClient).getPdlList();
        verify(mockOpsHttpClient).getUserAppkeyInfo(Mockito.anyString());
    }

    @Test
    public void cacheUserBgTree() {
        UserBgBO bgBO = UserBgBO.builder().build();
        when(cacheRepository.setUserBgTree(Mockito.anyString(), Mockito.anyList(), Mockito.anyInt())).thenReturn(true);
        Boolean success = resource.cacheUserBgTree("x", Collections.singletonList(bgBO));
        Assert.assertTrue(success);
        verify(cacheRepository).setUserBgTree(Mockito.anyString(), Mockito.anyList(), Mockito.anyInt());
    }

    @Test
    public void getBgTree() {
        OpsBg opsBg = OpsBg.builder().id(1).name("服务运维部").build();
        when(mockOpsHttpClient.getBgList()).thenReturn(Collections.singletonList(opsBg));
        when(mockOpsHttpClient.getOwtList()).thenReturn(Collections.singletonList(opsOwt));
        when(mockOpsHttpClient.getPdlList()).thenReturn(Collections.singletonList(opsPdl));
        when(mockOpsHttpClient.isUserOpsSre(Mockito.anyString())).thenReturn(true);
        List<UserBgBO> boList = resource.getBgTreeNoCache("x", false);
        Assert.assertTrue(CollectionUtils.isNotEmpty(boList));
        verify(mockOpsHttpClient).getOwtList();
        verify(mockOpsHttpClient).getPdlList();
        verify(mockOpsHttpClient).getBgList();
    }

    @Test
    public void getBgTreeOnlyMine() {
        when(mockOpsHttpClient.getOwtList()).thenReturn(Collections.singletonList(opsOwt));
        when(mockOpsHttpClient.getPdlList()).thenReturn(Collections.singletonList(opsPdl));
        when(mockOpsHttpClient.getBgListByUser(Mockito.anyString())).thenReturn(Arrays.asList("服务运维部","基础架构部"));
        List<UserBgBO> boList = resource.getBgTreeNoCache("x", true);
        Assert.assertTrue(CollectionUtils.isNotEmpty(boList));
        verify(mockOpsHttpClient).getOwtList();
        verify(mockOpsHttpClient).getPdlList();
        verify(mockOpsHttpClient).getBgListByUser(Mockito.anyString());
    }

    @Test
    public void getPdlSrvCountWithCache() {
        when(cacheRepository.getPdlSrvCount(Mockito.anyString())).thenReturn(ImmutableMap.of("pdl", 1));
        Integer count = resource.getPdlSrvCount("pdl");
        Assert.assertEquals(1, count.intValue());
        verify(cacheRepository).getPdlSrvCount(Mockito.anyString());
        verify(mockOpsHttpClient, times(0)).getSrvListByPdl(Mockito.anyString());
    }

    @Test
    public void getPdlSrvCount() {
        when(cacheRepository.getPdlSrvCount(Mockito.anyString())).thenReturn(null);
        when(mockOpsHttpClient.getSrvListByPdl(Mockito.anyString())).thenReturn(Collections.singletonList(OpsSrv.builder().build()));
        when(cacheRepository.setPdlSrvCount(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(true);
        Integer count = resource.getPdlSrvCount("pdl");
        Assert.assertEquals(1, count.intValue());
        verify(cacheRepository).getPdlSrvCount(Mockito.anyString());
        verify(mockOpsHttpClient).getSrvListByPdl(Mockito.anyString());
        verify(cacheRepository).setPdlSrvCount(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt());
    }

    @Test
    public void getPdlSrvCountNoCache() {
        Assert.assertEquals(0, resource.getPdlSrvCountNoCache(null).intValue());
        when(mockOpsHttpClient.getSrvListByPdl(Mockito.anyString())).thenReturn(Collections.singletonList(OpsSrv.builder().build()));
        Integer count = resource.getPdlSrvCount("pdl");
        Assert.assertEquals(1, count.intValue());
        verify(mockOpsHttpClient).getSrvListByPdl(Mockito.anyString());
    }

    @Test
    public void getPdlSrvCountNoCacheThrowException() {
        when(mockOpsHttpClient.getSrvListByPdl(Mockito.anyString())).thenThrow(SdkCallException.class);
        Integer count = resource.getPdlSrvCount("pdl");
        Assert.assertNull(count);
        verify(mockOpsHttpClient).getSrvListByPdl(Mockito.anyString());
    }

    @Test
    public void cachePdlSrvCount() {
        when(cacheRepository.setPdlSrvCount(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(true);
        Boolean cache = cacheRepository.setPdlSrvCount("pdl", 1, -1);
        Assert.assertTrue(cache);
    }

    @Test
    public void batchCacheGetPdlSrvCount() {
        when(cacheRepository.multiGetPdlSrvCount(Mockito.anyList())).thenReturn(ImmutableMap.of("pdl", 1));
        Map<String, Integer> pdlMap = resource.batchCacheGetPdlSrvCount(Collections.singletonList("pdl"));
        Assert.assertEquals(1, pdlMap.get("pdl").intValue());
        verify(cacheRepository).multiGetPdlSrvCount(Mockito.anyList());
    }

}

