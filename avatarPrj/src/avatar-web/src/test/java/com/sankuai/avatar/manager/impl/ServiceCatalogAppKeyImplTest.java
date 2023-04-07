package com.sankuai.avatar.manager.impl;

import com.sankuai.avatar.TestBase;
import com.sankuai.avatar.sdk.entity.servicecatalog.*;
import com.sankuai.avatar.sdk.manager.ServiceCatalogAppKey;
import com.sankuai.avatar.sdk.manager.impl.ServiceCatalogAppKeyImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ServiceCatalogAppKeyImplTest extends TestBase {
    @Autowired
    private ServiceCatalogAppKeyImpl serviceCatalogAppKey;

    @Test
    public void listAppKey() throws Exception {
        ServiceCatalogAppKey.AppKeyQueryParams appKeyQueryParams = new ServiceCatalogAppKey.AppKeyQueryParams();
        appKeyQueryParams.setPage(1);
        appKeyQueryParams.setPageSize(10);
        Slice<AppKey> appKeySlice = serviceCatalogAppKey.listAppKey(appKeyQueryParams);
        assertTrue(appKeySlice.getItems().size() > 0);
    }

    @Test
    public void getAppKey() throws Exception {
        String appKey = "avatar-workflow-web";
        AppKey appkey = serviceCatalogAppKey.getAppKey(appKey);
        System.out.println(appkey.getAppKey());
        assertEquals(appKey, appkey.getAppKey());
    }

    @Test
    public void getAppkeylistByCondition() throws Exception {
        ServiceCatalogAppKey.AppKeyQueryParams appKeyQueryParams = new ServiceCatalogAppKey.AppKeyQueryParams();
        appKeyQueryParams.setOrgIds("1021866");
        appKeyQueryParams.setApplication("Avatar");
        List<String> appkeyList = serviceCatalogAppKey.getAppkeylistByCondition(appKeyQueryParams);
        System.out.println(appkeyList);
    }

    @Test
    public void getAllTags() throws Exception {
        List<Tag> tagList = serviceCatalogAppKey.getAllTags();
        assertTrue(tagList.size() > 200);
    }

    @Test
    public void getAllCategories() throws Exception {
       List<Category> categoryList = serviceCatalogAppKey.getAllCategories();
       assertTrue(categoryList.size() > 4);
    }

    @Test
    public void getAllFrameworks() throws Exception {
        List<Framework> frameworkList = serviceCatalogAppKey.getAllFrameworks();
        assertTrue(frameworkList.size() > 10);
    }
}
