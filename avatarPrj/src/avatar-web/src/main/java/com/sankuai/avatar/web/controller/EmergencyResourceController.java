package com.sankuai.avatar.web.controller;

import com.meituan.mdp.boot.starter.config.annotation.MdpConfig;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.emergency.constant.OperationType;
import com.sankuai.avatar.web.dto.emergency.EmergencyResourceDTO;
import com.sankuai.avatar.web.dto.user.UserDTO;
import com.sankuai.avatar.web.request.EmergencyResourcePageRequest;
import com.sankuai.avatar.web.service.EmergencyService;
import com.sankuai.avatar.web.service.UserService;
import com.sankuai.avatar.web.transfer.emergency.EmergencyHostVOTransfer;
import com.sankuai.avatar.web.transfer.emergency.EmergencyResourceVOTransfer;
import com.sankuai.avatar.web.util.UserUtils;
import com.sankuai.avatar.web.vo.emergency.EmergencyOfflineVO;
import com.sankuai.avatar.web.vo.emergency.EmergencyOnlineVO;
import com.sankuai.avatar.web.vo.emergency.EmergencyResourceVO;
import com.sankuai.avatar.web.vo.user.UserPermissionVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author caoyang
 */
@RestController
@RequestMapping("/api/v2/avatar/emergency_resource")
public class EmergencyResourceController {

    private final EmergencyService emergencyService;
    private final UserService userService;

    /**
     * 资源池是否放开的开关
     */
    @MdpConfig("UNIT_PERMISSION_SWITCH:true")
    private Boolean unitSwitch;

    /**
     * 资源池部门权限白名单
     */
    @MdpConfig("UNIT_PERMISSION_BGS:[]")
    private String[] unitBgs;

    /**
     * 资源池人员权限白名单
     */
    @MdpConfig("UNIT_PERMISSION_USERS:[]")
    private String[] unitUsers;

    /**
     * avatar 团队
     */
    @MdpConfig("AVATAR_TEAM:['jie.li.sh']")
    private String[] avatarUsers;

    @Autowired
    public EmergencyResourceController(EmergencyService emergencyService,
                                       UserService userService) {
        this.emergencyService = emergencyService;
        this.userService = userService;
    }

    @GetMapping("/online")
    PageResponse<EmergencyOnlineVO> getPageEmergencyOnline(EmergencyResourcePageRequest pageRequest){
        PageResponse<EmergencyOnlineVO> pageResponse = new PageResponse<>();
        pageRequest.setOperationType(OperationType.ECS_ONLINE);
        PageResponse<EmergencyResourceDTO> dtoPageResponse = emergencyService.queryPage(pageRequest);
        pageResponse.setPage(dtoPageResponse.getPage());
        pageResponse.setPageSize(dtoPageResponse.getPageSize());
        pageResponse.setTotalCount(dtoPageResponse.getTotalCount());
        pageResponse.setTotalPage(dtoPageResponse.getTotalPage());
        pageResponse.setItems(EmergencyHostVOTransfer.INSTANCE.toEmergencyOnlineVOList(dtoPageResponse.getItems()));
        return pageResponse;
    }

    @GetMapping("/offline")
    PageResponse<EmergencyOfflineVO> getPageEmergencyOffline(EmergencyResourcePageRequest pageRequest){
        PageResponse<EmergencyOfflineVO> pageResponse = new PageResponse<>();
        pageRequest.setOperationType(OperationType.ECS_OFFLINE);
        PageResponse<EmergencyResourceDTO> dtoPageResponse = emergencyService.queryPage(pageRequest);
        pageResponse.setPage(dtoPageResponse.getPage());
        pageResponse.setPageSize(dtoPageResponse.getPageSize());
        pageResponse.setTotalCount(dtoPageResponse.getTotalCount());
        pageResponse.setTotalPage(dtoPageResponse.getTotalPage());
        pageResponse.setItems(EmergencyHostVOTransfer.INSTANCE.toEmergencyOfflineVOList(dtoPageResponse.getItems()));
        return pageResponse;
    }

    @DeleteMapping("/{id}")
    public Boolean deleteEmergencyResource(@PathVariable Integer id){
        return emergencyService.deleteEmergencyResourceByPk(id);
    }

    @PostMapping("")
    public Boolean saveEmergencyResource(@Valid @RequestBody EmergencyResourceVO emergencyResourceVO){
        return emergencyService.saveEmergencyResource(EmergencyResourceVOTransfer.INSTANCE.toDTO(emergencyResourceVO));
    }

    /**
     * 用户资源池操作权限
     *
     * @param mis 管理信息系统
     * @return {@link Map}<{@link String}, {@link ?}>
     */
    @GetMapping("/permission")
    public UserPermissionVO isUnitSre(@RequestParam(value = "mis", required = false) String mis) {
        if (!Boolean.TRUE.equals(unitSwitch)) {
            return UserPermissionVO.builder().sre(false).msg("该功能暂未开放，如有疑问，请联系 avatar 管理员").build();
        }
        if (StringUtils.isEmpty(mis)) {
            mis = UserUtils.getCurrentCasUser().getLoginName();
        }
        List<UserDTO> dtoList = userService.queryUserByCacheDbOrg(Collections.singletonList(mis));
        if (CollectionUtils.isEmpty(dtoList)) {
            return UserPermissionVO.builder().sre(false).msg("用户不存在").build();
        }
        // 部门白名单
        String orgName = dtoList.get(0).getOrganization();
        if (StringUtils.isNotEmpty(orgName) && Arrays.stream(unitBgs).anyMatch(orgName::contains)) {
            return UserPermissionVO.builder().sre(true).msg("您拥有SRE角色权限").build();
        }
        // avatar团队
        if (Arrays.asList(avatarUsers).contains(mis) || Arrays.asList(unitUsers).contains(mis)) {
            return UserPermissionVO.builder().sre(true).msg("您拥有SRE角色权限").build();
        }
        // ops sre
        if (userService.isOpsSre(mis)) {
            return UserPermissionVO.builder().sre(true).msg("您拥有SRE角色权限").build();
        }
        return UserPermissionVO.builder().sre(false).msg("您无SRE角色权限").build();
    }

}
