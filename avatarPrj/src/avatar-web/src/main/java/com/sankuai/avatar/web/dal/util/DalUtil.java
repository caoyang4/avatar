package com.sankuai.avatar.web.dal.util;

import com.sankuai.avatar.web.dal.interceptor.TimeUpdateInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @author Jie.li.sh
 * @create 2020-03-18
 **/
@Configuration
public class DalUtil {
    @Bean(name = "timeUpdate")
    public Interceptor timeUpdate(){
        Properties props = new Properties();
        props.put("dialectClass","com.dianping.zebra.dao.dialect.MySQLDialect");
        TimeUpdateInterceptor interceptor = new TimeUpdateInterceptor();
        interceptor.setProperties(props);
        return interceptor;
    }
}
