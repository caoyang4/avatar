package com.sankuai.avatar.dao.es;

import com.sankuai.avatar.common.utils.DateUtils;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.dao.es.exception.EsException;
import com.sankuai.avatar.dao.es.impl.AppkeyEsRepositoryImpl;
import com.sankuai.avatar.dao.es.request.AppkeyQueryRequest;
import com.sankuai.avatar.dao.es.request.AppkeyTreeRequest;
import com.sankuai.avatar.dao.es.request.AppkeyUpdateRequest;
import com.sankuai.avatar.dao.es.request.UserAppkeyRequest;
import com.sankuai.avatar.dao.resource.repository.TestBase;
import com.sankuai.avatar.dao.config.DataSourceConfig;
import com.sankuai.avatar.dao.resource.repository.model.AppkeyDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.util.*;

/**
 * @author caoyang
 * @create 2022-12-07 18:35
 */
@Slf4j
public class AppkeyEsRepositoryTest extends TestBase {

    private final AppkeyEsRepository repository;
    static AppkeyUpdateRequest appkeyUpdateRequest;
    static {
        appkeyUpdateRequest = AppkeyUpdateRequest.builder()
                .appkey("com.sankuai.avatar.develop123")
                .name("")
                .description("avatar使用的java服务，目前提供阿凡达web接口使用")
                .srv("meituan.avatar.test.avatar-develop")
                .owt("meituan.avatar")
                .pdl("meituan.avatar.test")
                .rank("核心服务")
                .containerType("jdk8")
                .serviceType("Code: Java")
                .stateful(false)
                .statefulReason("")
                .compatibleIpv6(true)
                .isLiteset(false)
                .isSet(false)
                .tenant("")
                .gitRepository("ssh://git@git.dianpingoa.com/dpop-app/avatar.git")
                .projectType("")
                .isOffline(false)
                .hostCount(3)
                .prodHostCount(1)
                .capacity(5)
                .capacityReason("该服务已接入弹性伸缩，且满足3级容灾，判定容灾等级为5")
                .capacityUpdateAt(new Date())
                .capacityUpdateBy("avatar")
                .weekResourceUtil("66.66%")
                .admin("qinwei05")
                .rdAdmin("caoyang42,qinwei05")
                .opAdmin("caoyang42,qinwei05")
                .epAdmin("caoyang42,qinwei05")
                .applicationId(1)
                .applicationName("Avatar")
                .applicationChName("阿凡达")
                .orgId("100046,150042,1573,150044,1021866")
                .orgName("服务运维部/运维工具开发组/变更管理开发组")
                .type("BACKEND")
                .billingUnitId("31")
                .billingUnit("技术工程及基础数据平台-服务运维-运维管理平台")
                .categories("用户端,运营端")
                .tags("aq,运维工具")
                .frameworks("")
                .isBoughtExternal("")
                .createTime(new Date())
                .updateTime(new Date())
                .build();
    }

    public AppkeyEsRepositoryTest(){
        final ApplicationContext ctx = new AnnotationConfigApplicationContext(DataSourceConfig.class);
        repository = (AppkeyEsRepositoryImpl) ctx.getBean("appkeyEsRepositoryImpl");
    }

    @Test
    public void search() throws EsException {
        System.out.println(repository);
        AppkeyQueryRequest request = AppkeyQueryRequest.builder().appkey("com.sankuai.avatar").build();
        PageResponse<AppkeyDO> search = repository.search(request);
        Assert.assertNotNull(search);
        Assert.assertTrue(search.getItems().size() > 1);
    }

    @Test
    public void searchByRdAdmin() throws EsException {
        System.out.println(repository);
        AppkeyQueryRequest request = AppkeyQueryRequest.builder().rdAdmin("caoyang42").build();
        PageResponse<AppkeyDO> search = repository.search(request);
        Assert.assertNotNull(search);
        Assert.assertTrue(search.getItems().size() > 1);
    }


    @Test
    public void update() throws EsException {
        Boolean update = repository.update(appkeyUpdateRequest);
        Assert.assertTrue(update);
    }

    @Test
    public void query() {
        AppkeyQueryRequest request = AppkeyQueryRequest.builder()
                .srv("meituan.avatar.test.avatar-develop")
                .appkey("com.sankuai.avatar.develop")
                .owt("meituan.avatar")
                .pdl("meituan.avatar.test")
                .stateful(false)
                .build();
        PageResponse<AppkeyDO> query = repository.query(request);
        Assert.assertNotNull(query);
        Assert.assertTrue(CollectionUtils.isNotEmpty(query.getItems()));
    }

    @Test
    public void getOwnAppkey() throws IOException {
        String mis = "caoyang42";
        List<AppkeyDO> doList = repository.getOwnAppkey(UserAppkeyRequest.builder().user(mis).rank("core").stateful(true).build());
        Assert.assertNotNull(doList);
        for (AppkeyDO appkeyDO : doList) {
            Assert.assertTrue(appkeyDO.getRdAdmin().contains(mis) || appkeyDO.getEpAdmin().contains(mis));
        }
    }

    @Test
    public void getTopSortOwnAppkey() throws IOException {
        String mis = "qinwei05";
        List<String> topAppkeys = new ArrayList<>();
        topAppkeys.add("avatar-workflow-web");
        topAppkeys.add("com.sankuai.avatar.gateway.server");
        UserAppkeyRequest request = UserAppkeyRequest.builder()
                .page(1)
                .pageSize(100)
                .user(mis)
                .rank("core")
                .stateful(false)
                .capacity("0,1,2,3,4,5")
                .query("avatar")
                .paas(false)
                .build();
        List<AppkeyDO> doList = repository.getOwnAppkey(request, topAppkeys).getItems();
        Assert.assertNotNull(doList);
        for (AppkeyDO appkeyDO : doList) {
            Assert.assertTrue(appkeyDO.getRdAdmin().contains(mis) || appkeyDO.getEpAdmin().contains(mis));
        }
        AppkeyDO top = doList.get(0);
        Assert.assertEquals(top.getAppkey(), "avatar-workflow-web");
    }

    @Test
    public void getOwnAppkeyByCell() throws IOException {
        String mis = "caoyang42";
        List<AppkeyDO> doList = repository.getOwnAppkey(UserAppkeyRequest.builder().user(mis).cell("set").build());
        Assert.assertNotNull(doList);
        for (AppkeyDO appkeyDO : doList) {
            Assert.assertTrue(appkeyDO.getRdAdmin().contains(mis) || appkeyDO.getEpAdmin().contains(mis));
        }

        List<AppkeyDO> doList2 = repository.getOwnAppkey(UserAppkeyRequest.builder().user(mis).cell("liteset").build());
        Assert.assertNotNull(doList2);
        for (AppkeyDO appkeyDO : doList2) {
            Assert.assertTrue(appkeyDO.getRdAdmin().contains(mis) || appkeyDO.getEpAdmin().contains(mis));
        }
    }

    @Test
    public void getPageAppkey() {
        AppkeyTreeRequest request = new AppkeyTreeRequest();
        request.setPage(1);
        request.setPageSize(500);
        request.setAppkey("avatar.web");
        request.setOwt("dianping.tbd");
        request.setPdl("dianping.tbd.change");
        request.setRank("core");
        request.setStateful(false);
        request.setCapacity("4,5");
        request.setStartTime(DateUtils.StrToDate("2019-12-09 20:45:39"));
        request.setEndTime(DateUtils.StrToDate("2019-12-09 20:50:39"));
        PageResponse<AppkeyDO> pageResponse = repository.getPageAppkey(request);
        Assert.assertTrue(CollectionUtils.isNotEmpty(pageResponse.getItems()));
    }
}