package com.sankuai.avatar.client;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 测试应用程序
 *
 * @author qinwei05
 * @date 2022/10/20
 */
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class}, scanBasePackages = "com.sankuai.avatar.client")
public class TestApplication {
}