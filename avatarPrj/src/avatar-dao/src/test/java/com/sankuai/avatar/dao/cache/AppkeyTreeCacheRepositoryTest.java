package com.sankuai.avatar.dao.cache;

import com.sankuai.avatar.dao.cache.impl.AppkeyTreeCacheRepositoryImpl;
import com.sankuai.avatar.dao.resource.repository.TestBase;
import com.sankuai.avatar.dao.config.DataSourceConfig;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author caoyang
 * @create 2023-01-09 15:28
 */
public class AppkeyTreeCacheRepositoryTest extends TestBase {

    private final AppkeyTreeCacheRepository cacheRepository;

    public AppkeyTreeCacheRepositoryTest(){
        final ApplicationContext ctx = new AnnotationConfigApplicationContext(DataSourceConfig.class);
        cacheRepository = (AppkeyTreeCacheRepositoryImpl) ctx.getBean("appkeyTreeCacheRepositoryImpl");
    }

    @Test
    public void getUserBg() {
        List<String> userBg = cacheRepository.getUserBg("test-user");
        Assert.assertTrue(CollectionUtils.isNotEmpty(userBg));
    }

    @Test
    public void setUserBg() {
        Boolean cache = cacheRepository.setUserBg("test-user", Arrays.asList("部门 1", "部门 2"), -1);
        Assert.assertTrue(cache);
    }

    @Test
    public void getPdlSrvCount() {
        Map<String, Integer> pdlSrvCount = cacheRepository.getPdlSrvCount("test-pdl");
        Assert.assertEquals(1, pdlSrvCount.getOrDefault("test-pdl", 1).intValue());
    }

    @Test
    public void multiGetPdlSrvCount() {
        Map<String, Integer> maps = cacheRepository.multiGetPdlSrvCount(Arrays.asList("test-pdl", "test-pdlx"));
        Assert.assertTrue(MapUtils.isNotEmpty(maps));
    }

    @Test
    public void setPdlSrvCount() {
        Boolean cache = cacheRepository.setPdlSrvCount("test-pdlx", 1, -1);
        Assert.assertTrue(cache);
    }

}