package com.sankuai.avatar.web.controller;

import com.meituan.mdp.boot.starter.config.annotation.MdpConfig;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.web.dto.application.ApplicationDTO;
import com.sankuai.avatar.web.dto.application.ApplicationDetailDTO;
import com.sankuai.avatar.web.dto.application.ScQueryApplicationDTO;
import com.sankuai.avatar.web.dto.application.UserOwnerApplicationDTO;
import com.sankuai.avatar.web.dto.user.UserDTO;
import com.sankuai.avatar.web.request.application.ApplicationPageRequestDTO;
import com.sankuai.avatar.web.request.application.ApplicationPageRequestVO;
import com.sankuai.avatar.web.service.ApplicationService;
import com.sankuai.avatar.web.service.UserService;
import com.sankuai.avatar.web.transfer.application.ApplicationDetailTransfer;
import com.sankuai.avatar.web.transfer.application.ApplicationPageRequestTransfer;
import com.sankuai.avatar.web.transfer.application.ApplicationPageResponseTransfer;
import com.sankuai.avatar.web.util.UserUtils;
import com.sankuai.avatar.web.vo.application.*;
import com.sankuai.avatar.web.vo.orgtree.OrgTreeUserVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Application的Contorller类
 *
 * @author zhangxiaoning07
 * @create 2022/11/21
 **/
@RestController
@RequestMapping("/api/v2/avatar/application")
public class ApplicationController {

    private final ApplicationService applicationService;

    private final UserService userService;

    @MdpConfig("CAPACITY_WHITE_APPLICATION:[]")
    private String[] whiteApplications;

    /**
     * 超过该数目的服务的应用不展示Away Team
     */
    private static final Integer APPLICATION_APPKEY_COUNT_LIMIT = 1000;

    @Autowired
    public ApplicationController(ApplicationService applicationService, UserService userService) {
        this.applicationService = applicationService;
        this.userService = userService;
    }

    /**
     * 新增服务时获取(检索)应用列表：默认返回十条
     * 1、默认获取用户同组下应用
     * 2、支持检索
     *
     * @param requestVO 请求参数
     * @return {@link PageResponse}<{@link ApplicationQueryVO}>
     */
    @GetMapping("applyAppkey")
    public PageResponse<ApplicationQueryVO> getDefaultApplication(ApplicationPageRequestVO requestVO) {
        ApplicationPageRequestDTO requestDTO = ApplicationPageRequestTransfer.INSTANCE.toDTO(requestVO);
        requestDTO.setPage(requestVO.getPage());
        requestDTO.setPageSize(requestVO.getPageSize());
        PageResponse<ApplicationQueryVO> applicationVOPageResponse;

        if (StringUtils.isBlank(requestDTO.getQuery())){
            // 不检索时走SC默认接口，返回用户同组下应用
            if (StringUtils.isBlank(requestDTO.getMis())) {
                requestDTO.setMis(UserUtils.getCurrentCasUser().getLoginName());
            }
            PageResponse<UserOwnerApplicationDTO> userOwnerApplicationDTOPageResponse = applicationService.getUserOwnerApplications(requestDTO);
            applicationVOPageResponse = ApplicationPageResponseTransfer.INSTANCE.toUserOwnerApplicationVOPageResponse(userOwnerApplicationDTOPageResponse);
        } else {
            // 检索时走SC查询应用信息接口
            requestDTO.setMis("");
            PageResponse<ScQueryApplicationDTO> scQueryApplicationDTOPageResponse = applicationService.queryApplications(requestDTO);
            applicationVOPageResponse = ApplicationPageResponseTransfer.INSTANCE.toApplicationVOPageResponse(scQueryApplicationDTOPageResponse);
        }
        return applicationVOPageResponse;
    }

    /**
     * 获取应用列表方法
     *
     * @param requestVO 请求参数
     * @param type      用以标识是否为"我的应用"字段
     * @return {@link PageResponse}<{@link ApplicationVO}>
     */
    @GetMapping("")
    public PageResponse<ApplicationVO> getApplications(ApplicationPageRequestVO requestVO,
                                                       @RequestParam(value = "type", required = false) String type) {
        if (StringUtils.equals(type, "mine")) {
            requestVO.setMember(UserUtils.getCurrentCasUser().getLoginName());
        }
        ApplicationPageRequestDTO requestDTO = ApplicationPageRequestTransfer.INSTANCE.toDTO(requestVO);
        PageResponse<ApplicationDTO> dtoPageResponse = applicationService.getApplications(requestDTO);
        return ApplicationPageResponseTransfer.INSTANCE.toVO(dtoPageResponse);
    }

    /**
     * 获取应用详细信息方法
     *
     * @param name 应用名称
     * @return {@link PageResponse}<{@link ApplicationDetailVO}>
     */
    @GetMapping("/{name}")
    public ApplicationDetailVO getApplication(@PathVariable(name = "name") String name) {
        ApplicationDetailDTO applicationDetailDTO = applicationService.getApplication(name);
        ApplicationDetailVO applicationDetailVO = ApplicationDetailTransfer.INSTANCE.toVO(applicationDetailDTO);
        if (Objects.nonNull(applicationDetailVO)) {
            truncateParticipatedTeams(applicationDetailVO);
            setAvatarUrls(applicationDetailVO);
        }
        return applicationDetailVO;
    }

    /**
     * 应用展示三个Away Team && 大于 APPLICATION_APPKEY_COUNT_LIMIT 个服务的应用不展示
     *
     * @param applicationDetail 应用信息对象
     */
    private void truncateParticipatedTeams(ApplicationDetailVO applicationDetail) {
        if (applicationDetail.getAppKeyCount() < APPLICATION_APPKEY_COUNT_LIMIT) {
            List<TeamVO> conciseParticipatedTeams = applicationDetail.getParticipatedTeams().stream().limit(3).collect(Collectors.toList());
            applicationDetail.setParticipatedTeams(conciseParticipatedTeams);
        } else {
            applicationDetail.setParticipatedTeams(Collections.emptyList());
        }
    }

    /**
     * 为admin, pms, adminTeam, participatedTeams四个字段中的用户的AvatarUrl设置头像链接
     *
     * @param applicationDetail 应用信息对象
     */
    private void setAvatarUrls(ApplicationDetailVO applicationDetail) {
        Map<String, String> misMap = getMisToAvatarUrlMap(applicationDetail);
        OrgTreeUserVO admin = applicationDetail.getAdmin();
        applicationDetail.getAdmin().setAvatarUrl(misMap.getOrDefault(admin.getMis(),""));

        Stream.of(applicationDetail.getPms(), applicationDetail.getAdminTeam().getUsers()).forEach(
                users -> users.forEach(user -> user.setAvatarUrl(misMap.getOrDefault(user.getMis(),"")))
        );
        applicationDetail.getParticipatedTeams().forEach(
                teamVO -> teamVO.getUsers().forEach(user -> user.setAvatarUrl(misMap.getOrDefault(user.getMis(),"")))
        );
    }

    /**
     * 获取admin, pms, adminTeam, participatedTeams四个字段的所有mis与avatarUrl关系。一次性查询，避免多次调用。
     *
     * @param applicationDetail 应用信息对象
     * @return {@link Map}<{@link String}, {@link String}>
     */
    private Map<String, String> getMisToAvatarUrlMap(ApplicationDetailVO applicationDetail) {
        List<String> userList = new ArrayList<>();
        userList.add(applicationDetail.getAdmin().getMis());
        userList.addAll(applicationDetail.getPms().stream().map(OrgTreeUserVO::getMis).collect(Collectors.toList()));
        userList.addAll(applicationDetail.getAdminTeam().getUsers().stream().map(OrgTreeUserVO::getMis).collect(Collectors.toList()));
        userList.addAll(applicationDetail.getParticipatedTeams().stream().
                flatMap(i -> i.getUsers().stream().map(OrgTreeUserVO::getMis)).collect(Collectors.toList()));
        List<UserDTO> userDTOList = userService.queryUserByMisNoOrder(new ArrayList<>(new HashSet<>(userList)));
        return userDTOList.stream().collect(Collectors.toMap(UserDTO::getMis, UserDTO::getAvatarUrl));
    }


    @GetMapping("/{name}/isWhite")
    public ApplicationWhiteVO getApplicationWhiteVO(@PathVariable(name = "name") String name){
        ApplicationWhiteVO whiteVO = new ApplicationWhiteVO();
        whiteVO.setName(name);
        boolean isWhite = Arrays.asList(whiteApplications).contains(name.toLowerCase());
        whiteVO.setIsWhite(isWhite);
        whiteVO.setDescription(isWhite ? "该应用下新增服务，默认容灾加白。" : "");
        return whiteVO;
    }

}
