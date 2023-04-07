package com.sankuai.avatar.web.controller;

import com.sankuai.avatar.common.utils.ObjectUtils;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.orgRole.constant.OrgRoleType;
import com.sankuai.avatar.web.dto.orgRole.DxGroupDTO;
import com.sankuai.avatar.web.dto.orgRole.OrgRoleAdminDTO;
import com.sankuai.avatar.web.dto.orgRole.OrgSreTreeDTO;
import com.sankuai.avatar.web.dto.user.UserDTO;
import com.sankuai.avatar.web.exception.SupportErrorException;
import com.sankuai.avatar.web.request.OrgRoleAdminPageRequest;
import com.sankuai.avatar.web.service.OrgRoleAdminService;
import com.sankuai.avatar.web.service.UserService;
import com.sankuai.avatar.web.transfer.orgRole.DxGroupVOTransfer;
import com.sankuai.avatar.web.transfer.orgRole.OrgRoleAdminVOTransfer;
import com.sankuai.avatar.web.transfer.orgRole.OrgSreTreeVOTransfer;
import com.sankuai.avatar.web.transfer.user.UserVOTransfer;
import com.sankuai.avatar.web.util.UserUtils;
import com.sankuai.avatar.web.vo.orgRole.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Jie.li.sh
 * @create 2020-03-18
 **/
@Slf4j
@Validated
@RestController
@RequestMapping("/api/v2/avatar/orgRoleAdmin")
public class OrgRoleAdminController {

    private final OrgRoleAdminService orgRoleAdminService;

    private final UserService userService;

    @Autowired
    public OrgRoleAdminController(OrgRoleAdminService orgRoleAdminService,
                                  UserService userService) {
        this.orgRoleAdminService = orgRoleAdminService;
        this.userService = userService;
    }

    @GetMapping("")
    public PageResponse<OrgRoleAdminVO> getPageOrgRoleAdmin(@Valid OrgRoleAdminPageRequest request) {
        PageResponse<OrgRoleAdminDTO> dtoPageResponse = orgRoleAdminService.queryPage(request);
        PageResponse<OrgRoleAdminVO> pageResponse = new PageResponse<>();
        pageResponse.setPage(request.getPage());
        pageResponse.setPageSize(request.getPageSize());
        pageResponse.setTotalPage(dtoPageResponse.getTotalPage());
        pageResponse.setTotalCount(dtoPageResponse.getTotalCount());
        List<OrgRoleAdminDTO> orgRoleAdminDTOList = dtoPageResponse.getItems();
        List<OrgRoleAdminVO> voList = new ArrayList<>();
        for (OrgRoleAdminDTO dto : orgRoleAdminDTOList) {
            OrgRoleAdminVO orgRoleAdminVO = OrgRoleAdminVOTransfer.INSTANCE.toVO(dto);
            if (StringUtils.isNotEmpty(dto.getGroupId())) {
                List<String> groupList = Arrays.asList(dto.getGroupId().split(","));
                orgRoleAdminVO.setGroups(DxGroupVOTransfer.INSTANCE.toVOList(
                        orgRoleAdminService.getDxGroupByGroupIds(groupList))
                );
            } else {
                orgRoleAdminVO.setGroups(Collections.emptyList());
            }
            voList.add(orgRoleAdminVO);
        }
        pageResponse.setItems(voList);
        return pageResponse;
    }

    @PostMapping("")
    public boolean saveOrgRoleAdmin(@Valid @RequestBody OrgRoleAdminVO orgRoleAdminVO) {
        orgRoleAdminVO.setOrgId(formatOrgId(orgRoleAdminVO.getOrgId()));
        OrgRoleAdminDTO orgRoleAdminDTO = OrgRoleAdminVOTransfer.INSTANCE.toDTO(orgRoleAdminVO);
        return orgRoleAdminService.saveOrgRoleAdmin(orgRoleAdminDTO, orgRoleAdminVO.getDeleteAllChild());
    }

    @PostMapping("/dxgroup")
    public boolean saveOrgDxGroup(@Valid @RequestBody OrgDxGroupVO orgDxGroupVO){
        String orgId = formatOrgId(orgDxGroupVO.getOrgId());
        List<DxGroupDTO> dxGroupDTOList = DxGroupVOTransfer.INSTANCE.toDTOList(orgDxGroupVO.getDxGroupList());
        return orgRoleAdminService.saveOrgDxGroup(orgId,dxGroupDTOList);
    }

    @GetMapping("/dxgroup")
    public List<DxGroupVO> getDxGroupVO(
            @RequestParam(value = "groupIds", required = false, defaultValue = "") List<String> groupIds){
        List<DxGroupDTO> dxGroupDTOList;
        if (CollectionUtils.isNotEmpty(groupIds)) {
            dxGroupDTOList = orgRoleAdminService.getDxGroupByGroupIds(groupIds);
        } else {
            dxGroupDTOList = orgRoleAdminService.getAllDxGroup();
        }
        return DxGroupVOTransfer.INSTANCE.toVOList(dxGroupDTOList);
    }


    @GetMapping("/org/{orgId}")
    public OrgRoleAdminTreeVO getOrgRoleAdminTreeByOrgId(@NotBlank @PathVariable String orgId, @NotBlank @RequestParam(value = "role") String role){
        OrgRoleType roleType = toOrgRoleType(role);
        return getOrgRoleAdminTreeVO(orgId, roleType);
    }

    @GetMapping("/orgId/{orgId}")
    public OrgRoleAdminTreeVO getOrgRoleAdminTree(@NotBlank @PathVariable String orgId, @NotBlank @RequestParam(value = "role") String role){
        OrgRoleType roleType = toOrgRoleType(role);
        return getOrgRoleAdminTreeVO(orgId, roleType);
    }

    @GetMapping("/user/{mis:.+}")
    public OrgRoleAdminTreeVO getOrgRoleAdminTreeByMis(@PathVariable String mis, @RequestParam(value = "role") String role) {
        String orgId;
        List<UserDTO> userDTOList = userService.queryUserByMis(Collections.singletonList(mis));
        if (CollectionUtils.isEmpty(userDTOList) || StringUtils.isEmpty(orgId = userDTOList.get(0).getOrgId())) {
            UserDTO userDTO = userService.getDxUserByClient(mis);
            if (Objects.isNull(userDTO) || StringUtils.isEmpty(orgId = userDTO.getOrgId())) {
                throw new SupportErrorException(String.format("用户[%s]对应的组织架构信息不存在", mis));
            }
        }
        return getOrgRoleAdminTreeByOrgId(orgId, role);
    }

    @GetMapping("/tree")
    public List<OrgSreTreeVO> getOrgSreTreeByMis(@RequestParam(value = "mis", required = false) String mis,
                                                 @RequestParam(value = "orgIds", required = false, defaultValue = "") String orgIds){
        if (StringUtils.isEmpty(mis)) {
            mis = UserUtils.getCurrentCasUser().getLoginName();
        }
        List<OrgSreTreeDTO> orgSreTreeDTOList = orgRoleAdminService.getOrgSreTreeListByOrgId(mis, orgIds);
        return OrgSreTreeVOTransfer.INSTANCE.toVOList(orgSreTreeDTOList);
    }

    /**
     * 转为枚举角色类型
     *
     * @param role 角色
     * @return {@link OrgRoleType}
     */
    private OrgRoleType toOrgRoleType(String role){
        OrgRoleType roleType = OrgRoleType.getInstance(role);
        if (Objects.isNull(roleType)) {
            throw new SupportErrorException("role 仅支持 op_admin/ep_admin");
        }
        return roleType;
    }

    /**
     * 组织树
     *
     * @param orgId    org id
     * @param roleType 角色类型
     * @return {@link OrgRoleAdminTreeVO}
     */
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

    @GetMapping("/role")
    public OrgRoleAdminDetailVO getOrgApplication(@RequestParam(value = "orgIds") String orgIds) {
        final String dot = ",";
        OrgRoleAdminDetailVO orgApplicationVO = new OrgRoleAdminDetailVO();
        OrgRoleAdminTreeVO opAdmin = getOrgRoleAdminTreeVO(orgIds, OrgRoleType.OP_ADMIN);
        orgApplicationVO.setOpRoleAdmin(opAdmin);
        orgApplicationVO.setDxGroups(opAdmin.getGroupList());
        if (StringUtils.isNotEmpty(opAdmin.getRoleUsers())) {
            List<String> opUsers = Arrays.asList(opAdmin.getRoleUsers().split(dot));
            orgApplicationVO.setOpAdmins(UserVOTransfer.INSTANCE.toDxUserVOList(userService.queryUserByMis(opUsers)));
        } else {
            orgApplicationVO.setOpAdmins(Collections.emptyList());
        }
        OrgRoleAdminTreeVO epAdmin = getOrgRoleAdminTreeVO(orgIds, OrgRoleType.EP_ADMIN);
        orgApplicationVO.setEpRoleAdmin(epAdmin);
        if (StringUtils.isNotEmpty(epAdmin.getRoleUsers())) {
            List<String> epUsers = Arrays.asList(epAdmin.getRoleUsers().split(dot));
            orgApplicationVO.setEpAdmins(UserVOTransfer.INSTANCE.toDxUserVOList(userService.queryUserByMis(epUsers)));
        } else {
            orgApplicationVO.setEpAdmins(Collections.emptyList());
        }
        return orgApplicationVO;
    }

    /**
     * 格式org id
     *
     * @param orgId org id
     * @return {@link String}
     */
    private String formatOrgId(String orgId){
        String[] orgIds = orgId.split(",");
        return orgIds[orgIds.length-1];
    }
}
