package com.sankuai.avatar.dao.workflow.repository.impl;

import com.dianping.lion.Environment;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.base.Preconditions;
import com.sankuai.avatar.common.utils.JsonUtil;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.dao.workflow.repository.FlowRepository;
import com.sankuai.avatar.dao.workflow.repository.entity.FlowEntity;
import com.sankuai.avatar.dao.workflow.repository.mapper.FlowDOMapper;
import com.sankuai.avatar.dao.workflow.repository.model.FlowDO;
import com.sankuai.avatar.dao.workflow.repository.model.FlowTemplateTask;
import com.sankuai.avatar.dao.workflow.repository.request.FlowPublicDataUpdateRequest;
import com.sankuai.avatar.dao.workflow.repository.request.FlowQueryRequest;
import com.sankuai.avatar.dao.workflow.repository.transfer.FlowTransfer;
import com.sankuai.avatar.dao.workflow.repository.utils.PicklerDataUtils;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * @author zhaozhifan02
 */
@Slf4j
@Repository
public class FlowRepositoryImpl implements FlowRepository {

    /**
     * 测试环境
     */
    private static List<String> TEST_ENV_LIST = Arrays.asList("test", "dev");

    private final FlowDOMapper mapper;

    @Autowired
    public FlowRepositoryImpl(FlowDOMapper mapper) {
        this.mapper = mapper;
    }


    @Override
    public PageResponse<FlowEntity> queryPage(FlowQueryRequest request) {
        PageResponse<FlowEntity> pageResponse = new PageResponse<>();
        if (Objects.isNull(request)) {
            return pageResponse;
        }
        int page = request.getPage();
        int pageSize = request.getPageSize();
        Page<FlowDO> doPage = PageHelper.startPage(page, pageSize).doSelectPage(
                () ->  mapper.selectByExample(buildExample(request))
        );
        pageResponse.setPage(page);
        pageResponse.setPageSize(pageSize);
        pageResponse.setTotalPage(doPage.getPages());
        pageResponse.setTotalCount(doPage.getTotal());
        pageResponse.setItems( FlowTransfer.INSTANCE.toEntityList(doPage));
        return pageResponse;
    }

    @Override
    public FlowEntity getFlowEntityByUuid(String uuid) {
        if (StringUtils.isBlank(uuid)) {
            return null;
        }
        FlowQueryRequest request = new FlowQueryRequest();
        request.setUuid(uuid);
        List<FlowDO> flowDOList = mapper.selectByExample(buildExample(request));
        if (CollectionUtils.isEmpty(flowDOList)) {
            return null;
        }
        return FlowTransfer.INSTANCE.toEntity(flowDOList.get(0));
    }

    /**
     * 根据流程UUID获取流程详情
     *
     * @param id UUID
     * @return {@link FlowEntity}
     */
    @Override
    public FlowEntity getFlowEntityById(Integer id) {
        if (id == null) {
            return null;
        }
        FlowDO flowDO = mapper.selectByPrimaryKey(id);
        if (flowDO == null ){
            return null;
        }
        return FlowTransfer.INSTANCE.toEntity(flowDO);
    }

    @Override
    public boolean addFlow(FlowEntity flowEntity) {
        if (flowEntity == null) {
            return false;
        }
        FlowDO flowDO = FlowTransfer.INSTANCE.toDO(flowEntity);

        // 老的 pickler 类型字段，保持和 V1 对齐
        if (CollectionUtils.isNotEmpty(flowEntity.getTasks())) {
            flowDO.setTasks(getTaskPickerData(flowEntity.getTasks()));
        }
        flowDO.setConfig(PicklerDataUtils.picklerData(flowEntity.getConfig()));
        flowDO.setLogs(PicklerDataUtils.picklerData(Collections.emptyMap()));
        flowDO.setInput(PicklerDataUtils.picklerData(JsonUtil.beanToMap(flowEntity.getInput())));
        flowDO.setApproveUsers("");
        flowDO.setApproveStatus("AUTO_ACCESS");
        // 兼容V1字段 1-mbop、2-avatar，切换完成后可删除
        flowDO.setFlowType(2);
        if (TEST_ENV_LIST.contains(Environment.getEnvironment())) {
            flowDO.setFlowType(1);
        }
        int count = mapper.insertSelective(flowDO);
        boolean status = count == 1;
        if (status) {
            flowEntity.setId(flowDO.getId());
        }
        return status;
    }

    @Override
    public boolean updateFlow(FlowEntity flowEntity) {
        if (flowEntity == null || flowEntity.getId() == null) {
            return false;
        }
        FlowDO flowDO = FlowTransfer.INSTANCE.toDO(flowEntity);
        int count = mapper.updateByPrimaryKeySelective(flowDO);
        return count == 1;
    }

    private Example buildExample(FlowQueryRequest request){
        Example example = new Example(FlowDO.class);
        Example.Criteria criteria = example.createCriteria();
        if (Objects.nonNull(request.getId())) {
            criteria.andEqualTo("id", request.getId());
            return example;
        }
        if (StringUtils.isNotEmpty(request.getUuid())) {
            criteria.andEqualTo("uuid", request.getUuid());
        }
        if (Objects.nonNull(request.getTemplateId())) {
            criteria.andEqualTo("templateId", request.getTemplateId());
        }
        if (StringUtils.isNotEmpty(request.getTemplateName())) {
            criteria.andEqualTo("templateName", request.getTemplateName());
        }
        if (StringUtils.isNotEmpty(request.getFlowType())) {
            criteria.andEqualTo("flowType", request.getFlowType());
        }
        if (StringUtils.isNotEmpty(request.getStatus())) {
            criteria.andEqualTo("status", request.getStatus());
        }
        if (StringUtils.isNotEmpty(request.getApproveStatus())) {
            criteria.andEqualTo("approveStatus", request.getApproveStatus());
        }
        if (StringUtils.isNotEmpty(request.getApproveUsers())) {
            criteria.andLike("approveUsers", String.format("%%%s%%",request.getApproveUsers()));
        }
        if (StringUtils.isNotEmpty(request.getCreateUser())) {
            criteria.andEqualTo("createUser", request.getCreateUser());
        }
        if (StringUtils.isNotEmpty(request.getAppkey())) {
            criteria.andEqualTo("appkey", request.getAppkey());
        }
        if (StringUtils.isNotEmpty(request.getEnv())) {
            criteria.andEqualTo("env", request.getEnv());
        }
        if (StringUtils.isNotEmpty(request.getType())) {
            criteria.andEqualTo("type", request.getType());
        }
        if (Objects.nonNull(request.getStartTimeGt())) {
            criteria.andGreaterThanOrEqualTo("startTime", request.getStartTimeGt());
        }
        if (Objects.nonNull(request.getStartTimeLt())) {
            criteria.andGreaterThanOrEqualTo("startTime", request.getStartTimeGt());
        }
        // 是否需要状态排序
        if (Boolean.FALSE.equals(request.getStateSort())) {
            example.orderBy("startTime").desc();
        } else {
            example.setOrderByClause(buildDefaultOrderStatement());
        }

        return example;
    }

    private String buildDefaultOrderStatement(){
        return " CASE status \n" +
                "    WHEN 'BREAK_POINT' THEN 0 \n" +
                "    WHEN 'HOLDING' THEN 1 \n" +
                "    WHEN 'DELAYED' THEN 2 \n" +
                "    WHEN 'RUNNING' THEN 3 \n" +
                "    WHEN 'GRAY_PUBLISH' THEN 4 \n" +
                "    ELSE 5 \n" +
                "  END asc, start_time DESC";
    }

    @Override
    public boolean updatePublicData(FlowPublicDataUpdateRequest request) {
        Preconditions.checkNotNull(request.getFlowId(), "flowId must not be null");
        FlowDO flowDO = FlowTransfer.INSTANCE.publicDataUpdateRequestToDO(request);
        int count = mapper.updateByPrimaryKeySelective(flowDO);
        return count == 1;
    }

    /**
     * 兼容V1数据格式，tasks 转 picker 类型
     *
     * @param tasks 模板任务列表
     * @return byte[]
     */
    private byte[] getTaskPickerData(List<FlowTemplateTask> tasks) {
        List<Map<String, Object>> taskMapList = new ArrayList<>(Collections.emptyList());
        for (FlowTemplateTask task : tasks) {
            Map<String, Object> map = JsonUtil.beanToMap(task);
            taskMapList.add(map);
        }
        return PicklerDataUtils.picklerData(taskMapList);
    }
}
