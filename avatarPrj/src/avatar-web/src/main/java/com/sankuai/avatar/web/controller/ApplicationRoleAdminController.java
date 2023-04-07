package com.sankuai.avatar.web.controller;

import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.web.dto.application.ApplicationRoleAdminDTO;
import com.sankuai.avatar.web.dto.user.DxUserDTO;
import com.sankuai.avatar.web.request.ApplicationRoleAdminPageRequest;
import com.sankuai.avatar.web.service.ApplicationRoleAdminService;
import com.sankuai.avatar.web.service.UserService;
import com.sankuai.avatar.web.transfer.application.ApplicationRoleAdminVOTransfer;
import com.sankuai.avatar.web.transfer.user.UserVOTransfer;
import com.sankuai.avatar.web.vo.application.ApplicationRoleAdminVO;
import com.sankuai.avatar.web.vo.application.ApplicationRoleUserAdminVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

/**
 * @author caoyang
 * @create 2023-01-17 10:29
 */
@Validated
@RestController
@RequestMapping("/api/v2/avatar/applicationRoleAdmin")
public class ApplicationRoleAdminController {

    private final ApplicationRoleAdminService adminService;
    private final UserService userService;

    @Autowired
    public ApplicationRoleAdminController(ApplicationRoleAdminService adminService,
                                          UserService userService) {
        this.adminService = adminService;
        this.userService = userService;
    }

    @GetMapping("")
    PageResponse<ApplicationRoleAdminVO> getPageApplicationRoleAdmin(@Valid ApplicationRoleAdminPageRequest request){
        PageResponse<ApplicationRoleAdminDTO> dtoPageResponse = adminService.getPageApplicationRoleAdmin(request);
        PageResponse<ApplicationRoleAdminVO> pageResponse = new PageResponse<>();
        pageResponse.setPage(request.getPage());
        pageResponse.setPageSize(request.getPageSize());
        pageResponse.setTotalPage(dtoPageResponse.getTotalPage());
        pageResponse.setTotalCount(dtoPageResponse.getTotalCount());
        pageResponse.setItems(ApplicationRoleAdminVOTransfer.INSTANCE.toVOList(dtoPageResponse.getItems()));
        return pageResponse;
    }

    @GetMapping("/applicationId/{applicationId}")
    ApplicationRoleUserAdminVO getByApplicationId(@PathVariable("applicationId") Integer applicationId){
        ApplicationRoleAdminDTO adminDTO = adminService.getByApplicationId(applicationId);
        return polishApplicationRoleUserAdmin(adminDTO);
    }

    @GetMapping("/applicationName/{applicationName}")
    ApplicationRoleUserAdminVO getByApplicationName(@PathVariable("applicationName") String applicationName){
        ApplicationRoleAdminDTO adminDTO = adminService.getByApplicationName(applicationName);
        return polishApplicationRoleUserAdmin(adminDTO);
    }

    private ApplicationRoleUserAdminVO polishApplicationRoleUserAdmin(ApplicationRoleAdminDTO adminDTO){
        ApplicationRoleUserAdminVO roleUserAdminVO = ApplicationRoleAdminVOTransfer.INSTANCE.toRoleVO(adminDTO);
        if (Objects.nonNull(roleUserAdminVO)) {
            String opAdmin = roleUserAdminVO.getOpRoleAdmin().getRoleUsers();
            if (StringUtils.isNotEmpty(opAdmin)) {
                List<String> users = Arrays.asList(opAdmin.split(","));
                List<DxUserDTO> dxUserDTOList = userService.getDxUserByMis(new ArrayList<>(new HashSet<>(users)));
                roleUserAdminVO.getOpRoleAdmin().setAdmins(UserVOTransfer.INSTANCE.toDxUserVOListByDto(dxUserDTOList));
            }
            String epAdmin = roleUserAdminVO.getEpRoleAdmin().getRoleUsers();
            if (StringUtils.isNotEmpty(epAdmin)) {
                List<String> users = Arrays.asList(epAdmin.split(","));
                List<DxUserDTO> dxUserDTOList = userService.getDxUserByMis(new ArrayList<>(new HashSet<>(users)));
                roleUserAdminVO.getEpRoleAdmin().setAdmins(UserVOTransfer.INSTANCE.toDxUserVOListByDto(dxUserDTOList));
            }
        }
        return roleUserAdminVO;
    }

    @PostMapping("")
    public Boolean saveApplicationRoleAdmin(@Valid @RequestBody ApplicationRoleAdminVO applicationRoleAdminVO){
        return adminService.saveApplicationRoleAdmin(ApplicationRoleAdminVOTransfer.INSTANCE.toDTO(applicationRoleAdminVO));
    }

    @DeleteMapping("/{id}")
    public Boolean deleteApplicationRoleAdminByPk(@PathVariable("id") Integer id) {
        return Objects.nonNull(id) && adminService.deleteApplicationRoleAdminByPk(id);
    }
}
