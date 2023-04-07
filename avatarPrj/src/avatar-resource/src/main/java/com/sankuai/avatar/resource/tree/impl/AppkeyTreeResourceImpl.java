package com.sankuai.avatar.resource.tree.impl;

import com.dianping.cat.util.StringUtils;
import com.sankuai.avatar.client.ops.OpsHttpClient;
import com.sankuai.avatar.client.ops.model.*;
import com.sankuai.avatar.client.ops.request.SrvQueryRequest;
import com.sankuai.avatar.client.ops.response.OpsAvatarSrvsResponse;
import com.sankuai.avatar.client.ops.response.OpsPdlResponse;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;
import com.sankuai.avatar.common.utils.ObjectUtils;
import com.sankuai.avatar.dao.cache.AppkeyTreeCacheRepository;
import com.sankuai.avatar.dao.cache.exception.CacheException;
import com.sankuai.avatar.dao.cache.model.UserBg;
import com.sankuai.avatar.resource.tree.AppkeyTreeResource;
import com.sankuai.avatar.resource.tree.bo.*;
import com.sankuai.avatar.resource.tree.request.SrvQueryRequestBO;
import com.sankuai.avatar.resource.tree.transfer.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * AppkeyTreeResource的实现类
 *
 * @author caoyang
 * @date 2022-10-22
 */
@Slf4j
@Component
public class AppkeyTreeResourceImpl implements AppkeyTreeResource {

    private final OpsHttpClient opsHttpClient;
    private final AppkeyTreeCacheRepository cacheRepository;

    @Autowired
    public AppkeyTreeResourceImpl(OpsHttpClient opsHttpClient, AppkeyTreeCacheRepository cacheRepository) {
        this.opsHttpClient = opsHttpClient;
        this.cacheRepository = cacheRepository;
    }

    @Override
    public AppkeyTreeBO getAppkeyTreeByKey(String srv) throws SdkCallException, SdkBusinessErrorException {
        OpsTree opsTree = opsHttpClient.getSrvTreeByKey(srv);
        return AppkeyTreeTransfer.INSTANCE.toAppkeyTreeBO(opsTree);
    }

    @Override
    public AppkeyTreeSrvDetailBO getAppkeyTreeSrvDetailByKey(String srv) throws SdkCallException, SdkBusinessErrorException {
        OpsSrvDetail opsSrvDetail = opsHttpClient.getSrvDetailByKey(srv);
        return AppkeyTreeTransfer.INSTANCE.toAppkeyTreeSrvDetailBO(opsSrvDetail);
    }

    @Override
    public AppkeyTreeSrvDetailResponseBO getSrvsByQueryRequest(SrvQueryRequestBO srvQueryRequestBO) throws SdkCallException, SdkBusinessErrorException {
        SrvQueryRequest srvQueryRequest = SrvQueryTransfer.INSTANCE.toSrvQueryRequest(srvQueryRequestBO);
        OpsAvatarSrvsResponse opsAvatarSrvsResponse = opsHttpClient.getSrvsByQueryRequest(srvQueryRequest);
        return AppkeyTreeTransfer.INSTANCE.toAppkeyTreeSrvsResponseBO(opsAvatarSrvsResponse);
    }

    @Override
    public List<String> getSrvSubscribers(String srv) throws SdkCallException, SdkBusinessErrorException {
        return opsHttpClient.getSrvSubscribers(srv);
    }

    @Override
    public List<AppkeyTreeBgBO> getBgList() {
        return AppkeyTreeBgTransfer.INSTANCE.toBOList(opsHttpClient.getBgList());
    }

    @Override
    public List<AppkeyTreeBgBO> getBgListByUser(String user) {
        try {
            List<String> userBgNameList = opsHttpClient.getBgListByUser(user);
            return AppkeyTreeBgTransfer.INSTANCE.toAppkeyTreeBgBOList(userBgNameList);
        } catch (SdkBusinessErrorException e){
            // 404，表示人员信息在ops未注册，一般为实习生，返回空列表
            if (e.getMessage().contains("code=404")) {
                return new ArrayList<>();
            }
            throw e;
        }
    }

    @Override
    public List<AppkeyTreeOwtBO> getOwtList() {
        List<OpsOwt> opsOwtList = opsHttpClient.getOwtList();
        return AppkeyTreeOwtTransfer.INSTANCE.toBOList(opsOwtList);
    }

    @Override
    public AppkeyTreeOwtBO getOwtByKey(String key) {
        if (StringUtils.isEmpty(key)) {return null;}
        OpsOwt opsOwt = null;
        try {
            opsOwt = opsHttpClient.getOwtByKey(key);
        } catch (SdkCallException | SdkBusinessErrorException ignored) {}
        AppkeyTreeOwtBO owtBO = null;
        if (Objects.nonNull(opsOwt)) {
            owtBO = AppkeyTreeOwtTransfer.INSTANCE.toBO(opsOwt);
            owtBO.setOrg(new ArrayList<>());
            try {
                List<OpsOrg> opsOrgList = opsHttpClient.getOrgListByKey(key);
                owtBO.setOrg(AppkeyTreeOwtTransfer.INSTANCE.toOwtOrgBOList(opsOrgList));
            } catch (SdkCallException | SdkBusinessErrorException ignored) {}
        }
        return owtBO;
    }

    @Override
    public List<AppkeyTreePdlBO> getPdlListByOwt(String owt) {
        if (StringUtils.isEmpty(owt)) {
            return Collections.emptyList();
        }
        List<OpsPdl> pdlList = new ArrayList<>();
        try {
            pdlList = opsHttpClient.getPdlListByOwtKey(owt);
        } catch (SdkCallException | SdkBusinessErrorException ignored) {}
        if (CollectionUtils.isEmpty(pdlList)) {
            return Collections.emptyList();
        }
        List<AppkeyTreePdlBO> pdlBOList = AppkeyTreePdlTransfer.INSTANCE.toBOList(pdlList);
        List<String> pdls = pdlBOList.stream().map(AppkeyTreePdlBO::getKey).filter(StringUtils::isNotEmpty).distinct().collect(Collectors.toList());
        Map<String, Integer> srvCountMap = batchCacheGetPdlSrvCount(pdls);
        for (AppkeyTreePdlBO pdlBO : pdlBOList) {
            String pdl = pdlBO.getKey();
            if (MapUtils.isNotEmpty(srvCountMap) && srvCountMap.containsKey(pdl)) {
                pdlBO.setSrvCount(srvCountMap.get(pdl));
            } else {
                try {
                    pdlBO.setSrvCount(opsHttpClient.getSrvListByPdl(pdl).size());
                } catch (SdkCallException |SdkBusinessErrorException ignored) {}
            }
        }
        return pdlBOList;
    }

    @Override
    public List<AppkeyTreePdlBO> getPdlList() {
        List<OpsPdl> pdlList = opsHttpClient.getPdlList();
        return AppkeyTreePdlTransfer.INSTANCE.toBOList(pdlList);
    }

    @Override
    public AppkeyTreePdlBO getPdlByKey(String key) {
        AppkeyTreePdlBO pdlBO = null;
        try {
            OpsPdlResponse opsPdl = opsHttpClient.getPdlByKey(key);
            OpsPdl pdl = opsPdl.getPdl();
            OpsOwt owt = opsPdl.getOwt();
            // pdl 人员信息若没有，则继承 owt
            if (StringUtils.isEmpty(pdl.getRdAdmin())) {
                pdl.setRdAdmin(owt.getRdAdmin());
            }
            if (StringUtils.isEmpty(pdl.getOpAdmin())) {
                pdl.setOpAdmin(owt.getOpAdmin());
            }
            if (StringUtils.isEmpty(pdl.getEpAdmin())) {
                pdl.setEpAdmin(owt.getEpAdmin());
            }
            pdlBO = AppkeyTreePdlTransfer.INSTANCE.toBO(pdl);
            pdlBO.setOwt(AppkeyTreeOwtTransfer.INSTANCE.toBO(owt));
        } catch (SdkCallException | SdkBusinessErrorException ignored) {}
        if (Objects.nonNull(pdlBO)) {
            pdlBO.setSrvCount(ObjectUtils.null2zero(getPdlSrvCount(pdlBO.getKey())));
        }
        return pdlBO;
    }

    @Override
    public List<String> getUserBg(String user) {
        try {
            List<String> userBg = cacheRepository.getUserBg(user);
            if (CollectionUtils.isNotEmpty(userBg)) {return userBg;}
        } catch (CacheException ignored) {}
        List<String> list = getUserBgNoCache(user);
        cacheUserBg(user, list);
        return list;
    }

    @Override
    public List<String> getUserBgNoCache(String user) {
        Set<String> userBgSet = new HashSet<>();
        List<AppkeyTreeOwtBO> owtAllList = getOwtList();
        Map<String, AppkeyTreeOwtBO> owtBOMap = owtAllList.stream().filter(owt -> StringUtils.isNotEmpty(owt.getKey())).collect(Collectors.toMap(AppkeyTreeOwtBO::getKey, owt -> owt));
        List<OpsSrv> opsSrvList = new ArrayList<>();
        try {
            opsSrvList = opsHttpClient.getUserAppkeyInfo(user);
        } catch (SdkCallException | SdkBusinessErrorException ignored) {}
        for (OpsSrv opsSrv : opsSrvList) {
            String[] keys = opsSrv.getKey().split("\\.");
            if(keys.length < 3){continue;}
            String owt = keys[0] + "." + keys[1];
            AppkeyTreeOwtBO owtBO = owtBOMap.getOrDefault(owt, null);
            if (Objects.isNull(owtBO) || StringUtils.isEmpty(owtBO.getBusinessGroup())) {continue;}
            userBgSet.add(owtBO.getBusinessGroup());
        }
        Set<String> userBgs = getBgListByUser(user).stream().map(AppkeyTreeBgBO::getName).collect(Collectors.toSet());
        userBgSet.addAll(userBgs);
        return new ArrayList<>(userBgSet);
    }

    @Override
    public Boolean cacheUserBg(String user, List<String> bgList) {
        try {
            if (CollectionUtils.isNotEmpty(bgList)) {
                return cacheRepository.setUserBg(user, bgList, -1);
            }
        } catch (CacheException ignored) {
        }
        return false;
    }

    @Override
    public List<UserBgBO> getUserBgTree(String user) {
        try {
            List<UserBg> userBgTree = cacheRepository.getUserBgTree(user);
            if (CollectionUtils.isNotEmpty(userBgTree)) {
                return UserBgTreeTransfer.INSTANCE.toBOList(userBgTree);
            }
        } catch (CacheException ignored) {}
        List<UserBgBO> list = getUserBgTreeNoCache(user);
        cacheUserBgTree(user, list);
        return list;
    }

    @Override
    public List<UserBgBO> getUserBgTreeNoCache(String user) {
        List<AppkeyTreeOwtBO> owtAllList = getOwtList();
        Map<String, AppkeyTreeOwtBO> owtBOMap = owtAllList.stream().filter(owt -> StringUtils.isNotEmpty(owt.getKey())).collect(Collectors.toMap(AppkeyTreeOwtBO::getKey, owt -> owt));
        List<AppkeyTreePdlBO> pdlAllList = getPdlList();
        Map<String, AppkeyTreePdlBO> pdlBOMap = pdlAllList.stream().filter(pdl -> StringUtils.isNotEmpty(pdl.getKey())).collect(Collectors.toMap(AppkeyTreePdlBO::getKey, pdl -> pdl));
        List<OpsSrv> opsSrvList;
        try {
            opsSrvList = opsHttpClient.getUserAppkeyInfo(user);
        } catch (SdkCallException | SdkBusinessErrorException e) {
            return Collections.emptyList();
        }
        Map<String, UserBgBO> bgMap = new HashMap<>();
        for (OpsSrv opsSrv : opsSrvList) {
            if (StringUtils.isEmpty(opsSrv.getKey())) { continue; }
            String[] keys = opsSrv.getKey().split("\\.");
            if(keys.length < 3){continue;}
            String owt = keys[0] + "." + keys[1];
            String pdl = owt + "." + keys[2];
            AppkeyTreeOwtBO owtBO = owtBOMap.getOrDefault(owt, null);
            if (Objects.nonNull(owtBO)) {
                String bg = owtBO.getBusinessGroup();
                String owtName = owtBO.getName();
                AppkeyTreePdlBO pdlBO = pdlBOMap.getOrDefault(pdl, null);
                String pdlName = Objects.nonNull(pdlBO) ? pdlBO.getName() : "";
                UserBgBO userBgBO = bgMap.getOrDefault(bg, UserBgBO.builder().bgName(bg).appkeyCount(0).build());
                List<UserOwtBO> owtList = Objects.nonNull(userBgBO.getOwtList()) ? userBgBO.getOwtList() : new ArrayList<>();
                UserOwtBO userOwtBO = owtList.stream().filter(userOwt -> Objects.equals(owt, userOwt.getOwt())).findFirst().orElse(UserOwtBO.builder().owt(owt).owtName(owtName).pdlList(new ArrayList<>()).build());
                UserPdlBO userPdlBO = userOwtBO.getPdlList().stream().filter(userPdl -> Objects.equals(pdl, userPdl.getPdl())).findFirst().orElse(null);
                if (Objects.isNull(userPdlBO)) {
                    userOwtBO.getPdlList().add(UserPdlBO.builder().pdl(pdl).pdlName(pdlName).build());
                }
                if (Objects.isNull(owtList.stream().filter(o -> Objects.equals(owt, o.getOwt())).findFirst().orElse(null))) {
                    owtList.add(userOwtBO);
                }
                userBgBO.setOwtList(owtList);
                userBgBO.setAppkeyCount(userBgBO.getAppkeyCount()+1);
                bgMap.put(bg, userBgBO);
            }
        }
        List<UserBgBO> list = new ArrayList<>(bgMap.values());
        // 按照 bg 下的服务数量倒序排列
        list.sort((o1, o2) -> o2.getAppkeyCount() - o1.getAppkeyCount());
        return list;
    }

    @Override
    public Boolean cacheUserBgTree(String user, List<UserBgBO> bgTree) {
        try {
            if (CollectionUtils.isNotEmpty(bgTree)) {
                return cacheRepository.setUserBgTree(user, UserBgTreeTransfer.INSTANCE.toUserBgList(bgTree), -1);
            }
        } catch (CacheException ignored) {}
        return false;
    }

    @Override
    public List<UserBgBO> getBgTreeNoCache(String user, boolean mine) {
        Map<String, UserBgBO> bgMap = new HashMap<>();
        List<AppkeyTreeBgBO> bgBOList;
        if (!mine && opsHttpClient.isUserOpsSre(user)) {
            bgBOList = getBgList();
        } else {
            bgBOList = getBgListByUser(user);
        }
        List<String> bgNames = bgBOList.stream().map(AppkeyTreeBgBO::getName).distinct().collect(Collectors.toList());
        List<AppkeyTreeOwtBO> owtAllList = getOwtList();
        Map<String, AppkeyTreeOwtBO> owtBOMap = owtAllList.stream().filter(owt -> StringUtils.isNotEmpty(owt.getKey())).collect(Collectors.toMap(AppkeyTreeOwtBO::getKey, owt -> owt));
        for (AppkeyTreePdlBO pdlBO : getPdlList()) {
            String pdlKey = pdlBO.getKey();
            String pdlName = pdlBO.getName();
            String[] keys = pdlKey.split("\\.");
            if(keys.length < 3) {continue;}
            String owtKey = keys[0] + "." + keys[1];
            if (!owtBOMap.containsKey(owtKey)) {continue;}
            String bgName = owtBOMap.get(owtKey).getBusinessGroup();
            String owtName = owtBOMap.get(owtKey).getName();
            if (!bgNames.contains(bgName)) {continue;}
            UserBgBO userBgBO = bgMap.getOrDefault(bgName, UserBgBO.builder().bgName(bgName).appkeyCount(0).build());
            List<UserOwtBO> owtList = Objects.nonNull(userBgBO.getOwtList()) ? userBgBO.getOwtList() : new ArrayList<>();
            UserOwtBO userOwtBO = owtList.stream().filter(userOwt -> Objects.equals(owtKey, userOwt.getOwt())).findFirst().orElse(UserOwtBO.builder().owt(owtKey).owtName(owtName).pdlList(new ArrayList<>()).build());
            UserPdlBO userPdlBO = userOwtBO.getPdlList().stream().filter(userPdl -> Objects.equals(pdlKey, userPdl.getPdl())).findFirst().orElse(null);
            if (Objects.isNull(userPdlBO)) {
                userOwtBO.getPdlList().add(UserPdlBO.builder().pdl(pdlKey).pdlName(pdlName).build());
            }
            if (Objects.isNull(owtList.stream().filter(o -> Objects.equals(owtKey, o.getOwt())).findFirst().orElse(null))) {
                owtList.add(userOwtBO);
            }
            userBgBO.setOwtList(owtList);
            bgMap.put(bgName, userBgBO);
        }
        return new ArrayList<>(bgMap.values());
    }

    @Override
    public Integer getPdlSrvCount(String pdl) {
        try {
            Map<String, Integer> pdlSrvCount = cacheRepository.getPdlSrvCount(pdl);
            if (Objects.nonNull(pdlSrvCount) && pdlSrvCount.containsKey(pdl)) {
                return pdlSrvCount.get(pdl);
            }
        } catch (CacheException ignored) {
        }
        Integer count = getPdlSrvCountNoCache(pdl);
        if (Objects.nonNull(count)) {cachePdlSrvCount(pdl, count);}
        return count;
    }

    @Override
    public Map<String, Integer> batchCacheGetPdlSrvCount(List<String> pdlList) {
        Map<String, Integer> srvCountMap = new HashMap<>();
        try {
            return cacheRepository.multiGetPdlSrvCount(pdlList);
        } catch (CacheException ignored) {}
        return srvCountMap;
    }

    @Override
    public Integer getPdlSrvCountNoCache(String pdl) {
        try {
            if (StringUtils.isEmpty(pdl)) {return 0;}
            List<OpsSrv> opsSrvList = opsHttpClient.getSrvListByPdl(pdl);
            if (Objects.nonNull(opsSrvList)) {
                return opsSrvList.size();
            }
        } catch (SdkCallException | SdkBusinessErrorException ignored) {
        }
        return null;
    }

    @Override
    public Boolean cachePdlSrvCount(String pdl, int count) {
        try {
           return cacheRepository.setPdlSrvCount(pdl, count, -1);
        } catch (CacheException ignored) {
        }
        return false;
    }
}
