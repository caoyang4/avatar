package com.sankuai.avatar.web.service.impl;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import com.meituan.mdp.boot.starter.threadpool.ExecutorServices;
import com.sankuai.avatar.capacity.util.GsonUtils;
import com.sankuai.avatar.capacity.util.SquirrelUtils;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.orgRole.DxGroupResource;
import com.sankuai.avatar.resource.orgRole.OrgRoleAdminResource;
import com.sankuai.avatar.resource.orgRole.bo.DxGroupBO;
import com.sankuai.avatar.resource.orgRole.bo.OrgBO;
import com.sankuai.avatar.resource.orgRole.bo.OrgRoleAdminBO;
import com.sankuai.avatar.resource.orgRole.constant.OrgRoleType;
import com.sankuai.avatar.resource.orgRole.request.OrgRoleAdminRequestBO;
import com.sankuai.avatar.sdk.entity.servicecatalog.OrgInfo;
import com.sankuai.avatar.sdk.manager.ServiceCatalogOrg;
import com.sankuai.avatar.web.dto.orgRole.DxGroupDTO;
import com.sankuai.avatar.web.dto.orgRole.OrgRoleAdminDTO;
import com.sankuai.avatar.web.dto.orgRole.OrgSreTreeDTO;
import com.sankuai.avatar.web.exception.SupportErrorException;
import com.sankuai.avatar.web.request.OrgRoleAdminPageRequest;
import com.sankuai.avatar.web.service.OrgRoleAdminService;
import com.sankuai.avatar.web.service.UserService;
import com.sankuai.avatar.web.transfer.orgRole.DxGroupDTOTransfer;
import com.sankuai.avatar.web.transfer.orgRole.OrgRoleAdminDTOTransfer;
import com.sankuai.avatar.web.transfer.orgRole.OrgSreTreeDTOTransfer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author Jie.li.sh
 **/
@Slf4j
@Service
public class OrgRoleAdminServiceImpl implements OrgRoleAdminService {

    private static final String COMMA = ",";

    private static final String ORG_INFO_KEY = "ORG_INFO_KEY";

    private final ServiceCatalogOrg serviceCatalogOrg;
    private final OrgRoleAdminResource orgRoleAdminResource;
    private final DxGroupResource dxGroupResource;
    private final UserService userService;

    public OrgRoleAdminServiceImpl(ServiceCatalogOrg serviceCatalogOrg,
                                   OrgRoleAdminResource orgRoleAdminResource,
                                   DxGroupResource dxGroupResource,
                                   UserService userService) {
        this.serviceCatalogOrg = serviceCatalogOrg;
        this.orgRoleAdminResource = orgRoleAdminResource;
        this.dxGroupResource = dxGroupResource;
        this.userService = userService;
    }

    private final ExecutorService executorService = ExecutorServices.forThreadPoolExecutor(
            "SreManger",
            5,
            TimeUnit.SECONDS,
            new ThreadPoolExecutor.AbortPolicy()
    );


    private OrgInfo getOrgInfoDetailByCache(String orgId){
        try {
            String result = SquirrelUtils.get(ORG_INFO_KEY + orgId);
            if (result != null) {
                return GsonUtils.deserialization(result, OrgInfo.class);
            } else {
                OrgInfo orgInfo = serviceCatalogOrg.getOrgInfo(orgId);
                String orgInfoString = GsonUtils.serialization(orgInfo);
                if (StringUtils.isNotBlank(orgInfoString) && orgInfo != null) {
                    SquirrelUtils.setEx(ORG_INFO_KEY + orgId, orgInfoString, 60 * 60 * 12);
                }
                return orgInfo;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<OrgSreTreeDTO> getOrgSreTreeListByOrgId(String mis, String orgId) {
        Transaction t = Cat.newTransaction("OrgSreTree", mis);
        try {
            // TODO 1: 剥离 sdk，待 sdk 重构完，替换
            List<com.sankuai.avatar.sdk.entity.servicecatalog.Org> scOrgList = serviceCatalogOrg.listUserOrg(mis);
            if (CollectionUtils.isNotEmpty(scOrgList)) {
                List<OrgSreTreeDTO> orgSreTreeDTOList = OrgSreTreeDTOTransfer.INSTANCE.toOrgSreTreeDTOList(scOrgList);
                List<OrgSreTreeDTO> orgSreTree = getOrgSreChildren(orgSreTreeDTOList, orgId, new ArrayList<>());
                if (CollectionUtils.isNotEmpty(orgSreTree)) {
                    Map<String, String> orgTreeRoleUsers = getOrgSreTreeRoleUsers(orgSreTree);
                    orgSreTree.forEach(orgSre -> {
                        orgSre.setOrgPath(StringUtils.isNotBlank(orgId)
                                ? String.format("%s,%s", orgId, orgSre.getId())
                                : orgSre.getId());
                        String roleUsers = orgTreeRoleUsers.getOrDefault(orgSre.getId(), "");
                        orgSre.setRoleUsers(roleUsers);
                        // TODO 2: 剥离 sdk，待 sdk 重构完，替换
                        OrgInfo orgInfo = getOrgInfoDetailByCache(orgSre.getOrgPath());
                        orgSre.setAppkeyCount(Objects.nonNull(orgInfo) ? orgInfo.getAppKeyCount() : 0);
                        if (StringUtils.isNotEmpty(roleUsers)) {
                            orgSre.setOpAdmins(userService.getDxUserByMis(Arrays.asList(roleUsers.split(COMMA))));
                        }
                    });
                }
                return orgSreTree;
            }
        } catch (Exception e) {
            Cat.logError(e);
            throw new SupportErrorException(String.format("MIS:%s 获取部门[%s]数据异常", mis, orgId));
        } finally {
            t.complete();
        }

        return Collections.emptyList();
    }

    private Map<String, String> getOrgSreTreeRoleUsers(List<OrgSreTreeDTO> orgSreTreeDTOList) {
        List<CompletableFuture<Map<String, String>>> futures = new ArrayList<>();
        Map<String, String> orgRoleUser = new ConcurrentHashMap<>(16);
        for (OrgSreTreeDTO orgSreTreeDTO : orgSreTreeDTOList) {
            String orgId = orgSreTreeDTO.getId();
            CompletableFuture<Map<String, String>> future = CompletableFuture.supplyAsync(() -> {
                try {
                    return orgRoleAdminResource.getRoleUserMap(orgId, OrgRoleType.OP_ADMIN);
                } catch (Exception e) {
                    Cat.logError(e);
                    return new HashMap<>();
                }
            }, executorService);
            futures.add(future);
        }
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        for (CompletableFuture<Map<String, String>> future : futures) {
            try {
                orgRoleUser.putAll(future.get(3, TimeUnit.SECONDS));
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                Cat.logError(e);
                Thread.currentThread().interrupt();
            }
        }
        return orgRoleUser;
    }

    /**
     * 广度优先搜索遍历获取所有子组织
     *
     * @param childrenOrgSre 子组织
     * @param orgId          org id
     * @param orgList        org 列表
     * @return {@link List}<{@link OrgSreTreeDTO}>
     */
    private List<OrgSreTreeDTO> getOrgSreChildren(List<OrgSreTreeDTO> childrenOrgSre, String orgId, List<OrgSreTreeDTO> orgList){
        if (StringUtils.isEmpty(orgId) || CollectionUtils.isEmpty(childrenOrgSre)){
            return childrenOrgSre;
        }
        String ancestorId;
        String childrenId;
        if (orgId.contains(COMMA)) {
            ancestorId = orgId.substring(0, orgId.indexOf(COMMA));
            childrenId = orgId.substring(orgId.indexOf(COMMA) + 1);
        } else {
            childrenId = ancestorId = orgId;
        }
        for (OrgSreTreeDTO orgSreTreeDTO : childrenOrgSre) {
            if (!Objects.equals(orgSreTreeDTO.getId(), ancestorId)){
                continue;
            }
            if (childrenId.equals(ancestorId)) {
                orgList.addAll(CollectionUtils.isNotEmpty(orgSreTreeDTO.getChildren())
                                ? orgSreTreeDTO.getChildren()
                                : Collections.emptyList());
            } else {
                getOrgSreChildren(orgSreTreeDTO.getChildren(), childrenId, orgList);
            }
        }
        return orgList;
    }

    @Override
    public PageResponse<OrgRoleAdminDTO> queryPage(OrgRoleAdminPageRequest request) {
        OrgRoleAdminRequestBO requestBO = OrgRoleAdminRequestBO.builder()
                .orgIds(Collections.singletonList(request.getOrgId()))
                .role(OrgRoleType.getInstance(request.getRole()))
                .roleUser(request.getRoleUser())
                .orgName(request.getOrgName())
                .orgPath(request.getOrgPath())
                .build();
        requestBO.setPage(request.getPage());
        requestBO.setPageSize(request.getPageSize());
        PageResponse<OrgRoleAdminBO> boPageResponse = orgRoleAdminResource.queryPage(requestBO);
        PageResponse<OrgRoleAdminDTO> pageResponse = new PageResponse<>();
        pageResponse.setPage(boPageResponse.getPage());
        pageResponse.setPageSize(boPageResponse.getPageSize());
        pageResponse.setTotalPage(boPageResponse.getTotalPage());
        pageResponse.setTotalCount(boPageResponse.getTotalCount());
        pageResponse.setItems(OrgRoleAdminDTOTransfer.INSTANCE.toDTOList(boPageResponse.getItems()));
        return pageResponse;
    }

    @Override
    public List<DxGroupDTO> getAllDxGroup() {
        return DxGroupDTOTransfer.INSTANCE.toDTOList(dxGroupResource.getAllDxGroup());
    }

    @Override
    public List<DxGroupDTO> getDxGroupByGroupIds(List<String> groupList) {
        if (CollectionUtils.isEmpty(groupList)) {
            return Collections.emptyList();
        }
        List<DxGroupBO> dxGroupBOList = dxGroupResource.getDxGroupByGroupIds(groupList);
        return DxGroupDTOTransfer.INSTANCE.toDTOList(dxGroupBOList);
    }

    @Override
    public boolean saveOrgRoleAdmin(OrgRoleAdminDTO orgRoleAdminDTO, Boolean deleteChildren) {
        boolean success = orgRoleAdminResource.save(OrgRoleAdminDTOTransfer.INSTANCE.toBO(orgRoleAdminDTO));
        if (BooleanUtils.isTrue(deleteChildren)) {
            String orgId = orgRoleAdminDTO.getOrgId();
            List<OrgRoleAdminDTO> childrenOrg = getChildrenOrgByOrgId(orgId, orgRoleAdminDTO.getRole());
            List<String> childrenOrgIds = childrenOrg.stream().map(OrgRoleAdminDTO::getOrgId).filter(id -> !orgId.equals(id)).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(childrenOrgIds)) {
                orgRoleAdminResource.deleteByCondition(OrgRoleAdminRequestBO.builder().orgIds(childrenOrgIds).role(orgRoleAdminDTO.getRole()).build());
            }
        }
        orgRoleAdminResource.cacheRoleUsers(orgRoleAdminDTO.getOrgId(),orgRoleAdminDTO.getRole(), orgRoleAdminDTO.getRoleUsers());
        return success;
    }

    @Override
    public boolean saveOrgDxGroup(String orgId, List<DxGroupDTO> dxGroupDTOList) {
        List<OrgRoleAdminBO> boList = orgRoleAdminResource.queryOrgOpRolesWithNoCache(Collections.singletonList(orgId));
        OrgBO orgBO = null;
        try {
            orgBO = orgRoleAdminResource.getOrgByOrgClient(orgId);
        } catch (SdkBusinessErrorException ignored) {}
        if (Objects.isNull(orgBO) && CollectionUtils.isEmpty(boList)) {
            throw new SupportErrorException(String.format("无效 orgId:%s，更新大象群失败", orgId));
        }
        dxGroupResource.batchSave(DxGroupDTOTransfer.INSTANCE.toBOList(dxGroupDTOList));
        String groupIds = dxGroupDTOList.stream().map(DxGroupDTO::getGroupId).collect(Collectors.joining(","));
        OrgRoleAdminBO orgRoleAdminBO;
        if (CollectionUtils.isEmpty(boList)) {
            orgRoleAdminBO = new OrgRoleAdminBO();
            orgRoleAdminBO.setOrgId(orgId);
            orgRoleAdminBO.setOrgName(Objects.requireNonNull(orgBO).getOrgNamePath());
            orgRoleAdminBO.setRole(OrgRoleType.OP_ADMIN);
            orgRoleAdminBO.setOrgPath(orgBO.getOrgPath());
            OrgRoleAdminBO ancestorOrg = orgRoleAdminResource.getAncestorOrgRole(orgId, OrgRoleType.OP_ADMIN);
            orgRoleAdminBO.setRoleUsers(Objects.nonNull(ancestorOrg)
                                        ? ancestorOrg.getRoleUsers()
                                        : "");
        } else {
            orgRoleAdminBO = boList.get(0);
        }
        orgRoleAdminBO.setGroupId(groupIds);
        return orgRoleAdminResource.save(orgRoleAdminBO);
    }

    @Override
    public OrgRoleAdminDTO getByOrgIdAndRole(String orgId, OrgRoleType role) {
        return OrgRoleAdminDTOTransfer.INSTANCE.toDTO(orgRoleAdminResource.getByOrgIdAndRole(orgId, role));
    }

    @Override
    public List<OrgRoleAdminDTO> getChildrenOrgByOrgId(String orgId, OrgRoleType role) {
        return OrgRoleAdminDTOTransfer.INSTANCE.toDTOList(orgRoleAdminResource.getChildrenOrgRole(orgId, role));
    }

    @Override
    public OrgRoleAdminDTO getAncestorOrgByOrgId(String orgId, OrgRoleType role) {
        return OrgRoleAdminDTOTransfer.INSTANCE.toDTO(orgRoleAdminResource.getAncestorOrgRole(orgId, role));
    }
}
