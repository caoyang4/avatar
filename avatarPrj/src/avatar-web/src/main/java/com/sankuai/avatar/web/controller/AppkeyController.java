package com.sankuai.avatar.web.controller;

import com.google.common.collect.ImmutableMap;
import com.meituan.mdp.boot.starter.config.annotation.MdpConfig;
import com.sankuai.avatar.client.http.core.EnvEnum;
import com.sankuai.avatar.common.utils.JsonUtil;
import com.sankuai.avatar.common.utils.ObjectUtils;
import com.sankuai.avatar.common.utils.PageHelperUtils;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.web.dto.appkey.*;
import com.sankuai.avatar.web.dto.octo.OctoRouteStrategyDTO;
import com.sankuai.avatar.web.dto.tree.AppkeyTreeSrvDTO;
import com.sankuai.avatar.web.dto.tree.AppkeyTreeSrvDetailResponseDTO;
import com.sankuai.avatar.web.dto.user.UserDTO;
import com.sankuai.avatar.web.exception.SupportErrorException;
import com.sankuai.avatar.web.request.AppkeyQueryPageRequest;
import com.sankuai.avatar.web.request.AppkeySearchPageRequest;
import com.sankuai.avatar.web.request.AppkeyTreeQueryRequest;
import com.sankuai.avatar.web.request.IsoltAppkeyPageRequest;
import com.sankuai.avatar.web.service.AppkeyService;
import com.sankuai.avatar.web.service.UserService;
import com.sankuai.avatar.web.transfer.appkey.AppkeyVOTransfer;
import com.sankuai.avatar.web.util.FileUtils;
import com.sankuai.avatar.web.util.UserUtils;
import com.sankuai.avatar.web.vo.appkey.*;
import com.sankuai.avatar.web.vo.whitelist.WhitelistAppVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author caoyang
 * @create 2022-12-14 14:59
 */
@Slf4j
@RestController
@RequestMapping("/api/v2/avatar/appkey")
public class AppkeyController {

    private final AppkeyService appkeyService;
    private final UserService userService;

    @Autowired
    public AppkeyController(AppkeyService appkeyService,
                            UserService userService) {
        this.appkeyService = appkeyService;
        this.userService = userService;
    }

    /**
     * OPS系统数据源切换开关
     */
    @MdpConfig("OPS_DATA_SOURCE_SWITCH:true")
    private Boolean opsDataSourceSwitch;

    /**
     * SC系统数据源切换开关
     */
    @MdpConfig("SC_DATA_SOURCE_SWITCH:true")
    private Boolean scDataSourceSwitch;

    /**
     * 编辑服务时，预装软件显示
     */
    @MdpConfig("UPDATE_SRV_SOFT_MAP:{}")
    private String updateSrvSoftMap;

    /**
     * 新增服务时，预装软件显示
     */
    @MdpConfig("SRV_SOFT_MAP:{}")
    private String srvSoftMap;

    /**
     * 服务状态原因
     */
    @MdpConfig("STATE_REASON:{\n" +
            "  \"stateful\": [\n" +
            "    \"未使用公司基础组件自建的存储服务（DB、ZK、ES等）\",\n" +
            "    \"本地存储持久化数据\",\n" +
            "    \"服务强依赖主机名或IP\",\n" +
            "    \"自研系统，当前需手动部署安装软件或配置\",\n" +
            "    \"外采系统，或者第三方服务无法自动化部署\",\n" +
            "    \"前置机/加密机\",\n" +
            "    \"其他原因，请补充填写\"\n" +
            "  ],\n" +
            "  \"noStateful\": [\n" +
            "    \"资源腾挪类服务\",\n" +
            "    \"服务自建高可用，支持自动迁移\",\n" +
            "    \"测试类服务，不对外提供接口\",\n" +
            "    \"演练环境服务，不对外提供接口\",\n" +
            "    \"前端类服务，不存储任何数据\",\n" +
            "    \"其他原因，请补充填写\"\n" +
            "  ]\n" +
            "}")
    private String stateReason;

    /**
     * 通过appkey获取服务详情
     *
     * @param appkey appkey
     * @return {@link AppkeyCollectVO}
     */
    @GetMapping("/{appkey}/detail")
    public AppkeyDetailVO getAppkeyDetailInfo(@PathVariable("appkey") @Valid @NotBlank String appkey){
        String realAppkey = appkey.trim();
        AppkeyDetailVO appkeyDetailVO;
        if (Boolean.TRUE.equals(opsDataSourceSwitch)) {
            appkeyDetailVO = AppkeyVOTransfer.INSTANCE.toAppkeyDetailVO(appkeyService.getAppkeyDetailInfoByHttpClient(realAppkey));
        } else {
            appkeyDetailVO = AppkeyVOTransfer.INSTANCE.toAppkeyDetailVO(appkeyService.getAppkeyDetailByRepository(realAppkey));
        }
        appkeyDetailVO.setAppkey(realAppkey);
        return appkeyDetailVO;
    }

    /**
     * 服务树-全部服务-下载
     * TODO 多线程按页码(每次100个)依次查询，聚合结果后下载
     *
     * @param request 请求
     */
    @GetMapping("/download")
    public void download(HttpServletResponse response, @Valid AppkeyTreeQueryRequest request){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String adminFormat = "%s:%s";
        PageResponse<AppkeyVO> userRelatedAppkeyVOPageResponse = getAllAppkey(request);
        List<AppkeyDownloadVO> appkeyDownloadVOList = userRelatedAppkeyVOPageResponse.getItems()
                .stream().map(userRelatedAppkey -> AppkeyDownloadVO.builder()
                    .appkey(userRelatedAppkey.getAppkey())
                    .comment(userRelatedAppkey.getComment())
                    .hostCount(userRelatedAppkey.getHostCount())
                    .rank(userRelatedAppkey.getRank())
                    .capacity(userRelatedAppkey.getCapacity())
                    .isSet(Boolean.TRUE.equals(userRelatedAppkey.getIsSet()) ? "是" : "否")
                    .isLiteset(Boolean.TRUE.equals(userRelatedAppkey.getIsLiteset()) ? "是" : "否")
                    .stateful(Boolean.TRUE.equals(userRelatedAppkey.getStateful()) ? "有状态" : "无状态")
                    .rdAdmin(userRelatedAppkey.getRdAdmin().stream()
                            .map(i -> String.format(adminFormat, i.getName(), i.getLoginName())).collect(Collectors.joining(",")))
                    .epAdmin(userRelatedAppkey.getRdAdmin().stream()
                            .map(i -> String.format(adminFormat, i.getName(), i.getLoginName())).collect(Collectors.joining(",")))
                    .opAdmin(userRelatedAppkey.getRdAdmin().stream()
                            .map(i -> String.format(adminFormat, i.getName(), i.getLoginName())).collect(Collectors.joining(",")))
                    .createAt(sdf.format(userRelatedAppkey.getCreateAt()))
                    .build())
                .collect(Collectors.toList());
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        FileUtils.downloadCsv(response, String.format("appkey_%s.csv", dateFormat.format(new Date())),
                new String[]{"appkey", "comment", "capacity", "hostCount", "rank", "stateful", "set", "liteSet", "rdAdmin", "epAdmin", "opAdmin", "createAt"},
                new String[]{"appkey", "描述", "容灾等级", "服务器数", "服务等级", "服务状态", "Set", "liteSet", "服务负责人", "测试负责人", "运维负责人", "上线时间"}
                , appkeyDownloadVOList);
    }

    /**
     * 首页：获取用户相关appkey
     *
     * @param request 我的服务查询请求参数
     * @param source  临时控制，支持单个请求切换数据源，上线后观察调试，确认信息无误后去除
     * @return {@link PageResponse}<{@link AppkeyVO}>
     */
    @GetMapping("/home/mine")
    public PageResponse<AppkeyHomeVO> getHomeUserRelatedAppkey(
            @RequestParam(value = "source", required = false, defaultValue = "") String source,
            @Valid AppkeyTreeQueryRequest request){
        PageResponse<AppkeyVO> pageResponseVO = getUserRelatedAppkey(source, request);
        PageResponse<AppkeyHomeVO> appkeyHomeVOPageResponse = AppkeyVOTransfer.INSTANCE.toAppkeyHomeVO(pageResponseVO);
        // 展示服务置顶标识
        List<String> topAppkeys = appkeyService.getUserTopAppkey(request.getUser());
        for (AppkeyHomeVO appkeyHomeVO: appkeyHomeVOPageResponse.getItems()) {
            appkeyHomeVO.setTop(topAppkeys.contains(appkeyHomeVO.getAppkey()));
        }
        return appkeyHomeVOPageResponse;
    }

    /**
     * 首页：获取Appkey待执行、执行中流程信息
     *
     * @param appkeyList 服务(逗号分隔)
     * @return {@link PageResponse}<{@link AppkeyVO}>
     */
    @GetMapping("/flow")
    public PageResponse<AppkeyFlowVO> getAppkeyFlow(@RequestParam(value = "appkeys")
                                                        @Valid @NotBlank(message = "请指定appkey查询!") String appkeyList){
        PageResponse<AppkeyFlowDTO> appkeyFlowDTOPageResponse = appkeyService.batchGetAppkeyRunningAndHoldingFlowList(
                Arrays.asList(appkeyList.split(",")));
        return AppkeyVOTransfer.INSTANCE.toAppkeyFlowVO(appkeyFlowDTOPageResponse);
    }

    /**
     * 获取用户相关appkey
     *
     * @param request 我的服务查询请求参数
     * @param source  临时控制，支持单个请求切换数据源，上线后观察调试，确认信息无误后去除
     * @return {@link PageResponse}<{@link AppkeyVO}>
     */
    @GetMapping("/mine")
    public PageResponse<AppkeyVO> getUserRelatedAppkey(
            @RequestParam(value = "source", required = false, defaultValue = "") String source,
            @Valid AppkeyTreeQueryRequest request){
        Integer page = request.getPage();
        Integer pageSize = request.getPageSize();
        if (StringUtils.isBlank(request.getUser())){
            request.setUser(UserUtils.getCurrentCasUser().getLoginName());
        }
        PageResponse<AppkeyVO> pageResponseVO = new PageResponse<>();
        // 二处数据源:ES(优先)或第三方OPS查询,可通过Lion开关进行切换
        List<AppkeyVO> userRelatedAppkeyList;
        if (Boolean.TRUE.equals(opsDataSourceSwitch) || Objects.equals("external", source)){
            request.setType(StringUtils.isNotBlank(request.getType()) ? request.getType() : "mine");
            List<AppkeyTreeSrvDTO> appkeyTreeSrvDetailDTOList = appkeyService.getMineAppkeysByHttpClient(request);
            userRelatedAppkeyList = AppkeyVOTransfer.INSTANCE.batchToAppkeyVO(appkeyTreeSrvDetailDTOList);
            return PageHelperUtils.toPageResponse(page, pageSize, userRelatedAppkeyList);
        } else {
            PageResponse<AppkeyDTO> pageResponse = appkeyService.getMineAppkeysByRepository(request);
            userRelatedAppkeyList = AppkeyVOTransfer.INSTANCE.toRelatedAppkeyVOList(pageResponse.getItems());
            polishUserRelatedAppkey(userRelatedAppkeyList);
            pageResponseVO.setPage(page);
            pageResponseVO.setPageSize(pageSize);
            pageResponseVO.setTotalPage(pageResponse.getTotalPage());
            pageResponseVO.setTotalCount(pageResponse.getTotalCount());
            pageResponseVO.setItems(userRelatedAppkeyList);
        }
        return pageResponseVO;
    }

    private void polishUserRelatedAppkey(List<AppkeyVO> userRelatedAppkeyList) {
        List<String> users = new ArrayList<>();
        for (AppkeyVO appkeyVO : userRelatedAppkeyList) {
            Arrays.asList(appkeyVO.getRdAdmin(), appkeyVO.getEpAdmin(), appkeyVO.getOpAdmin()).forEach(
                    admins -> users.addAll(admins.stream().map(AppkeyUserSimpleVO::getLoginName).distinct().collect(Collectors.toList()))
            );
        }
        List<UserDTO> userDTOList = userService.queryUserByMisNoOrder(new ArrayList<>(new HashSet<>(users)));
        Map<String, String> userMap = userDTOList.stream()
                .filter(userDTO -> StringUtils.isNotEmpty(userDTO.getMis()) && StringUtils.isNotEmpty(userDTO.getName()))
                .collect(Collectors.toMap(UserDTO::getMis, UserDTO::getName));
        for (AppkeyVO appkeyVO : userRelatedAppkeyList) {
            Arrays.asList(appkeyVO.getRdAdmin(), appkeyVO.getEpAdmin(), appkeyVO.getOpAdmin()).forEach(
                    admins -> admins.forEach(admin -> admin.setName(
                            MapUtils.isEmpty(userMap)
                                    ? admin.getLoginName()
                                    : userMap.getOrDefault(admin.getLoginName(), admin.getLoginName())
                            )
                    )
            );
        }
    }

    /**
     * 获取用户关注appkey
     *
     * @param request 页面请求
     * @return {@link PageResponse}<{@link AppkeyVO}>
     */
    @GetMapping("/favor")
    public PageResponse<AppkeyVO> getFavorPage(@Valid AppkeyTreeQueryRequest request){
        if (StringUtils.isBlank(request.getUser())){
            request.setUser(UserUtils.getCurrentCasUser().getLoginName());
        }
        request.setType("favor");
        // OPS接口实时查询，不支持从存储端获取
        AppkeyTreeSrvDetailResponseDTO srvDetailResponseDTO = appkeyService.getOpsAppkeysByHttpClient(request);
        List<AppkeyVO> appkeyVOList = AppkeyVOTransfer.INSTANCE.batchToAppkeyVO(srvDetailResponseDTO.getSrvs());
        return PageHelperUtils.toPageResponse(request.getPage(), request.getPageSize(), srvDetailResponseDTO.getTotal(), appkeyVOList);
    }

    /**
     * 判断用户是否关注指定appkey
     *
     * @param appkey appkey
     * @param mis    用户MIS号
     * @return {@link Boolean}
     */
    @GetMapping("/{appkey}/favor")
    public Boolean getAppkeyFavor(@PathVariable("appkey") @Valid @NotBlank String appkey,
                                  @RequestParam(value = "mis", required = false, defaultValue = "") String mis){
        String realAppkey = appkey.trim();
        if (StringUtils.isBlank(realAppkey)) {
            return Boolean.FALSE;
        }
        String user = StringUtils.isNotBlank(mis) ? mis : UserUtils.getCurrentCasUser().getLoginName();
        return appkeyService.isUserFavorAppkey(user, realAppkey);
    }

    /**
     * 服务树-全部服务
     *
     * @param request 请求
     * @return {@link PageResponse}<{@link AppkeyVO}>
     */
    @GetMapping("")
    public PageResponse<AppkeyVO> getAllAppkey(@Valid AppkeyTreeQueryRequest request){
        if (StringUtils.isBlank(request.getUser())){
            request.setUser(UserUtils.getCurrentCasUser().getLoginName());
        }
        PageResponse<AppkeyVO> pageResponseVO = new PageResponse<>();

        // 二处数据源,可通过Lion开关：ALL_APPKEY_SOURCE_SWITCH 进行切换
        List<AppkeyVO> appkeyVOList;
        if (Boolean.TRUE.equals(opsDataSourceSwitch)) {
            // 1、第三方OPS接口实时查询
            AppkeyTreeSrvDetailResponseDTO srvDetailResponseDTO = appkeyService.getOpsAppkeysByHttpClient(request);
            appkeyVOList = AppkeyVOTransfer.INSTANCE.batchToAppkeyVO(srvDetailResponseDTO.getSrvs());
            return PageHelperUtils.toPageResponse(request.getPage(), request.getPageSize(), srvDetailResponseDTO.getTotal(), appkeyVOList);
        } else {
            // 2、从 es 或 db 查询
            PageResponse<AppkeyDTO> pageResponse = appkeyService.getAllAppkeysByRepository(request);
            List<AppkeyVO> userRelatedAppkeyList = AppkeyVOTransfer.INSTANCE.toRelatedAppkeyVOList(pageResponse.getItems());
            polishUserRelatedAppkey(userRelatedAppkeyList);
            pageResponseVO.setPage(request.getPage());
            pageResponseVO.setPageSize(request.getPageSize());
            pageResponseVO.setTotalPage(pageResponse.getTotalPage());
            pageResponseVO.setTotalCount(pageResponse.getTotalCount());
            pageResponseVO.setItems(userRelatedAppkeyList);
        }
        return pageResponseVO;
    }

    /**
     * 通过srv获取服务详情,
     *
     * @param srv srv
     * @return {@link AppkeyCollectVO}
     */
    @GetMapping("/srv/{srv}")
    public AppkeyCollectVO getBySrv(@PathVariable("srv") @Valid @NotBlank String srv){
        return AppkeyVOTransfer.INSTANCE.toVO(appkeyService.getBySrv(srv));
    }

    /**
     * db分页查询
     *
     * @param pageRequest 页面请求
     * @return {@link PageResponse}<{@link AppkeyCollectVO}>
     */
    @GetMapping("repository")
    public PageResponse<AppkeyCollectVO> queryPage(@Valid AppkeyQueryPageRequest pageRequest){
        PageResponse<AppkeyDTO> dtoPageResponse = appkeyService.queryPage(pageRequest);
        return toVOPageResponse(dtoPageResponse);
    }

    /**
     * 搜索分页查询，无过滤条件，默认返回系统用户所属 owt 下服务
     *
     * @param pageRequest 页面请求
     * @return {@link PageResponse}<{@link AppkeyCollectVO}>
     */
    @GetMapping("/search")
    public PageResponse<AppkeyCollectVO> searchPage(@RequestParam(value = "user", required = false, defaultValue = "") String user,
                                                    @Valid AppkeySearchPageRequest pageRequest){
        int page = pageRequest.getPage();
        int pageSize = pageRequest.getPageSize();
        pageRequest.setPage(null);
        pageRequest.setPageSize(null);
        if (StringUtils.isEmpty(user) && ObjectUtils.checkObjAllFieldsIsNull(pageRequest)) {
            pageRequest.setUser(UserUtils.getCurrentCasUser().getLoginName());
        }
        pageRequest.setPage(page);
        pageRequest.setPageSize(pageSize);
        PageResponse<AppkeyDTO> dtoPageResponse = appkeyService.searchPage(pageRequest);
        return toVOPageResponse(dtoPageResponse);
    }

    /**
     * 添加关注appkey
     *
     * @param appkey appkey
     * @return {@link Boolean}
     */
    @PostMapping("/{appkey}/favor")
    public Boolean addFavorAppkey(@PathVariable("appkey") @Valid @NotBlank String appkey,
                                  @RequestParam(value = "mis", required = false, defaultValue = "") String mis){
        if (StringUtils.isEmpty(mis)) {
            mis = UserUtils.getCurrentCasUser().getLoginName();
        }
        return appkeyService.favorAppkey(appkey, mis);
    }

    /**
     * 取消关注appkey
     *
     * @param appkey appkey
     * @return {@link Boolean}
     */
    @DeleteMapping("/{appkey}/favor")
    public Boolean cancelFavorAppkey(@PathVariable("appkey") @Valid @NotBlank String appkey,
                                     @RequestParam(value = "mis", required = false, defaultValue = "") String mis){
        if (StringUtils.isEmpty(mis)) {
            mis = UserUtils.getCurrentCasUser().getLoginName();
        }
        return appkeyService.cancelFavorAppkey(appkey, mis);
    }


    private PageResponse<AppkeyCollectVO> toVOPageResponse(PageResponse<AppkeyDTO> dtoPageResponse){
        PageResponse<AppkeyCollectVO> voPageResponse = new PageResponse<>();
        voPageResponse.setPage(dtoPageResponse.getPage());
        voPageResponse.setPageSize(dtoPageResponse.getPageSize());
        voPageResponse.setTotalCount(dtoPageResponse.getTotalCount());
        voPageResponse.setTotalPage(dtoPageResponse.getTotalPage());
        voPageResponse.setItems(AppkeyVOTransfer.INSTANCE.toVOList(dtoPageResponse.getItems()));
        return voPageResponse;
    }

    /**
     * 根据机器、vip、appkey 名称搜索返回服务列表
     *
     * @param query 查询
     * @return {@link List}<{@link String}>
     */
    @GetMapping("/query")
    public List<String> getAppkeys(@RequestParam(value = "query", required = false, defaultValue = "") String query){
        String queryString = query.trim();
        if (StringUtils.isEmpty(queryString)) {
            return Collections.emptyList();
        }
        // 1、通过机器名称、ip、物理机 SN 搜索
        String appkey = appkeyService.getByHost(queryString);
        if (StringUtils.isNotEmpty(appkey)) {
            return Collections.singletonList(appkey);
        }
        // 2、负载均衡 VIP 搜索服务
        List<String> appkeys = appkeyService.getByVip(queryString);
        if (CollectionUtils.isNotEmpty(appkeys)) {
            return appkeys;
        }
        // 3、通过 appkey 名称前缀搜索
        if (Boolean.TRUE.equals(opsDataSourceSwitch)) {
            // 第三方OPS接口实时查询（默认一次加载 50 条数据，增大匹配范围）
            AppkeyTreeQueryRequest request = new AppkeyTreeQueryRequest();
            request.setUser(UserUtils.getCurrentCasUser().getLoginName());
            request.setPage(1);
            request.setPageSize(50);
            request.setQuery(queryString);
            AppkeyTreeSrvDetailResponseDTO srvDetailResponseDTO = appkeyService.getOpsAppkeysByHttpClient(request);
            if (CollectionUtils.isNotEmpty(srvDetailResponseDTO.getSrvs())){
                return srvDetailResponseDTO.getSrvs().stream()
                        .map(AppkeyTreeSrvDTO::getAppkey)
                        .filter(StringUtils::isNotEmpty)
                        .distinct().collect(Collectors.toList());
            }
        } else {
            // ES查询
            AppkeySearchPageRequest esRequest = new AppkeySearchPageRequest();
            esRequest.setAppkey(queryString);
            esRequest.setPageSize(100);
            PageResponse<AppkeyDTO> dtoPageResponse = appkeyService.searchPage(esRequest);
            if (Objects.isNull(dtoPageResponse) || CollectionUtils.isEmpty(dtoPageResponse.getItems())) {
                return Collections.emptyList();
            }
            return dtoPageResponse.getItems().stream()
                    .map(AppkeyDTO::getAppkey)
                    .filter(StringUtils::isNotEmpty)
                    .distinct().collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    /**
     * 通过appkey获取服务资源利用率详情
     *
     * @param appkey appkey
     * @return {@link AppkeyResourceUtilVO}
     */
    @GetMapping("/{appkey}/utilization")
    public AppkeyResourceUtilVO getAppkeyUtilInfo(@PathVariable("appkey") @Valid @NotBlank String appkey){
        String standardInfo = String.format("<br>了解资源利用率计算方法，可<a href=%s target=\"_blank\">点击查看</a> " +
                        "<br>了解资源利用率达标计算规则，可<a href=%s target=\"_blank\">点击查看</a>",
                "https://km.sankuai.com/page/14741528", "https://km.sankuai.com/page/368687685");
        String detailUrl = String.format("'https://yuntu.sankuai.com/v3/dashboard/dashboard-66c27e86-da44-436a-bd37-cf0be96dcabe/view?config=" +
                        "{{\"dom_appkey\":\"%s\",\"env\":\"prod\",\"startDate\":\"%s\"," +
                        "\"endDate\":\"%s\",\"dom_white\":\"1\",\"dateDim\":\"DAY_DATE\"}}'", appkey,
                LocalDateTime.now().plusDays(-30).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        WhitelistAppVO whitelistAppVO = new WhitelistAppVO();
        whitelistAppVO.setCname("资源利用率");
        whitelistAppVO.setApp("utilization");
        whitelistAppVO.setDescription("申请加白后，将不参与资源利用率运营，如扩容限制、未达标推送通知等规则");

        // 获取并格式化展示资源利用率信息
        AppkeyResourceUtilDTO appkeyResourceUtilDTO = appkeyService.getAppkeyUtilization(appkey);
        AppkeyResourceUtilVO appkeyResourceUtilVO = AppkeyVOTransfer.INSTANCE.toAppkeyResourceUtilVO(appkeyResourceUtilDTO);

        appkeyResourceUtilVO.setStandardInfo(standardInfo);
        appkeyResourceUtilVO.setDetailUrl(detailUrl);
        appkeyResourceUtilVO.setWhiteApply(whitelistAppVO);
        return appkeyResourceUtilVO;
    }

    /**
     * 获取服务预装软件信息
     * 新增与编辑服务时展示列表存在不同，需要进行区分
     *
     * @param source 来源
     * @return {@link ContainerTypeVO}
     */
    @GetMapping("/containerType")
    public ContainerTypeVO getAppkeyContainerType(@RequestParam(value = "source", required = false, defaultValue = "add") String source){
        String soft = "update".equals(source) ? updateSrvSoftMap : srvSoftMap;
        ContainerTypeVO containerTypeVO = JsonUtil.json2Bean(soft, ContainerTypeVO.class);
        if (containerTypeVO == null || containerTypeVO.getItems() == null) {
            return ContainerTypeVO.builder().defaultType("Code").items(new ArrayList<>()).build();
        }
        containerTypeVO.setDefaultType("Code");
        return containerTypeVO;
    }

    /**
     * 获取服务状态原因
     *
     * @param state 状态
     * @return {@link List}<{@link String}>
     */
    @GetMapping("/stateReason")
    public List<String> getAppkeyStateReason(@RequestParam(value = "state", required = false, defaultValue = "stateful") String state){
        StateReasonVO stateReasonVO = JsonUtil.json2Bean(stateReason, StateReasonVO.class);
        return "stateful".equals(state) ? stateReasonVO.getStateful() : stateReasonVO.getNoStateful();
    }

    /**
     * 查询演练Appkey
     *
     * @param request 请求
     * @return {@link PageResponse}<{@link String}>
     */
    @GetMapping("/isolt")
    public PageResponse<IsoltAppkeyVO> getIsoltAppkey(@Valid IsoltAppkeyPageRequest request){
        if (Objects.equals("mine", request.getType())){
            request.setMis(UserUtils.getCurrentCasUser().getLoginName());
        }
        PageResponse<IsoltAppkeyVO> pageResponseVO = new PageResponse<>();
        // 二处数据源:ES(优先)或第三方SC查询,可通过Lion开关进行切换
        if (Boolean.TRUE.equals(scDataSourceSwitch)){
            PageResponse<IsoltAppkeyDTO> appkeyDTOPageResponse = appkeyService.getIsoltAppkeysByHttpClient(request);
            pageResponseVO = AppkeyVOTransfer.INSTANCE.toIsoltAppkeyVO(appkeyDTOPageResponse);
        }
        return pageResponseVO;
    }

    /**
     * 由基础Appkey生成演练Appkey名称
     *
     * @param originAppkey 基础Appkey名称
     * @return {@link IsoltAppkeyGenerateDisplayVO}
     */
    @GetMapping("/isolt/generate")
    public IsoltAppkeyGenerateDisplayVO generateIsoltAppkey(@RequestParam(value = "originAppkey", required = false, defaultValue = "") String originAppkey,
                                                            @RequestParam(value = "soamod", required = false, defaultValue = "") String soamod,
                                                            @RequestParam(value = "soasrv", required = false, defaultValue = "") String soasrv){
        if (StringUtils.isBlank(originAppkey)){
            return IsoltAppkeyGenerateDisplayVO.builder().isoltAppkeyName("").msg("").build();
        }
        IsoltAppkeyGenerateDisplayDTO isoltAppkeyGenerateDisplayDTO = appkeyService.generateIsoltAppkeyName(originAppkey, soamod, soasrv);
        return AppkeyVOTransfer.INSTANCE.toIsoltAppkeyGenerateDisplayVO(isoltAppkeyGenerateDisplayDTO);
    }

    /**
     * 服务接入弹性提示
     *
     * @return {@link ElasticTipVO}
     */
    @GetMapping("/elastic/tips")
    public ElasticTipVO getElasticTips(){
        ElasticTipDTO elasticTipDTO = appkeyService.getElasticTips();
        return AppkeyVOTransfer.INSTANCE.toElasticTipVO(elasticTipDTO);
    }

    /**
     * 部分OWT开启弹性灰度
     *
     * @param owt owt
     * @return {@link Map}<{@link String}, {@link Boolean}>
     */
    @GetMapping("/elastic/gray")
    public Map<String, Boolean> getElasticGrayScale(@RequestParam(value = "owt", required = false, defaultValue = "") String owt){
        Boolean gray = appkeyService.getElasticGrayScale(owt);
        return ImmutableMap.of("gray", gray);
    }

    /**
     * 查询服务路由分组: 给出扩容时机房选择机房参考，尽量符合分组策略
     *
     * @param appkey 服务名
     * @param env    环境
     * @return {@link AppkeyRouteStrategyVO}
     */
    @GetMapping("/{appkey}/routeStrategy")
    public AppkeyRouteStrategyVO getAppkeyOctoRouteStrategy(@PathVariable("appkey") @Valid @NotBlank String appkey,
                                                            @RequestParam(value = "env", required = false, defaultValue = "prod") String env) {
        if (!Arrays.asList("prod", "staging", "test", "dev").contains(env)) {
            throw new SupportErrorException(String.format("env参数异常 %s", env));
        }
        OctoRouteStrategyDTO octoRouteStrategyDTO = appkeyService.getAppkeyOctoRouteStrategy(appkey, EnvEnum.valueOf(env.toUpperCase()));
        return AppkeyVOTransfer.INSTANCE.toAppkeyRouteStrategyVO(octoRouteStrategyDTO);
    }

    /**
     * 查询服务的结算单元信息
     *
     * @param appkey 服务名
     * @return {@link AppkeyBillingUnitVO}
     */
    @GetMapping("/{appkey}/billingUnit")
    public AppkeyBillingUnitVO getAppkeyBillingUnit(@PathVariable("appkey") @NotBlank String appkey) {
        AppkeyBillingUnitDTO appkeyBillingUnitDTO = appkeyService.getAppkeyUnitList(appkey);
        return AppkeyVOTransfer.INSTANCE.toAppkeyBillingUnitVO(appkeyBillingUnitDTO);
    }
}
