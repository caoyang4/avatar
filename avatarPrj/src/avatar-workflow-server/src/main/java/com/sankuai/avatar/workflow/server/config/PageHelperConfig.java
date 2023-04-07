package com.sankuai.avatar.workflow.server.config;

import com.github.pagehelper.PageInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @author caoyang
 * @create 2023-02-21 10:37
 */
@Configuration
public class PageHelperConfig {

    @Bean(name = "zebraPage")
    public PageInterceptor zebraPage() {
        Properties props = new Properties();
        props.put("dialectClass", "com.dianping.zebra.dao.dialect.MySQLDialect");
        PageInterceptor interceptor = new PageInterceptor();
        interceptor.setProperties(props);
        return interceptor;
    }

}
