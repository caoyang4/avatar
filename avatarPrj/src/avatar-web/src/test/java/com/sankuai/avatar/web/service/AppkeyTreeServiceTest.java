package com.sankuai.avatar.web.service;

import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;
import com.sankuai.avatar.resource.tree.AppkeyTreeResource;
import com.sankuai.avatar.resource.tree.bo.*;
import com.sankuai.avatar.web.dto.tree.*;
import com.sankuai.avatar.web.service.impl.AppkeyTreeServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * 测试AppkeyTreeService逻辑，Resource使用Mock数据，根据不同入参设计Case，测试Service的不同分支逻辑
 *
 * @author zhangxiaoning07
 * @Date 2022-10-24
 */
@RunWith(MockitoJUnitRunner.class)
public class AppkeyTreeServiceTest {

    private AppkeyTreeServiceImpl service;

    @Mock
    private AppkeyTreeResource resource;

    static AppkeyTreeOwtBO owtBO;

    static AppkeyTreePdlBO pdlBO;

    static {
        owtBO = AppkeyTreeOwtBO.builder()
                .id(1)
                .key("dianping.tbd")
                .name("服务运维部")
                .businessGroup("服务运维部")
                .kind("business")
                .rdAdmin(Arrays.asList("caoyang42","qinwei05"))
                .build();
        pdlBO = AppkeyTreePdlBO.builder()
                .id(1)
                .key("dianping.tbd.change")
                .name("变更管理工具")
                .rdAdmin(Arrays.asList("caoyang42","qinwei05"))
                .build();
    }

    @Before
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        service = new AppkeyTreeServiceImpl(resource);
        Field field = AppkeyTreeServiceImpl.class.getDeclaredField("addSrvDefaultBgMap");
        field.setAccessible(true);
        field.set(service, "{\"点评搜索智能中心\": \"meituan.eagle\",\"新人培训专用\": \"meituan.new\"}");
    }

    @Test
    public void getAllBgList() {
        AppkeyTreeBgBO bgBO = AppkeyTreeBgBO.builder().id(1).name("服务运维部").build();
        when(resource.getBgList()).thenReturn(Collections.singletonList(bgBO));
        List<String> bgList = service.getAllBgList();
        Assert.assertEquals(1, bgList.size());
        verify(resource).getBgList();
    }

    @Test
    public void getUserBg() {
        when(resource.getUserBg(Mockito.anyString())).thenReturn(Arrays.asList("服务运维部", "基础架构部"));
        List<String> list = service.getUserBg("x");
        Assert.assertEquals(2, list.size());
        verify(resource).getUserBg(Mockito.anyString());
    }

    @Test
    public void getBgForAddSrv() {
        AppkeyTreeBgBO bgBO1 = AppkeyTreeBgBO.builder().id(1).name("服务运维部").build();
        AppkeyTreeBgBO bgBO2 = AppkeyTreeBgBO.builder().id(2).name("基础架构部").build();
        when(resource.getBgList()).thenReturn(Arrays.asList(bgBO1, bgBO2));
        when(resource.getUserBg(Mockito.anyString())).thenReturn(Collections.singletonList("基础架构部"));
        List<String> list = service.getBgForAddSrv("x");
        Assert.assertTrue(CollectionUtils.isNotEmpty(list));
        verify(resource).getBgList();
        verify(resource).getUserBg(Mockito.anyString());
    }

    @Test
    public void getUserBgTree() {
        UserBgBO bgBO = UserBgBO.builder().bgName("服务运维部").build();
        when(resource.getUserBgTree(Mockito.anyString())).thenReturn(Collections.singletonList(bgBO));
        List<UserBgDTO> bgTree = service.getUserBgTree("x");
        Assert.assertTrue(CollectionUtils.isNotEmpty(bgTree));
        verify(resource).getUserBgTree(Mockito.anyString());
    }

    @Test
    public void getUserOwtList() {
        when(resource.getOwtList()).thenReturn(Collections.singletonList(owtBO));
        when(resource.getUserBg(Mockito.anyString())).thenReturn(Collections.singletonList("基础架构部"));
        List<AppkeyTreeOwtDTO> owtList = service.getUserOwtList("x", "服务运维部", "运维");
        Assert.assertTrue(CollectionUtils.isNotEmpty(owtList));
        verify(resource).getOwtList();
        verify(resource).getUserBg(Mockito.anyString());
    }

    @Test
    public void getOwtByKey() {
        when(resource.getOwtByKey(Mockito.anyString())).thenReturn(owtBO);
        AppkeyTreeOwtDTO owtDTO = service.getOwtByKey("x", "caoyang42");
        Assert.assertEquals("dianping.tbd", owtDTO.getKey());
        Assert.assertTrue(owtDTO.getUserCanEdit());
    }

    @Test
    public void getPdlListByOwtKey() {
        when(resource.getPdlListByOwt(Mockito.anyString())).thenReturn(Collections.singletonList(pdlBO));
        List<AppkeyTreePdlDTO> list = service.getPdlListByOwtKey("x", "变更");
        Assert.assertTrue(CollectionUtils.isNotEmpty(list));
        verify(resource).getPdlListByOwt(Mockito.anyString());
    }

    @Test
    public void getPdlByKey() {
        when(resource.getPdlByKey(Mockito.anyString())).thenReturn(pdlBO);
        AppkeyTreePdlDTO pdlDTO = service.getPdlByKey("x", "caoyang42");
        Assert.assertEquals("dianping.tbd.change", pdlDTO.getKey());
        Assert.assertTrue(pdlDTO.getUserCanEdit());
    }

    @Test
    public void testGetAppkeyTreeByKey() {
        // Setup
        final AppkeyTreeDTO expectedResult = new AppkeyTreeDTO();
        expectedResult.setOwt(AppkeyTreeOwtDTO.builder().build());
        expectedResult.setPdl(AppkeyTreePdlDTO.builder().build());
        expectedResult.setSrv(AppkeyTreeSrvDTO.builder().build());
        // Configure AppkeyTreeResource.getAppkeyTreeByKey(...).
        AppkeyTreeBO appkeyTreeBO = new AppkeyTreeBO();
        appkeyTreeBO.setOwt(AppkeyTreeOwtBO.builder().build());
        appkeyTreeBO.setPdl(AppkeyTreePdlBO.builder().build());
        appkeyTreeBO.setSrv(AppkeyTreeSrvBO.builder().build());
        when(resource.getAppkeyTreeByKey("srv")).thenReturn(appkeyTreeBO);
        // Run the test
        final AppkeyTreeDTO result = service.getAppkeyTreeByKey("srv");
        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }
    @Test
    public void testGetAppkeyTreeByKeyThatThrowsSdkCallException() {
        // Setup
        when(resource.getAppkeyTreeByKey("srv")).thenThrow(SdkCallException.class);
        // Run the test
        assertThatThrownBy(() -> service.getAppkeyTreeByKey("srv")).isInstanceOf(SdkCallException.class);
    }
    @Test
    public void testGetAppkeyTreeByKeyThatThrowsSdkBusinessErrorException() {
        // Setup
        when(resource.getAppkeyTreeByKey("srv")).thenThrow(SdkBusinessErrorException.class);
        // Run the test
        assertThatThrownBy(() -> service.getAppkeyTreeByKey("srv")).isInstanceOf(SdkBusinessErrorException.class);
        verify(resource).getAppkeyTreeByKey(Mockito.anyString());
    }
    @Test
    public void testGetAppkeyTreeSrvDetailByKey() {
        // Setup
        final AppkeyTreeSrvDetailDTO expectedResult = new AppkeyTreeSrvDetailDTO(0, Collections.singletonList("value"), new HashMap<>(),
                new AppkeyTreeSrvDTO());
        // Configure AppkeyTreeResource.getAppkeyTreeSrvDetailByKey(...).
        final AppkeyTreeSrvDetailBO appkeyTreeSrvDetailBO = new AppkeyTreeSrvDetailBO(0, Collections.singletonList("value"), new HashMap<>(),
                new AppkeyTreeSrvBO());
        when(resource.getAppkeyTreeSrvDetailByKey("srv")).thenReturn(appkeyTreeSrvDetailBO);
        // Run the test
        final AppkeyTreeSrvDetailDTO result = service.getAppkeyTreeSrvDetailByKey("srv");
        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }
    @Test
    public void testGetAppkeyTreeSrvDetailByKeyThatThrowsSdkCallException() {
        // Setup
        when(resource.getAppkeyTreeSrvDetailByKey("srv")).thenThrow(SdkCallException.class);
        // Run the test
        assertThatThrownBy(() -> service.getAppkeyTreeSrvDetailByKey("srv")).isInstanceOf(SdkCallException.class);
        verify(resource).getAppkeyTreeSrvDetailByKey(Mockito.anyString());
    }
    @Test
    public void testGetAppkeyTreeSrvDetailByKeyThatThrowsSdkBusinessErrorException() {
        // Setup
        when(resource.getAppkeyTreeSrvDetailByKey("srv")).thenThrow(SdkBusinessErrorException.class);
        // Run the test
        assertThatThrownBy(() -> service.getAppkeyTreeSrvDetailByKey("srv")).isInstanceOf(SdkBusinessErrorException.class);
        verify(resource).getAppkeyTreeSrvDetailByKey(Mockito.anyString());
    }
    @Test
    public void testGetSrvSubscribers() {
        // Setup
        when(resource.getSrvSubscribers("srv")).thenReturn(Collections.singletonList("value"));
        // Run the test
        final List<String> result = service.getSrvSubscribers("srv");
        // Verify the results
        assertThat(result).isEqualTo(Collections.singletonList("value"));
    }
    @Test
    public void testGetSrvSubscribersThatReturnsNoItems() {
        // Setup
        when(resource.getSrvSubscribers("srv")).thenReturn(Collections.emptyList());
        // Run the test
        final List<String> result = service.getSrvSubscribers("srv");
        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }
    @Test
    public void testGetSrvSubscribersThatThrowsSdkCallException() {
        // Setup
        when(resource.getSrvSubscribers("srv")).thenThrow(SdkCallException.class);
        // Run the test
        assertThatThrownBy(() -> service.getSrvSubscribers("srv"))
                .isInstanceOf(SdkCallException.class);
        verify(resource).getSrvSubscribers(Mockito.anyString());
    }
    @Test
    public void testGetSrvSubscribersThatThrowsSdkBusinessErrorException() {
        // Setup
        when(resource.getSrvSubscribers("srv")).thenThrow(SdkBusinessErrorException.class);
        // Run the test
        assertThatThrownBy(() -> service.getSrvSubscribers("srv"))
                .isInstanceOf(SdkBusinessErrorException.class);
        verify(resource).getSrvSubscribers(Mockito.anyString());
    }

    @Test
    public void getBgTree() {
        UserBgBO bgBO = UserBgBO.builder().bgName("服务运维部").build();
        when(resource.getBgTreeNoCache(Mockito.anyString(), Mockito.anyBoolean())).thenReturn(Collections.singletonList(bgBO));
        List<UserBgDTO> bgTree = service.getBgTree("x", false);
        Assert.assertTrue(CollectionUtils.isNotEmpty(bgTree));
        verify(resource).getBgTreeNoCache(Mockito.anyString(), Mockito.anyBoolean());
    }
}

