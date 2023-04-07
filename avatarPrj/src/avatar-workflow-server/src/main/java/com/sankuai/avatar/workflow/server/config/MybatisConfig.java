package com.sankuai.avatar.workflow.server.config;

import tk.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @author zhaozhifan02
 */
@Configuration
public class MybatisConfig {
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        Properties properties = new Properties();
        properties.put("mappers", "tk.mybatis.mapper.common.Mapper,tk.mybatis.mapper.common.special.InsertListMapper");

        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.getMapperHelper().setProperties(properties);
        mapperScannerConfigurer.setBasePackage("com.sankuai.avatar.workflow.dao.repository.mapper");

        return mapperScannerConfigurer;
    }
}
