package com.sankuai.avatar.dao.resource.repository;

import com.sankuai.avatar.dao.config.DataSourceConfig;
import com.sankuai.avatar.dao.resource.repository.impl.WhitelistAppRepositoryImpl;
import com.sankuai.avatar.dao.resource.repository.model.WhitelistAppDO;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author caoyang
 * @create 2022-11-02 21:55
 */
public class WhitelistAppRepositoryTest extends TestBase {

    private final WhitelistAppRepository repository;

    public WhitelistAppRepositoryTest() {
        final ApplicationContext ctx = new AnnotationConfigApplicationContext(DataSourceConfig.class);
        repository = (WhitelistAppRepositoryImpl) ctx.getBean("whitelistAppRepositoryImpl");
    }

    @Test
    public void query() {
        Assert.assertTrue(repository.query().size() > 0);
    }

    @Test
    public void insert() {
        Assert.assertFalse(repository.insert(null));
    }

    @Test
    public void update() {
        WhitelistAppDO whitelistAppDO = new WhitelistAppDO();
        whitelistAppDO.setId(7);
        whitelistAppDO.setApp("service-drill");
        whitelistAppDO.setCname("非核心演习");
        whitelistAppDO.setDescription("非核心服务演习白名单，演习系统会忽略加入白名单的服务");
        Assert.assertTrue(repository.update(whitelistAppDO));
    }
}