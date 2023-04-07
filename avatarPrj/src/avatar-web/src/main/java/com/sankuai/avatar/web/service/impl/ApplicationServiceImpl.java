package com.sankuai.avatar.web.service.impl;

import com.google.common.collect.Lists;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.application.ApplicationResource;
import com.sankuai.avatar.resource.application.bo.ApplicationBO;
import com.sankuai.avatar.resource.application.bo.ApplicationDetailBO;
import com.sankuai.avatar.resource.application.bo.UserOwnerApplicationBO;
import com.sankuai.avatar.resource.application.request.ApplicationPageRequestBO;
import com.sankuai.avatar.resource.application.request.ScQueryApplicationBO;
import com.sankuai.avatar.sdk.entity.servicecatalog.AppKey;
import com.sankuai.avatar.sdk.entity.servicecatalog.Slice;
import com.sankuai.avatar.sdk.manager.ServiceCatalogAppKey;
import com.sankuai.avatar.web.dto.application.ApplicationDTO;
import com.sankuai.avatar.web.dto.application.ApplicationDetailDTO;
import com.sankuai.avatar.web.dto.application.ScQueryApplicationDTO;
import com.sankuai.avatar.web.dto.application.UserOwnerApplicationDTO;
import com.sankuai.avatar.web.request.application.ApplicationPageRequestDTO;
import com.sankuai.avatar.web.service.ApplicationService;
import com.sankuai.avatar.web.service.DXUserService;
import com.sankuai.avatar.web.transfer.application.ApplicationDetailTransfer;
import com.sankuai.avatar.web.transfer.application.ApplicationPageRequestTransfer;
import com.sankuai.avatar.web.transfer.application.ApplicationPageResponseTransfer;
import com.sankuai.avatar.web.util.OpsUtils;
import com.sankuai.avatar.web.util.UserUtils;
import com.sankuai.avatar.web.vo.DXUserVO;
import com.sankuai.avatar.web.vo.PageInfo;
import com.sankuai.avatar.web.vo.application.AppKeyVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 应用
 *
 * @author Jie.li.sh
 * @create 2020-02-18
 **/

@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final Integer APPLICATION_APPKEY_COUNT_LIMIT = 1000;

    @Autowired
    private ServiceCatalogAppKey serviceCatalogAppKey;

    @Autowired
    private DXUserService dxUserService;

    @Autowired
    private ApplicationResource applicationResource;

    @Override
    public PageInfo<AppKeyVO> getAppKeyByApplication(ServiceCatalogAppKey.AppKeyQueryParams appKeyQueryParams) {
        PageInfo<AppKeyVO> appKeyPageInfo = new PageInfo<AppKeyVO>() {
        };
        Slice<AppKey> appKeySlice = new Slice<AppKey>() {
        };
        ArrayList<String> myFavorAppkey = null;
        try {
            String curUserLoginName = UserUtils.getCurrentCasUser().getLoginName();
            if (curUserLoginName != null && curUserLoginName.length() > 0) {
                myFavorAppkey = OpsUtils.getUserFavorAppkey(curUserLoginName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            appKeySlice = serviceCatalogAppKey.listAppKey(appKeyQueryParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (appKeySlice.getItems() == null) {
            return appKeyPageInfo;
        }
        appKeyPageInfo.setPage(appKeySlice.getCn());
        appKeyPageInfo.setPageSize(appKeySlice.getSn());
        appKeyPageInfo.setTotalCount(appKeySlice.getTn());
        appKeyPageInfo.setTotalPage(appKeySlice.getPn());
        List<AppKeyVO> appKeyVOList = new ArrayList<>();
        Map<String, DXUserVO> stringDXUserVOMap = patchUserInfo(appKeySlice.getItems());
        for (AppKey appKey : appKeySlice.getItems()) {
            AppKeyVO appKeyVO = new AppKeyVO();
            appKeyVO.setAppKey(appKey.getAppKey());
            appKeyVO.setComment(appKey.getDescription());
            appKeyVO.setCapacity(appKey.getCapacity());
            appKeyVO.setHost(appKey.getHost());
            appKeyVO.setRank(appKey.getServiceLevel());
            if (myFavorAppkey != null && myFavorAppkey.size() > 0) {
                appKeyVO.setFavor(myFavorAppkey.contains(appKey.getAppKey()));
            }

            if (StringUtils.isNotBlank(appKey.getOpAdmin())) {
                appKeyVO.setOpAdmin(Arrays.stream(appKey.getOpAdmin().split(","))
                        .filter(admin -> StringUtils.isNotBlank(admin) && stringDXUserVOMap.containsKey(admin))
                        .map(stringDXUserVOMap::get)
                        .collect(Collectors.toList())
                );
            } else {
                appKeyVO.setOpAdmin(new ArrayList<>());
            }
            if (StringUtils.isNotBlank(appKey.getRdAdmin())) {
                appKeyVO.setRdAdmin(Arrays.stream(appKey.getRdAdmin().split(","))
                        .filter(admin -> StringUtils.isNotBlank(admin) && stringDXUserVOMap.containsKey(admin))
                        .map(stringDXUserVOMap::get)
                        .collect(Collectors.toList())
                );
            } else {
                appKeyVO.setRdAdmin(new ArrayList<>());
            }
            if (StringUtils.isNotBlank(appKey.getEpAdmin())) {
                appKeyVO.setEpAdmin(Arrays.stream(appKey.getEpAdmin().split(","))
                        .filter(admin -> StringUtils.isNotBlank(admin) && stringDXUserVOMap.containsKey(admin))
                        .map(stringDXUserVOMap::get)
                        .collect(Collectors.toList())
                );
            } else {
                appKeyVO.setEpAdmin(new ArrayList<>());
            }
            appKeyVO.setCreateTime(appKey.getCreateTime());
            appKeyVOList.add(appKeyVO);
        }
        appKeyPageInfo.setItems(appKeyVOList);
        return appKeyPageInfo;
    }

    @Override
    public PageResponse<ApplicationDTO> getApplications(ApplicationPageRequestDTO requestDTO) {
        ApplicationPageRequestBO requestBO = ApplicationPageRequestTransfer.INSTANCE.toBO(requestDTO);
        PageResponse<ApplicationBO> boPageResponse = applicationResource.getApplications(requestBO);
        return ApplicationPageResponseTransfer.INSTANCE.toDTO(boPageResponse);
    }

    @Override
    public PageResponse<UserOwnerApplicationDTO> getUserOwnerApplications(ApplicationPageRequestDTO requestDTO) throws SdkCallException, SdkBusinessErrorException {
        ApplicationPageRequestBO requestBO = ApplicationPageRequestTransfer.INSTANCE.toBO(requestDTO);
        PageResponse<UserOwnerApplicationBO> boPageResponse = applicationResource.getUserOwnerApplications(requestBO);
        PageResponse<UserOwnerApplicationDTO> userOwnerApplicationDTOPageResponse = ApplicationPageResponseTransfer.INSTANCE
                .toUserOwnerApplicationDTOPageResponse(boPageResponse);
        // 补充应用负责人信息
        for (UserOwnerApplicationDTO userOwnerApplicationDTO : userOwnerApplicationDTOPageResponse.getItems()){
            userOwnerApplicationDTO.setApplicationChName(userOwnerApplicationDTO.getName());
            String admin = userOwnerApplicationDTO.getAdmin();
            if (StringUtils.isBlank(admin)){
                userOwnerApplicationDTO.setAdminMis("");
                userOwnerApplicationDTO.setAdminName("");
            } else if (!admin.contains(":") || admin.split(":").length < 2) {
                userOwnerApplicationDTO.setAdminMis(admin);
                userOwnerApplicationDTO.setAdminName(admin);
            } else {
                userOwnerApplicationDTO.setAdminMis(admin.split(":")[0]);
                userOwnerApplicationDTO.setAdminName(admin.split(":")[1]);
            }
        }
        return userOwnerApplicationDTOPageResponse;
    }

    @Override
    public PageResponse<ScQueryApplicationDTO> queryApplications(ApplicationPageRequestDTO requestDTO) throws SdkCallException, SdkBusinessErrorException {
        ApplicationPageRequestBO requestBO = ApplicationPageRequestTransfer.INSTANCE.toBO(requestDTO);
        PageResponse<ScQueryApplicationBO> boPageResponse = applicationResource.queryApplications(requestBO);

        PageResponse<ScQueryApplicationDTO> userOwnerApplicationDTOPageResponse = ApplicationPageResponseTransfer.INSTANCE
                .toScQueryApplicationDTOPageResponse(boPageResponse);
        for (ScQueryApplicationDTO scQueryApplicationDTO : userOwnerApplicationDTOPageResponse.getItems()){
            ScQueryApplicationDTO.Admin admin = scQueryApplicationDTO.getAdmin();
            if (admin == null){
                scQueryApplicationDTO.setAdminMis("");
                scQueryApplicationDTO.setAdminName("");
            } else {
                scQueryApplicationDTO.setAdminMis(admin.getMis());
                scQueryApplicationDTO.setAdminName(admin.getName());
            }
            scQueryApplicationDTO.setBillingUnitId(scQueryApplicationDTO.getCostProductId());
            scQueryApplicationDTO.setBillingUnitName(scQueryApplicationDTO.getCostProduct());
        }
        return userOwnerApplicationDTOPageResponse;
    }

    @Override
    public ApplicationDetailDTO getApplication(String name) {
        ApplicationDetailBO applicationDetailBO = applicationResource.getApplication(name);
        return ApplicationDetailTransfer.INSTANCE.toDTO(applicationDetailBO);
    }

    private Map<String, DXUserVO> patchUserInfo(List<AppKey> appKeys) {
        List<String> admins = Collections.synchronizedList(new ArrayList<>());
        for (AppKey appKey : appKeys) {
            if (!StringUtils.isBlank(appKey.getOpAdmin())) {
                admins.addAll(Arrays.stream(appKey.getOpAdmin().split(",")).collect(Collectors.toList()));
            }
            if (!StringUtils.isBlank(appKey.getRdAdmin())) {
                admins.addAll(Arrays.stream(appKey.getRdAdmin().split(",")).collect(Collectors.toList()));
            }
            if (!StringUtils.isBlank(appKey.getEpAdmin())) {
                admins.addAll(Arrays.stream(appKey.getEpAdmin().split(",")).collect(Collectors.toList()));
            }
        }
        List<DXUserVO> dxUserVos = new ArrayList<>();
        List<List<String>> adminParts = Lists.partition(admins.stream().distinct()
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.toList()), 150);
        for (List<String> adminPart : adminParts) {
            List<DXUserVO> dxUserVOList = dxUserService.getUserVo(adminPart);
            dxUserVos.addAll(dxUserVOList);
        }
        return dxUserVos.stream()
                .distinct()
                .collect(Collectors.toMap(
                        DXUserVO::getMis,
                        dxUser -> dxUser,
                        (misOne, misTwo) -> misOne)
                );
    }
}
