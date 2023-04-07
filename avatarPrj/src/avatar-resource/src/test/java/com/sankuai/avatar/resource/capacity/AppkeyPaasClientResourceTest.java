package com.sankuai.avatar.resource.capacity;

import com.sankuai.avatar.resource.TestBase;
import com.sankuai.avatar.resource.capacity.bo.AppkeyPaasClientBO;
import com.sankuai.avatar.resource.capacity.request.AppkeyPaasClientRequestBO;
import com.sankuai.avatar.dao.resource.repository.AppkeyPaasClientRepository;
import com.sankuai.avatar.dao.resource.repository.model.AppkeyPaasClientDO;
import com.sankuai.avatar.dao.resource.repository.request.AppkeyPaasClientRequest;
import com.sankuai.avatar.resource.capacity.impl.AppkeyPaasClientResourceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import tk.mybatis.mapper.entity.Config;
import tk.mybatis.mapper.mapperhelper.EntityHelper;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * @author caoyang
 * @create 2022-10-11 18:50
 */
public class AppkeyPaasClientResourceTest extends TestBase {

    @Mock
    private AppkeyPaasClientRepository repository;

    private AppkeyPaasClientResource resource;

    static AppkeyPaasClientDO appkeyPaasClientDO;
    static {
        appkeyPaasClientDO = new AppkeyPaasClientDO();
        appkeyPaasClientDO.setId(123);
        appkeyPaasClientDO.setPaasName("S3");
        appkeyPaasClientDO.setClientAppkey("appkey-paas-client-test");
        appkeyPaasClientDO.setLanguage("java");
        appkeyPaasClientDO.setVersion("3.10.6");
        appkeyPaasClientDO.setStandardVersion("3.16.5");
        appkeyPaasClientDO.setIsCapacityStandard(false);
        appkeyPaasClientDO.setUpdateBy("caoyang42");
        appkeyPaasClientDO.setGroupId("com.meituan.s3");
        appkeyPaasClientDO.setArtifactId("s3-client");
        appkeyPaasClientDO.setUpdateDate(new Date());
    }

    static AppkeyPaasClientBO appkeyPaasClientBO;
    static {
        appkeyPaasClientBO = new AppkeyPaasClientBO();
        appkeyPaasClientBO.setId(123);
        appkeyPaasClientBO.setPaasName("Eagle");
        appkeyPaasClientBO.setClientAppkey("appkey-paas-client-test");
        appkeyPaasClientBO.setLanguage("java");
        appkeyPaasClientBO.setVersion("3.18.16");
        appkeyPaasClientBO.setStandardVersion("3.16.5");
        appkeyPaasClientBO.setIsCapacityStandard(true);
        appkeyPaasClientBO.setUpdateBy("caoyang42");
        appkeyPaasClientBO.setGroupId("com.meituan.eagle");
        appkeyPaasClientBO.setArtifactId("eagle-client");
        appkeyPaasClientBO.setUpdateDate(new Date());
    }

    @Override
    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        EntityHelper.initEntityNameMap(AppkeyPaasClientDO.class, new Config());
        resource = new AppkeyPaasClientResourceImpl(repository);
        when(repository.query(Mockito.any(AppkeyPaasClientRequest.class))).thenReturn(Collections.singletonList(appkeyPaasClientDO));
        when(repository.update(Mockito.any(AppkeyPaasClientDO.class))).thenReturn(true);
        when(repository.delete(Mockito.any(Integer.class))).thenReturn(true);
    }

    @Test
    public void testQuery() {
        List<AppkeyPaasClientBO> appkeyPaasClientBOList = resource.query(AppkeyPaasClientRequestBO.builder().build());
        Assert.assertEquals(1, appkeyPaasClientBOList.size());
        AppkeyPaasClientBO appkeyPaasClientBO = appkeyPaasClientBOList.get(0);
        Assert.assertEquals("S3", appkeyPaasClientBO.getPaasName());
        Assert.assertEquals("appkey-paas-client-test", appkeyPaasClientBO.getClientAppkey());
        Assert.assertEquals("java", appkeyPaasClientBO.getLanguage());
    }

    @Test
    public void testSave() {
        boolean save = resource.save(appkeyPaasClientBO);
        Assert.assertTrue(save);
    }

    @Test
    public void testDeleteByCondition() {
        Assert.assertFalse(resource.deleteByCondition(null));
        resource.deleteByCondition(AppkeyPaasClientRequestBO.builder().appkey("test").build());
    }
}