package com.sankuai.avatar.web.config;

import com.dianping.rhino.api.RhinoAPIOneLimiterService;
import com.meituan.service.inf.kms.client.KmsAuthDataSource;
import com.meituan.service.mobile.mtthrift.auth.DefaultSignHandler;
import com.meituan.service.mobile.mtthrift.proxy.ThriftClientProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author qinwei05 (ว ˙o˙)ง
 * @date 2022/3/25 19:06
 * @version 1.0
 */

@Configuration
public class RhinoSdkConfig {

    @Bean
    public RhinoAPIOneLimiterService.Iface RhinoAPIOneLimiterService() throws Exception {
        KmsAuthDataSource kmsAuthDataSource = new KmsAuthDataSource();
        String kmsClientAppkey = "com.sankuai.avatar.web";
        kmsAuthDataSource.setAppkey(kmsClientAppkey);
        DefaultSignHandler defaultSignHandler = new DefaultSignHandler();
        defaultSignHandler.setAuthDataSource(kmsAuthDataSource);

        ThriftClientProxy rhinoOneLimiterAPIClient = new ThriftClientProxy();
        rhinoOneLimiterAPIClient.setServiceInterface(RhinoAPIOneLimiterService.class);
        String rhinoKey = "rhino-open-api-service";
        rhinoOneLimiterAPIClient.setRemoteAppkey(rhinoKey);
        rhinoOneLimiterAPIClient.setNettyIO(true);
        rhinoOneLimiterAPIClient.setRemoteUniProto(true);
        rhinoOneLimiterAPIClient.setRemoteServerPort(9003);
        rhinoOneLimiterAPIClient.setSignHandler(defaultSignHandler);
        rhinoOneLimiterAPIClient.setTimeout(5000);
        rhinoOneLimiterAPIClient.afterPropertiesSet();
        return (RhinoAPIOneLimiterService.Iface) rhinoOneLimiterAPIClient.getObject();
    }
}
