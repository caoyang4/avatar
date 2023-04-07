package com.sankuai.avatar.dao.es;

import com.sankuai.avatar.common.utils.GsonUtils;
import com.sankuai.avatar.dao.config.DataSourceConfig;
import com.sankuai.avatar.dao.es.impl.FlowAuditEsRepositoryImpl;
import com.sankuai.avatar.dao.es.request.FlowAuditUpdateRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;

@Slf4j
public class FlowAuditEsRepositoryTest {

    private final FlowAuditEsRepository repository;

    public FlowAuditEsRepositoryTest() {
        final ApplicationContext ctx = new AnnotationConfigApplicationContext(DataSourceConfig.class);
        repository = (FlowAuditEsRepositoryImpl) ctx.getBean("flowAuditEsRepositoryImpl");
        ReflectionTestUtils.setField(repository, "indexPrefix", "avatar_workflow_web_audit_log");

    }

    @Test
    public void testUpdateNull() {
        boolean status = repository.update(null);
        Assert.assertFalse(status);
    }

    @Test
    public void testUpdate() {
        String json = "{\"template_name\":\"org_role_admin_change\",\"is_super_role\":\"yes\",\"result\":{\"status\":\"success\",\"finish\":true,\"title\":\"需要Leader审批\",\"user\":\"liucheng06\",\"time\":\"2022-09-15 19:59:03\",\"desc\":\"超级权限审批\",\"op\":\"ACCESS\"},\"final_approve_user\":\"liucheng06\",\"id\":816886,\"index\":0,\"dynamic_refresh\":0,\"title\":\"需要Leader审批\",\"decision\":{},\"flow_id\":2168703,\"end_time\":\"2022-09-15 19:59:03\",\"start_time\":\"2022-09-15 19:58:50\",\"approve_user\":{\"status\":\"info\",\"title\":\"需要Leader审批\",\"tag\":\"single\",\"user\":[\"zuopucun\"],\"op\":\"HOLDING\",\"desc\":\"需要Leader审批\"},\"types\":\"and\",\"desc\":\"需要Leader审批\",\"approve_status\":\"success\",\"effective\":1,\"final_approve_user_org_id\":\"104602\",\"interval\":13,\"template_cn_name\":\"组织架构负责人配置\",\"approve_data\":{},\"week_of_year\":\"W38(2022-09-12~2022-09-18)\",\"klass\":\"LeaderApprove\",\"final_approve_user_org_path\":\"公司/美团/基础研发平台/基础技术部/服务运维部/业务SRE一组/到店平台SRE组\"}";
        FlowAuditUpdateRequest updateRequest = GsonUtils.deserialization(json, FlowAuditUpdateRequest.class);
        boolean status = repository.update(updateRequest);
        Assert.assertTrue(status);
        log.info("Update audit flow(id:{}) status {}", updateRequest.getId(), status);
    }

}