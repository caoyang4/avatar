package com.sankuai.avatar.dao.config;

import com.dianping.squirrel.client.impl.redis.spring.RedisClientBeanFactory;
import com.dianping.zebra.dao.mybatis.ZebraMapperScannerConfigurer;
import com.dianping.zebra.group.jdbc.GroupDataSource;
import com.github.pagehelper.PageInterceptor;
import com.sankuai.meituan.poros.client.PorosHighLevelClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author caoyang
 * @create 2022-09-29 14:43
 */
@Configuration
@ComponentScan(basePackages = {"com.sankuai.avatar.dao.*"})
@MapperScan("com.sankuai.avatar.dao.*.repository.mapper")
public class DataSourceConfig {

    private static final String JDBC_REF = "avatar_workflow";
    private static final String MODEL_PACKAGE = "com.sankuai.avatar.dao.*.model";
    private static final String MAPPER_PACKAGE = "com.sankuai.avatar.dao.*.repository.mapper";

    private static final String ES_CLUSTER_NAME = "eagle_avatar_zf";
    private static final String ES_APPKEY = "com.sankuai.avatar.web";
    private static final String ES_ACCESS_KEY = "F639C0586EE9A0FE8627D61498D96C4E";

    @Bean
    public DataSource zebraDataSource() {
        GroupDataSource ds = new GroupDataSource(JDBC_REF);
        ds.setRouterType("master-only");
        ds.setJdbcRefMaxInitialCount(1000);
        ds.init();
        return ds;
    }

    @Bean(name="sqlSessionFactory")
    public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) {
        SqlSessionFactoryBean ssfb = new SqlSessionFactoryBean();
        ssfb.setDataSource(dataSource);
        ssfb.setTypeAliasesPackage(MODEL_PACKAGE);
        return ssfb;
    }

    @Bean(name = "redisClient")
    public RedisClientBeanFactory redisClientBeanFactory(){
        RedisClientBeanFactory redisClientBeanFactory = new RedisClientBeanFactory();
        redisClientBeanFactory.setClusterName("redis-avatar-flow-cluster_qa");
        redisClientBeanFactory.setRouterType("master-slave");
        return redisClientBeanFactory;
    }

    @Bean
    public RestHighLevelClient restHighLevelClient() {
        return PorosHighLevelClientBuilder.builder()
                .clusterName(ES_CLUSTER_NAME)
                .appKey(ES_APPKEY)
                .accessKey(ES_ACCESS_KEY)
                .httpIOThreadCount(20)
                .timeoutMillis(3000)
                .callESDirectly(true)
                .build();
    }

    @Bean(name = "zebraPage")
    public PageInterceptor zebraPage() {
        Properties props = new Properties();
        props.put("dialectClass", "com.dianping.zebra.dao.dialect.MySQLDialect");
        PageInterceptor interceptor = new PageInterceptor();
        interceptor.setProperties(props);
        return interceptor;
    }

    @Configuration
    @AutoConfigureAfter(DataSourceConfig.class)
    public static class MyBatisMapperScannerConfig {
        @Bean
        public ZebraMapperScannerConfigurer mapperScannerConfigurer() {
            ZebraMapperScannerConfigurer configurer = new ZebraMapperScannerConfigurer();
            configurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
            configurer.setBasePackage(MAPPER_PACKAGE);
            return configurer;
        }
    }

}
