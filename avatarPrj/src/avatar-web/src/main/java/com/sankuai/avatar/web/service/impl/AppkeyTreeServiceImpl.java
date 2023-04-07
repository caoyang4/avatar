package com.sankuai.avatar.web.service.impl;

import com.meituan.mdp.boot.starter.config.annotation.MdpConfig;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;
import com.sankuai.avatar.common.utils.ObjectUtils;
import com.sankuai.avatar.resource.tree.AppkeyTreeResource;
import com.sankuai.avatar.resource.tree.bo.*;
import com.sankuai.avatar.web.dto.tree.*;
import com.sankuai.avatar.web.service.AppkeyTreeService;
import com.sankuai.avatar.web.transfer.AppkeyTreeNodeTransfer;
import com.sankuai.avatar.web.transfer.orgtree.UserBgTransfer;
import com.sankuai.avatar.web.transfer.tree.AppkeyTreeOwtTransfer;
import com.sankuai.avatar.web.transfer.tree.AppkeyTreePdlTransfer;
import com.sankuai.avatar.web.util.GsonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * AppkeyTreeService实现类
 *
 * @author zhangxiaoning07
 * @create 2022-10-24
 */
@Service
public class AppkeyTreeServiceImpl implements AppkeyTreeService {

    private final AppkeyTreeResource appkeyTreeResource;

    /**
     * 新增服务开放所有用户可见的BG列表。 如meituan.eagle对应的点评大搜
     */
    @MdpConfig("ADD_SRV_BG:{}")
    private String addSrvDefaultBgMap;

    @Autowired
    public AppkeyTreeServiceImpl(AppkeyTreeResource appkeyTreeResource) {
        this.appkeyTreeResource = appkeyTreeResource;
    }

    @Override
    public List<String> getAllBgList() {
        List<AppkeyTreeBgBO> bgList = appkeyTreeResource.getBgList();
        return bgList.stream().map(AppkeyTreeBgBO::getName).filter(StringUtils::isNotEmpty).distinct().collect(Collectors.toList());
    }

    @Override
    public List<String> getUserBg(String user) {
        return appkeyTreeResource.getUserBg(user);
    }

    @Override
    public List<String> getBgForAddSrv(String user) {
        List<String> userBg = new ArrayList<>(appkeyTreeResource.getUserBg(user));
        Map<String, String>  addSrvMap = GsonUtils.deserialization(addSrvDefaultBgMap, Map.class);
        if (MapUtils.isNotEmpty(addSrvMap)) {
            Set<String> addSrvBg = addSrvMap.keySet();
            List<String> bgList = getAllBgList();
            if (CollectionUtils.isNotEmpty(bgList)) {
                List<String> srvBgList = addSrvBg.stream().filter(bgList::contains).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(srvBgList)) {userBg.addAll(srvBgList);}
            }
            return new ArrayList<>(new HashSet<>(userBg));
        }
        return userBg;
    }

    @Override
    public List<UserBgDTO> getUserBgTree(String user) {
        List<UserBgBO> userBgBOList = appkeyTreeResource.getUserBgTree(user);
        return UserBgTransfer.INSTANCE.toBgDTOList(userBgBOList);
    }

    @Override
    public List<UserBgDTO> getBgTree(String user, Boolean mine) {
        List<UserBgBO> userBgBOList = appkeyTreeResource.getBgTreeNoCache(user, mine);
        return UserBgTransfer.INSTANCE.toBgDTOList(userBgBOList);
    }

    @Override
    public List<AppkeyTreeOwtDTO> getUserOwtList(String user, String bg, String keyword) {
        List<AppkeyTreeOwtBO> owtBOList = appkeyTreeResource.getOwtList();
        if (StringUtils.isNotEmpty(bg)) {
            owtBOList = owtBOList.stream().filter(
                            owt -> ObjectUtils.null2Empty(owt.getBusinessGroup()).contains(bg))
                    .collect(Collectors.toList());
            Map<String, String>  addSrvMap = GsonUtils.deserialization(addSrvDefaultBgMap, Map.class);
            if (MapUtils.isNotEmpty(addSrvMap)) {
                List<String> userBg = getUserBg(user);
                if (addSrvMap.containsKey(bg) && !userBg.contains(bg)) {
                    owtBOList = owtBOList.stream().filter(owt -> addSrvMap.containsValue(owt.getKey())).collect(Collectors.toList());
                }
            }
        }
        if (StringUtils.isNotEmpty(keyword)) {
            owtBOList = owtBOList.stream().filter(
                    owt -> ObjectUtils.null2Empty(owt.getKey()).contains(keyword) || ObjectUtils.null2Empty(owt.getName()).contains(keyword))
                    .collect(Collectors.toList());
        }
        return AppkeyTreeOwtTransfer.INSTANCE.toDTOList(owtBOList);
    }

    @Override
    public AppkeyTreeOwtDTO getOwtByKey(String key, String user) {
        AppkeyTreeOwtBO owtBO = appkeyTreeResource.getOwtByKey(key);
        if (Objects.nonNull(owtBO)) {
            AppkeyTreeOwtDTO owtDTO = AppkeyTreeOwtTransfer.INSTANCE.toDTO(owtBO);
            owtDTO.setUserCanEdit(ObjectUtils.null2EmptyList(owtBO.getOpAdmin()).contains(user)
                    || ObjectUtils.null2EmptyList(owtBO.getEpAdmin()).contains(user)
                    || ObjectUtils.null2EmptyList(owtBO.getRdAdmin()).contains(user));
            return owtDTO;
        }
        return null;
    }

    @Override
    public List<AppkeyTreePdlDTO> getPdlListByOwtKey(String key, String keyword) {
        List<AppkeyTreePdlBO> pdlList = appkeyTreeResource.getPdlListByOwt(key);
        if (StringUtils.isNotEmpty(keyword)) {
            pdlList = pdlList.stream().filter(
                    pdl -> ObjectUtils.null2Empty(pdl.getKey()).contains(keyword) || ObjectUtils.null2Empty(pdl.getName()).contains(keyword))
                    .collect(Collectors.toList());
        }
        return AppkeyTreePdlTransfer.INSTANCE.toDTOList(pdlList);
    }

    @Override
    public AppkeyTreePdlDTO getPdlByKey(String key, String user) {
        AppkeyTreePdlBO pdlBO = appkeyTreeResource.getPdlByKey(key);
        if (Objects.nonNull(pdlBO)) {
            AppkeyTreePdlDTO pdlDTO = AppkeyTreePdlTransfer.INSTANCE.toDTO(pdlBO);
            AppkeyTreeOwtBO owtBO = pdlBO.getOwt();
            pdlDTO.setUserCanEdit(ObjectUtils.null2EmptyList(pdlBO.getOpAdmin()).contains(user)
                    || ObjectUtils.null2EmptyList(pdlBO.getEpAdmin()).contains(user)
                    || ObjectUtils.null2EmptyList(pdlBO.getRdAdmin()).contains(user)
                    || ObjectUtils.null2EmptyList(owtBO.getOpAdmin()).contains(user)
                    || ObjectUtils.null2EmptyList(owtBO.getEpAdmin()).contains(user)
                    || ObjectUtils.null2EmptyList(owtBO.getRdAdmin()).contains(user));
            return pdlDTO;
        }
        return null;
    }

    @Override
    public AppkeyTreeDTO getAppkeyTreeByKey(String srv) throws SdkCallException, SdkBusinessErrorException {
        AppkeyTreeBO appkeyTreeBO = appkeyTreeResource.getAppkeyTreeByKey(srv);
        return AppkeyTreeNodeTransfer.INSTANCE.toAppkeyTreeDTO(appkeyTreeBO);
    }

    @Override
    public AppkeyTreeSrvDetailDTO getAppkeyTreeSrvDetailByKey(String srv) throws SdkCallException, SdkBusinessErrorException {
        AppkeyTreeSrvDetailBO appkeyTreeSrvDetailBO = appkeyTreeResource.getAppkeyTreeSrvDetailByKey(srv);
        return AppkeyTreeNodeTransfer.INSTANCE.toAppkeyTreeSrvDetailDTO(appkeyTreeSrvDetailBO);
    }

    @Override
    public List<String> getSrvSubscribers(String srv) throws SdkCallException, SdkBusinessErrorException {
        return appkeyTreeResource.getSrvSubscribers(srv);
    }

}
