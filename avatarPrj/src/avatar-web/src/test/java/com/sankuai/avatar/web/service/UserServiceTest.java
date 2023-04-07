package com.sankuai.avatar.web.service;

import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.user.UserResource;
import com.sankuai.avatar.resource.user.bo.UserBO;
import com.sankuai.avatar.resource.user.request.UserRequestBO;
import com.sankuai.avatar.web.dto.user.DxUserDTO;
import com.sankuai.avatar.web.dto.user.UserDTO;
import com.sankuai.avatar.web.request.UserPageRequest;
import com.sankuai.avatar.web.service.impl.UserServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest  {

    @Mock
    private UserResource resource;

    private UserService userService;

    static UserBO userBO = new UserBO();

    static {
        userBO.setNumber(666);
        userBO.setName("unit-test");
        userBO.setMis("unit-mis");
        userBO.setSource("MT");
        userBO.setOrganization("公司/美团/基础研发平台/基础技术部/服务运维部/运维工具开发组/变更管理开发组");
        userBO.setRole("rd");
        userBO.setOrgPath("0-1-2-100046-150042-1573-150044-1021866");
        userBO.setOrgId("1021866");
        userBO.setJobStatus("在职");
        userBO.setLeader("xxx");
        userBO.setUserImage("xxx");
    }

    static UserDTO userDTO = new UserDTO();

    static {
        userDTO.setNumber(666);
        userDTO.setName("unit-test");
        userDTO.setMis("unit-mis");
        userDTO.setSource("MT");
        userDTO.setOrganization("公司/美团/基础研发平台/基础技术部/服务运维部/运维工具开发组/变更管理开发组");
        userDTO.setRole("rd");
        userDTO.setOrgPath("0-1-2-100046-150042-1573-150044-1021866");
        userDTO.setOrgId("1021866");
        userDTO.setLeader("xxx");
        userDTO.setJobStatus("在职");
        userDTO.setAvatarUrl("xxx");
    }

    static DxUserDTO dxUserDTO = new DxUserDTO();

    static {
        dxUserDTO.setMis("mis");
        dxUserDTO.setName("name");
        dxUserDTO.setLeader("leader");
        dxUserDTO.setAvatarUrl("avatarUrl");
        dxUserDTO.setOrg("org");
    }

    @Before
    public void setUp() {
        userService = new UserServiceImpl(resource);
    }

    @Test
    public void testQueryPage() {
        final UserPageRequest userPageRequest = new UserPageRequest();
        userPageRequest.setLoginName("unit-mis");
        final PageResponse<UserBO> pageResponse = new PageResponse<>();
        pageResponse.setPage(1);
        pageResponse.setTotalPage(1);
        pageResponse.setPageSize(10);
        pageResponse.setTotalCount(1);
        pageResponse.setItems(Collections.singletonList(userBO));
        when(resource.queryPage(Mockito.any(UserRequestBO.class))).thenReturn(pageResponse);
        final PageResponse<UserDTO> result = userService.queryPage(userPageRequest);
        verify(resource, times(1)).queryPage(any());
        Assert.assertEquals(10, result.getPageSize());
        Assert.assertEquals(1, result.getItems().size());
    }

    @Test
    public void testQueryUserByMis() {
        final List<UserBO> userBOList = Collections.singletonList(userBO);
        when(resource.queryByMisWithOrder(Mockito.anyList())).thenReturn(userBOList);
        final List<UserDTO> result = userService.queryUserByMis(Collections.singletonList("unit-mis"));
        verify(resource, times(1)).queryByMisWithOrder(Mockito.anyList());
        Assert.assertEquals(1, result.size());
        Assert.assertEquals("unit-mis", result.get(0).getMis());
    }

    @Test
    public void testQueryUserByMisNoItems() {
        when(resource.queryByMisWithOrder(Collections.singletonList("x"))).thenReturn(Collections.emptyList());
        final List<UserDTO> result = userService.queryUserByMis(Collections.singletonList("x"));
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    public void testGetDxUserByMis() {
        final List<UserBO> userBOList = Collections.singletonList(userBO);
        when(resource.queryByMisWithOrder(Mockito.anyList())).thenReturn(userBOList);
        final List<DxUserDTO> result = userService.getDxUserByMis(Collections.singletonList("mis"));
        verify(resource, times(1)).queryByMisWithOrder(Mockito.anyList());
        Assert.assertTrue(CollectionUtils.isNotEmpty(result));
        Assert.assertEquals("xxx", result.get(0).getAvatarUrl());
    }

    @Test
    public void testGetDxUserByMis_UserResourceReturnsNoItems() {
        when(resource.queryByMisWithOrder(Mockito.anyList())).thenReturn(Collections.emptyList());
        final List<DxUserDTO> result = userService.getDxUserByMis(Collections.singletonList("x"));
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    public void getUserJobStatus() {
        when(resource.isUserOnJob(Mockito.anyString())).thenReturn(true);
        Assert.assertEquals("在职", userService.getUserJobStatus("x"));
    }

    @Test
    public void queryUserByMisNoOrder() {
        final List<UserBO> userBOList = Collections.singletonList(userBO);
        when(resource.getUserByCacheOrDB(Mockito.anyList())).thenReturn(userBOList);
        final List<UserDTO> result = userService.queryUserByMisNoOrder(Collections.singletonList("unit-mis"));
        verify(resource, times(1)).getUserByCacheOrDB(Mockito.anyList());
        Assert.assertEquals(1, result.size());
        Assert.assertEquals("unit-mis", result.get(0).getMis());
    }

    @Test
    public void getDxUserByClient() {
        when(resource.getUserByOrgDx(Mockito.anyString())).thenReturn(userBO);
        UserDTO userDTO = userService.getDxUserByClient("x");
        Assert.assertEquals("unit-mis", userDTO.getMis());
        verify(resource).getUserByOrgDx(Mockito.anyString());
    }
    @Test
    public void getDxUserByClientThrowException() {
        when(resource.getUserByOrgDx(Mockito.anyString())).thenThrow(SdkBusinessErrorException.class);
        UserDTO userDTO = userService.getDxUserByClient("x");
        Assert.assertNull(userDTO);
        verify(resource).getUserByOrgDx(Mockito.anyString());
    }

    @Test
    public void queryUserByCacheDbOrg(){
        when(resource.queryByMis(Mockito.anyList())).thenReturn(Collections.singletonList(userBO));
        List<UserDTO> result = userService.queryUserByCacheDbOrg(Collections.singletonList("unit-mis"));
        verify(resource, times(1)).queryByMis(Mockito.anyList());
        Assert.assertEquals(1, result.size());
    }

    @Test
    public void isOpsSre(){
        when(resource.isOpsSre(Mockito.anyString())).thenReturn(true);
        boolean sre = userService.isOpsSre("x");
        Assert.assertTrue(sre);
        verify(resource).isOpsSre(Mockito.anyString());
    }

    @Test
    public void updateDBUser(){
        Mockito.doAnswer(invocation -> true).when(resource).asyncUpdateUserRegister(Mockito.any(UserBO.class));
        userService.updateDBUser(userDTO);
        verify(resource).asyncUpdateUserRegister(Mockito.any(UserBO.class));
    }
}
