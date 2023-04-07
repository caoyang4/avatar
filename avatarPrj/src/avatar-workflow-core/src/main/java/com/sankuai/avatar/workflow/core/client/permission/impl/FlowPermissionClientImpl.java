package com.sankuai.avatar.workflow.core.client.permission.impl;

import com.meituan.mdp.boot.starter.config.annotation.MdpConfig;
import com.sankuai.avatar.common.utils.JsonUtil;
import com.sankuai.avatar.resource.tree.AppkeyTreeResource;
import com.sankuai.avatar.resource.tree.bo.AppkeyTreeBO;
import com.sankuai.avatar.resource.tree.bo.AppkeyTreeOwtBO;
import com.sankuai.avatar.resource.tree.bo.AppkeyTreePdlBO;
import com.sankuai.avatar.resource.tree.bo.AppkeyTreeSrvBO;
import com.sankuai.avatar.workflow.core.client.permission.AdminRole;
import com.sankuai.avatar.workflow.core.client.permission.AdminRoleInfo;
import com.sankuai.avatar.workflow.core.client.permission.AegisGateWay;
import com.sankuai.avatar.workflow.core.client.permission.FlowPermissionClient;
import com.sankuai.avatar.workflow.core.client.permission.request.FlowPermissionRequest;
import com.sankuai.avatar.workflow.core.client.permission.response.FlowPermissionResponse;
import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.context.FlowResource;
import com.sankuai.avatar.dao.workflow.repository.PermissionRepository;
import com.sankuai.avatar.dao.workflow.repository.entity.PermissionEntity;
import com.sankuai.avatar.dao.workflow.repository.request.PermissionRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author zhaozhifan02
 */
@Slf4j
@Component
public class FlowPermissionClientImpl implements FlowPermissionClient {

    /**
     * 权限角色
     */
    private static final String PERMISSION_ROLE = "any";

    /**
     * 是否允许发起流程
     */
    private static final String PERMISSION_IS_APPLY = "Y";

    /**
     * 公共服务
     */
    @MdpConfig("PUBLIC_APPKEY:[]")
    private String[] publicAppKey;

    /**
     * api 网关
     */
    @MdpConfig("APIGW:meituan.apigw")
    private String apiGwSrv;

    /**
     * aegis 网关
     */
    @MdpConfig("AEGIS_GATEWAY:-")
    private String aegisGateWay;

    /**
     * 对外开放流程
     */
    @MdpConfig("OPEN_CREATE_FLOW:[]")
    private String[] openCreateFlow;

    /**
     * 超级管理员
     */
    @MdpConfig("AVATAR_ADMIN:[]")
    private String[] avatarSuperAdmin;

    private final AppkeyTreeResource appkeyTreeResource;

    private final PermissionRepository permissionRepository;

    @Autowired
    public FlowPermissionClientImpl(PermissionRepository permissionRepository, AppkeyTreeResource appkeyTreeResource) {
        this.permissionRepository = permissionRepository;
        this.appkeyTreeResource = appkeyTreeResource;
    }

    @Override
    public boolean canSkip(FlowPermissionRequest request) {
        FlowContext flowContext = request.getFlowContext();
        FlowResource flowResource = flowContext.getResource();
        String templateName = flowContext.getTemplateName();
        // appKey 为空不校验权限
        if (flowResource == null || flowResource.getAppkey() == null) {
            return true;
        }
        // 网关 SRV 不校验权限
        if (isGateWaySrv(flowResource.getSrv())) {
            return true;
        }
        List<String> openFlows = Arrays.asList(openCreateFlow);
        // 公共服务、开放流程不校验
        if (openFlows.contains(templateName) && isPublicAppKey(flowResource.getAppkey())) {
            return true;
        }
        return false;
    }

    @Override
    public FlowPermissionResponse validate(FlowPermissionRequest request) {
        FlowContext flowContext = request.getFlowContext();
        FlowResource flowResource = flowContext.getResource();
        String srv = flowResource.getSrv();
        String templateName = flowContext.getTemplateName();

        FlowPermissionResponse response = FlowPermissionResponse.builder().hasPermission(true).build();

        // srv 为空或者 permission 表有 any 角色有发起权限则校验通过
        PermissionRequest permissionRequest = PermissionRequest.builder()
                .templateName(templateName)
                .role(PERMISSION_ROLE)
                .isApply(PERMISSION_IS_APPLY)
                .build();
        if (StringUtils.isEmpty(srv) || CollectionUtils.isNotEmpty(getRolePermissionEntity(permissionRequest))) {
            return response;
        }
        // TODO(zhaozhifan02): iam鉴权流程
        // 获取 srv、owt、pdl 开发、运维、测试负责人
        AppkeyTreeBO appkeyTreeBO = appkeyTreeResource.getAppkeyTreeByKey(srv);
        if (appkeyTreeBO == null) {
            return response;
        }
        Map<String, AdminRoleInfo> adminRoleMap = getAdminRoleMap(appkeyTreeBO, flowContext.getEnv());
        if (userHasRolePermission(adminRoleMap, templateName, flowContext.getCreateUser())) {
            return response;
        }
        // 生成提示信息
        String msg = generateNoPermissionMsg(templateName, flowResource.getAppkey());
        return FlowPermissionResponse.builder().hasPermission(false).message(msg).build();
    }


    /**
     * 判断是否是公共服务
     *
     * @param appKey 服务appKey
     * @return boolean
     */
    private boolean isPublicAppKey(String appKey) {
        List<String> publicAppKeyList = Arrays.asList(publicAppKey);
        if (CollectionUtils.isEmpty(publicAppKeyList)) {
            return false;
        }
        return publicAppKeyList.contains(appKey);
    }

    /**
     * 判断是否是网关service
     *
     * @param srv service
     * @return boolean
     */
    private boolean isGateWaySrv(String srv) {
        if (StringUtils.isBlank(srv) || StringUtils.isBlank(aegisGateWay)) {
            return false;
        }
        if (srv.startsWith(apiGwSrv)) {
            return true;
        }
        // 异常处理
        List<AegisGateWay> aegisGateWays = JsonUtil.json2List(aegisGateWay, AegisGateWay.class);
        if (aegisGateWays != null) {
            List<String> aegisGateWayValues = aegisGateWays.stream()
                    .map(AegisGateWay::getValue).collect(Collectors.toList());
            return aegisGateWayValues.contains(srv);
        }
        return false;
    }

    /**
     * 查询流程角色发起权限
     *
     * @param request 请求参数
     * @return List<PermissionEntity>
     */
    private List<PermissionEntity> getRolePermissionEntity(PermissionRequest request) {
        return permissionRepository.query(request);
    }

    /**
     * 获取服务树节点负责人
     *
     * @param appkeyTree {@link AppkeyTreeBO}
     * @return Map<String, AdminRole>
     */
    private Map<String, AdminRoleInfo> getAdminRoleMap(AppkeyTreeBO appkeyTree, String env) {
        AppkeyTreeSrvBO srvBO = appkeyTree.getSrv();
        AppkeyTreeOwtBO owtBO = appkeyTree.getOwt();
        AppkeyTreePdlBO pdlBO = appkeyTree.getPdl();

        // 获取服务树角色负责人
        Map<AdminRole, List<String>> adminMemberMap = new HashMap<>();
        Set<String> rdAdmins = Stream.of(srvBO.getRdAdmin(), owtBO.getRdAdmin(), pdlBO.getRdAdmin())
                .flatMap(x -> x == null ? null : x.stream()).collect(Collectors.toSet());
        adminMemberMap.put(AdminRole.RD_ADMIN, new ArrayList<>(rdAdmins));

        Set<String> opAdmins = Stream.of(srvBO.getOpAdmin(), owtBO.getOpAdmin(), pdlBO.getOpAdmin())
                .flatMap(x -> x == null ? null : x.stream()).collect(Collectors.toSet());
        adminMemberMap.put(AdminRole.SRE, new ArrayList<>(opAdmins));

        Set<String> epAdmins = Stream.of(srvBO.getEpAdmin(), owtBO.getEpAdmin(), pdlBO.getEpAdmin())
                .flatMap(x -> x == null ? null : x.stream()).collect(Collectors.toSet());
        adminMemberMap.put(AdminRole.EP_ADMIN, new ArrayList<>(epAdmins));
        adminMemberMap.put(AdminRole.SUPER_ADMIN, Arrays.asList(avatarSuperAdmin));

        // 构造所有角色Map
        Map<String, AdminRoleInfo> adminRoleMap = new HashMap<>();
        for (AdminRole role : AdminRole.values()) {
            AdminRoleInfo adminRoleInfo = AdminRoleInfo.builder()
                    .name(role.getName()).users(adminMemberMap.get(role)).defaultPermission(true).build();
            if (AdminRole.EP_ADMIN.equals(role)) {
                adminRoleInfo.setDefaultPermission(!"prod".equals(env));
            }
            adminRoleMap.put(role.getValue(), adminRoleInfo);
        }
        return adminRoleMap;
    }

    /**
     * 判断用户是否有角色权限
     *
     * @param adminRoleMap 管理员角色
     * @param templateName 模板名称
     * @param createUser   创建人
     * @return 是否有权限
     */
    private boolean userHasRolePermission(Map<String, AdminRoleInfo> adminRoleMap,
                                          String templateName, String createUser) {
        for (Map.Entry<String, AdminRoleInfo> entry : adminRoleMap.entrySet()) {
            String key = entry.getKey();
            AdminRoleInfo value = entry.getValue();
            // 当前用户为开发、运维、测试负责人
            if (value.getUsers().contains(createUser)) {
                List<PermissionEntity> rolePermission = getRolePermissionEntity(PermissionRequest.builder()
                        .templateName(templateName)
                        .role(key)
                        .build());
                if (CollectionUtils.isEmpty(rolePermission) && value.isDefaultPermission()) {
                    return true;
                }
                long applyCount = rolePermission.stream()
                        .filter(i -> PERMISSION_IS_APPLY.equals(i.getIsApply())).count();
                if (applyCount > 0L) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 生成无权限的提示信息
     *
     * @param templateName 模板名称
     * @param appKey       服务
     * @return 提示信息
     */
    private String generateNoPermissionMsg(String templateName, String appKey) {
        List<AdminRole> hasPermissionRoles = Collections.singletonList(AdminRole.RD_ADMIN);
        List<PermissionEntity> permissionEntityList = getRolePermissionEntity(PermissionRequest.builder()
                .templateName(templateName)
                .isApply(PERMISSION_IS_APPLY)
                .build());
        if (CollectionUtils.isNotEmpty(permissionEntityList)) {
            List<String> roles = permissionEntityList.stream().map(PermissionEntity::getRole)
                    .collect(Collectors.toList());
            hasPermissionRoles = roles.stream().map(AdminRole::getByValue).collect(Collectors.toList());
        }
        String roleStr = hasPermissionRoles.stream().map(AdminRole::getName).collect(Collectors.joining(","));
        return String.format("您不是服务%s的%s，无操作权限。", appKey, String.join(",", roleStr));
    }
}
