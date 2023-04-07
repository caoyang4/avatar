package com.sankuai.avatar.dao.es.impl;

import com.dianping.cat.Cat;
import com.meituan.mdp.boot.starter.config.annotation.MdpConfig;
import com.sankuai.avatar.common.utils.DateUtils;
import com.sankuai.avatar.common.utils.GsonUtils;
import com.sankuai.avatar.dao.es.FlowOceanusEsRepository;
import com.sankuai.avatar.dao.es.request.FlowOceanusUpdateRequest;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;

/**
 * oceanus流程es数据管理接口实现
 *
 * @author zhaozhifan02
 */
@Slf4j
@Repository
public class FlowOceanusEsRepositoryImpl implements FlowOceanusEsRepository {

    /**
     * 全量索引后缀
     */
    private static final String INDEX_SUFFIX = "*";

    /**
     * 索引时间后缀格式
     */
    private static final String DATE_FORMAT = "yyyy-MM";

    @MdpConfig("OCEANUS_FLOW_INDEX_PREFIX:avatar_workflow_oceanus")
    private String indexPrefix;

    private final RestHighLevelClient restHighLevelClient;

    @Autowired
    public FlowOceanusEsRepositoryImpl(RestHighLevelClient restHighLevelClient) {
        this.restHighLevelClient = restHighLevelClient;
    }

    @Override
    public boolean update(FlowOceanusUpdateRequest request) {
        if (Objects.isNull(request)) {
            return false;
        }
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.docAsUpsert(true);
        Date startTime = DateUtils.StrToDate(request.getStartTime());
        String dateStr = DateUtil.format(DateUtil.date(startTime), DATE_FORMAT);
        updateRequest.index(String.format("%s_%s", indexPrefix, dateStr));
        updateRequest.id(request.getUuid());
        updateRequest.doc(GsonUtils.serialization(request), XContentType.JSON);
        try {
            UpdateResponse response = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
            return RestStatus.CREATED.equals(response.status()) || RestStatus.OK.equals(response.status());
        } catch (IOException e) {
            Cat.logError("Update oceanus flow caught exception: ", e);
            log.error("Update oceanus flow caught exception: ", e);
            return false;
        }
    }
}
