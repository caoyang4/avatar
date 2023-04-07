package com.sankuai.avatar.web;

import com.meituan.mdp.boot.starter.MdpContextUtils;
import com.meituan.mdp.boot.starter.web.filter.ImportWebFilter;
import com.meituan.mdp.boot.starter.web.filter.MdpWebFilter;
import com.sankuai.microplat.logrecord.sdk.starter.annotation.EnableLogRecord;
import com.sankuai.oceanus.http.filter.InfFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

import javax.servlet.annotation.WebInitParam;

/**
 * 应用程序加载器
 * web 启动排除扫描 workflow 相关的 dao
 *
 * @author caoyang
 * @date 2023/03/16
 */
@ImportWebFilter({
        @MdpWebFilter(filterName = "filterA", clazz = InfFilter.class, urlPatterns = "/*", order = 1, initParams = {
                @WebInitParam(name = "auth", value = "true"),
                @WebInitParam(name = "limit", value = "true"),
                @WebInitParam(name = "rehearse", value = "true"),
                @WebInitParam(name = "oceanus-http.inffilter.auth-include-uri", value = "/api/v2/avatar/**, /open/api/**")}
        ),
})

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class}, scanBasePackages = {"com.sankuai.avatar"})
@MapperScan(basePackages = {"com.sankuai.avatar.web.dal.mapper", "com.sankuai.avatar.dao.*.repository.mapper"})
@EnableLogRecord(tenant = "com.sankuai.avatar.web")
@ServletComponentScan
public class ApplicationLoader {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ApplicationLoader.class);
        application.setAdditionalProfiles(MdpContextUtils.getHostEnvStr());
        application.run(args);
    }
}



