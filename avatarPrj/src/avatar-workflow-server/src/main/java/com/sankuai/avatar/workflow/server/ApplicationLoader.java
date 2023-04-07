package com.sankuai.avatar.workflow.server;

import com.dianping.rhino.spring.RhinoConfiguration;
import com.meituan.mdp.boot.starter.MdpContextUtils;
import com.meituan.mdp.boot.starter.web.filter.ImportWebFilter;
import com.meituan.mdp.boot.starter.web.filter.MdpWebFilter;
import com.sankuai.oceanus.http.filter.InfFilter;
import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import javax.servlet.annotation.WebInitParam;

@ImportWebFilter({
        @MdpWebFilter(filterName = "filterA", clazz = InfFilter.class, urlPatterns = "/*", order = 1, initParams = {
                @WebInitParam(name = "auth", value = "true"),
                @WebInitParam(name = "rehearse", value = "true"),
                @WebInitParam(name = "oceanus-http.inffilter.auth-include-uri",
                        value = "/api/v2/avatar/workflow/**, /open/api/v2/avatar/workflow/**")}
        ),
})

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class}, scanBasePackages = "com.sankuai.avatar")
@MapperScan(basePackages = {"com.sankuai.avatar.dao.*.repository.mapper"})
@RhinoConfiguration
public class ApplicationLoader {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ApplicationLoader.class);
        application.setAdditionalProfiles(MdpContextUtils.getHostEnvStr());
        application.run(args);
    }
}


