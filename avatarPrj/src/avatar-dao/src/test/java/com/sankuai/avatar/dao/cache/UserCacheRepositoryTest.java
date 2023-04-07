package com.sankuai.avatar.dao.cache;

import com.sankuai.avatar.dao.cache.impl.UserCacheRepositoryImpl;
import com.sankuai.avatar.dao.resource.repository.TestBase;
import com.sankuai.avatar.dao.resource.repository.UserRepository;
import com.sankuai.avatar.dao.config.DataSourceConfig;
import com.sankuai.avatar.dao.resource.repository.impl.UserRepositoryImpl;
import com.sankuai.avatar.dao.resource.repository.model.UserDO;
import com.sankuai.avatar.dao.resource.repository.request.UserRequest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author caoyang
 * @create 2022-10-19 20:04
 */
public class UserCacheRepositoryTest extends TestBase {

    private final UserCacheRepository cacheRepository;
    private final UserRepository userRepository;

    public UserCacheRepositoryTest(){
        final ApplicationContext ctx = new AnnotationConfigApplicationContext(DataSourceConfig.class);
        cacheRepository = (UserCacheRepositoryImpl) ctx.getBean("userCacheRepositoryImpl");
        userRepository = (UserRepositoryImpl) ctx.getBean("userRepositoryImpl");
    }

    @Test
    public void testGet() {
        String mis = "unit-test1";
        UserDO userDO = cacheRepository.get(mis);
        Assert.assertNotNull(userDO);
        Assert.assertEquals(mis, userDO.getLoginName());
    }

    @Test
    public void testMultiGet() {
        List<String> mises = Arrays.asList("unit-test1", "unit-test2");
        List<UserDO> userDOList = cacheRepository.multiGet(mises);
        assert userDOList.size() == 2;
        UserDO userDO = userDOList.get(0);
        Assert.assertTrue(userDO.getLoginName().contains("unit-test"));
    }

    @Test
    public void testSet() {
        String mis = "caoyang42";
        List<UserDO> userDOList = userRepository.query(UserRequest.builder().misList(Collections.singletonList(mis)).build());
        assert userDOList.size() > 0;
        UserDO userDO = userDOList.get(0);
        userDO.setJobStatus("在职");
        int expireTime = 3600;
        Assert.assertTrue(cacheRepository.set(userDO, expireTime));
    }

    @Test
    public void testMultiSet() {
        List<String> mises = Arrays.asList("unit-test1", "unit-test2");
        List<UserDO> userDOList = userRepository.query(UserRequest.builder().misList(mises).build());
        assert userDOList.size() == 2;
        Assert.assertTrue(cacheRepository.multiSet(userDOList,0));
    }
}