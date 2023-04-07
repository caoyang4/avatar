package com.sankuai.avatar.dao.cache.impl;

import com.google.common.collect.ImmutableMap;
import com.jayway.jsonpath.TypeRef;
import com.sankuai.avatar.common.exception.JsonSerializationException;
import com.sankuai.avatar.common.utils.JsonUtil;
import com.sankuai.avatar.dao.cache.AppkeyTreeCacheRepository;
import com.sankuai.avatar.dao.cache.CacheClient;
import com.sankuai.avatar.dao.cache.model.UserBg;
import com.sankuai.avatar.dao.cache.exception.CacheException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author caoyang
 * @create 2023-01-09 15:29
 */
@Slf4j
@Repository
public class AppkeyTreeCacheRepositoryImpl implements AppkeyTreeCacheRepository {

    private static final String CATEGORY = "avatar-appkey-tree";

    private final CacheClient cache;

    public AppkeyTreeCacheRepositoryImpl(CacheClient cache) {
        this.cache = cache;
    }
    private String formatBg(String user){
        return String.format("%s_bg", user);
    }

    private String formatOwt(String user){
        return String.format("%s_owt", user);
    }

    private String formatPdl(String user){
        return String.format("%s_pdl", user);
    }

    private String formatPdlSrv(String pdl){
        return String.format("%s_srv_count", pdl);
    }

    private String formatTree(String user){
        return String.format("%s_tree", user);
    }

    @Override
    public List<String> getUserBg(String user) throws CacheException {
        String[] userBgs = cache.get(CATEGORY, formatBg(user), String[].class);
        return Objects.nonNull(userBgs) ? Arrays.asList(userBgs) : Collections.emptyList();
    }

    @Override
    public Boolean setUserBg(String user, List<String> bgList, int expireTime) throws CacheException {
        return cache.set(CATEGORY, formatBg(user), bgList, expireTime);
    }

    @Override
    public List<UserBg> getUserBgTree(String user) throws CacheException {
        UserBg[] userBgs = cache.get(CATEGORY, formatTree(user), UserBg[].class);
        return Objects.nonNull(userBgs) ? Arrays.asList(userBgs) : Collections.emptyList();
    }

    @Override
    public Boolean setUserBgTree(String user, List<UserBg> bgTree, int expireTime) throws CacheException {
        return cache.set(CATEGORY, formatTree(user), bgTree, expireTime);
    }

    @Override
    public Map<String, Integer> getPdlSrvCount(String pdl) throws CacheException {
        String json = cache.get(CATEGORY, formatPdlSrv(pdl));
        if (StringUtils.isEmpty(json)) {return null;}
        try {
            return JsonUtil.jsonPath2NestedBean(json, new TypeRef<Map<String, Integer>>() {});
        } catch (JsonSerializationException e) {
            throw new CacheException(e.getMessage());
        }
    }

    @Override
    public Map<String, Integer> multiGetPdlSrvCount(List<String> pdlList) throws CacheException {
        List<String> pdls = pdlList.stream().map(this::formatPdlSrv).collect(Collectors.toList());
        List<String> jsonList = cache.get(CATEGORY, pdls);
        if (CollectionUtils.isEmpty(jsonList)) {return null;}
        Map<String, Integer> map = new HashMap<>();
        try {
            for (String json : jsonList) {
                if (StringUtils.isEmpty(json)) {continue;}
                Map<String, Integer> srvMap = JsonUtil.jsonPath2NestedBean(json, new TypeRef<Map<String, Integer>>() {});
                map.putAll(srvMap);
            }
            return map;
        } catch (JsonSerializationException e) {
            throw new CacheException(e.getMessage());
        }
    }

    @Override
    public Boolean setPdlSrvCount(String pdl, int count, int expireTime) throws CacheException {
        return  cache.set(CATEGORY, formatPdlSrv(pdl), ImmutableMap.of(pdl, count), expireTime);
    }
}
