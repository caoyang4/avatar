package com.sankuai.avatar.client.ops;

import com.sankuai.avatar.client.TestBase;
import com.sankuai.avatar.client.ops.impl.OpsHttpClientImpl;
import com.sankuai.avatar.client.ops.model.*;
import com.sankuai.avatar.client.ops.request.SrvQueryRequest;
import com.sankuai.avatar.client.ops.response.OpsAvatarSrvsResponse;
import com.sankuai.avatar.client.ops.response.OpsPdlResponse;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;
import java.util.List;


@Slf4j
public class OpsHttpClientTest extends TestBase {

    private OpsHttpClient opsHttpClient;

    public OpsHttpClientTest() {
        this.opsHttpClient = new OpsHttpClientImpl();
    }

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(opsHttpClient, "opsDomain", "http://ops.sre.test.sankuai.com");
        logger.info("=============" + getClass().getName() + "============");
    }

    @Test
    public void testGetHostsByAppkey() {
        String appkey = "com.sankuai.avatar.web";
        List<OpsHost> opsHostList = opsHttpClient.getHostsByAppkey(appkey);
        logger.info(opsHostList.toString());
        Assert.assertTrue(opsHostList.size() > 0);
    }

    @Test
    public void testGetNotExistHostsByAppkey() {
        // 获取不存在的主机信息
        List<OpsHost> opsHostList = opsHttpClient.getHostsByAppkey(testAppkey + "test123");
        logger.info(opsHostList.toString());
        Assert.assertEquals(0, opsHostList.size());
    }

    @Test
    public void testGetHostsBySrv() {
        List<OpsHost> opsHostList = opsHttpClient.getHostsBySrv(testSrvKey);
        logger.info(opsHostList.toString());
        Assert.assertTrue(opsHostList.size() > 0);
    }

    @Test
    public void testGetAppkeyInfo() {
        OpsAppkey opsAppkey = opsHttpClient.getAppkeyInfo(testAppkey);
        logger.info(opsAppkey.toString());
        Assert.assertEquals(opsAppkey.getAppkey(), testAppkey);
    }

    @Test
    public void testGetNotExistAppkeyInfo() {
        try {
            opsHttpClient.getAppkeyInfo(testAppkey + "test123");
        } catch (SdkBusinessErrorException e) {
            Assert.assertNotNull(e.getMessage());
        }
    }

    @Test
    public void getSrvTreeByKey() {
        OpsTree opsTree = opsHttpClient.getSrvTreeByKey(testSrvKey);
        logger.info(opsTree.toString());
        Assert.assertEquals(opsTree.getSrv().getKey(), testSrvKey);
    }


    @Test
    public void getNotExistSrvTreeByKey() {
        try {
            opsHttpClient.getSrvTreeByKey(testSrvKey + "123");
        } catch (SdkBusinessErrorException e) {
            Assert.assertNotNull(e.getMessage());
        }
    }


    @Test
    @Ignore
    public void getSrvsByQueryRequest() {
        final SrvQueryRequest srvQueryRequest = SrvQueryRequest.builder()
                .page(1)
                .pageSize(100)
                .type("mine")
                .user("qinwei05")
                .rank("core")
                .capacity("0,1,2,3,4,5,-1")
                .stateful(true)
                .query("")
                .build();
        List<OpsSrvDetail> opsSrvDetails = opsHttpClient.getSrvsByQueryRequest(srvQueryRequest).getSrvs();
        logger.info(opsSrvDetails.toString());
        Assert.assertTrue(opsSrvDetails.size() > 0);
    }

    @Test
    public void getAllSrvsByQueryRequest() {
        final SrvQueryRequest srvQueryRequest = SrvQueryRequest.builder()
                .page(1)
                .pageSize(100)
                .bg("服务运维部")
                .owt("dianping.tbd")
                .pdl("dianping.tbd.change")
                .startTime(new Date())
                .endTime(new Date())
                .user("qinwei05")
                .rank("core")
                .capacity("0,1,2,3,4,5,-1")
                .stateful(true)
                .query("")
                .build();
        OpsAvatarSrvsResponse opsAvatarSrvsResponse = opsHttpClient.getSrvsByQueryRequest(srvQueryRequest);
        logger.info(opsAvatarSrvsResponse.toString());
        Assert.assertNotNull(opsAvatarSrvsResponse);
    }

    @Test
    @Ignore
    public void getSrvDetailByKey() {
        OpsSrvDetail opsSrvDetail = opsHttpClient.getSrvDetailByKey(testSrvKey);
        logger.info(opsSrvDetail.toString());
        Assert.assertEquals(opsSrvDetail.getSrv().getKey(), testSrvKey);
    }

    @Test
    public void getNotExistSrvDetailByKey() {
        try {
            opsHttpClient.getSrvDetailByKey(testSrvKey + "123");
        } catch (SdkBusinessErrorException e) {
            Assert.assertNotNull(e.getMessage());
        }
    }

    @Test
    public void getSrvSubscribers() {
        List<String> srvSubscribers = opsHttpClient.getSrvSubscribers(testSrvKey);
        logger.info(srvSubscribers.toString());
        Assert.assertTrue(srvSubscribers.size() >= 0);
    }

    @Test
    public void getNotExistSrvSubscribers() {
        try {
            opsHttpClient.getSrvSubscribers(testSrvKey + "123");
        } catch (SdkBusinessErrorException e) {
            Assert.assertNotNull(e.getMessage());
        }
    }

    @Test
    public void testGetAllAppkeyInfo() {
        List<OpsSrv> opsAppkeyList = opsHttpClient.getAllAppkeyInfo();
        logger.info(String.valueOf(opsAppkeyList.size()));
        Assert.assertTrue(opsAppkeyList.size() > 0);
    }

    @Test
    public void testGetSrvInfoByAppkey() {
        OpsSrv opsSrv = opsHttpClient.getSrvInfoByAppkey(testAppkey);
        logger.info(opsSrv.toString());
        Assert.assertEquals(opsSrv.getAppkey(), testAppkey);
    }

    @Test
    public void testGetHostInfo() {
        OpsHost opsHost = opsHttpClient.getHostInfo(hostName);
        logger.info(opsHost.toString());
        Assert.assertEquals(opsHost.getName(), hostName);
    }

    @Test
    @Ignore
    public void testGetOwtList() {
        List<OpsOwt> owtList = opsHttpClient.getOwtList();
        logger.info(owtList.toString());
        Assert.assertTrue(owtList.size() > 0);
    }

    @Test
    @Ignore
    public void testGetOwtByKey() {
        OpsOwt owt = opsHttpClient.getOwtByKey("dianping.tbd");
        logger.info(owt.toString());
        Assert.assertEquals("dianping.tbd", owt.getKey());
    }

    @Test
    public void testGetOrgByOwtKey() {
        List<OpsOrg> orgs = opsHttpClient.getOrgListByKey("meituan.ai");
        logger.info(orgs.toString());
        Assert.assertTrue(orgs.size() > 0);
    }

    @Test
    public void testGetBgs() {
        List<OpsBg> bgs = opsHttpClient.getBgList();
        logger.info(bgs.toString());
        Assert.assertTrue(bgs.size() > 0);
    }

    @Test
    @Ignore
    public void testGetBgsByUser() {
        List<String> bgs = opsHttpClient.getBgListByUser("zhangxiaoning07");
        logger.info(bgs.toString());
        Assert.assertTrue(bgs.size() > 0);
    }

    @Test
    @Ignore
    public void testGetOpsPdlListByOwtKey() {
        List<OpsPdl> pdls = opsHttpClient.getPdlListByOwtKey("dianping.tbd");
        logger.info(pdls.toString());
        Assert.assertTrue(pdls.size() > 0);
    }

    @Test
    @Ignore
    public void testGetOpsPdlByKey() {
        OpsPdlResponse response = opsHttpClient.getPdlByKey("dianping.tbd.change");
        logger.info(response.toString());
        Assert.assertEquals("dianping.tbd.change", response.getPdl().getKey());
        Assert.assertEquals("dianping.tbd", response.getOwt().getKey());
    }

    @Test
    public void testGetSrvListByPdlKey() {
        List<OpsSrv> srvList = opsHttpClient.getSrvListByPdl("dianping.tbd.change");
        logger.info(srvList.toString());
        Assert.assertTrue(srvList.size() > 0);
    }

    @Test
    public void testGetOpsPdl() {
        List<OpsPdl> pdls = opsHttpClient.getPdlList();
        logger.info(pdls.toString());
        Assert.assertTrue(pdls.size() > 0);

    }

    @Test
    public void getSrvKeyByAppkey(){
        String appkey = "com.sankuai.avatar.develop";
        String srv = "meituan.avatar.test.avatar-develop";
        Assert.assertEquals(srv,opsHttpClient.getSrvKeyByAppkey(appkey));
    }

    @Test
    public void isExistAppkey(){
        String appkey = "com.sankuai.avatar.develop";
        Assert.assertTrue(opsHttpClient.isExistAppkey(appkey));
        appkey = "x";
        Assert.assertFalse(opsHttpClient.isExistAppkey(appkey));
    }

    @Test
    public void testUpdateOpsCapacity() {
        String appkey = "com.sankuai.avatar.develop";
        OpsCapacity opsCapacity = OpsCapacity.builder().capacity(5).reason("测试服务，无需关注").build();
        Boolean update = opsHttpClient.updateOpsCapacity(appkey, opsCapacity);
        Assert.assertTrue(update);
    }

    @Test
    @Ignore
    public void testGetUserAppkeyInfo(){
        String user = "caoyang42";
        List<OpsSrv> opsSrvList = opsHttpClient.getUserAppkeyInfo(user);
        Assert.assertTrue(CollectionUtils.isNotEmpty(opsSrvList));
    }

    @Test
    public void getSrvInfoByHost() {
        OpsSrv srv = opsHttpClient.getSrvInfoByHost(hostName);
        Assert.assertNotNull(srv);
    }

    @Test
    public void getAppkeyByHost() {
        String appkeyByHost = opsHttpClient.getAppkeyByHost(hostName);
        Assert.assertEquals("com.sankuai.avatar.cscscscs", appkeyByHost);
    }

    @Test
    public void favorAppkey() {
        String appkey = "com.sankuai.avatar.develop";
        String mis = "caoyang42";
        Boolean favor = opsHttpClient.favorAppkey(appkey, mis);
        Assert.assertTrue(favor);
    }

    @Test
    public void cancelFavorAppkey() {
        String appkey = "com.sankuai.avatar.develop";
        String mis = "caoyang42";
        Boolean cancel = opsHttpClient.cancelFavorAppkey(appkey, mis);
        Assert.assertTrue(cancel);
    }

    @Test
    @Ignore
    public void getUserFavorAppkey(){
        String mis = "caoyang42";
        List<String> favorAppkey = opsHttpClient.getUserFavorAppkey(SrvQueryRequest.builder().user(mis).type("favor").build());
        Assert.assertTrue(CollectionUtils.isNotEmpty(favorAppkey));
    }


    @Test
    public void getOpsUser() {
        String user = "caoyang42";
        OpsUser opsUser = opsHttpClient.getOpsUser(user);
        Assert.assertNotNull(opsUser);
    }

    @Test
    public void isUserOpsSre() {
        String user = "caoyang42";
        boolean sre = opsHttpClient.isUserOpsSre(user);
        Assert.assertFalse(sre);
    }
}
