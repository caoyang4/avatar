package com.sankuai.avatar.web.controller;

import com.sankuai.avatar.common.utils.ObjectUtils;
import com.sankuai.avatar.resource.orgRole.constant.OrgRoleType;
import com.sankuai.avatar.sdk.entity.servicecatalog.*;
import com.sankuai.avatar.sdk.manager.ServiceCatalogAppKey;
import com.sankuai.avatar.sdk.manager.ServiceCatalogApplication;
import com.sankuai.avatar.sdk.manager.ServiceCatalogOrg;
import com.sankuai.avatar.web.dto.orgRole.OrgRoleAdminDTO;
import com.sankuai.avatar.web.exception.SupportErrorException;
import com.sankuai.avatar.web.service.ApplicationService;
import com.sankuai.avatar.web.service.DXUserService;
import com.sankuai.avatar.web.service.OrgRoleAdminService;
import com.sankuai.avatar.web.service.UserService;
import com.sankuai.avatar.web.transfer.orgRole.DxGroupVOTransfer;
import com.sankuai.avatar.web.transfer.orgRole.OrgRoleAdminVOTransfer;
import com.sankuai.avatar.web.transfer.user.UserVOTransfer;
import com.sankuai.avatar.web.util.UserUtils;
import com.sankuai.avatar.web.vo.PageInfo;
import com.sankuai.avatar.web.vo.application.AppKeyVO;
import com.sankuai.avatar.web.vo.org.OrgInfoVO;
import com.sankuai.avatar.web.vo.orgRole.OrgRoleAdminTreeVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Jie.li.sh
 * @create 2020-02-14
 **/
@Slf4j
@RestController
@RequestMapping("/api/v2/avatar/sc")
public class ServiceCatalogController {
    private static final String C_KEY = "dx_avatar_url";
    private static final int APPLICATION_APPKEY_COUNT_LIMIT = 1000;

    @Autowired
    private ServiceCatalogApplication serviceCatalogApplication;

    @Autowired
    private ServiceCatalogOrg serviceCatalogOrg;

    @Autowired
    private ServiceCatalogAppKey serviceCatalogAppKey;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private DXUserService dxUserService;

    @Autowired
    private OrgRoleAdminService orgRoleAdminService;

    @Autowired
    private UserService userService;


    @GetMapping("/application/{name}")
    public Application getApplication(@PathVariable("name") String name) throws Exception {
        Application application;
        try {
            application = serviceCatalogApplication.getApplication(name);
        } catch (Exception e) {
            throw new SupportErrorException(String.format("应用:%s 不存在", name));
        }
        List<String> userList = new ArrayList<>();
        userList.add(application.getAdmin().getMis());
        userList.addAll(application.getPms().stream().map(User::getMis).collect(Collectors.toList()));
        userList.addAll(application.getAdminTeam().getUsers().stream().map(User::getMis).collect(Collectors.toList()));
        // 应用下不展示额外的Away Team && 大于1000个服务的应用也不展示
        List<AggregatedUsers> simpleParticipatedTeams = new ArrayList<>();
        if (application.getAppKeyTotal() < APPLICATION_APPKEY_COUNT_LIMIT){
            simpleParticipatedTeams = application.getParticipatedTeams().stream().limit(3).collect(Collectors.toList());
        }
        application.setParticipatedTeams(simpleParticipatedTeams);
        userList.addAll(application.getParticipatedTeams().stream().
                flatMap(i -> i.getUsers().stream().map(User::getMis)).collect(Collectors.toList()));

        Map<String, String> userUrlMap = dxUserService.batchGetUserAvatarUrlCache(userList);
        application.getAdmin().setAvatarUrl(dxUserService.getUserVo(application.getAdmin().getMis()).getAvatarUrl());
        for (User user : application.getPms()) {
            user.setAvatarUrl(dxUserService.getUserAvatarUrlByCache(user.getMis(), userUrlMap));
        }
        for (User user : application.getAdminTeam().getUsers()) {
            user.setAvatarUrl(dxUserService.getUserAvatarUrlByCache(user.getMis(), userUrlMap));
        }
        for (AggregatedUsers aggregatedUsers : application.getParticipatedTeams()) {
            for (User user : aggregatedUsers.getUsers()) {
                user.setAvatarUrl(dxUserService.getUserAvatarUrlByCache(user.getMis(), userUrlMap));
            }
        }
        return application;
    }

    @GetMapping("/application")
    public PageInfo<Application> listApplication(ServiceCatalogApplication.ApplicationQueryParams applicationQueryParams,
                                                 @RequestParam(value = "type", required = false) String type) {
        PageInfo<Application> applicationPageInfo = new PageInfo<Application>() {
        };
        if (StringUtils.equals(type, "mine")) {
            applicationQueryParams.setMember(UserUtils.getCurrentCasUser().getLoginName());
        }
        try {
            Slice<Application> applicationSlice = serviceCatalogApplication.listApplication(applicationQueryParams);
            applicationPageInfo.setItems(applicationSlice.getItems());
            applicationPageInfo.setPage(applicationSlice.getCn());
            applicationPageInfo.setPageSize(applicationSlice.getSn());
            applicationPageInfo.setTotalCount(applicationSlice.getTn());
            applicationPageInfo.setTotalPage(applicationSlice.getPn());
            return applicationPageInfo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return applicationPageInfo;
    }

    @GetMapping("/application/{application}/appkeys")
    public PageInfo<AppKeyVO> listAppKeysByApplication(@Valid ServiceCatalogAppKey.AppKeyQueryParams appKeyQueryParams) {
        // 查看应用下的Appkey，默认展示PAAS服务
        if (appKeyQueryParams.getIncludePaaS() == null) {
            appKeyQueryParams.setIncludePaaS("true");
        }
        return applicationService.getAppKeyByApplication(appKeyQueryParams);
    }

    @GetMapping("/appkey")
    public PageInfo<AppKeyVO> listAppKeys(@Valid ServiceCatalogAppKey.AppKeyQueryParams appKeyQueryParams) {
        return applicationService.getAppKeyByApplication(appKeyQueryParams);
    }

    @GetMapping("/appkey/tags")
    public List<Tag> listTags() throws Exception {
        return serviceCatalogAppKey.getAllTags();
    }

    @GetMapping("/appkey/categories")
    public List<Category> listCategories() throws Exception {
        return serviceCatalogAppKey.getAllCategories();
    }

    @GetMapping("/appkey/frameworks")
    public List<Framework> listFrameworks() throws Exception {
        return serviceCatalogAppKey.getAllFrameworks();
    }

    @GetMapping("/org/tree")
    public List<Org> listUserOrg(@RequestParam(value = "mis", required = false) String mis) {
        try {
            if (mis == null) {
                mis = UserUtils.getCurrentCasUser().getLoginName();
            }
            return serviceCatalogOrg.listUserOrg(mis);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Deprecated
    @GetMapping("/org/info")
    public OrgInfoVO getOrgInfo(@RequestParam(value = "orgIds", required = true) String orgIds) throws Exception {
        OrgInfo orgInfo = serviceCatalogOrg.getOrgInfo(orgIds);
        OrgInfoVO orgInfoVO = new OrgInfoVO(orgInfo);
        final String dot = ",";
        OrgRoleAdminTreeVO opAdmin = getOrgRoleAdminTreeVO(orgIds, OrgRoleType.OP_ADMIN);
        orgInfoVO.setOpRoleAdmin(opAdmin);
        orgInfoVO.setDxGroups(opAdmin.getGroupList());
        if (StringUtils.isNotEmpty(opAdmin.getRoleUsers())) {
            List<String> opUsers = Arrays.asList(opAdmin.getRoleUsers().split(dot));
            orgInfoVO.setOpAdmins(UserVOTransfer.INSTANCE.toDxUserVOList(userService.queryUserByMis(opUsers)));
        } else {
            orgInfoVO.setOpAdmins(Collections.emptyList());
        }
        OrgRoleAdminTreeVO epAdmin = getOrgRoleAdminTreeVO(orgIds, OrgRoleType.EP_ADMIN);
        orgInfoVO.setEpRoleAdmin(epAdmin);
        if (StringUtils.isNotEmpty(epAdmin.getRoleUsers())) {
            List<String> epUsers = Arrays.asList(epAdmin.getRoleUsers().split(dot));
            orgInfoVO.setEpAdmins(UserVOTransfer.INSTANCE.toDxUserVOList(userService.queryUserByMis(epUsers)));
        } else {
            orgInfoVO.setEpAdmins(Collections.emptyList());
        }
        return orgInfoVO;
    }

    private OrgRoleAdminTreeVO getOrgRoleAdminTreeVO(String orgId, OrgRoleType roleType) {
        orgId = formatOrgId(orgId);
        OrgRoleAdminTreeVO orgRoleAdminTreeVO = new OrgRoleAdminTreeVO();
        orgRoleAdminTreeVO.setRole(roleType.getRoleType());
        orgRoleAdminTreeVO.setOrgId(orgId);
        OrgRoleAdminDTO current = orgRoleAdminService.getByOrgIdAndRole(orgId, roleType);
        OrgRoleAdminDTO ancestor = null;
        List<OrgRoleAdminDTO> children = null;
        if (Objects.isNull(current)) {
            ancestor = orgRoleAdminService.getAncestorOrgByOrgId(orgId, roleType);
            if (Objects.isNull(ancestor)) {
                children = orgRoleAdminService.getChildrenOrgByOrgId(orgId, roleType);
            }
        }

        orgRoleAdminTreeVO.setCurrent(OrgRoleAdminVOTransfer.INSTANCE.toVO(current));
        orgRoleAdminTreeVO.setAncestor(OrgRoleAdminVOTransfer.INSTANCE.toVO(ancestor));
        orgRoleAdminTreeVO.setChildren(OrgRoleAdminVOTransfer.INSTANCE.toVOList(children));

        String roleDesc =  OrgRoleType.OP_ADMIN.equals(roleType) ? "运维" : "测试";
        String descTemp = "当前部门" + roleDesc + "负责人是由 %s %s获得";
        if (Objects.nonNull(current)) {
            orgRoleAdminTreeVO.setDesc(String.format(descTemp, current.getOrgName(), "配置"));
            orgRoleAdminTreeVO.setRoleUsers(ObjectUtils.null2Empty(current.getRoleUsers()));
            orgRoleAdminTreeVO.setOrgName(current.getOrgName());
            orgRoleAdminTreeVO.setOrgId(current.getOrgId());
            if (StringUtils.isNotEmpty(current.getGroupId())) {
                List<String> groupList = Arrays.asList(current.getGroupId().split(","));
                orgRoleAdminTreeVO.setGroupList(DxGroupVOTransfer.INSTANCE.toVOList(
                        orgRoleAdminService.getDxGroupByGroupIds(groupList))
                );
            } else {
                orgRoleAdminTreeVO.setGroupList(Collections.emptyList());
            }
        } else {
            orgRoleAdminTreeVO.setGroupList(Collections.emptyList());
            if (Objects.nonNull(ancestor)) {
                orgRoleAdminTreeVO.setRoleUsers(ObjectUtils.null2Empty(ancestor.getRoleUsers()));
                orgRoleAdminTreeVO.setOrgName(ancestor.getOrgName());
                orgRoleAdminTreeVO.setDesc(String.format(descTemp, ancestor.getOrgName(), "继承"));
            } else if (CollectionUtils.isNotEmpty(children)) {
                String roleUsers = Arrays.stream(
                        children.stream().map(OrgRoleAdminDTO::getRoleUsers).filter(StringUtils::isNotEmpty).distinct().collect(Collectors.joining(","))
                                .split(",")).distinct().collect(Collectors.joining(","));
                orgRoleAdminTreeVO.setRoleUsers(roleUsers);
                orgRoleAdminTreeVO.setOrgName(
                        children.stream().map(OrgRoleAdminDTO::getOrgName).distinct().collect(Collectors.joining(","))
                );
                if (children.size() > 3) {
                    orgRoleAdminTreeVO.setDesc(String.format(descTemp, "下层组织节点", "聚合"));
                } else {
                    orgRoleAdminTreeVO.setDesc(String.format(descTemp, orgRoleAdminTreeVO.getOrgName(), "聚合"));
                }
            }
        }
        return orgRoleAdminTreeVO;
    }

    private String formatOrgId(String orgId){
        String[] orgIds = orgId.split(",");
        return orgIds[orgIds.length-1];
    }
}
