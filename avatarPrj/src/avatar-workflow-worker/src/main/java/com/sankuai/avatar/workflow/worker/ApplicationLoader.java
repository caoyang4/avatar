package com.sankuai.avatar.workflow.worker;

import com.meituan.mdp.boot.starter.MdpContextUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author zhaozhifan02
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class}, scanBasePackages = "com.sankuai.avatar")
public class ApplicationLoader {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ApplicationLoader.class);
        application.setAdditionalProfiles(MdpContextUtils.getHostEnvStr());
        application.run(args);
    }
}
