package com.sankuai.avatar.web.controller.open;

import com.meituan.servicecatalog.api.annotations.*;
import com.sankuai.avatar.common.utils.ObjectUtils;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.orgRole.constant.OrgRoleType;
import com.sankuai.avatar.web.dto.orgRole.OrgRoleAdminDTO;
import com.sankuai.avatar.web.exception.SupportErrorException;
import com.sankuai.avatar.web.request.OrgRoleAdminPageRequest;
import com.sankuai.avatar.web.service.OrgRoleAdminService;
import com.sankuai.avatar.web.transfer.orgRole.DxGroupVOTransfer;
import com.sankuai.avatar.web.transfer.orgRole.OrgRoleAdminVOTransfer;
import com.sankuai.avatar.web.vo.orgRole.OrgRoleAdminTreeVO;
import com.sankuai.avatar.web.vo.orgRole.OrgRoleAdminVO;
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
 * @author caoyang
 * @create 2023-02-09 15:48
 */
@InterfaceDoc(
        displayName = "部门角色信息",
        type = "restful",
        description = "部门角色信息接口",
        scenarios = "接口用于查看部门运维/测试负责人角色及其他部门信息"
)
@Slf4j
@Validated
@RestController
@RequestMapping("/open/api/v2/avatar/orgRoleAdmin")
public class OrgRoleAdminOpenController {

    private final OrgRoleAdminService orgRoleAdminService;

    @Autowired
    public OrgRoleAdminOpenController(OrgRoleAdminService orgRoleAdminService) {
        this.orgRoleAdminService = orgRoleAdminService;
    }

    @MethodDoc(displayName = "部门角色信息分页查询接口",
            description = "接口用于分页查询部门角色信息",
            parameters = {
                    @ParamDoc(
                            name = "pageRequest",
                            description = "部门角色分页查询支持的查询参数",
                            paramType = ParamType.REQUEST_PARAM,
                            requiredness = Requiredness.OPTIONAL
                    )
            },
            requestMethods = {HttpMethod.GET},
            restExampleUrl = "http://avatar.vip.sankuai.com/open/api/v2/avatar/orgRoleAdmin?orgId=1021866",
            responseParams = {
                    @ParamDoc(name = "code", description = "返回码"),
                    @ParamDoc(name = "message", description = "返回描述"),
                    @ParamDoc(name = "data", description = "部门角色信息分页查询数据", type = PageResponse.class),
            },
            restExampleResponseData = "{\n" +
                    "  \"code\": 0,\n" +
                    "  \"message\": \"操作成功\",\n" +
                    "  \"data\": {\n" +
                    "    \"totalCount\": 469,\n" +
                    "    \"page\": 1,\n" +
                    "    \"totalPage\": 47,\n" +
                    "    \"pageSize\": 10,\n" +
                    "    \"items\": [\n" +
                    "      {\n" +
                    "        \"orgId\": \"104074\",\n" +
                    "        \"orgName\": \"公司-美团-基础研发平台-信息安全部-基础安全\",\n" +
                    "        \"role\": \"op_admin\",\n" +
                    "        \"roleUsers\": \"baishaohua\",\n" +
                    "        \"updateUser\": \"baishaohua\",\n" +
                    "        \"orgPath\": \"0-1-2-100046-1418-104074\",\n" +
                    "        \"groups\": []\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"orgId\": \"105289\",\n" +
                    "        \"orgName\": \"公司-美团-基础研发平台-信息安全部-数据安全和合规\",\n" +
                    "        \"role\": \"op_admin\",\n" +
                    "        \"roleUsers\": \"baishaohua\",\n" +
                    "        \"updateUser\": \"\",\n" +
                    "        \"orgPath\": \"0-1-2-100046-1418-105289\",\n" +
                    "        \"groups\": []\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"orgId\": \"105291\",\n" +
                    "        \"orgName\": \"公司-美团-基础研发平台-信息安全部-技术和算法-安全技术和质量保障\",\n" +
                    "        \"role\": \"op_admin\",\n" +
                    "        \"roleUsers\": \"baishaohua\",\n" +
                    "        \"updateUser\": \"zhangnan78\",\n" +
                    "        \"orgPath\": \"0-1-2-100046-1418-1825-105291\",\n" +
                    "        \"groups\": []\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"orgId\": \"1825\",\n" +
                    "        \"orgName\": \"公司-美团-基础研发平台-信息安全部-技术和算法\",\n" +
                    "        \"role\": \"op_admin\",\n" +
                    "        \"roleUsers\": \"baishaohua,zhangnan78\",\n" +
                    "        \"updateUser\": \"baishaohua\",\n" +
                    "        \"orgPath\": \"0-1-2-100046-1418-1825\",\n" +
                    "        \"groups\": [\n" +
                    "          {\n" +
                    "            \"groupId\": \"https://x.sankuai.com/chat/119486?type=groupchat\",\n" +
                    "            \"groupName\": \"北京业务安全中心&SRE\",\n" +
                    "            \"groupStatus\": \"\"\n" +
                    "          },\n" +
                    "          {\n" +
                    "            \"groupId\": \"https://x.sankuai.com/chat/64076625224?type=groupchat\",\n" +
                    "            \"groupName\": \"上海业务安全中心&SRE\",\n" +
                    "            \"groupStatus\": \"\"\n" +
                    "          }\n" +
                    "        ]\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"orgId\": \"152543\",\n" +
                    "        \"orgName\": \"公司-美团-基础研发平台-信息安全部-安全能力\",\n" +
                    "        \"role\": \"op_admin\",\n" +
                    "        \"roleUsers\": \"baishaohua\",\n" +
                    "        \"updateUser\": \"\",\n" +
                    "        \"orgPath\": \"0-1-2-100046-1418-152543\",\n" +
                    "        \"groups\": []\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"orgId\": \"106289\",\n" +
                    "        \"orgName\": \"公司-美团-美团平台-商业分析部\",\n" +
                    "        \"role\": \"op_admin\",\n" +
                    "        \"roleUsers\": \"huguochao\",\n" +
                    "        \"updateUser\": \"\",\n" +
                    "        \"orgPath\": \"0-1-2-152983-106289\",\n" +
                    "        \"groups\": []\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"orgId\": \"1828\",\n" +
                    "        \"orgName\": \"公司-美团-人力资源-互联网+大学-技术学院\",\n" +
                    "        \"role\": \"op_admin\",\n" +
                    "        \"roleUsers\": \"shijiashuo\",\n" +
                    "        \"updateUser\": \"\",\n" +
                    "        \"orgPath\": \"0-1-2-11-102935-1828\",\n" +
                    "        \"groups\": []\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"orgId\": \"102842\",\n" +
                    "        \"orgName\": \"公司-美团-到家事业群-到家研发平台-质量效能部\",\n" +
                    "        \"role\": \"op_admin\",\n" +
                    "        \"roleUsers\": \"tiandongdong\",\n" +
                    "        \"updateUser\": \"wangjing25\",\n" +
                    "        \"orgPath\": \"0-1-2-103100-153262-102842\",\n" +
                    "        \"groups\": [\n" +
                    "          {\n" +
                    "            \"groupId\": \"https://x.sankuai.com/chat/64062546912?type=groupchat\",\n" +
                    "            \"groupName\": \"外卖变更周知\",\n" +
                    "            \"groupStatus\": \"\"\n" +
                    "          }\n" +
                    "        ]\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"orgId\": \"111066\",\n" +
                    "        \"orgName\": \"公司-美团-到家事业群-外卖事业部-企业业务部-智能硬件组\",\n" +
                    "        \"role\": \"op_admin\",\n" +
                    "        \"roleUsers\": \"huxia\",\n" +
                    "        \"updateUser\": \"\",\n" +
                    "        \"orgPath\": \"0-1-2-103100-4-160683-111066\",\n" +
                    "        \"groups\": []\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"orgId\": \"112545\",\n" +
                    "        \"orgName\": \"公司-美团-到家事业群-到家研发平台-交易系统平台部\",\n" +
                    "        \"role\": \"op_admin\",\n" +
                    "        \"roleUsers\": \"tiandongdong\",\n" +
                    "        \"updateUser\": \"tiandongdong\",\n" +
                    "        \"orgPath\": \"0-1-2-103100-153262-112545\",\n" +
                    "        \"groups\": [\n" +
                    "          {\n" +
                    "            \"groupId\": \"https://x.sankuai.com/chat/64066052078?type=groupchat\",\n" +
                    "            \"groupName\": \"交易平台SRE\",\n" +
                    "            \"groupStatus\": \"\"\n" +
                    "          }\n" +
                    "        ]\n" +
                    "      }\n" +
                    "    ]\n" +
                    "  }\n" +
                    "}"
    )
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

    @MethodDoc(displayName = "指定部门运维角色查询接口",
            description = "接口用于查询指定部门运维角色信息",
            parameters = {
                    @ParamDoc(
                            name = "role",
                            description = "角色类型",
                            paramType = ParamType.REQUEST_PARAM,
                            requiredness = Requiredness.REQUIRED,
                            example = "op_admin",
                            rule = "仅限于op_admin/ep_admin"
                    )
            },
            requestMethods = {HttpMethod.GET},
            restExampleUrl = "http://avatar.vip.sankuai.com/open/api/v2/avatar/orgRoleAdmin/org/1021866?role=op_admin",
            responseParams = {
                    @ParamDoc(name = "code", description = "返回码"),
                    @ParamDoc(name = "message", description = "返回描述"),
                    @ParamDoc(name = "data", description = "部门组织数据", type = OrgRoleAdminTreeVO.class),
            },
            example = "{\n" +
                    "  \"code\": 0,\n" +
                    "  \"message\": \"操作成功\",\n" +
                    "  \"data\": {\n" +
                    "    \"orgId\": \"1021866\",\n" +
                    "    \"orgName\": \"公司-美团-基础研发平台-基础技术部-服务运维部-运维工具开发组-变更管理开发组\",\n" +
                    "    \"role\": \"op_admin\",\n" +
                    "    \"roleUsers\": \"kui.xu\",\n" +
                    "    \"children\": null,\n" +
                    "    \"ancestor\": null,\n" +
                    "    \"current\": {\n" +
                    "      \"orgId\": \"1021866\",\n" +
                    "      \"orgName\": \"公司-美团-基础研发平台-基础技术部-服务运维部-运维工具开发组-变更管理开发组\",\n" +
                    "      \"role\": \"op_admin\",\n" +
                    "      \"roleUsers\": \"kui.xu\",\n" +
                    "      \"updateUser\": \"\",\n" +
                    "      \"orgPath\": \"0-1-2-100046-150042-1573-150044-1021866\"\n" +
                    "    },\n" +
                    "    \"groupList\": [\n" +
                    "      {\n" +
                    "        \"groupId\": \"https://x.sankuai.com/chat/64011253273?type=groupchat\",\n" +
                    "        \"groupName\": \"变更管理开发\",\n" +
                    "        \"groupStatus\": \"\"\n" +
                    "      }\n" +
                    "    ],\n" +
                    "    \"desc\": \"当前部门运维负责人是由 公司-美团-基础研发平台-基础技术部-服务运维部-运维工具开发组-变更管理开发组 配置获得\"\n" +
                    "  }\n" +
                    "}"
    )
    @GetMapping("/org/{orgId}")
    public OrgRoleAdminTreeVO getOrgRoleAdminTreeByOrgId(@NotBlank @PathVariable String orgId, @NotBlank @RequestParam(value = "role") String role){
        OrgRoleType roleType = toOrgRoleType(role);
        return getOrgRoleAdminTreeVO(orgId, roleType);
    }

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
        OrgRoleAdminTreeVO orgRoleAdminTreeVO = new OrgRoleAdminTreeVO();
        String[] orgIds = orgId.split(",");
        orgId = orgIds[orgIds.length-1];
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

}
