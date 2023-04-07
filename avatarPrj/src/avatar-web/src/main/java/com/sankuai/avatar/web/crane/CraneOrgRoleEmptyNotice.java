package com.sankuai.avatar.web.crane;

import com.cip.crane.client.spring.annotation.Crane;
import com.cip.crane.client.spring.annotation.CraneConfiguration;
import com.dianping.cat.Cat;
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
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;


/**
 * @Author: Wellqin
 * @Date: 2020/8/25 11:55
 */

@CraneConfiguration
@Component
@Slf4j
public class CraneOrgRoleEmptyNotice {
    private static final int SINGLE_OP_ADMIN = 1;
    private static final int MULTI_OP_ADMIN = 2;

    private final ServiceCatalogHttpClient serviceCatalogHttpClient;
    private final ServiceCatalogAppKey serviceCatalogAppKey;
    private final DxMessageResource dxMessageResource;
    private final OrgRoleAdminResource orgRoleAdminResource;


    public CraneOrgRoleEmptyNotice(ServiceCatalogHttpClient serviceCatalogHttpClient,
                                   ServiceCatalogAppKey serviceCatalogAppKey,
                                   OrgRoleAdminResource orgRoleAdminResource,
                                   DxMessageResource dxMessageResource) {
        this.serviceCatalogHttpClient = serviceCatalogHttpClient;
        this.serviceCatalogAppKey = serviceCatalogAppKey;
        this.orgRoleAdminResource = orgRoleAdminResource;
        this.dxMessageResource = dxMessageResource;
    }

    @Crane("com.sankuai.avatar.web.noticeEmptyOrgSre")
    public void noticeEmptyOrgSre() throws Exception {
        List<String> orgIds = getTreePaths();
        for (String orgId : orgIds) {
            OrgBO orgBO = null;
            try {
                orgBO = orgRoleAdminResource.getOrgByOrgClient(orgId);
            } catch (SdkBusinessErrorException ignored) {}
            if (Objects.isNull(orgBO)) {continue;}
            String roleUsers = orgRoleAdminResource.getRoleUsersNoCache(orgId, OrgRoleType.OP_ADMIN);
            if (StringUtils.isEmpty(roleUsers)) {
                try {
                    String candidateOpAdmin = getCandidateOpAdmin(orgId);
                    if (StringUtils.isNotEmpty(candidateOpAdmin)) {
                        pushTextMessage(candidateOpAdmin, orgBO.getOrgName());
                        log.info("组织{}不存在SRE, 推荐的运维负责人为{}", orgBO.getOrgName(), candidateOpAdmin);
                    }
                } catch (Exception e){
                    Cat.logError(e);
                }
            }
        }
    }

    /**
     * 获取ORG下无SRE时的推荐候选人
     * - ORG下存在非PaaS服务
     * - 取前100个服务中出现最多的1-2位SRE作为候选人
     * @param orgId 无SRE的组织架构ID
     * @return 组织下Sre候选人
     */
    public String getCandidateOpAdmin(String orgId) throws Exception {
        List<String> candidateOpAdminList = new ArrayList<>();
        List<String> candidateOpAdmin = new ArrayList<>();

        ServiceCatalogAppKey.AppKeyQueryParams appKeyQueryParams = new ServiceCatalogAppKey.AppKeyQueryParams();
        appKeyQueryParams.setOrgIds(orgId);
        appKeyQueryParams.setPageSize(100);
        Slice<AppKey> appKeySlice = serviceCatalogAppKey.listAppKey(appKeyQueryParams);
        if (appKeySlice.getItems() != null && CollectionUtils.isNotEmpty(appKeySlice.getItems())) {
            for (AppKey appKey : appKeySlice.getItems()) {
                if (!appKey.getTags().contains("PaaS")) {
                    List<String> opAdmin = Arrays.asList(appKey.getOpAdmin().split(","));
                    candidateOpAdminList.addAll(opAdmin);
                }
            }
            if (CollectionUtils.isNotEmpty(candidateOpAdminList)) {
                Map<String, Long> opAdminNameCount = candidateOpAdminList.stream().collect(Collectors.groupingBy(p -> p, Collectors.counting()));
                if (opAdminNameCount != null && opAdminNameCount.size() > 0) {
                    Map<String, Long> sortCandidate = new LinkedHashMap<>();
                    opAdminNameCount.entrySet().stream()
                                    .sorted(Map.Entry.<String, Long>comparingByValue()
                                    .reversed())
                                    .forEachOrdered(e -> sortCandidate.put(e.getKey(), e.getValue()));
                    sortCandidate.forEach((k, v) -> candidateOpAdmin.add(k));
                }
            }
        }
        if (candidateOpAdmin.size() >= MULTI_OP_ADMIN) {
            return candidateOpAdmin.stream().limit(2).collect(Collectors.joining(","));
        } else if (candidateOpAdmin.size() == SINGLE_OP_ADMIN) {
            return candidateOpAdmin.get(0);
        } else {
            return "";
        }
    }


    private void dfs(List<Org> root, String path, List<String> res) {
        for (Org childNode : root) {
            List<Org> children = childNode.getChildren();
            if (CollectionUtils.isNotEmpty(children)) {
                dfs(children, path + childNode.getId() + ",", res);
            }
            if (children == null) {
                res.add(path + childNode.getId());
            }
        }
    }


    /**
     * 从SC中获取所有ORG叶子节点ID
     * @return ORG叶子节点ID列表
     */
    public List<String> getTreePaths() throws Exception {
        List<String> allPath = new ArrayList<>();
        List<Org> treeNode;
        final String path = "/open/api/avatar/v1/org/tree";
        Map<String, Object> params = new HashMap<>(2);
        params.put("source", "avatar");
        treeNode =  serviceCatalogHttpClient.getListData(path, params, Org.class);
        dfs(treeNode, "", allPath);
        return allPath;
    }


    public void pushTextMessage(String candidateOpAdmin, String orgName) {
        String text = "【组织架构运维负责人配置通知】\n"
                    + "【现状】%s不存在运维负责人\n"
                    + "【操作】请前往【Avatar-部门】对应的组织架构进行运维负责人配置\n"
                    + "【原因】综合当前组织架构下Appkey服务的运维负责人配置情况，您是服务配置最多的SRE，故推荐您作为组织架构运维负责人";
        String sendText = String.format(text, orgName);
        String[] misList = candidateOpAdmin.split(",");
        dxMessageResource.pushDxMessage(Arrays.asList(misList), sendText);
        log.info("推荐运维负责人, 通知SRE:{} 消息发送成功", candidateOpAdmin);
        try {
            dxMessageResource.pushDxMessage(Arrays.asList("qinwei05", "caoyang42"), sendText);
        } catch (SdkBusinessErrorException ignored) {
        }
    }
}



