package com.sankuai.avatar.web.service.impl;

import com.dianping.cat.Cat;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.sankuai.avatar.client.http.core.EnvEnum;
import com.sankuai.avatar.common.exception.ResourceNotFoundErrorException;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;
import com.sankuai.avatar.common.utils.DisplayUtils;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.appkey.AppkeyResource;
import com.sankuai.avatar.resource.appkey.bo.*;
import com.sankuai.avatar.resource.appkey.request.AppkeySrvsQueryRequest;
import com.sankuai.avatar.resource.appkey.request.AppkeyTreeQueryRequestBO;
import com.sankuai.avatar.resource.appkey.request.IsoltAppkeyRequestBO;
import com.sankuai.avatar.resource.favor.UserRelationResource;
import com.sankuai.avatar.resource.octo.OctoResource;
import com.sankuai.avatar.resource.octo.model.OctoProviderGroupBO;
import com.sankuai.avatar.resource.tree.AppkeyTreeResource;
import com.sankuai.avatar.resource.tree.bo.AppkeyTreeBO;
import com.sankuai.avatar.resource.tree.bo.AppkeyTreeSrvDetailBO;
import com.sankuai.avatar.resource.tree.bo.AppkeyTreeSrvDetailResponseBO;
import com.sankuai.avatar.resource.tree.request.SrvQueryRequestBO;
import com.sankuai.avatar.resource.user.UserResource;
import com.sankuai.avatar.resource.user.bo.UserBO;
import com.sankuai.avatar.resource.whitelist.constant.WhiteType;
import com.sankuai.avatar.web.constant.UtilizationStandardTypeEnum;
import com.sankuai.avatar.web.dto.appkey.*;
import com.sankuai.avatar.web.dto.octo.OctoRouteStrategyDTO;
import com.sankuai.avatar.web.dto.tree.AppkeyTreeSrvDTO;
import com.sankuai.avatar.web.dto.tree.AppkeyTreeSrvDetailDTO;
import com.sankuai.avatar.web.dto.tree.AppkeyTreeSrvDetailResponseDTO;
import com.sankuai.avatar.web.dto.user.UserDTO;
import com.sankuai.avatar.web.dto.whitelist.ServiceWhitelistDTO;
import com.sankuai.avatar.web.request.AppkeyQueryPageRequest;
import com.sankuai.avatar.web.request.AppkeySearchPageRequest;
import com.sankuai.avatar.web.request.AppkeyTreeQueryRequest;
import com.sankuai.avatar.web.request.IsoltAppkeyPageRequest;
import com.sankuai.avatar.web.service.AppkeyService;
import com.sankuai.avatar.web.service.UserService;
import com.sankuai.avatar.web.service.WhitelistService;
import com.sankuai.avatar.web.transfer.AppkeyTreeNodeTransfer;
import com.sankuai.avatar.web.transfer.appkey.AppkeyDTOTransfer;
import com.sankuai.avatar.web.transfer.appkey.AppkeyQueryRequestTransfer;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author caoyang
 * @create 2022-12-14 14:40
 */
@Service
public class AppkeyServiceImpl implements AppkeyService {

    private static final String SPLIT_CHAR = "\\.";

    private final AppkeyResource appkeyResource;
    private final UserResource userResource;
    private final UserRelationResource relationResource;
    private final AppkeyTreeResource appkeyTreeResource;
    private final WhitelistService whitelistService;
    private final UserService userService;
    private final OctoResource octoResource;

    public AppkeyServiceImpl(AppkeyResource appkeyResource,
                             UserResource userResource,
                             UserRelationResource relationResource,
                             AppkeyTreeResource appkeyTreeResource,
                             WhitelistService whitelistService,
                             UserService userService,
                             OctoResource octoResource) {
        this.appkeyResource = appkeyResource;
        this.userResource = userResource;
        this.appkeyTreeResource = appkeyTreeResource;
        this.relationResource = relationResource;
        this.whitelistService = whitelistService;
        this.userService = userService;
        this.octoResource = octoResource;
    }

    @Override
    public List<AppkeyDTO> getByAppkeyRandom(Integer count) {
        return AppkeyDTOTransfer.INSTANCE.toDTOList(appkeyResource.getByAppkeyRandom(count));
    }

    @Override
    public AppkeyResourceUtilDTO getAppkeyUtilization(String appkey) {
        AppkeyResourceUtilBO appkeyResourceUtilBO;
        try {
            appkeyResourceUtilBO = appkeyResource.getAppkeyResourceUtil(appkey);
        } catch (Exception e) {
            // 降级展示
            return defaultAppkeyUtilization();
        }
        // 资源利用率达标基线
        Double baseLine = appkeyResourceUtilBO.getMsgConfig().getBaseline();
        NumberFormat fmt = NumberFormat.getPercentInstance();
        fmt.setMaximumFractionDigits(0);

        // 资源利用率白名单
        List<ServiceWhitelistDTO> serviceWhitelistDTOList = whitelistService.getServiceWhitelistByAppkey(appkey, WhiteType.UTILIZATION);
        Boolean isWhite = CollectionUtils.isNotEmpty(serviceWhitelistDTOList) ? Boolean.TRUE : Boolean.FALSE;

        // 已达标、未达标、免评估
        String standardTips = "";
        Boolean isStandard = Boolean.TRUE;
        String standardType = "";
        String standardTypeCn = "";
        String standardReason = "";
        if (Objects.equals(appkeyResourceUtilBO.getUtilizationStandard(), UtilizationStandardTypeEnum.STANDARD.name())) {
            standardType = UtilizationStandardTypeEnum.STANDARD.getName();
            standardTypeCn = UtilizationStandardTypeEnum.STANDARD.getCnName();
        } else if (Objects.equals(appkeyResourceUtilBO.getUtilizationStandard(), UtilizationStandardTypeEnum.UN_STANDARD.name())) {
            standardType = UtilizationStandardTypeEnum.UN_STANDARD.getName();
            standardTypeCn = UtilizationStandardTypeEnum.UN_STANDARD.getCnName();
            isStandard = Boolean.FALSE;
            standardReason = "资源利用率不达标，将限制新增机器申请（需直属主管审核）以及定时运营推送，请及时优化";
            standardTips = String.join("<br>", appkeyResourceUtilBO.getOptimizeMsg())
                    .replace("\n", "<br>").replace("；", "<br>");
        } else if (Objects.equals(appkeyResourceUtilBO.getUtilizationStandard(), UtilizationStandardTypeEnum.SKIP_STANDARD.name())) {
            standardType = UtilizationStandardTypeEnum.SKIP_STANDARD.getName();
            standardTypeCn = UtilizationStandardTypeEnum.SKIP_STANDARD.getCnName();
        }
        return AppkeyResourceUtilDTO.builder()
                .standardTips(standardTips)
                .standardData(baseLine > 0 ? String.format(">%s", fmt.format(baseLine)) : ">20%")
                .standardReason(standardReason)
                .standardType(standardType)
                .standardTypeCn(standardTypeCn)
                .util(appkeyResourceUtilBO.getResourceUtil().getLastWeekValue())
                .isStandard(isStandard)
                .isWhite(isWhite)
                .isApplyWhite(true)
                .build();
    }

    private AppkeyResourceUtilDTO defaultAppkeyUtilization() {
        return AppkeyResourceUtilDTO.builder()
                .standardTips("")
                .standardData(">20%")
                .standardReason("")
                .standardType(UtilizationStandardTypeEnum.SKIP_STANDARD.getName())
                .standardTypeCn(UtilizationStandardTypeEnum.SKIP_STANDARD.getCnName())
                .util((double) 0)
                .isStandard(true)
                .isWhite(false)
                .isApplyWhite(true)
                .build();
    }

    @Override
    public AppkeyDetailDTO getAppkeyDetailByRepository(String appkey) {
        AppkeyDTO appkeyDTO = AppkeyDTOTransfer.INSTANCE.toDTO(appkeyResource.getByAppkey(appkey));
        AppkeyDetailDTO appkeyDetailDTO = AppkeyDTOTransfer.INSTANCE.toAppkeyDetailDTO(appkeyDTO);
        if (StringUtils.isBlank(appkeyDTO.getIsBoughtExternal())) {
            appkeyDetailDTO.setIsBoughtExternal("UNCERTAIN");
        }
        appkeyDetailDTO.setTeam(AppkeyDTOTransfer.INSTANCE.toTeamDTO(appkeyDTO));
        appkeyDetailDTO.setTree(AppkeyDTOTransfer.INSTANCE.toTree(appkeyDTO));

        List<String> users = new ArrayList<>();
        Arrays.asList(appkeyDetailDTO.getOpAdmin(), appkeyDetailDTO.getEpAdmin(), appkeyDetailDTO.getRdAdmin()).forEach(
                admins -> users.addAll(admins.stream().map(AppkeyUserDTO::getLoginName).filter(StringUtils::isNotEmpty).collect(Collectors.toList()))
        );
        if (Objects.nonNull(appkeyDetailDTO.getAdmin()) && StringUtils.isNotEmpty(appkeyDetailDTO.getAdmin().getLoginName())) {
            users.add(appkeyDetailDTO.getAdmin().getLoginName());
        }
        List<UserDTO> userDTOList = userService.queryUserByMisNoOrder(new ArrayList<>(new HashSet<>(users)));
        Map<String, UserDTO> userMap = userDTOList.stream().collect(Collectors.toMap(UserDTO::getMis, userDTO -> userDTO));
        if (Objects.nonNull(appkeyDetailDTO.getAdmin())) {
            UserDTO userDTO = userMap.getOrDefault(appkeyDetailDTO.getAdmin().getLoginName(), null);
            appkeyDetailDTO.getAdmin().setAvatarUrl(Objects.nonNull(userDTO) ? userDTO.getAvatarUrl() : "");
            appkeyDetailDTO.getAdmin().setName(Objects.nonNull(userDTO) ? userDTO.getName() : "");
            appkeyDetailDTO.getAdmin().setOrgName(Objects.nonNull(userDTO) ? userDTO.getOrganization() : "");
        }
        Arrays.asList(appkeyDetailDTO.getOpAdmin(), appkeyDetailDTO.getEpAdmin(), appkeyDetailDTO.getRdAdmin()).forEach(
                admins -> admins.forEach(admin -> {
                    UserDTO userDTO = userMap.getOrDefault(admin.getLoginName(), null);
                    admin.setAvatarUrl(Objects.nonNull(userDTO) ? userDTO.getAvatarUrl() : "");
                    admin.setName(Objects.nonNull(userDTO) ? userDTO.getName() : "");
                    admin.setOrgName(Objects.nonNull(userDTO) ? userDTO.getOrganization() : "");
                })
        );
        return appkeyDetailDTO;
    }

    @Override
    public AppkeyDetailDTO getAppkeyDetailInfoByHttpClient(String appkey) {
        String srv = appkeyResource.getSrvKeyByAppkey(appkey);
        if (StringUtils.isBlank(srv)) {
            throw new ResourceNotFoundErrorException(String.format("Appkey：%s服务树节点信息不存在", appkey));
        }
        // OPS服务基础信息
        AppkeyTreeBO appkeyTreeBO = appkeyTreeResource.getAppkeyTreeByKey(srv);
        AppkeyDetailDTO appkeyDetailDTO = AppkeyDTOTransfer.INSTANCE.toAppkeyDetailDTO(appkeyTreeBO.getSrv());
        AppkeyDetailDTO.Tree tree = AppkeyDetailDTO.Tree.builder()
                .bg(appkeyTreeBO.getOwt().getBusinessGroup())
                .owt(AppkeyDetailDTO.TreeData.builder().key(appkeyTreeBO.getOwt().getKey()).name(appkeyTreeBO.getOwt().getName()).build())
                .pdl(AppkeyDetailDTO.TreeData.builder().key(appkeyTreeBO.getPdl().getKey()).name(appkeyTreeBO.getPdl().getName()).build())
                .build();
        appkeyDetailDTO.setTree(tree);
        // SC服务运营属性数据
        ScAppkeyBO scAppkeyBO = appkeyResource.getAppkeyBySc(appkey);
        ScAppkeyDTO scAppkeyDTO = AppkeyDTOTransfer.INSTANCE.toScAppkeyDTO(scAppkeyBO);
        appkeyDetailDTO = AppkeyDTOTransfer.INSTANCE.toAppkeyDetailDTO(scAppkeyDTO, appkeyDetailDTO);
        appkeyDetailDTO.setTeam(parseTeam(scAppkeyDTO.getTeam()));
        if (StringUtils.isBlank(scAppkeyDTO.getIsBoughtExternal())) {
            appkeyDetailDTO.setIsBoughtExternal("UNCERTAIN");
        }
        // 服务Owner/RD/EP/OP等负责人处理
        return formatAppkeyAdmin(appkeyDetailDTO, appkeyTreeBO, scAppkeyBO);
    }

    /**
     * 展示Appkey所在部门属性
     *
     * @param team 团队
     * @return {@link TeamDTO}
     */
    private TeamDTO parseTeam(ScAppkeyDTO.Team team) {
        if (team == null) {
            return null;
        }
        List<String> displayNameList = Arrays.asList(team.getDisplayName().split("/"));
        List<String> displayOrgIdList = Arrays.asList(team.getOrgIdList().split("/"));
        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setOrgName(String.join("/", displayNameList.size() > 3
                ? displayNameList.subList(Math.max(displayNameList.size() - 3, 0), displayNameList.size())
                : displayNameList));
        teamDTO.setOrgId(String.join(",", displayOrgIdList.size() > 3
                ? displayOrgIdList.subList(Math.max(displayOrgIdList.size() - 3, 0), displayOrgIdList.size())
                : displayOrgIdList));
        return teamDTO;
    }

    /**
     * 获取SRV的RD负责人: srv > pdl > owt
     *
     * @param appkeyTreeBO appkey树
     * @return {@link List}<{@link String}>
     */
    private List<String> getSrvRdAdmin(AppkeyTreeBO appkeyTreeBO) {
        List<String> rdAdmin = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(appkeyTreeBO.getSrv().getRdAdmin())) {
            rdAdmin = appkeyTreeBO.getSrv().getRdAdmin();
        } else if (CollectionUtils.isNotEmpty(appkeyTreeBO.getPdl().getRdAdmin())) {
            rdAdmin = appkeyTreeBO.getPdl().getRdAdmin();
        } else if (CollectionUtils.isNotEmpty(appkeyTreeBO.getOwt().getRdAdmin())) {
            rdAdmin = appkeyTreeBO.getOwt().getRdAdmin();
        }
        return rdAdmin;
    }

    /**
     * 获取SRV的EP负责人
     *
     * @param appkeyTreeBO appkey树
     * @return {@link List}<{@link String}>
     */
    private List<String> getSrvEpAdmin(AppkeyTreeBO appkeyTreeBO) {
        List<String> epAdmin = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(appkeyTreeBO.getSrv().getEpAdmin())) {
            epAdmin = appkeyTreeBO.getSrv().getEpAdmin();
        } else if (CollectionUtils.isNotEmpty(appkeyTreeBO.getPdl().getEpAdmin())) {
            epAdmin = appkeyTreeBO.getPdl().getEpAdmin();
        } else if (CollectionUtils.isNotEmpty(appkeyTreeBO.getOwt().getEpAdmin())) {
            epAdmin = appkeyTreeBO.getOwt().getEpAdmin();
        }
        return epAdmin;
    }

    /**
     * 获取SRV的OP负责人
     *
     * @param appkeyTreeBO appkey树
     * @return {@link List}<{@link String}>
     */
    private List<String> getSrvOpAdmin(AppkeyTreeBO appkeyTreeBO) {
        List<String> opAdmin = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(appkeyTreeBO.getSrv().getOpAdmin())) {
            opAdmin = appkeyTreeBO.getSrv().getOpAdmin();
        } else if (CollectionUtils.isNotEmpty(appkeyTreeBO.getPdl().getOpAdmin())) {
            opAdmin = appkeyTreeBO.getPdl().getOpAdmin();
        } else if (CollectionUtils.isNotEmpty(appkeyTreeBO.getOwt().getOpAdmin())) {
            opAdmin = appkeyTreeBO.getOwt().getOpAdmin();
        }
        return opAdmin;
    }

    /**
     * 展示appkey负责人信息
     *
     * @param appkeyDetailDTO appkey dto对象
     * @param appkeyTreeBO    appkey树
     * @param scAppkeyBO      sc appkey
     * @return {@link AppkeyDetailDTO}
     */
    private AppkeyDetailDTO formatAppkeyAdmin(AppkeyDetailDTO appkeyDetailDTO, AppkeyTreeBO appkeyTreeBO, ScAppkeyBO scAppkeyBO) {
        List<String> allAminList = new ArrayList<>();
        // Owner/Rd/Ep/Op Admin
        String owner = scAppkeyBO.getAdmin().getMis();
        List<String> rdAdmin = getSrvRdAdmin(appkeyTreeBO);
        List<String> epAdmin = getSrvEpAdmin(appkeyTreeBO);
        List<String> opAdmin = getSrvOpAdmin(appkeyTreeBO);

        allAminList.add(owner);
        allAminList.addAll(rdAdmin);
        allAminList.addAll(epAdmin);
        allAminList.addAll(opAdmin);
        // ORG获取人员详细信息并补充
        Map<String, AppkeyUserDTO> adminUserDTOMap = formatUserMisInfo(allAminList);

        appkeyDetailDTO.setAdmin(adminUserDTOMap.getOrDefault(owner, getDefaultAppkeyUserDTO(owner)));

        List<AppkeyUserDTO> rdAdminList = rdAdmin.stream()
                .map(mis -> adminUserDTOMap.getOrDefault(mis, getDefaultAppkeyUserDTO(mis))).collect(Collectors.toList());
        appkeyDetailDTO.setRdAdmin(rdAdminList);

        List<AppkeyUserDTO> epAdminList = epAdmin.stream()
                .map(mis -> adminUserDTOMap.getOrDefault(mis, getDefaultAppkeyUserDTO(mis))).collect(Collectors.toList());
        appkeyDetailDTO.setEpAdmin(epAdminList);

        List<AppkeyUserDTO> opAdminList = opAdmin.stream()
                .map(mis -> adminUserDTOMap.getOrDefault(mis, getDefaultAppkeyUserDTO(mis))).collect(Collectors.toList());
        appkeyDetailDTO.setOpAdmin(opAdminList);

        return appkeyDetailDTO;
    }

    /**
     * 用户信息降级默认展示格式
     *
     * @param mis 用户名
     * @return {@link AppkeyUserDTO}
     */
    private AppkeyUserDTO getDefaultAppkeyUserDTO(String mis) {
        AppkeyUserDTO appkeyUserDTO = new AppkeyUserDTO();
        appkeyUserDTO.setName(mis);
        appkeyUserDTO.setLoginName(mis);
        appkeyUserDTO.setOrgName("");
        appkeyUserDTO.setAvatarUrl("");
        return appkeyUserDTO;
    }

    /**
     * ORG/DX 获取用户信息
     *
     * @param misList 用户名列表
     * @return {@link Map}<{@link String}, {@link AppkeyUserDTO}>
     */
    private Map<String, AppkeyUserDTO> formatUserMisInfo(List<String> misList) {
        List<UserBO> userBOList = userResource.queryByMisWithOrder(misList.stream()
                .distinct().filter(StringUtils::isNotBlank)
                .collect(Collectors.toList()));
        List<AppkeyUserDTO> appkeyUserSimpleDTOS = AppkeyDTOTransfer.INSTANCE.toAdminUserDTOList(userBOList);
        return appkeyUserSimpleDTOS.stream()
                .collect(Collectors.toMap(AppkeyUserDTO::getLoginName, a -> a, (k1, k2) -> k1));
    }

    @Override
    public AppkeyTreeSrvDetailResponseDTO getOpsAppkeysByHttpClient(AppkeyTreeQueryRequest appkeyTreeQueryRequest) {
        AppkeyTreeSrvDetailResponseDTO appkeyTreeSrvDetailResponseDTO = new AppkeyTreeSrvDetailResponseDTO();
        List<AppkeyTreeSrvDTO> appkeyTreeSrvDTOList = new ArrayList<>();
        // OPS系统查询服务信息
        SrvQueryRequestBO srvQueryRequestBO = AppkeyDTOTransfer.INSTANCE.toSrvQueryRequestBO(appkeyTreeQueryRequest);
        AppkeyTreeSrvDetailResponseBO appkeyTreeSrvDetailResponseBO = appkeyTreeResource.getSrvsByQueryRequest(srvQueryRequestBO);
        List<AppkeyTreeSrvDetailBO> appkeyTreeSrvDetailBOList = appkeyTreeSrvDetailResponseBO.getSrvs();

        for (AppkeyTreeSrvDetailBO appkeyTreeSrvDetailBO : appkeyTreeSrvDetailBOList) {
            AppkeyTreeSrvDetailDTO appkeyTreeSrvDetailDTO = AppkeyTreeNodeTransfer.INSTANCE.toAppkeyTreeSrvDetailDTO(appkeyTreeSrvDetailBO);
            AppkeyTreeSrvDTO appkeyTreeSrvDTO = appkeyTreeSrvDetailDTO.getSrv();
            // 补充服务主机数据
            appkeyTreeSrvDTO.setHostCount(appkeyTreeSrvDetailDTO.getHostCount());
            // 格式化用户信息展示：Mis/Name
            formatUserRelatedAppkeyAdmin(appkeyTreeSrvDTO, appkeyTreeSrvDetailBO);
            appkeyTreeSrvDTOList.add(appkeyTreeSrvDTO);
        }

        appkeyTreeSrvDetailResponseDTO.setSrvs(appkeyTreeSrvDTOList);
        appkeyTreeSrvDetailResponseDTO.setTotal(appkeyTreeSrvDetailResponseBO.getTotal());
        appkeyTreeSrvDetailResponseDTO.setCount(appkeyTreeSrvDetailResponseBO.getCount());
        return appkeyTreeSrvDetailResponseDTO;
    }

    @Override
    public List<AppkeyTreeSrvDTO> getMineAppkeysByHttpClient(AppkeyTreeQueryRequest appkeyTreeQueryRequest) {
        Integer pageSize = appkeyTreeQueryRequest.getPageSize();
        if (pageSize < 100) {
            appkeyTreeQueryRequest.setPage(1);
            appkeyTreeQueryRequest.setPageSize(200);
        }
        List<AppkeyTreeSrvDTO> appkeyTreeSrvDTOList = getOpsAppkeysByHttpClient(appkeyTreeQueryRequest).getSrvs();
        if (pageSize >= 100) {
            return appkeyTreeSrvDTOList;
        }
        // 服务置顶排序展示
        return topSort(appkeyTreeSrvDTOList, appkeyTreeQueryRequest.getUser());
    }

    /**
     * 过滤maven类型appkey（故障时忽略过滤，降级策略不依赖其他第三方系统）
     * 增加异常捕获: 故障时不影响主逻辑
     *
     * @param appkeyTreeSrvDTOList appkey信息
     * @return {@link List}<{@link AppkeyTreeSrvDTO}>
     */
    private List<AppkeyTreeSrvDTO> filterMavenAppkey(List<AppkeyTreeSrvDTO> appkeyTreeSrvDTOList) {
        List<String> mavenAppkeyList = new ArrayList<>();
        try {
            List<String> appkeyList = appkeyTreeSrvDTOList.stream().map(AppkeyTreeSrvDTO::getAppkey).collect(Collectors.toList());
            List<List<String>> appkeyListParts = Lists.partition(appkeyList.stream().distinct()
                    .filter(StringUtils::isNotBlank)
                    .collect(Collectors.toList()), 100);
            for (List<String> appkeyListPart : appkeyListParts) {
                mavenAppkeyList.addAll(batchGetMavenAppkeyBySc(appkeyListPart));
            }
            if (CollectionUtils.isEmpty(mavenAppkeyList)) {
                return appkeyTreeSrvDTOList;
            }
            return appkeyTreeSrvDTOList.stream().filter(i -> !mavenAppkeyList.contains(i.getAppkey())).collect(Collectors.toList());
        } catch (Exception e) {
            Cat.logError(e);
        }
        return appkeyTreeSrvDTOList;
    }

    /**
     * 批量从SC查询服务类型信息,并过滤出Maven类型Appkey
     *
     * @param appkeyList appkey列表
     * @return {@link List}<{@link String}>
     */
    private List<String> batchGetMavenAppkeyBySc(List<String> appkeyList) {
        List<ScAppkeyBO> scAppkeyBOList = appkeyResource.batchGetAppkeyBySc(appkeyList);
        return scAppkeyBOList.stream().
                filter(i -> Objects.equals(i.getType(), "MAVEN"))
                .map(ScAppkeyBO::getAppKey)
                .collect(Collectors.toList());
    }

    /**
     * 服务置顶排序
     *
     * @param appkeyTreeSrvDTOList 用户有权限的服务树列表
     * @param user                 用户Mis
     * @return {@link List}<{@link AppkeyTreeSrvDTO}>
     */
    private List<AppkeyTreeSrvDTO> topSort(List<AppkeyTreeSrvDTO> appkeyTreeSrvDTOList, String user) {
        List<String> topAppkeys = relationResource.getUserTopAppkey(user);
        appkeyTreeSrvDTOList.sort((bo1, bo2) -> {
            int i = topAppkeys.size();
            int j = topAppkeys.size();
            if (topAppkeys.contains(bo1.getAppkey())) {
                i = topAppkeys.indexOf(bo1.getAppkey());
            }
            if (topAppkeys.contains(bo2.getAppkey())) {
                j = topAppkeys.indexOf(bo2.getAppkey());
            }
            return i - j;
        });
        return appkeyTreeSrvDTOList;
    }

    /**
     * 我的服务: 格式化展示appkey admin
     * 用户信息直接从OPS返回信息中获取,不走ORG接口进行查询
     *
     * @param appkeyTreeSrvDTO      服务树节点
     * @param appkeyTreeSrvDetailBO 服务树SRV
     */
    private void formatUserRelatedAppkeyAdmin(AppkeyTreeSrvDTO appkeyTreeSrvDTO, AppkeyTreeSrvDetailBO appkeyTreeSrvDetailBO) {
        Map<String, String> allUsers = appkeyTreeSrvDetailBO.getUsers();

        List<String> rdAdmins = appkeyTreeSrvDetailBO.getSrv().getRdAdmin();
        appkeyTreeSrvDTO.setRdAdmin(formatAdmin(allUsers, rdAdmins));

        List<String> epAdmins = appkeyTreeSrvDetailBO.getSrv().getEpAdmin();
        appkeyTreeSrvDTO.setEpAdmin(formatAdmin(allUsers, epAdmins));

        List<String> opAdmins = appkeyTreeSrvDetailBO.getSrv().getOpAdmin();
        appkeyTreeSrvDTO.setOpAdmin(formatAdmin(allUsers, opAdmins));
    }

    private List<AppkeyUserDTO> formatAdmin(Map<String, String> allUsers, List<String> admins) {
        List<AppkeyUserDTO> appkeyUserDTOList = new ArrayList<>();
        for (String admin : admins) {
            AppkeyUserDTO appkeyRdUserDTO = new AppkeyUserDTO();
            appkeyRdUserDTO.setLoginName(admin);
            appkeyRdUserDTO.setName(allUsers.getOrDefault(admin, admin));
            appkeyUserDTOList.add(appkeyRdUserDTO);
        }
        return appkeyUserDTOList;
    }

    @Override
    public AppkeyDTO getBySrv(String srv) {
        AppkeyBO appkeyBO = appkeyResource.getBySrv(srv);
        return AppkeyDTOTransfer.INSTANCE.toDTO(appkeyBO);
    }

    @Override
    public PageResponse<AppkeyDTO> queryPage(AppkeyQueryPageRequest pageRequest) {
        PageResponse<AppkeyBO> boPageResponse = appkeyResource.queryPage(AppkeyDTOTransfer.INSTANCE.toRequestBO(pageRequest));
        return toDTOPageResponse(boPageResponse);
    }

    @Override
    public PageResponse<AppkeyDTO> searchPage(AppkeySearchPageRequest pageRequest) {
        PageResponse<AppkeyBO> boPageResponse = appkeyResource.searchAppkey(AppkeyDTOTransfer.INSTANCE.toSearchRequestBO(pageRequest));
        return toDTOPageResponse(boPageResponse);
    }

    private PageResponse<AppkeyDTO> toDTOPageResponse(PageResponse<AppkeyBO> boPageResponse) {
        PageResponse<AppkeyDTO> dtoPageResponse = new PageResponse<>();
        dtoPageResponse.setPage(boPageResponse.getPage());
        dtoPageResponse.setPageSize(boPageResponse.getPageSize());
        dtoPageResponse.setTotalCount(boPageResponse.getTotalCount());
        dtoPageResponse.setTotalPage(boPageResponse.getTotalPage());
        dtoPageResponse.setItems(AppkeyDTOTransfer.INSTANCE.toDTOList(boPageResponse.getItems()));
        return dtoPageResponse;
    }

    @Override
    public String getByHost(String host) {
        return appkeyResource.getByHost(host);
    }

    @Override
    public PageResponse<AppkeyDTO> getMineAppkeysByRepository(AppkeyTreeQueryRequest request) {
        AppkeySrvsQueryRequest srvsQueryRequest = AppkeyQueryRequestTransfer.INSTANCE.toAppkeySrvsQueryRequest(request);
        PageResponse<AppkeyBO> appkeyBOList = appkeyResource.getOwnAppkey(srvsQueryRequest);
        return AppkeyDTOTransfer.INSTANCE.toAppkeyDTOPageResponse(appkeyBOList);
    }

    @Override
    public List<String> getUserTopAppkey(String mis) {
        try {
            return relationResource.getUserTopAppkey(mis);
        } catch (Exception e){
            // 非核心逻辑,异常忽略
            return new ArrayList<>();
        }
    }

    @Override
    public PageResponse<AppkeyDTO> getAllAppkeysByRepository(AppkeyTreeQueryRequest request) {
        AppkeyTreeQueryRequestBO requestBO = AppkeyQueryRequestTransfer.INSTANCE.toTreeRequest(request);
        PageResponse<AppkeyBO> boPageResponse = appkeyResource.getPageAppkey(requestBO);
        return toDTOPageResponse(boPageResponse);
    }

    /**
     * 服务详情页：用户是否关注了Appkey
     * 故障时默认自动返回不关注
     *
     * @param user 用户
     * @param appkey  appkey
     * @return {@link Boolean}
     */
    @Override
    public Boolean isUserFavorAppkey(String user, String appkey) {
        String srv = appkeyResource.getSrvKeyByAppkey(appkey);
        if (StringUtils.isBlank(srv)) {
            return Boolean.FALSE;
        }
        try {
            List<String> srvSubscribers = appkeyTreeResource.getSrvSubscribers(srv);
            return srvSubscribers.contains(user);
        } catch (Exception ignored) {
        }
        return Boolean.FALSE;
    }

    @Override
    public PageResponse<AppkeyDTO> getFavorPageAppkey(AppkeyTreeQueryRequest request) {
        int page = request.getPage();
        int pageSize = request.getPageSize();
        AppkeySrvsQueryRequest srvsQueryRequest = AppkeyQueryRequestTransfer.INSTANCE.toAppkeySrvsQueryRequest(request);
        srvsQueryRequest.setPage(page);
        srvsQueryRequest.setPageSize(pageSize);
        List<AppkeyBO> appkeyBOList = appkeyResource.getFavorAppkey(srvsQueryRequest);
        if (CollectionUtils.isNotEmpty(appkeyBOList) && StringUtils.isNotEmpty(request.getQuery())) {
            appkeyBOList = appkeyBOList.stream().filter(appkeyBO -> StringUtils.isNotEmpty(appkeyBO.getAppkey()) && appkeyBO.getAppkey().contains(request.getQuery())).collect(Collectors.toList());
        }
        return toDTOPageResponse(page, pageSize, appkeyBOList);
    }

    @NotNull
    private PageResponse<AppkeyDTO> toDTOPageResponse(int page, int pageSize, List<AppkeyBO> appkeyBOList) {
        int total = appkeyBOList.size();
        int totalPage = (total - 1) / pageSize + 1;
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, total);
        PageResponse<AppkeyDTO> pageResponse = new PageResponse<>();
        pageResponse.setPage(page);
        pageResponse.setPageSize(pageSize);
        pageResponse.setTotalCount(total);
        pageResponse.setTotalPage(totalPage);
        List<AppkeyDTO> dtoList = (start < end) ? AppkeyDTOTransfer.INSTANCE.toDTOList(appkeyBOList.subList(start, end)) : Collections.emptyList();
        pageResponse.setItems(dtoList);
        return pageResponse;
    }

    @Override
    public List<String> getByVip(String vip) {
        return appkeyResource.getByVip(vip);
    }

    @Override
    public Boolean favorAppkey(String appkey, String mis) {
        return appkeyResource.favorAppkey(appkey, mis);
    }

    @Override
    public Boolean cancelFavorAppkey(String appkey, String mis) {
        return appkeyResource.cancelFavorAppkey(appkey, mis);
    }

    @Override
    public ElasticTipDTO getElasticTips() throws SdkCallException, SdkBusinessErrorException {
        ElasticTipBO elasticTipBO = appkeyResource.getElasticTips();
        return AppkeyDTOTransfer.INSTANCE.toElasticTipDTO(elasticTipBO);
    }

    @Override
    public Boolean getElasticGrayScale(String owt) throws SdkCallException, SdkBusinessErrorException {
        return appkeyResource.getElasticGrayScale(owt);
    }

    @Override
    public PageResponse<AppkeyFlowDTO> batchGetAppkeyRunningAndHoldingFlowList(List<String> appkeyList) throws SdkCallException, SdkBusinessErrorException {
        PageResponse<AppkeyFlowBO> appkeyFlowPageResponse = new PageResponse<>();
        try {
            appkeyFlowPageResponse = appkeyResource.batchGetAppkeyRunningAndHoldingFlowList(appkeyList);
        } catch (Exception ignore){}
        return AppkeyDTOTransfer.INSTANCE.toAppkeyFlowDTOPageResponse(appkeyFlowPageResponse);
    }

    @Override
    public PageResponse<IsoltAppkeyDTO> getIsoltAppkeysByHttpClient(IsoltAppkeyPageRequest request) {
        IsoltAppkeyRequestBO isoltAppkeyRequest = AppkeyQueryRequestTransfer.INSTANCE.toIsoltAppkeyRequestBO(request);
        PageResponse<ScIsoltAppkeyBO> scIsoltAppkeyBOPageResponse = appkeyResource.getIsoltAppkeys(isoltAppkeyRequest);
        return AppkeyDTOTransfer.INSTANCE.toScIsoltAppkeyDTOPageResponse(scIsoltAppkeyBOPageResponse);
    }

    @Override
    public IsoltAppkeyGenerateDisplayDTO generateIsoltAppkeyName(String originAppkey, String soamod, String soasrv) {
        IsoltAppkeyGenerateDisplayDTO isoltAppkeyGenerateDisplayDTO = IsoltAppkeyGenerateDisplayDTO.builder().isoltAppkeyName("").msg("").build();
        if (!originAppkey.startsWith("com.sankuai")) {
            isoltAppkeyGenerateDisplayDTO.setMsg("基础Appkey非标准格式（com.sankuai开头），不支持演练Appkey直接创建，请走正常的新增服务流程");
            return isoltAppkeyGenerateDisplayDTO;
        }
        OpsSrvBO opsSrvBO = appkeyResource.getAppkeyRelatedSrvInfo(originAppkey);
        if (Objects.isNull(opsSrvBO)) {
            isoltAppkeyGenerateDisplayDTO.setMsg("该服务不存在服务树节点，无法从Avatar创建演练服务！");
            return isoltAppkeyGenerateDisplayDTO;
        }
        MutablePair<String, String> modAndSrv = getIsoltAppkeyModAndSrv(originAppkey, opsSrvBO.getSoamod(), opsSrvBO.getSoasrv());
        String opsSoaMod = modAndSrv.getLeft();
        String srv = modAndSrv.getRight();

        if (StringUtils.isNotBlank(soamod) && StringUtils.isNotBlank(soasrv)) {
            opsSoaMod = soamod;
            srv = soasrv;
        }
        if (StringUtils.isBlank(srv)) {
            isoltAppkeyGenerateDisplayDTO.setMsg("基础Appkey名称异常，无法生成演练服务！");
            return isoltAppkeyGenerateDisplayDTO;
        }

        List<ScAppkeyBO> scAppkeyBOList = appkeyResource.batchGetAppkeyBySc(Collections.singletonList(originAppkey));
        if (CollectionUtils.isEmpty(scAppkeyBOList)) {
            isoltAppkeyGenerateDisplayDTO.setMsg("OpenSc接口调用失败，请检查基础Appkey信息后重试！");
            return isoltAppkeyGenerateDisplayDTO;
        }
        String soaapp = scAppkeyBOList.get(0).getApplicationName().toLowerCase();
        String isoltAppkey = getIsoltAppkeyName(soaapp, opsSoaMod, srv);

        return IsoltAppkeyGenerateDisplayDTO.builder().isoltAppkeyName(isoltAppkey).msg("").build();
    }

    /**
     * 计算真实的模块名、服务名
     * 1、兼容OPS不存在模块名、服务名情形
     * 2、兼容Appkey按"."号分隔后长度为4或5的情形
     *
     * @param originAppkey 起源appkey
     * @param opsSoaMod    运维soa国防部
     * @param opsSoaSrv    运维soa深水救生艇
     * @return {@link MutablePair}<{@link String}, {@link String}>
     */
    private MutablePair<String, String> getIsoltAppkeyModAndSrv(String originAppkey, String opsSoaMod, String opsSoaSrv){
        String isoltSuffix = "isolt";
        String srv = "";
        if (StringUtils.isBlank(opsSoaMod) && StringUtils.isBlank(opsSoaSrv)) {
            if (originAppkey.contains(".")) {
                if (originAppkey.split(SPLIT_CHAR).length == 4) {
                    String lastKey = originAppkey.split(SPLIT_CHAR)[3];
                    opsSoaMod = lastKey + isoltSuffix;
                    srv = lastKey + isoltSuffix;
                } else if (originAppkey.split(SPLIT_CHAR).length == 5) {
                    opsSoaMod = originAppkey.split(SPLIT_CHAR)[3];
                    srv = originAppkey.split(SPLIT_CHAR)[4] + isoltSuffix;
                }
            }
        } else {
            if (Objects.equals(opsSoaMod, opsSoaSrv)) {
                opsSoaMod = opsSoaMod + isoltSuffix;
            }
            srv = opsSoaSrv + isoltSuffix;
        }
        return MutablePair.of(opsSoaMod, srv);
    }

    private String getIsoltAppkeyName(String soaapp, String opsSoaMod, String srv) {
        String isoltAppkey;
        if (StringUtils.isNotBlank(opsSoaMod) && !Objects.equals(opsSoaMod, srv)) {
            isoltAppkey = String.format("com.sankuai.%s.%s.%s", soaapp, opsSoaMod, srv);
        } else if (StringUtils.isNotBlank(srv) && !Objects.equals(soaapp, srv)) {
            isoltAppkey = String.format("com.sankuai.%s.%s", soaapp, srv);
        } else {
            isoltAppkey = String.format("com.sankuai.%s", soaapp);
        }
        return isoltAppkey;
    }

    @Override
    public OctoRouteStrategyDTO getAppkeyOctoRouteStrategy(String appkey, EnvEnum env) throws SdkCallException, SdkBusinessErrorException {
        String effectiveHttpRouteStrategy = getEffectiveOctoRouteStrategy(appkey, env, "http");
        String effectiveThriftRouteStrategy = getEffectiveOctoRouteStrategy(appkey, env, "thrift");
        // 格式化展示服务路由策略，提醒用户注意选择合适的机房、地域机器
        Map<String, String> routeStrategyMap = ImmutableMap.of(
                "sameidc", "同机房优先",
                "samecity", "同城市优先",
                "idc_optimize", "同机房优先",
                "region_optimize", "同城市优先");
        List<String> msgList = new ArrayList<>();
        if (StringUtils.isNotBlank(effectiveHttpRouteStrategy)){
            msgList.add(String.format("Http协议 [%s]",
                    String.format("'<font color=\"#FF0000\">%s</font>'", routeStrategyMap.getOrDefault(effectiveHttpRouteStrategy, effectiveHttpRouteStrategy))));
        }
        if (StringUtils.isNotBlank(effectiveThriftRouteStrategy)) {
            msgList.add(String.format("Thrift协议 [%s]",
                    String.format("'<font color=\"#FF0000\">%s</font>'", routeStrategyMap.getOrDefault(effectiveThriftRouteStrategy, effectiveThriftRouteStrategy))));
        }
        String octoLink = DisplayUtils.formatHrefLink(String.format("https://octo.mws.sankuai.com/service-group?appkey=%s&env=%s",
                appkey, env.getName()), "OCTO服务分组");
        String context = "";
        if (CollectionUtils.isNotEmpty(msgList)){
            String displayMsg = String.join(",", msgList);
            context = String.format("当前服务路由策略: %s, 服务扩容请尽量按照路由策略选择对应的机房, %s", displayMsg, octoLink);
        }
        return OctoRouteStrategyDTO.builder()
                .http(effectiveHttpRouteStrategy)
                .thrift(effectiveThriftRouteStrategy)
                .context(context)
                .build();
    }

    /**
     * 获取正在生效中的OCTO服务分组路由策略
     *
     * @param appkey appkey
     * @param env    env
     * @param type   类型：http、thrift
     * @return {@link String}
     */
    private String getEffectiveOctoRouteStrategy(String appkey, EnvEnum env, String type) {
        Integer effectiveFlagNum = 1;
        List<OctoProviderGroupBO> octoProviderGroupBOList = octoResource.getOctoProviderGroup(appkey, env, type);
        String effectiveOctoRouteStrategy = octoProviderGroupBOList.stream()
                .filter(i -> Objects.equals(i.getStatus(), effectiveFlagNum))
                .map(OctoProviderGroupBO::getName)
                .collect(Collectors.joining(","));
        return StringUtils.isNotBlank(effectiveOctoRouteStrategy) ? effectiveOctoRouteStrategy : "";
    }

    @Override
    public AppkeyBillingUnitDTO getAppkeyUnitList(String appkey) throws SdkCallException, SdkBusinessErrorException {
        BillingUnitBO billingUnitBO = appkeyResource.getAppkeyUnitList(appkey);
        return AppkeyDTOTransfer.INSTANCE.toAppkeyBillingUnitDTO(billingUnitBO);
    }
}
