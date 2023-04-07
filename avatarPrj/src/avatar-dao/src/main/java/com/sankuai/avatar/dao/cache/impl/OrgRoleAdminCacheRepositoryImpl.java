package com.sankuai.avatar.dao.cache.impl;

import com.sankuai.avatar.dao.cache.OrgRoleAdminCacheRepository;
import com.sankuai.avatar.dao.resource.repository.model.OrgRoleAdminDO;
import com.sankuai.avatar.dao.cache.CacheClient;
import com.sankuai.avatar.dao.cache.exception.CacheException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * OrgRoleAdminCacheRepository实现类
 * @author caoyang
 * @create 2022-11-01 22:08
 */
@Slf4j
@Repository
public class OrgRoleAdminCacheRepositoryImpl implements OrgRoleAdminCacheRepository {

    private static final String CATEGORY = "avatar-org-admin";

    private static final String OP_ROLE = "op_admin";
    private static final String EP_ROLE = "ep_admin";

    private final CacheClient cache;

    @Autowired
    public OrgRoleAdminCacheRepositoryImpl(CacheClient cache){
        this.cache = cache;
    }

    private String formatOrgKey(String orgId, String role){
        return orgId + "_" + role;
    }

    private String formatOrgRoleKey(String orgId, String role){
        return role + "_" + orgId;
    }


    @Override
    public OrgRoleAdminDO getOpRole(String orgId) throws CacheException{
        return cache.get(CATEGORY, formatOrgKey(orgId, OP_ROLE), OrgRoleAdminDO.class);
    }

    @Override
    public List<OrgRoleAdminDO> multiGetOpRole(List<String> orgIds) throws CacheException{
        List<String> roleOrgIds = orgIds.stream().map(orgId -> formatOrgKey(orgId, OP_ROLE)).collect(Collectors.toList());
        return cache.multiGet(CATEGORY, roleOrgIds, OrgRoleAdminDO.class);
    }

    @Override
    public OrgRoleAdminDO getEpRole(String orgId) throws CacheException{
        return cache.get(CATEGORY, formatOrgKey(orgId, EP_ROLE), OrgRoleAdminDO.class);
    }

    @Override
    public OrgRoleAdminDO get(String orgId, String role) throws CacheException {
        return cache.get(CATEGORY, formatOrgKey(orgId, role), OrgRoleAdminDO.class);
    }

    @Override
    public List<OrgRoleAdminDO> multiGetEpRole(List<String> orgIds) throws CacheException {
        List<String> roleOrgIds = orgIds.stream().map(orgId -> formatOrgKey(orgId, EP_ROLE)).collect(Collectors.toList());
        return cache.multiGet(CATEGORY, roleOrgIds, OrgRoleAdminDO.class);
    }

    @Override
    public boolean set(OrgRoleAdminDO orgRoleAdminDO, int expireTime) throws CacheException {
        return cache.set(CATEGORY, formatOrgKey(orgRoleAdminDO.getOrgId(), orgRoleAdminDO.getRole()), orgRoleAdminDO, expireTime);
    }

    @Override
    public boolean multiSet(List<OrgRoleAdminDO> orgRoleAdminDOList, int expireTime) throws CacheException {
        Map<String, OrgRoleAdminDO> keyValues = orgRoleAdminDOList.stream().collect(Collectors.toMap(
                orgRoleAdminDO -> formatOrgKey(orgRoleAdminDO.getOrgId(), orgRoleAdminDO.getRole()),
                orgRoleAdminDO -> orgRoleAdminDO)
        );
        return cache.multiSet(CATEGORY, keyValues, expireTime);
    }

    @Override
    public boolean setOrgRoleUsers(String orgId, String role, String roleUsers, int expireTime) throws CacheException {
        return cache.set(CATEGORY, formatOrgRoleKey(orgId, role), roleUsers, expireTime);
    }

    @Override
    public String getOrgRoleUsers(String orgId, String role) throws CacheException {
        return cache.get(CATEGORY, formatOrgRoleKey(orgId, role), String.class);
    }
}
