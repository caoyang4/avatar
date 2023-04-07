package com.sankuai.avatar.manager;

import com.sankuai.avatar.TestBase;
import com.sankuai.avatar.sdk.entity.servicecatalog.Application;
import com.sankuai.avatar.sdk.entity.servicecatalog.Slice;
import com.sankuai.avatar.sdk.manager.ServiceCatalogApplication;
import com.sankuai.avatar.sdk.manager.impl.ServiceCatalogApplicationImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class ServiceCatalogApplicationTest extends TestBase {
    @Autowired
    private ServiceCatalogApplicationImpl serviceCatalogApplication;

    @Test
    public void listApplication() throws Exception {
        ServiceCatalogApplication.ApplicationQueryParams applicationQueryParams = new ServiceCatalogApplication.ApplicationQueryParams();
        applicationQueryParams.setPage(1);
        applicationQueryParams.setIsPublic(true);
        applicationQueryParams.setQuery("avatar");
        Slice<Application> sliceApplication = serviceCatalogApplication.listApplication(applicationQueryParams);
        assertEquals(1, sliceApplication.getCn());
    }

    @Test
    public void getApplication() throws Exception {
        String name = "Avatar";
        Application application = serviceCatalogApplication.getApplication(name);
        assertEquals(name, application.getName());
    }

    @Test
    public void testGetApplication() throws Exception {
        Integer id = 5386;
        Application application = serviceCatalogApplication.getApplication(id);
        assertEquals(id, application.getId());
    }
}
