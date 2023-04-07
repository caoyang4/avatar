package com.sankuai.avatar.workflow.core.client.permission;

import com.sankuai.avatar.dao.workflow.repository.PermissionRepository;
import com.sankuai.avatar.dao.workflow.repository.entity.PermissionEntity;
import com.sankuai.avatar.dao.workflow.repository.request.PermissionRequest;
import com.sankuai.avatar.resource.tree.AppkeyTreeResource;
import com.sankuai.avatar.resource.tree.bo.AppkeyTreeBO;
import com.sankuai.avatar.resource.tree.bo.AppkeyTreeOwtBO;
import com.sankuai.avatar.resource.tree.bo.AppkeyTreePdlBO;
import com.sankuai.avatar.resource.tree.bo.AppkeyTreeSrvBO;
import com.sankuai.avatar.workflow.core.client.permission.impl.FlowPermissionClientImpl;
import com.sankuai.avatar.workflow.core.client.permission.request.FlowPermissionRequest;
import com.sankuai.avatar.workflow.core.client.permission.response.FlowPermissionResponse;
import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.context.FlowResource;
import com.sankuai.avatar.workflow.core.context.FlowUserSource;
import com.sankuai.avatar.workflow.core.input.solution.UnlockDeployInput;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FlowPermissionClientTest {

    private static final String TEST_APP_KEY = "com.sankuai.avatar.workflow.server";

    /**
     * 权限角色
     */
    private static final String PERMISSION_ROLE = "any";

    /**
     * 是否允许发起流程
     */
    private static final String PERMISSION_IS_APPLY = "Y";

    @Mock
    private PermissionRepository permissionRepository;

    @Mock
    private AppkeyTreeResource appkeyTreeResource;

    private FlowPermissionClient flowPermissionClient;


    @Before
    public void setUp() {
        flowPermissionClient = new FlowPermissionClientImpl(permissionRepository, appkeyTreeResource);
    }

    @Test
    public void testCanSkipTrue() {
        FlowContext flowContext = buildUnlockDeployFlowContext();
        FlowPermissionRequest request = FlowPermissionRequest.builder()
                .flowContext(flowContext)
                .build();
        String[] publicAppKey = {TEST_APP_KEY};
        String[] openCreateFlow = {"unlock_deploy"};
        String aegisGateWay = "[{\"value\":\"com.sankuai.hotel.biz.gw\",\"label\":\"Aegis网关酒旅集群:com.sankuai.hotel.biz.gw\"}]";
        ReflectionTestUtils.setField(flowPermissionClient, "publicAppKey", publicAppKey);
        ReflectionTestUtils.setField(flowPermissionClient, "apiGwSrv", "meituan.apigw");
        ReflectionTestUtils.setField(flowPermissionClient, "aegisGateWay", aegisGateWay);
        ReflectionTestUtils.setField(flowPermissionClient, "openCreateFlow", openCreateFlow);

        boolean result = flowPermissionClient.canSkip(request);
        assertThat(result).isTrue();

    }

    @Test
    public void testCanSkipFalse() {
        FlowContext flowContext = buildUnlockDeployFlowContext();
        FlowPermissionRequest request = FlowPermissionRequest.builder()
                .flowContext(flowContext)
                .build();
        String[] publicAppKey = {TEST_APP_KEY};
        String[] openCreateFlow = {};
        String aegisGateWay = "[{\"value\":\"com.sankuai.hotel.biz.gw\",\"label\":\"Aegis网关酒旅集群:com.sankuai.hotel.biz.gw\"}]";
        ReflectionTestUtils.setField(flowPermissionClient, "publicAppKey", publicAppKey);
        ReflectionTestUtils.setField(flowPermissionClient, "apiGwSrv", "meituan.apigw");
        ReflectionTestUtils.setField(flowPermissionClient, "aegisGateWay", aegisGateWay);
        ReflectionTestUtils.setField(flowPermissionClient, "openCreateFlow", openCreateFlow);

        boolean result = flowPermissionClient.canSkip(request);
        assertThat(result).isFalse();

    }

    @Test
    public void testValidateAccept() {
        FlowContext flowContext = buildUnlockDeployFlowContext();
        FlowPermissionRequest request = FlowPermissionRequest.builder()
                .flowContext(flowContext)
                .build();
        FlowResource flowResource = flowContext.getResource();
        String srv = flowResource.getSrv();
        String templateName = flowContext.getTemplateName();

        FlowPermissionResponse expectedResult = FlowPermissionResponse.builder().hasPermission(true).build();
        String[] avatarSuperAdmin = {"zhaozhifan02"};
        List<PermissionEntity> anyRolePermission = Collections.emptyList();
        ReflectionTestUtils.setField(flowPermissionClient, "avatarSuperAdmin", avatarSuperAdmin);
        when(appkeyTreeResource.getAppkeyTreeByKey(srv)).thenReturn(buildSrvDetailBO());
        when(permissionRepository.query(buildAnyRoleQueryRequest(templateName))).thenReturn(anyRolePermission);
        for (AdminRole role : AdminRole.values()) {
            when(permissionRepository.query(PermissionRequest.builder().templateName(templateName).role(role.getValue())
                    .build())).thenReturn(anyRolePermission);
        }
        FlowPermissionResponse result = flowPermissionClient.validate(request);

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testValidateReject() {
        FlowContext flowContext = buildUnlockDeployFlowContext();
        FlowPermissionRequest request = FlowPermissionRequest.builder()
                .flowContext(flowContext)
                .build();
        FlowResource flowResource = flowContext.getResource();
        String srv = flowResource.getSrv();
        String templateName = flowContext.getTemplateName();

        FlowPermissionResponse expectedResult = FlowPermissionResponse.builder()
                .hasPermission(false)
                .message("您不是服务com.sankuai.avatar.workflow.server的服务负责人，无操作权限。")
                .build();
        String[] avatarSuperAdmin = {};
        List<PermissionEntity> anyRolePermission = Collections.emptyList();
        ReflectionTestUtils.setField(flowPermissionClient, "avatarSuperAdmin", avatarSuperAdmin);
        when(appkeyTreeResource.getAppkeyTreeByKey(srv)).thenReturn(buildSrvDetailBO());
        FlowPermissionResponse result = flowPermissionClient.validate(request);

        assertThat(result).isEqualTo(expectedResult);
    }


    private FlowContext buildUnlockDeployFlowContext() {
        /*
         * 高峰期解禁
         * {
         *     "input":{
         *         "reason":"紧急Bug修复及服务扩容",
         *         "comment":"测试",
         *         "appkey":"com.sankuai.avatar.workflow.server"
         *     }
         * }
         */
        UnlockDeployInput unlockDeployInput = getFlowInput();
        return FlowContext.builder()
                .templateName("unlock_deploy")
                .env("test")
                .flowInput(unlockDeployInput)
                .resource(FlowResource.builder().appkey(TEST_APP_KEY).srv("meituan.avatar.test.avatar-develop").build())
                .createUserSource(FlowUserSource.USER)
                .createUser("zhaozhifan02")
                .build();
    }

    private UnlockDeployInput getFlowInput() {
        UnlockDeployInput unlockDeployInput = new UnlockDeployInput();
        unlockDeployInput.setAppkey(TEST_APP_KEY);
        unlockDeployInput.setReason("紧急Bug修复及服务扩容");
        unlockDeployInput.setComment("测试");
        return unlockDeployInput;
    }

    private AppkeyTreeBO buildSrvDetailBO() {
        AppkeyTreeSrvBO srvBO = new AppkeyTreeSrvBO();
        AppkeyTreeOwtBO owtBO = new AppkeyTreeOwtBO();
        AppkeyTreePdlBO pdlBO = new AppkeyTreePdlBO();
        return AppkeyTreeBO.builder().srv(srvBO).owt(owtBO).pdl(pdlBO).build();
    }

    private PermissionRequest buildAnyRoleQueryRequest(String templateName) {
        return PermissionRequest.builder()
                .templateName(templateName)
                .role(PERMISSION_ROLE)
                .isApply(PERMISSION_IS_APPLY)
                .build();
    }
}
