package com.sankuai.avatar.client.config;

import com.sankuai.meituan.org.opensdk.client.RemoteServiceClient;
import com.sankuai.meituan.org.opensdk.service.*;
import com.sankuai.meituan.org.opensdk.service.impl.*;
import com.sankuai.meituan.org.queryservice.domain.param.DataScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * @author caoyang
 * @create 2022-10-26 15:32
 */
@Configuration
public class OrgSdkConfig {
    private final static String REMOTE_APP_KEY = "com.sankuai.it.bsi.mdmgatewayservice";

    private final String clientAppkey = "624c40d252";

    private final String appSecret ="48f6dbbb20e743c891a068ed5cee5ff3";

    private final static Integer APP_TENANT_ID = 1;

    /**
     *设置租户下对应的source.如果设置为"ALL"，则查tenantId下所有sources。
     */
    private final static String APP_SOURCE = "MT";


    @Bean
    public RemoteServiceClient remoteServiceClient() throws Exception {

        // 设置App默认的数据访问范围。如下设置，App默认所有的请求是针对美团租户下“MT”数据域的ORG数据
        DataScope dataScope = new DataScope();
        dataScope.setTenantId(APP_TENANT_ID);
        dataScope.setSources(Arrays.asList(APP_SOURCE));

        return new RemoteServiceClient(clientAppkey, appSecret, REMOTE_APP_KEY, dataScope, null, 3000);
    }

    @Bean
    public CompService compService(RemoteServiceClient remoteServiceClient) {
        return new CompServiceImpl(remoteServiceClient);
    }

    @Bean
    public DictService dictService(RemoteServiceClient remoteServiceClient) {
        return new DictServiceImpl(remoteServiceClient);
    }

    @Bean
    public EmpService empService(RemoteServiceClient remoteServiceClient) {
        return new EmpServiceImpl(remoteServiceClient);
    }

    @Bean
    public JobCodeService jobCodeService(RemoteServiceClient remoteServiceClient) {
        return new JobCodeServiceImpl(remoteServiceClient);
    }

    @Bean
    public OrgService orgService(RemoteServiceClient remoteServiceClient) {
        return new OrgServiceImpl(remoteServiceClient);
    }

    @Bean
    public SiteCodeService siteCodeService(RemoteServiceClient remoteServiceClient) {
        return new SiteCodeServiceImpl(remoteServiceClient);
    }
}
