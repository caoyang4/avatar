package com.sankuai.avatar.resource.orgRole.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.ImmutableMap;
import com.sankuai.avatar.client.org.OrgClient;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.utils.ObjectUtils;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.dao.cache.OrgRoleAdminCacheRepository;
import com.sankuai.avatar.dao.resource.repository.OrgRoleAdminRepository;
import com.sankuai.avatar.dao.cache.exception.CacheException;
import com.sankuai.avatar.dao.resource.repository.model.OrgRoleAdminDO;
import com.sankuai.avatar.dao.resource.repository.request.OrgRoleAdminRequest;
import com.sankuai.avatar.resource.exception.OrgNotExistException;
import com.sankuai.avatar.resource.orgRole.OrgRoleAdminResource;
import com.sankuai.avatar.resource.orgRole.bo.OrgBO;
import com.sankuai.avatar.resource.orgRole.bo.OrgRoleAdminBO;
import com.sankuai.avatar.resource.orgRole.constant.OrgRoleType;
import com.sankuai.avatar.resource.orgRole.request.OrgRoleAdminRequestBO;
import com.sankuai.avatar.resource.orgRole.transfer.OrgRoleAdminRequestTransfer;
import com.sankuai.avatar.resource.orgRole.transfer.OrgRoleAdminTransfer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author caoyang
 * @create 2022-11-10 16:47
 */
@Slf4j
@Component
public class OrgRoleAdminResourceImpl implements OrgRoleAdminResource {

    /**
     * org缓存有效期
     */
    private static final int ORG_CACHE_SPAN = 3600 * 24 * 3;

    private final OrgRoleAdminRepository repository;
    private final OrgRoleAdminCacheRepository cacheRepository;
    private final OrgClient orgClient;

    @Autowired
    public OrgRoleAdminResourceImpl(OrgRoleAdminRepository repository,
                                    OrgRoleAdminCacheRepository cacheRepository,
                                    OrgClient orgClient) {
        this.repository = repository;
        this.cacheRepository = cacheRepository;
        this.orgClient = orgClient;
    }

    @Override
    public PageResponse<OrgRoleAdminBO> queryPage(OrgRoleAdminRequestBO requestBO) {
        OrgRoleAdminRequest request = OrgRoleAdminRequestTransfer.INSTANCE.toOrgRoleAdminRequest(requestBO);
        int page = requestBO.getPage();
        int pageSize = requestBO.getPageSize();
        Page<OrgRoleAdminDO> doPage = PageHelper.startPage(page, pageSize).doSelectPage(
                () -> repository.query(request)
        );
        PageResponse<OrgRoleAdminBO> pageResponse = new PageResponse<>();
        pageResponse.setPage(page);
        pageResponse.setPageSize(pageSize);
        pageResponse.setTotalPage(doPage.getPages());
        pageResponse.setTotalCount(doPage.getTotal());
        pageResponse.setItems(OrgRoleAdminTransfer.INSTANCE.toBOList(doPage));
        return pageResponse;
    }

    @Override
    public List<OrgRoleAdminBO> queryOrgOpRolesWithNoCache(List<String> orgIds) {
        List<OrgRoleAdminDO> doList = repository.query(OrgRoleAdminRequest.builder()
                .orgIds(orgIds).role(OrgRoleType.OP_ADMIN.getRoleType()).build());
        return OrgRoleAdminTransfer.INSTANCE.toBOList(doList);
    }

    @Override
    public List<OrgRoleAdminBO> queryOrgOpRoles(List<String> orgIds) {
        if (CollectionUtils.isEmpty(orgIds)) {
            return Collections.emptyList();
        }
        try {
            List<OrgRoleAdminDO> doList = cacheRepository.multiGetOpRole(orgIds);
            if (CollectionUtils.isNotEmpty(doList) && orgIds.size() == doList.size()) {
                return OrgRoleAdminTransfer.INSTANCE.toBOList(doList);
            }
        } catch (CacheException ignore){}

        return queryOrgOpRolesWithNoCache(orgIds);
    }

    @Override
    public List<OrgRoleAdminBO> queryOrgEpRolesWithNoCache(List<String> orgIds) {
        List<OrgRoleAdminDO> doList = repository.query(OrgRoleAdminRequest.builder()
                .orgIds(orgIds).role(OrgRoleType.EP_ADMIN.getRoleType()).build());
        return OrgRoleAdminTransfer.INSTANCE.toBOList(doList);
    }

    @Override
    public List<OrgRoleAdminBO> queryOrgEpRoles(List<String> orgIds) {
        if (CollectionUtils.isEmpty(orgIds)) {
            return Collections.emptyList();
        }
        try {
            List<OrgRoleAdminDO> doList = cacheRepository.multiGetEpRole(orgIds);
            if (CollectionUtils.isNotEmpty(doList) && orgIds.size() == doList.size()) {
                return OrgRoleAdminTransfer.INSTANCE.toBOList(doList);
            }
        } catch (CacheException ignore) {}
        return queryOrgEpRolesWithNoCache(orgIds);
    }

    @Override
    public OrgRoleAdminBO getByOrgIdAndRole(String orgId, OrgRoleType role) {
        OrgRoleAdminDO orgRoleAdminDO = null;
        try {
            orgRoleAdminDO = cacheRepository.get(orgId, role.getRoleType());
            if (Objects.isNull(orgRoleAdminDO)) {
                List<OrgRoleAdminDO> doList = repository.query(OrgRoleAdminRequest.builder()
                        .orgIds(Collections.singletonList(orgId)).role(role.getRoleType()).build());
                if (CollectionUtils.isNotEmpty(doList)) {
                    orgRoleAdminDO = doList.get(0);
                    cacheRepository.set(orgRoleAdminDO, ORG_CACHE_SPAN);
                }
            }
        } catch (CacheException ignore) {}
        return OrgRoleAdminTransfer.INSTANCE.toBO(orgRoleAdminDO);
    }

    @Override
    public OrgRoleAdminBO getAncestorOrgRole(String orgId, OrgRoleType role) {
        final String middleLine = "-";
        final String zero = "0";
        if (zero.equals(orgId)) {return null;}
        OrgBO orgBO = null;
        try {
            orgBO = getOrgByOrgClient(orgId);
        } catch (SdkBusinessErrorException ignore) {}
        if (Objects.isNull(orgBO)) {
            throw new OrgNotExistException(String.format("org[%s]不存在", orgId));
        }
        OrgRoleAdminBO orgRoleAdminBO = getByOrgIdAndRole(orgId, role);
        if (Objects.nonNull(orgRoleAdminBO)) {return orgRoleAdminBO;}
        String orgPath = orgBO.getOrgPath();
        if (!orgPath.contains(middleLine)) {return null;}
        String[] ancestorPaths = orgPath.substring(0, orgPath.lastIndexOf(middleLine)).split(middleLine);
        return getAncestorOrgRole(ancestorPaths[ancestorPaths.length-1], role);
    }

    @Override
    public List<OrgRoleAdminBO> getChildrenOrgRole(String orgId, OrgRoleType role) {
        OrgBO orgBO = null;
        try {
            orgBO = getOrgByOrgClient(orgId);
        } catch (SdkBusinessErrorException ignore) {}
        if (Objects.isNull(orgBO)) {
            throw new OrgNotExistException(String.format("org[%s]不存在", orgId));
        }
        return OrgRoleAdminTransfer.INSTANCE.toBOList(repository.query(OrgRoleAdminRequest.builder()
                .orgPath(orgId).role(role.getRoleType()).build()));
    }

    @Override
    public boolean save(OrgRoleAdminBO orgRoleAdminBO) {
        List<OrgRoleAdminDO> doList = repository.query(OrgRoleAdminRequest.builder()
                        .role(orgRoleAdminBO.getRole().getRoleType())
                        .orgIds(Collections.singletonList(orgRoleAdminBO.getOrgId()))
                        .build());
        OrgRoleAdminDO orgRoleAdminDO = OrgRoleAdminTransfer.INSTANCE.toDO(orgRoleAdminBO);
        boolean success;
        if (CollectionUtils.isEmpty(doList)) {
            success = repository.insert(orgRoleAdminDO);
        } else {
            orgRoleAdminDO.setId(doList.get(0).getId());
            success = repository.update(orgRoleAdminDO);
        }
        if (success) {
            try {
                cacheRepository.set(orgRoleAdminDO, -1);
            } catch (CacheException ignored) {}
        }
        return success;
    }

    @Override
    public boolean cacheOrgRoleAdminBO(List<OrgRoleAdminBO> orgRoleAdminBOList) {
        List<OrgRoleAdminDO> orgRoleAdminDOList = OrgRoleAdminTransfer.INSTANCE.toDOList(orgRoleAdminBOList);
        try {
            return cacheRepository.multiSet(orgRoleAdminDOList, ORG_CACHE_SPAN);
        } catch (CacheException ignore) {
            return false;
        }
    }

    @Override
    public boolean deleteByCondition(OrgRoleAdminRequestBO requestBO) {
        if (ObjectUtils.checkObjAllFieldsIsNull(requestBO)) {
            return false;
        }
        List<OrgRoleAdminDO> doList = repository.query(OrgRoleAdminRequestTransfer.INSTANCE.toOrgRoleAdminRequest(requestBO));
        boolean success = true;
        for (OrgRoleAdminDO orgRoleAdminDO : doList) {
            if (!repository.delete(orgRoleAdminDO.getId())) {
                success = false;
            }
        }
        return success;
    }

    @Override
    public OrgBO getOrgByOrgClient(String orgId) throws SdkBusinessErrorException {
        return OrgRoleAdminTransfer.INSTANCE.toOrgBO(orgClient.getOrgByOrgId(orgId));
    }

    @Override
    public String getRoleUsers(String orgId, OrgRoleType role) {
        try {
            String roleUsers = cacheRepository.getOrgRoleUsers(orgId, role.getRoleType());
            if (StringUtils.isNotEmpty(roleUsers)) {
                return roleUsers;
            }
        }catch (CacheException ignore){}
        return getRoleUsersNoCache(orgId, role);
    }

    @Override
    public Map<String, String> getRoleUserMap(String orgId, OrgRoleType role) {
        return ImmutableMap.of(orgId, getRoleUsers(orgId, role));
    }

    @Override
    public String getRoleUsersNoCache(String orgId, OrgRoleType role) {
        String roleUsers = "";
        OrgRoleAdminBO orgRoleAdminBO = getByOrgIdAndRole(orgId, role);
        if (Objects.nonNull(orgRoleAdminBO) && StringUtils.isNotEmpty(orgRoleAdminBO.getRoleUsers())) {
            roleUsers = orgRoleAdminBO.getRoleUsers();
        } else {
            OrgRoleAdminBO ancestorOrg = getAncestorOrgRole(orgId, role);
            if (Objects.nonNull(ancestorOrg) && StringUtils.isNotEmpty(ancestorOrg.getRoleUsers())) {
                roleUsers = ancestorOrg.getRoleUsers();
            } else {
                List<OrgRoleAdminBO> childrenOrg = getChildrenOrgRole(orgId, role);
                if (CollectionUtils.isNotEmpty(childrenOrg)) {
                    roleUsers = Arrays.stream(childrenOrg.stream().map(OrgRoleAdminBO::getRoleUsers).distinct().collect(
                            Collectors.joining(",")
                        ).split(",")).distinct().collect(Collectors.joining(","));
                }
            }
        }
        if (StringUtils.isNotEmpty(roleUsers)) {
            cacheRepository.setOrgRoleUsers(orgId, role.getRoleType(), roleUsers, ORG_CACHE_SPAN);
        }
        return roleUsers;
    }

    @Override
    public boolean cacheRoleUsers(String orgId, OrgRoleType role) {
        String roleUsers = getRoleUsersNoCache(orgId, role);
        try {
            return cacheRepository.setOrgRoleUsers(orgId, role.getRoleType(), roleUsers, ORG_CACHE_SPAN);
        } catch (CacheException ignore) {
            return false;
        }
    }

    @Override
    public boolean cacheRoleUsers(String orgId, OrgRoleType role, String roleUsers) {
        try {
            return cacheRepository.setOrgRoleUsers(orgId, role.getRoleType(), roleUsers, ORG_CACHE_SPAN);
        } catch (CacheException ignore) {
            return false;
        }
    }
}
