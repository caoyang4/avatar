package com.sankuai.avatar.dao.es;

import com.sankuai.avatar.common.utils.DateUtils;
import com.sankuai.avatar.common.utils.GsonUtils;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.dao.config.DataSourceConfig;
import com.sankuai.avatar.dao.es.entity.FlowSearchEntity;
import com.sankuai.avatar.dao.es.impl.FlowSearchEsRepositoryImpl;
import com.sankuai.avatar.dao.es.request.FlowSearchQueryRequest;
import com.sankuai.avatar.dao.es.request.FlowSearchUpdateRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;

@Slf4j
public class FlowSearchEsRepositoryTest {

    private final FlowSearchEsRepository repository;

    public FlowSearchEsRepositoryTest() {
        final ApplicationContext ctx = new AnnotationConfigApplicationContext(DataSourceConfig.class);
        repository = (FlowSearchEsRepositoryImpl) ctx.getBean("flowSearchEsRepositoryImpl");
        ReflectionTestUtils.setField(repository, "indexPrefix", "avatar_workflow_search");

    }

    @Test
    public void testUpdate() {
        String json = "{\"template_name\":\"org_role_admin_change\",\"create_user\":\"qinwei05\",\"create_user_name\":\"秦伟\",\"id\":22721,\"approve_users\":\",jie.li.sh,zhaozhifan02\",\"uuid\":\"b66fc473-81bc-4198-aca2-f8cb446cbcf5\",\"env\":\"prod\",\"status\":\"SHUTDOWN\",\"approve_type\":1,\"start_time\":\"2022-09-15 21:18:46\",\"cn_name\":\"组织架构负责人配置\",\"fuzzy\":[],\"appkey\":\"\",\"approve_status\":\"SHUTDOWN\",\"keyword\":[],\"srv\":\"\",\"end_time\":\"2022-09-15 21:19:39\",\"flow_type\":2}";
        FlowSearchUpdateRequest updateRequest = GsonUtils.deserialization(json, FlowSearchUpdateRequest.class);
        boolean status = repository.update(updateRequest);
        Assert.assertTrue(status);
        log.info("Sync update Search flow(id:{}) status {}", updateRequest.getId(), status);
    }

    @Test
    public void testUpdateNull() {
        boolean status = repository.update(null);
        Assert.assertFalse(status);
    }

    @Test
    public void pageQueryTest() {
        FlowSearchQueryRequest query = FlowSearchQueryRequest.builder().createUser("zhaozhifan02").build();
        PageResponse<FlowSearchEntity> res = repository.pageQuery(query, 1, 1);
        Assert.assertNotNull(res);
        log.info("SearchFlow entity {}", res.getItems());
    }

    @Test
    public void pageApproveTest() {
        FlowSearchQueryRequest query = FlowSearchQueryRequest.builder()
                .approveUser("caoyang42")
                .appKey("avatar")
                .template("service_whitelist")
                .env("prod")
                .status("RUNNING")
                .build();
        PageResponse<FlowSearchEntity> res = repository.pageQuery(query, 1, 1);
        Assert.assertNotNull(res);
        for (FlowSearchEntity item : res.getItems()) {
            Assert.assertTrue(item.getApproveUsers().contains("caoyang42"));
        }
        log.info("SearchFlow entity {}", res.getItems());
    }

    @Test
    public void pageReasonTest() {
        FlowSearchQueryRequest query = FlowSearchQueryRequest.builder()
                .reason("研发/测试环境释放资源")
                .build();
        PageResponse<FlowSearchEntity> res = repository.pageQuery(query, 1, 1000);
        Assert.assertNotNull(res);
        log.info("SearchFlow entity {}", res.getItems());
    }

    @Test
    public void pageFuzzyTest() {
        FlowSearchQueryRequest query = FlowSearchQueryRequest.builder()
                .fuzzy("set-hh-ecs-abc05")
                .build();
        PageResponse<FlowSearchEntity> res = repository.pageQuery(query, 1, 1000);
        Assert.assertNotNull(res);
        log.info("SearchFlow entity {}", res.getItems());
    }

    @Test
    public void pageTimeRangeTest() {
        FlowSearchQueryRequest query = FlowSearchQueryRequest.builder()
                .createTimeBegin(DateUtils.StrToDate("2019-12-09 20:45:39"))
                .createTimeEnd(DateUtils.StrToDate("2023-02-09 20:45:39"))
                .build();
        PageResponse<FlowSearchEntity> res = repository.pageQuery(query, 1, 10);
        Assert.assertNotNull(res);
        log.info("SearchFlow entity {}", res.getItems());
    }


}