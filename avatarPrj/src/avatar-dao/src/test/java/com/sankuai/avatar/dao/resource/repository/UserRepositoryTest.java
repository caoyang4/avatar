package com.sankuai.avatar.dao.resource.repository;

import com.sankuai.avatar.common.utils.JsonUtil;
import com.sankuai.avatar.dao.config.DataSourceConfig;
import com.sankuai.avatar.dao.resource.repository.impl.UserRepositoryImpl;
import com.sankuai.avatar.dao.resource.repository.model.UserDO;
import com.sankuai.avatar.dao.resource.repository.request.UserRequest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * @author caoyang
 * @create 2022-10-19 14:36
 */
public class UserRepositoryTest extends TestBase {

    private final UserRepository repository;

    public UserRepositoryTest(){
        final ApplicationContext ctx = new AnnotationConfigApplicationContext(DataSourceConfig.class);
        repository = (UserRepositoryImpl) ctx.getBean("userRepositoryImpl");
    }
    private static final UserDO USER_DO = new UserDO();
    static {
        USER_DO.setNumber(666);
        USER_DO.setName("unit-test");
        USER_DO.setSource("MT");
        USER_DO.setOrganization("公司/美团/基础研发平台/基础技术部/服务运维部/运维工具开发组/变更管理开发组");
        USER_DO.setRole("rd");
        USER_DO.setOrgPath("0-1-2-100046-150042-1573-150044-1021866");
        USER_DO.setOrgId("1021866");
        USER_DO.setLeader("xxx");
        USER_DO.setUserImage("xxx");
    }

    @Test
    public void testQuery() {
        String mis = "caoyang42";
        List<UserDO> userDOList = repository.query(UserRequest.builder().misList(Collections.singletonList(mis)).build());
        Assert.assertEquals(1, userDOList.size());
        UserDO userDO = userDOList.get(0);
        Assert.assertEquals(mis, userDO.getLoginName());
    }

    @Test
    public void testSearch(){
        String search = "caoyang";
        List<UserDO> userDOList = repository.query(UserRequest.builder().search(search).build());
        assert userDOList.size() > 0;
        for (UserDO userDO : userDOList) {
            Assert.assertTrue(userDO.getLoginName().contains(search)
                    || userDO.getName().contains(search));
        }
    }

    @Test
    public void testInsert() {
        USER_DO.setLoginName(UUID.randomUUID().toString().substring(0,8));
        Assert.assertTrue(repository.insert(USER_DO));
    }

    @Test
    public void testInsertAndQuery() {
        UserDO userDO = JsonUtil.json2Bean(JsonUtil.bean2Json(USER_DO), UserDO.class);
        Assert.assertNotNull(userDO);
        String loginName = UUID.randomUUID().toString().substring(0, 8);
        userDO.setLoginName(loginName);
        UserDO user = repository.insertAndQuery(userDO);
        Assert.assertNotNull(user);
        Assert.assertNotNull(user.getId());
        Assert.assertEquals(loginName, user.getLoginName());
        Assert.assertEquals(userDO.getName(), user.getName());
    }

    @Test
    public void testUpdate() {
        String mis = "caoyang42";
        List<UserDO> userDOList = repository.query(UserRequest.builder().misList(Collections.singletonList(mis)).build());
        Assert.assertEquals(1, userDOList.size());
        UserDO userDO = userDOList.get(0);
        assert userDO.getId() > 0;
        userDO.setOrgPath("0-1-2-100046-150042-1573-150044-1021866");
        userDO.setOrgId("1021866");
        userDO.setUserImage("https://s3plus-img.meituan.net/v1/mss_491cda809310478f898d7e10a9bb68ec/profile14/b3e763b3-3748-472f-8859-97f28c0a5b7d_200_200");
        Assert.assertTrue(repository.update(userDO));
    }

    @Test
    public void testDelete() {
        int id = 1;
        List<UserDO> userDOList = repository.query(UserRequest.builder().id(id).build());
        boolean delete = repository.delete(id);
        if (!userDOList.isEmpty()) {
            Assert.assertTrue(delete);
        } else {
            Assert.assertFalse(delete);
        }
    }
}