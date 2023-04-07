package com.sankuai.avatar.sdk.manager.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meituan.mdp.boot.starter.config.annotation.MdpConfig;
import com.sankuai.avatar.sdk.entity.servicecatalog.Application;
import com.sankuai.avatar.sdk.entity.servicecatalog.Slice;
import com.sankuai.avatar.sdk.manager.ServiceCatalogApplication;
import com.sankuai.avatar.sdk.manager.ServiceCatalogHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Jie.li.sh
 * @create 2020-02-11
 **/
@Service
public class ServiceCatalogApplicationImpl implements ServiceCatalogApplication {
    @Autowired
    private ServiceCatalogHttpClient serviceCatalogHttpClient;

    @MdpConfig("SERVICECATALOG_DOMAIN:http://service.fetc.test.sankuai.com")
    private String serviceCatalogDomain;

    @Autowired
    private ObjectMapper objectMapper;

    private String buildPath(String path) {
        return String.format("%s%s", serviceCatalogDomain, path);
    }

    @Override
    public Slice<Application> listApplication(ApplicationQueryParams applicationQueryParams) throws Exception {
        String path = "/open/api/avatar/v1/applications";
        return serviceCatalogHttpClient.get(path, applicationQueryParams.buildQueryParams(), new TypeReference<Slice<Application>>() {});
    }

    @Override
    public Application getApplication(String name) throws Exception {
        String path = String.format("/open/api/avatar/v1/application/%s", name);
        return serviceCatalogHttpClient.get(path, Application.class);
    }

    @Override
    public Application getApplication(Integer applicationId) throws Exception {
        return getApplication(applicationId.toString());
    }
}
