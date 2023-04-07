package com.sankuai.avatar.collect;

import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author qinwei05
 * @date 2022/11/8 20:30
 * @version 1.0
 */
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class},
        scanBasePackages = {"com.sankuai.avatar.collect", "com.sankuai.avatar.client", "com.sankuai.avatar.resource.dao"})
public class TestApplication {
}
