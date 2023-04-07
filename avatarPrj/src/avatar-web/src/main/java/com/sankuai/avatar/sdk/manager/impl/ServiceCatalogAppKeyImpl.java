package com.sankuai.avatar.sdk.manager.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sankuai.avatar.sdk.entity.servicecatalog.*;
import com.sankuai.avatar.sdk.manager.ServiceCatalogAppKey;
import com.sankuai.avatar.sdk.manager.ServiceCatalogHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Jie.li.sh
 * @create 2020-02-11
 **/
@Service
public class ServiceCatalogAppKeyImpl implements ServiceCatalogAppKey {
    @Autowired
    private ServiceCatalogHttpClient serviceCatalogHttpClient;

    @Override
    public Slice<AppKey> listAppKey(AppKeyQueryParams appKeyQueryParams) throws Exception {
        String path = "/open/api/avatar/v1/appKeys";
        Map<String, Object> params = null;
        if (appKeyQueryParams != null) {
            params = appKeyQueryParams.buildQueryParams();
        }

        return serviceCatalogHttpClient.get(path, params, new TypeReference<Slice<AppKey>>() {
        });
    }

    @Override
    public AppKey getAppKey(String appkey) throws Exception {
        String path = String.format("/open/api/avatar/v1/appKey/%s", appkey);
        return serviceCatalogHttpClient.get(path, AppKey.class);
    }

    @Override
    public List<String> getAppkeylistByCondition(AppKeyQueryParams appKeyQueryParams) throws Exception {
        List<String> appKeys = new ArrayList<>();
        Slice<AppKey> appKeySlice;
        try {
            appKeySlice = listAppKey(appKeyQueryParams);
        }catch (Exception e){
            return appKeys;
        }
        final int totalPage = appKeySlice.getPn();
        final int curPage = appKeySlice.getCn();
        for (int i = curPage; i <= totalPage; i++) {
            try {
                appKeyQueryParams.setPage(i);
                Slice<AppKey> sliceResult = listAppKey(appKeyQueryParams);
                appKeys.addAll(sliceResult.getItems().stream().map(AppKey::getAppKey).collect(Collectors.toList()));
            } catch (IOException e) {
                continue;
            }
        }
        return appKeys;
    }

    @Override
    public List<Tag> getAllTags() throws Exception {
        String path = "/open/api/v1/appKey/tags";
        return serviceCatalogHttpClient.getListData(path, Tag.class);
    }

    @Override
    public List<Category> getAllCategories() throws Exception {
        String path = "/open/api/v1/appKey/categories";
        return serviceCatalogHttpClient.getListData(path, Category.class);
    }

    @Override
    public List<Framework> getAllFrameworks() throws Exception {
        String path = "/open/api/v1/appKey/frameworks";
        return serviceCatalogHttpClient.getListData(path, Framework.class);
    }
}
