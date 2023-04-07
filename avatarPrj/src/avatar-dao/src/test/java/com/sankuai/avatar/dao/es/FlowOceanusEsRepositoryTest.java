package com.sankuai.avatar.dao.es;

import com.sankuai.avatar.common.utils.GsonUtils;
import com.sankuai.avatar.dao.config.DataSourceConfig;
import com.sankuai.avatar.dao.es.impl.FlowOceanusEsRepositoryImpl;
import com.sankuai.avatar.dao.es.request.FlowOceanusUpdateRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;

@Slf4j
public class FlowOceanusEsRepositoryTest {

    private final FlowOceanusEsRepository repository;

    public FlowOceanusEsRepositoryTest() {
        final ApplicationContext ctx = new AnnotationConfigApplicationContext(DataSourceConfig.class);
        repository = (FlowOceanusEsRepositoryImpl) ctx.getBean("flowOceanusEsRepositoryImpl");
        ReflectionTestUtils.setField(repository, "indexPrefix", "avatar_workflow_oceanus");
    }

    @Test
    public void testUpdateNull() {
        boolean status = repository.update(null);
        Assert.assertFalse(status);
    }

    @Test
    public void update() {
        String json = "{\"status\":\"SUCCESS\",\"skynet_abnormal\":\"\",\"skynet_timeout\":\"\",\"template_name\":\"oceanus_new_location_add\",\"start_time\":\"2022-09-14 21:47:20\",\"flow_id\":2165593,\"ignore_reason\":\"non-exist\",\"err_reason\":null,\"create_user\":\"dongcongxu\",\"approve_interval\":621,\"check_abnormal_reason\":\"\",\"cn_name\":\"[oceanus变更]新增规则\",\"site_name\":\"zl.meituan.com\",\"publish_fail\":\"\",\"is_gray_publish\":\"no\",\"publish_interval\":692,\"uuid\":\"9878357e-ebd4-43a5-a482-68f2d9c243f7\",\"health_check_abnormal\":\"\",\"flow_interval\":1322,\"sre\":\"guolei14\",\"create_user_org_path\":\"公司/美团/优选事业部/研发部/买菜研发部/基础服务研发组/行业研发组\",\"create_user_org_id\":\"156707\",\"check_abnormal\":\"\",\"week_of_year\":\"W38(2022-09-12~2022-09-18)\",\"end_time\":\"2022-09-14 22:09:22\",\"publish_mode\":1,\"config_effect_timeout\":\"\",\"tasks_interval\":701,\"is_failed\":0}";
        FlowOceanusUpdateRequest updateRequest = GsonUtils.deserialization(json, FlowOceanusUpdateRequest.class);
        boolean status = repository.update(updateRequest);
        Assert.assertTrue(status);
        log.info("Update oceanus flow(id:{}) status {}", updateRequest.getUuid(), status);
    }
}