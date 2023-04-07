package com.sankuai.avatar.web.crane;

import com.cip.crane.client.spring.annotation.Crane;
import com.cip.crane.client.spring.annotation.CraneConfiguration;
import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import com.meituan.mdp.boot.starter.config.annotation.MdpConfig;
import com.meituan.mdp.boot.starter.threadpool.ExecutorServices;
import com.meituan.mdp.boot.starter.threadpool.NamedRunnableTask;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.resource.dxMessage.DxMessageResource;
import com.sankuai.avatar.resource.orgRole.OrgRoleAdminResource;
import com.sankuai.avatar.resource.orgRole.bo.OrgBO;
import com.sankuai.avatar.resource.orgRole.constant.OrgRoleType;
import com.sankuai.avatar.sdk.entity.servicecatalog.AppKey;
import com.sankuai.avatar.sdk.entity.servicecatalog.Org;
import com.sankuai.avatar.sdk.entity.servicecatalog.Slice;
import com.sankuai.avatar.sdk.manager.ServiceCatalogAppKey;
import com.sankuai.avatar.sdk.manager.ServiceCatalogHttpClient;
import com.sankuai.avatar.web.util.GsonUtils;
import com.sankuai.avatar.web.util.OpsUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * @Author: qinwei05
 * @Date: 2020/11/10 16:38
 */

@CraneConfiguration
@Component
@Slf4j
public class CraneOrgSreCorrectNotice {

    private final ServiceCatalogAppKey serviceCatalogAppKey;
    private final ServiceCatalogHttpClient serviceCatalogHttpClient;
    private final OrgRoleAdminResource orgRoleAdminResource;
    private final DxMessageResource dxMessageResource;

    @Autowired
    public CraneOrgSreCorrectNotice(ServiceCatalogAppKey serviceCatalogAppKey,
                                    ServiceCatalogHttpClient serviceCatalogHttpClient,
                                    OrgRoleAdminResource orgRoleAdminResource,
                                    DxMessageResource dxMessageResource) {
        this.serviceCatalogAppKey = serviceCatalogAppKey;
        this.serviceCatalogHttpClient = serviceCatalogHttpClient;
        this.orgRoleAdminResource = orgRoleAdminResource;
        this.dxMessageResource = dxMessageResource;
    }

    private static final String SC_ORG_TREE_API = "/open/api/avatar/v1/org/tree";

    @MdpConfig("ORG_SRE_NOTICE_WHITE_LIST:[]")
    private static String orgSreNoticeWhiteList;

    @MdpConfig("ORG_SRE_NOTICE_SWITCH:False")
    private static String noticeSwitch;

    private static List<String> paasOwts = new ArrayList<>();

    private final ExecutorService executorService = ExecutorServices.forThreadPoolExecutor(
            "PatchAppkeyDetailInfo",
            5,
            TimeUnit.SECONDS,
            new ThreadPoolExecutor.DiscardOldestPolicy()
    );

    static {
        try {
            paasOwts = OpsUtils.getPaasOwts();
        } catch (Exception e) {
            Cat.logError(e);
        }
    }

    private String replaceChar(String s){
        return s.replace("0-1-2-", "").replace("-", ",");
    }

    /**
     * 从SC中获取所有BG节点（包含猫眼）
     * @return ORG根节点
     */
    public List<Org> getOrgTreePaths() {
        List<Org> treeNode = new ArrayList<>();
        Map<String, Object> params = new HashMap<>(2);
        params.put("source", "avatar");
        try {
            List<Org> mtOrgTreeNode = serviceCatalogHttpClient.getListData(SC_ORG_TREE_API, params, Org.class);
            treeNode.addAll(mtOrgTreeNode);
            params.put("isMaoyan", "True");
            List<Org> maoyanOrgTreeNode = serviceCatalogHttpClient.getListData(SC_ORG_TREE_API, params, Org.class);
            treeNode.addAll(maoyanOrgTreeNode);
        } catch (Exception e) {
            Cat.logError(e);
        }
        return treeNode;
    }

    /**
     * 部门SRE配置数据校准提醒
     * */
    @Crane("com.sankuai.avatar.web.sendOrgSreNotice")
    public void sendOrgSreNotice() {
        Transaction t = Cat.newTransaction("OrgSreAdjust", "CalculateOrgSreInfo");
        int count = 0;
        List<String> dataTmp = new ArrayList<>();
        String[] noticeWhiteList = GsonUtils.deserialization(orgSreNoticeWhiteList, String[].class);
        Map<String, List<AppKey>> appkeys = getOrgAppkeys();
        try {
            for (Map.Entry<String, List<AppKey>> entry : appkeys.entrySet()) {
                String orgId = entry.getKey();
                OrgBO orgBO = null;
                try {
                    orgBO = orgRoleAdminResource.getOrgByOrgClient(orgId);
                } catch (SdkBusinessErrorException ignored) {continue;}
                String roleUsers = orgRoleAdminResource.getRoleUsers(orgId, OrgRoleType.OP_ADMIN);
                if (Objects.isNull(orgBO) || StringUtils.isEmpty(roleUsers) || Arrays.asList(noticeWhiteList).contains(orgId)) {
                    log.info("PAAS IGNORE CALC: " + (orgBO != null ? orgBO.getOrgNamePath() : orgId));
                    t.addData("PAAS IGNORE CALC: " + (orgBO != null ? orgBO.getOrgNamePath() : orgId));
                    continue;
                }
                String opAdmin = parseAndCombineSreData(roleUsers);
                int diffCount = calcDiffCount(roleUsers, entry.getValue());
                // 不一致处理
                if (diffCount > 0){
                    sendNotice(diffCount, opAdmin, orgBO);
                    count++;
                    dataTmp.add(String.format("%s: %s/%s", orgBO.getOrgNamePath().replace("-", "/"), diffCount, entry.getValue().size()));
                }
            }
            t.addData(String.format("Total Count：%s/%s", count, appkeys.size()));
        } catch (Exception e) {
            Cat.logError(e);
        } finally {
            t.complete();
        }

        log.info("Total Diff Date Calc End");
        dataTmp.forEach(log::info);
        log.info("Total Count：{}/{}", count, appkeys.size());
    }

    private void sendNotice(Integer diffCount, String opAdmin, OrgBO org){
        if (Objects.nonNull(org)){
            String orgCompleteId = org.getOrgPath();
            String orgNamePath = org.getOrgNamePath().replace("-", "/");
            log.info(String.format("SRE DIFF: %s(%s), COUNT: 【%s】", orgNamePath, opAdmin, diffCount));
            String text = "【组织架构运维负责人覆盖配置通知】\n"
                    + "【现状】%s存在%s个Appkey的运维负责人与部门负责人配置【%s】不一致（PaaS类型服务不计入计算），请前往Avatar部门处进行覆盖修改，"
                    + "若配置符合实情且无需修改可联系 [秦伟/qinwei05] 进行加白处理，之后将不会收到此通知。\n"
                    + "【链接】[点击前往查看|https://avatar.mws.sankuai.com/#/owt/service?ids=%s]";
            String sendText = String.format(text, orgNamePath, diffCount, opAdmin, replaceChar(orgCompleteId).replace(",", "%2C"));
            List<String> misList = Arrays.asList( opAdmin.split(","));
            if ("True".equals(noticeSwitch)){
                try {
                    misList.addAll(Arrays.asList("qinwei05", "caoyang42"));
                    dxMessageResource.pushDxMessage(misList, sendText);
                } catch (SdkBusinessErrorException ignored) {
                }
            }

        }
    }

    private int calcDiffCount(String roleUsers, List<AppKey> entryValue){
        int diffCount = 0;
        String opAdmin = parseAndCombineSreData(roleUsers);
        List<String> appKeySre = entryValue.stream().map(AppKey::getOpAdmin).collect(Collectors.toList());
        for (String appkeySre: appKeySre){
            String parseSre = parseAndCombineSreData(appkeySre);
            if (!Objects.equals(opAdmin, parseSre)){
                diffCount++;
            }
        }
        return diffCount;
    }

    /**
     * BG层面orgId -> 根节点全部Appkey -> appkey按照子部门归类 -> 部门SRE与Appkey挨个比对
     * */
    public Map<String, List<AppKey>> getOrgAppkeys(){
        Map<String, List<AppKey>> orgAppkeys = new HashMap<>(2000);
        List<Org> treeNode = getOrgTreePaths();
        for (Org org: treeNode) {
            Transaction t = Cat.newTransaction("Crane.getOrgAppkeys", String.format("orgName[%s]", org.getName()));
            try {
                List<AppKey> notPaasAppKeyList = getAppkeyByOrgId(String.valueOf(org.getId()));
                for (AppKey appKey: notPaasAppKeyList) {
                    String orgId = appKey.getOrgIds();
                    if (orgAppkeys.containsKey(orgId)) {
                        orgAppkeys.get(orgId).add(appKey);
                    } else {
                        List<AppKey> curOrgAppkey = new ArrayList<>();
                        curOrgAppkey.add(appKey);
                        orgAppkeys.put(orgId, curOrgAppkey);
                    }
                }
            } catch (Exception e) {
                Cat.logError(e);
            } finally {
                t.complete();
            }
        }
        return orgAppkeys;
    }


    private List<AppKey> getAppkeyByOrgId(String orgId){
        ServiceCatalogAppKey.AppKeyQueryParams appKeyQueryParams = new ServiceCatalogAppKey.AppKeyQueryParams();
        List<AppKey> appKeyList = new ArrayList<>();
        String scOrgId = replaceChar(orgId);
        appKeyQueryParams.setOrgIds(scOrgId);

        int page = 1;
        appKeyQueryParams.setPage(page);
        appKeyQueryParams.setPageSize(200);
        int maxRetryCount = 0;
        while (true) {
            Slice<AppKey> appKeyPageInfo = new Slice<>();
            try {
                appKeyPageInfo = serviceCatalogAppKey.listAppKey(appKeyQueryParams);
            } catch (Exception e) {
                maxRetryCount++;
                Cat.logError(e);
            }
            page += 1;
            appKeyQueryParams.setPage(page);
            appKeyList.addAll(appKeyPageInfo.getItems());
            if (appKeyPageInfo.getPn() < page || CollectionUtils.isEmpty(appKeyPageInfo.getItems()) || maxRetryCount >= 20){
                break;
            }
        }
        return filterPaasAppkey(scOrgId, appKeyList);
    }

    private List<AppKey> filterPaasAppkey(String scOrgId, List<AppKey> appKeyList){
        Transaction t = Cat.newTransaction("Crane.OrgPaasAppKeyStatistics", scOrgId);
        List<AppKey> notPaasAppKeyList = Collections.synchronizedList(new ArrayList<>());
        List<String> paasAppKeyList = Collections.synchronizedList(new ArrayList<>());
        CountDownLatch latch = new CountDownLatch(appKeyList.size());
        for (AppKey appKey: appKeyList) {
            executorService.submit(new NamedRunnableTask("${isPaasOwtAppkey}", () -> {
                try {
                    Boolean passAppkey = isPaasOwtAppkey(appKey.getAppKey());
                    if (Boolean.TRUE.equals(passAppkey)){
                        notPaasAppKeyList.add(appKey);
                    } else {
                        paasAppKeyList.add(appKey.getAppKey());
                    }
                } catch (Exception e) {
                    Cat.logError(e);
                } finally {
                    latch.countDown();
                }
            }));
        }
        try {
            // 一直等待latch减到0，等所有子线程执行完成后主线程才继续执行
            latch.await();
        } catch (InterruptedException e) {
            Cat.logError(e);
            Thread.currentThread().interrupt();
        } finally {
            t.complete();
        }
        t.addData(String.format("%s PaasAppKey: 总计%s个Appkey -> 非PAAS服务数量为%s，可疑PAAS信息 (%s)",
                scOrgId, appKeyList.size(), notPaasAppKeyList.size(), CollectionUtils.isEmpty(paasAppKeyList) ? "无" : paasAppKeyList));
        log.info(String.format("%s PaasAppKey: 总计%s个Appkey -> 非PAAS服务数量为%s，可疑PAAS信息 (%s)", scOrgId, appKeyList.size(), notPaasAppKeyList.size(), paasAppKeyList));
        return notPaasAppKeyList;
    }

    private Boolean isPaasOwtAppkey(String appkey){
        List<String> srvList;
        try {
            srvList = OpsUtils.getAppkeySrv(appkey);
        } catch (Exception e) {
            Cat.logError(e);
            return false;
        }
        if (srvList.size() == 1) {
            String srv = srvList.get(0);
            String owt = String.join(".", Arrays.asList(srv.split("\\.")).subList(0, 2));
            return paasOwts.isEmpty() || !paasOwts.contains(owt);
        }
        return false;
    }

    private String parseAndCombineSreData(String roleUsers){
        // 消除人员顺序影响
        List<String> sreList = Stream.of(roleUsers.split(","))
                .collect(Collectors.toCollection(ArrayList::new));
        return sreList.stream().sorted().collect(Collectors.joining(","));
    }
}
