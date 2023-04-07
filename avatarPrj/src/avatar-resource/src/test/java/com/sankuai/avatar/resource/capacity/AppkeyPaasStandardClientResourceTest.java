package com.sankuai.avatar.resource.capacity;

import com.sankuai.avatar.resource.TestBase;
import com.sankuai.avatar.resource.capacity.bo.AppkeyPaasStandardClientBO;
import com.sankuai.avatar.dao.resource.repository.AppkeyPaasStandardClientRepository;
import com.sankuai.avatar.dao.resource.repository.model.AppkeyPaasStandardClientDO;
import com.sankuai.avatar.dao.resource.repository.request.AppkeyPaasStandardClientRequest;
import com.sankuai.avatar.resource.capacity.impl.AppkeyPaasStandardClientResourceImpl;
import com.sankuai.avatar.resource.capacity.request.AppkeyPaasStandardClientRequestBO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import tk.mybatis.mapper.entity.Config;
import tk.mybatis.mapper.mapperhelper.EntityHelper;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * @author caoyang
 * @create 2022-10-11 18:50
 */
public class AppkeyPaasStandardClientResourceTest extends TestBase {

    @Mock
    private AppkeyPaasStandardClientRepository repository;

    private AppkeyPaasStandardClientResource resource;

    static AppkeyPaasStandardClientDO appkeyPaasStandardClientDO;
    static {
        appkeyPaasStandardClientDO = new AppkeyPaasStandardClientDO();
        appkeyPaasStandardClientDO.setPaasName("S3");
        appkeyPaasStandardClientDO.setLanguage("java");
        appkeyPaasStandardClientDO.setStandardVersion("3.16.5");
        appkeyPaasStandardClientDO.setGroupId("com.meituan.s3");
        appkeyPaasStandardClientDO.setArtifactId("s3-client");
        appkeyPaasStandardClientDO.setUpdateBy("caoyang42");
    }
    static AppkeyPaasStandardClientBO appkeyPaasStandardClientBO;
    static {
        appkeyPaasStandardClientBO = new AppkeyPaasStandardClientBO();
        appkeyPaasStandardClientBO.setPaasName("Eagle");
        appkeyPaasStandardClientBO.setLanguage("java");
        appkeyPaasStandardClientBO.setStandardVersion("5.16.5");
        appkeyPaasStandardClientBO.setGroupId("com.meituan.eagle");
        appkeyPaasStandardClientBO.setArtifactId("eagle-client");
        appkeyPaasStandardClientBO.setUpdateBy("caoyang42");
    }

    @Override
    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        EntityHelper.initEntityNameMap(AppkeyPaasStandardClientDO.class, new Config());
        resource = new AppkeyPaasStandardClientResourceImpl(repository);
        when(repository.query(Mockito.any(AppkeyPaasStandardClientRequest.class))).thenReturn(Collections.singletonList(appkeyPaasStandardClientDO));
        when(repository.update(Mockito.any(AppkeyPaasStandardClientDO.class))).thenReturn(true);
    }

    @Test
    public void testQuery() {
        List<AppkeyPaasStandardClientBO> appkeyPaasStandardClientBOList = resource.query(AppkeyPaasStandardClientRequestBO.builder().build());
        Assert.assertEquals(1, appkeyPaasStandardClientBOList.size());
        AppkeyPaasStandardClientBO appkeyPaasStandardClientBO = appkeyPaasStandardClientBOList.get(0);
        Assert.assertEquals("S3", appkeyPaasStandardClientBO.getPaasName());
        Assert.assertEquals("java", appkeyPaasStandardClientBO.getLanguage());
        Assert.assertEquals("com.meituan.s3", appkeyPaasStandardClientBO.getGroupId());
    }

    @Test
    public void testSave() {
        boolean save = resource.save(appkeyPaasStandardClientBO);
        Assert.assertTrue(save);
    }
}