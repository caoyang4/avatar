package com.sankuai.avatar.web.controller;

import com.jayway.jsonpath.TypeRef;
import com.meituan.mdp.boot.starter.config.annotation.MdpConfig;
import com.sankuai.avatar.common.utils.JsonUtil;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.web.dto.user.UserDTO;
import com.sankuai.avatar.web.exception.SupportErrorException;
import com.sankuai.avatar.web.request.UserPageRequest;
import com.sankuai.avatar.web.service.UserRelationService;
import com.sankuai.avatar.web.service.UserService;
import com.sankuai.avatar.web.transfer.user.OrgUserVOTransfer;
import com.sankuai.avatar.web.transfer.user.UserVOTransfer;
import com.sankuai.avatar.web.util.UserUtils;
import com.sankuai.avatar.web.vo.user.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.IOException;
import java.util.*;

/**
 * @author Jie
 */
@Validated
@RestController
@RequestMapping("/api/v2/avatar/user")
public class UserController {


    /**
     * 特定业务开放SRE白名单
     */
    @MdpConfig("BUSINESS_SRE_GROUP")
    private String businessSre;

    private final UserService userService;
    private final UserRelationService relationService;

    @Autowired
    public UserController(UserService userService,
                          UserRelationService relationService) {
        this.userService = userService;
        this.relationService = relationService;
    }

    @GetMapping("/error")
    public Object getError() throws Exception {
        throw new IOException("This is IOException.");
    }

    @GetMapping("/me")
    public UserVO loginUser(){
        if (Objects.isNull(UserUtils.getCurrentCasUser())) {
            throw new SupportErrorException("未能获取登录用户信息!");
        }
        String loginName = UserUtils.getCurrentCasUser().getLoginName();
        List<UserDTO> userDTOList = userService.queryUserByMis(Collections.singletonList(loginName));
        if (CollectionUtils.isEmpty(userDTOList)) {
            throw new SupportErrorException("未能获取登录用户信息!");
        }
        return UserVOTransfer.INSTANCE.toVO(userDTOList.get(0));
    }

    /**
     * 登录用户信息异步注册，异步更新登录时间
     *
     * @return {@link UserLoginVO}
     */
    @GetMapping("/my")
    public UserLoginVO login(){
        if (Objects.isNull(UserUtils.getCurrentCasUser())) {
            throw new SupportErrorException("未能获取登录用户信息!");
        }
        String loginName = UserUtils.getCurrentCasUser().getLoginName();
        List<UserDTO> dtoList = userService.queryUserByMisNoOrder(Collections.singletonList(loginName));
        UserDTO userDTO = null;
        if (CollectionUtils.isNotEmpty(dtoList)) {
            userDTO = dtoList.get(0);
            // 异步更新登录时间
            userService.updateDBUser(userDTO);
        } else {
            userDTO = userService.getDxUserByClient(loginName);
        }
        if (Objects.nonNull(userDTO)) {
            return UserVOTransfer.INSTANCE.toLoginVO(userDTO);
        }
        return UserLoginVO.builder()
                .loginName(loginName)
                .name(loginName)
                .avatarUrl("")
                .organization("")
                .build();
    }

    /**
     * 查询单个用户
     * @param mis mis
     * @return UserVO
     */
    @GetMapping("/{mis}")
    public UserVO queryUser(@PathVariable  String mis) {
        List<UserDTO> userDTOList = userService.queryUserByCacheDbOrg(Collections.singletonList(mis));
        if (CollectionUtils.isEmpty(userDTOList)) {
            throw new SupportErrorException("用户不存在, 请核对后查询!");
        }
        UserVO userVO = UserVOTransfer.INSTANCE.toVO(userDTOList.get(0));
        userVO.setJobStatus(userService.getUserJobStatus(mis));
        return userVO;
    }

    /**
     * 分页查询用户
     *
     * @param request 查询条件
     * @return PageResponse
     */
    @GetMapping("")
    public PageResponse<UserVO> queryUser(UserPageRequest request) {
        // 无参默认查询本部门人员信息
        if (Objects.isNull(request.getId())
            && StringUtils.isEmpty(request.getOrgId())
            && StringUtils.isEmpty(request.getLoginName())
            && StringUtils.isEmpty(request.getOrgPath())
            && Objects.nonNull(UserUtils.getCurrentCasUser())
            && Boolean.TRUE.equals(request.getIsGroup())
        ) {
            String loginName = UserUtils.getCurrentCasUser().getLoginName();
            List<UserDTO> dtoList = userService.queryUserByMis(Collections.singletonList(loginName));
            if (CollectionUtils.isNotEmpty(dtoList)) {
                request.setOrgPath(dtoList.get(0).getOrgPath());
                request.setPageSize(20);
            }
        }
        if (StringUtils.isNotEmpty(request.getLoginName())) {
            // 只保留 . - _ 及中英文，数字
            String name = request.getLoginName().replaceAll("[^(\\-_.a-zA-Z0-9\\u4e00-\\u9fa5)]", "");
            request.setLoginName(name);
        }
        PageResponse<UserDTO> dtoPageResponse = userService.queryPage(request);
        PageResponse<UserVO> pageResponse = new PageResponse<>();
        pageResponse.setPage(request.getPage());
        pageResponse.setPageSize(request.getPageSize());
        pageResponse.setTotalCount(dtoPageResponse.getTotalCount());
        pageResponse.setTotalPage(dtoPageResponse.getTotalPage());
        pageResponse.setItems(UserVOTransfer.INSTANCE.toVOList(dtoPageResponse.getItems()));
        return pageResponse;
    }

    /**
     * 得到组织用户信息
     *
     * @param misList mis号，支持多个 mis 号逗号分割，批量查询
     * @return {@link List}<{@link OrgUserVO}>
     */
    @GetMapping("/info")
    public List<OrgUserVO> getOrgUserVO(@RequestParam(value = "mis", required = true)
                                        @Valid @NotBlank(message = "请指定 mis 号查询!") String misList) {
        Set<String> misSet = new HashSet<>(Arrays.asList(misList.split(",")));
        List<UserDTO> userDTOList = userService.queryUserByMisNoOrder(new ArrayList<>(misSet));
        return OrgUserVOTransfer.INSTANCE.toVOList(userDTOList);
    }

    /**
     * 置顶 appkey
     *
     * @param userTopAppkeyVO userTopAppkeyVO
     * @return {@link Boolean}
     */
    @PostMapping("/topAppkey")
    public Boolean addTopAppkey(@Valid @RequestBody UserTopAppkeyVO userTopAppkeyVO){
        if (StringUtils.isEmpty(userTopAppkeyVO.getUser())) {
            userTopAppkeyVO.setUser(UserUtils.getCurrentCasUser().getLoginName());
        }
        return relationService.saveUserTopAppkey(userTopAppkeyVO.getUser(), userTopAppkeyVO.getAppkey());
    }

    /**
     * 取消置顶 appkey
     *
     * @param userTopAppkeyVO userTopAppkeyVO
     * @return {@link Boolean}
     */
    @DeleteMapping("/topAppkey")
    public Boolean deleteTopAppkey(@Valid @RequestBody UserTopAppkeyVO userTopAppkeyVO){
        if (StringUtils.isEmpty(userTopAppkeyVO.getUser())) {
            userTopAppkeyVO.setUser(UserUtils.getCurrentCasUser().getLoginName());
        }
        return relationService.cancelUserTopAppkey(userTopAppkeyVO.getUser(), userTopAppkeyVO.getAppkey());
    }

    /**
     * 查询用户在职状态
     *
     * @param users 用户
     * @return {@link Map}<{@link String}, {@link Boolean}>
     */
    @GetMapping("/jobStatus")
    public Map<String, String> getUserStatus(@Valid @Size(min = 1, max = 50, message = "最少指定1个，最多指定50个人员")
                                              @RequestParam(value = "users") List<String> users){
        Map<String, String> map = new HashMap<>(8);
        for (String user : new HashSet<>(users)) {
            map.put(user, userService.getUserJobStatus(user));
        }
        return map;
    }

    /**
     * 用户 sre 权限
     *
     * @param mis mis
     * @return {@link Map}<{@link String}, {@link ?}>
     */
    @GetMapping("/sre")
    public UserPermissionVO isUserSre(@RequestParam(value = "mis", required = false) String mis){
        if (StringUtils.isEmpty(mis)) {
            mis = UserUtils.getCurrentCasUser().getLoginName();
        }
        if (userService.isOpsSre(mis)) {
            return UserPermissionVO.builder().sre(true).msg("您拥有SRE角色权限").build();
        }
        Map<String, List<String>> sreMap = JsonUtil.jsonPath2NestedBean(businessSre, new TypeRef<Map<String, List<String>>>() {});
        if (MapUtils.isNotEmpty(sreMap)) {
            Set<String> sreSet = new HashSet<>();
            sreMap.values().forEach(sreSet::addAll);
            if (sreSet.contains(mis)) {
                return UserPermissionVO.builder().sre(true).msg("您拥有特定业务开放SRE权限").build();
            }
        }
        return UserPermissionVO.builder().sre(false).msg("您无SRE角色权限").build();
    }

}
