package com.sankuai.avatar.dao.resource.repository;

import com.sankuai.avatar.dao.config.DataSourceConfig;
import com.sankuai.avatar.dao.resource.repository.impl.UserRelationRepositoryImpl;
import com.sankuai.avatar.dao.resource.repository.model.UserRelationDO;
import com.sankuai.avatar.dao.resource.repository.request.UserRelationRequest;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Date;
import java.util.List;

/**
 * @author caoyang
 * @create 2022-11-02 19:44
 */
public class UserRelationRepositoryTest extends TestBase {

    private final UserRelationRepository repository;

    public UserRelationRepositoryTest(){
        final ApplicationContext ctx = new AnnotationConfigApplicationContext(DataSourceConfig.class);
        repository = (UserRelationRepositoryImpl) ctx.getBean("userRelationRepositoryImpl");
    }

    static UserRelationDO userRelationDO = new UserRelationDO();
    static {
        userRelationDO.setAppkey("test-appkey");
        userRelationDO.setLoginName("unit");
        userRelationDO.setTag("top");
    }

    @Test
    public void query() {
        String loginName = "qinwei05";
        List<UserRelationDO> query = repository.query(UserRelationRequest.builder().loginName(loginName).build());
        assert query.size() > 0;
        for (UserRelationDO userRelationDO : query) {
            Assert.assertEquals(loginName, userRelationDO.getLoginName());
        }
    }

    @Test
    public void insert() {
        Assert.assertTrue(repository.insert(userRelationDO));
    }

    @Test
    public void update() {
        List<UserRelationDO> query = repository.query(UserRelationRequest.builder().loginName("unit").build());
        Assert.assertNotNull(query);
        if (CollectionUtils.isNotEmpty(query)) {
            UserRelationDO userRelationDO = query.get(0);
            userRelationDO.setUpdateTime(new Date());
            Assert.assertTrue(repository.update(userRelationDO));
        }
    }

    @Test
    public void delete() {
        List<UserRelationDO> query = repository.query(UserRelationRequest.builder().loginName("unit").build());
        Assert.assertNotNull(query);
        if (CollectionUtils.isNotEmpty(query)) {
            UserRelationDO userRelationDO = query.get(0);
            Assert.assertTrue(repository.delete(userRelationDO.getId()));
        }
    }
}