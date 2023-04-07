package com.sankuai.avatar.web.dal.plugin;

import org.apache.ibatis.plugin.Interceptor;
import com.github.pagehelper.PageInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Properties;

/**
 * @author chenxinli
 */
@Configuration
public class PageHelper {
    @Bean(name = "zebraPage")
    public Interceptor page() {
        Properties props = new Properties();
        props.put("dialectClass", "com.dianping.zebra.dao.dialect.MySQLDialect");
        PageInterceptor interceptor = new PageInterceptor();
        interceptor.setProperties(props);
        return interceptor;
    }
}