package com.sankuai.avatar.resource.user;

import com.sankuai.avatar.client.dx.DxClient;
import com.sankuai.avatar.client.dx.model.DxUser;
import com.sankuai.avatar.client.org.OrgClient;
import com.sankuai.avatar.client.org.model.OrgUser;
import com.sankuai.avatar.common.utils.JsonUtil;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.TestBase;
import com.sankuai.avatar.dao.cache.UserCacheRepository;
import com.sankuai.avatar.dao.resource.repository.UserRepository;
import com.sankuai.avatar.dao.cache.exception.CacheException;
import com.sankuai.avatar.dao.resource.repository.model.UserDO;
import com.sankuai.avatar.dao.resource.repository.request.UserRequest;
import com.sankuai.avatar.resource.user.bo.UserBO;
import com.sankuai.avatar.resource.user.impl.UserResourceImpl;
import com.sankuai.avatar.resource.user.request.UserRequestBO;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import tk.mybatis.mapper.entity.Config;
import tk.mybatis.mapper.mapperhelper.EntityHelper;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * @author caoyang
 * @create 2022-10-20 17:34
 */
public class UserResourceTest extends TestBase {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserCacheRepository userCacheRepository;

    @Mock
    private OrgClient orgClient;

    @Mock
    private DxClient dxClient;



    private UserResource resource;

    static UserDO userDO = new UserDO();
    static {
        userDO.setId(123);
        userDO.setNumber(666);
        userDO.setName("unit-test");
        userDO.setLoginName("unit-mis");
        userDO.setSource("MT");
        userDO.setOrganization("公司/美团/基础研发平台/基础技术部/服务运维部/运维工具开发组/变更管理开发组");
        userDO.setRole("rd");
        userDO.setOrgPath("0-1-2-100046-150042-1573-150044-1021866");
        userDO.setOrgId("1021866");
        userDO.setLeader("xxx");
        userDO.setUserImage("xxx");
        userDO.setJobStatus("在职");
        userDO.setLoginTime(new Date());
        userDO.setRegisterTime(new Date());
    }

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

    static OrgUser orgUser = new OrgUser();
    static {
        orgUser.setName("unit-test");
        orgUser.setMis("unit-misx");
        orgUser.setSource("MT");
        orgUser.setLeader("xxx");
        orgUser.setJobStatus("在职");
    }

    static DxUser dxUser = new DxUser();
    static {
        dxUser.setName("unit-test");
        dxUser.setMis("unit-misx");
        dxUser.setAvatarUrl("xxx");
    }


    @Override
    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        EntityHelper.initEntityNameMap(UserDO.class, new Config());
        resource = new UserResourceImpl(userRepository, userCacheRepository, orgClient, dxClient);
        when(userCacheRepository.multiSet(Mockito.anyList(), Mockito.anyInt())).thenReturn(true);
        when(userCacheRepository.set(Mockito.any(UserDO.class), Mockito.anyInt())).thenReturn(true);
    }

    @Test
    public void testQueryPage() {
        int pageSize = 10;
        when(userRepository.query(Mockito.any(UserRequest.class))).thenReturn(Collections.singletonList(userDO));
        PageResponse<UserBO> pageResponse = resource.queryPage(UserRequestBO.builder().search("caoyang42").build());
        Assert.assertEquals(pageSize, pageResponse.getPageSize());
    }

    @Test
    public void testQueryByMis() {
        when(userCacheRepository.multiGet(Mockito.anyList())).thenReturn(Collections.singletonList(userDO));
        List<UserBO> userBOList = resource.queryByMis(Collections.singletonList("unit-mis"));
        Assert.assertEquals(1, userBOList.size());
        Assert.assertEquals("unit-mis", userBOList.get(0).getMis());
        when(userCacheRepository.multiGet(Mockito.anyList())).thenReturn(Collections.emptyList());
        userDO.setName("unit-test-query");
        when(userRepository.query(Mockito.any(UserRequest.class))).thenReturn(Collections.singletonList(userDO));
        userBOList = resource.queryByMis(Collections.singletonList("unit-mis"));
        Assert.assertEquals(1, userBOList.size());
        Assert.assertEquals("unit-test-query", userBOList.get(0).getName());
    }

    @Test
    public void testSave() {
        when(userRepository.query(Mockito.any(UserRequest.class))).thenReturn(Collections.singletonList(userDO));
        when(userRepository.update(Mockito.any(UserDO.class))).thenReturn(false);
        Assert.assertFalse(resource.save(userBO));
        when(userRepository.query(Mockito.any(UserRequest.class))).thenReturn(Collections.emptyList());
        when(userRepository.insert(Mockito.any(UserDO.class))).thenReturn(true);
        Assert.assertTrue(resource.save(userBO));
    }

    @Test
    public void testDeleteByCondition() {
        when(userRepository.delete(Mockito.any(Integer.class))).thenReturn(true);
        when(userRepository.query(Mockito.any(UserRequest.class))).thenReturn(Collections.singletonList(userDO));
        Assert.assertTrue(resource.deleteByCondition(UserRequestBO.builder().misList(Collections.singletonList("x")).build()));
    }

    @Test
    public void cacheUserBO() {
        when(userCacheRepository.multiSet(Mockito.anyList(), Mockito.anyInt())).thenReturn(true);
        Assert.assertTrue(resource.cacheUserBO(Collections.singletonList(userBO)));
    }

    @Test
    public void isUserOnDuty() {
        when(orgClient.getOrgUserByMis(Mockito.anyString())).thenReturn(orgUser);
        boolean onDuty = resource.isUserOnJob("x");
        Assert.assertTrue(onDuty);
        verify(orgClient).getOrgUserByMis(Mockito.anyString());
    }

    @Test
    public void queryByMis() {
        when(userCacheRepository.get(Mockito.anyString())).thenReturn(userDO);
        UserBO userBO = resource.queryByMis("x");
        verify(userRepository,times(0)).query(Mockito.any(UserRequest.class));
        Assert.assertEquals("unit-mis", userBO.getMis());


        when(userCacheRepository.get(Mockito.anyString())).thenReturn(null);
        userDO.setName("unit-test-query");
        when(userRepository.query(Mockito.any(UserRequest.class))).thenReturn(Collections.singletonList(userDO));
        userBO = resource.queryByMis("x");
        verify(orgClient,times(0)).getOrgUserByMis(Mockito.anyString());
        verify(dxClient, times(0)).getDxUserByMis(Mockito.anyString());
        Assert.assertEquals("unit-test-query", userBO.getName());

        when(userCacheRepository.get(Mockito.anyString())).thenReturn(null);
        when(userRepository.query(Mockito.any(UserRequest.class))).thenReturn(Collections.emptyList());
        when(orgClient.getOrgUserByMis(Mockito.anyString())).thenReturn(orgUser);
        when(dxClient.getDxUserByMis(Mockito.anyString())).thenReturn(dxUser);
        userBO = resource.queryByMis("x");
        verify(orgClient,times(1)).getOrgUserByMis(Mockito.anyString());
        verify(dxClient).getDxUserByMis(Mockito.anyString());
        Assert.assertEquals("unit-misx", userBO.getMis());
    }

    @Test
    public void getUserByOrgDx() {
        when(orgClient.getOrgUserByMis(Mockito.anyString())).thenReturn(orgUser);
        when(dxClient.getDxUserByMis(Mockito.anyString())).thenReturn(dxUser);
        userBO = resource.getUserByOrgDx("x");
        verify(orgClient).getOrgUserByMis(Mockito.anyString());
        verify(dxClient).getDxUserByMis(Mockito.anyString());
        Assert.assertEquals("unit-misx", userBO.getMis());
    }

    @Test
    public void queryByOrgId() {
        userDO.setLoginName("unit-mis");
        when(userRepository.query(Mockito.any(UserRequest.class))).thenReturn(Collections.singletonList(userDO));
        List<UserBO> userBOList = resource.queryByOrgId("1021866");
        verify(userRepository).query(Mockito.any());
        Assert.assertEquals("unit-mis", userBOList.get(0).getMis());
    }

    @Test
    public void getUserByCache() {
        when(userCacheRepository.multiGet(Mockito.anyList())).thenReturn(Collections.singletonList(userDO));
        List<UserBO> boList = resource.getUserByCache(Collections.singletonList("x"));
        Assert.assertTrue(CollectionUtils.isNotEmpty(boList));
        verify(userCacheRepository).multiGet(Mockito.anyList());
    }
    @Test
    public void getUserByCacheEmpty() {
        Assert.assertTrue(CollectionUtils.isEmpty(resource.getUserByCache(Collections.emptyList())));
    }
    @Test
    public void getUserByCacheThrow() {
        when(userCacheRepository.multiGet(Mockito.anyList())).thenThrow(CacheException.class);
        Assert.assertTrue(CollectionUtils.isEmpty(resource.getUserByCache(Collections.singletonList("x"))));
        verify(userCacheRepository).multiGet(Mockito.anyList());
    }

    @Test
    public void getUserByDbEmpty() {
        Assert.assertTrue(CollectionUtils.isEmpty(resource.getUserByDb(Collections.emptyList())));
    }

    @Test
    public void getUserByDb() {
        when(userRepository.query(Mockito.any(UserRequest.class))).thenReturn(Collections.singletonList(userDO));
        List<UserBO> boList = resource.getUserByDb(Collections.singletonList("x"));
        Assert.assertTrue(CollectionUtils.isNotEmpty(boList));
        verify(userRepository).query(Mockito.any(UserRequest.class));
    }

    @Test
    public void getUserWithCache() {
        when(userCacheRepository.multiGet(Mockito.anyList())).thenReturn(Collections.singletonList(userDO));
        List<UserBO> boList = resource.getUserByCacheOrDB(Collections.singletonList("x"));
        Assert.assertTrue(CollectionUtils.isNotEmpty(boList));
        verify(userCacheRepository).multiGet(Mockito.anyList());
        verify(userRepository, times(0)).query(Mockito.any(UserRequest.class));
    }

    @Test
    public void getUser() {
        when(userCacheRepository.multiGet(Mockito.anyList())).thenReturn(Collections.singletonList(userDO));
        when(userRepository.query(Mockito.any(UserRequest.class))).thenReturn(Arrays.asList(userDO, JsonUtil.json2Bean(JsonUtil.bean2Json(userDO), UserDO.class)));
        List<UserBO> boList = resource.getUserByCacheOrDB(Arrays.asList("x","y"));
        Assert.assertTrue(CollectionUtils.isNotEmpty(boList));
        verify(userCacheRepository).multiGet(Mockito.anyList());
        verify(userRepository).query(Mockito.any(UserRequest.class));
    }

    @Test
    public void asyncAddUser() {
        when(userRepository.insert(Mockito.any(UserDO.class))).thenReturn(true);
        resource.asyncAddUser(userBO);
    }

    @Test
    public void asyncUpdateUserRegister() {
        when(userRepository.insert(Mockito.any(UserDO.class))).thenReturn(true);
        resource.asyncAddUser(userBO);
    }

    @Test
    public void isOpsSre(){
        UserDO userDO1 = JsonUtil.json2Bean(JsonUtil.bean2Json(userDO), UserDO.class);
        userDO1.setOrgPath("150046");
        when(userCacheRepository.multiGet(Mockito.anyList())).thenReturn(Collections.singletonList(userDO1));
        boolean sre = resource.isOpsSre("x");
        Assert.assertTrue(sre);
        verify(userCacheRepository).multiGet(Mockito.anyList());
    }
}