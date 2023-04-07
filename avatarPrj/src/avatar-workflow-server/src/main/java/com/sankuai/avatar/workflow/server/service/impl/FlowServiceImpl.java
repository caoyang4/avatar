package com.sankuai.avatar.workflow.server.service.impl;

import com.meituan.mdp.boot.starter.config.annotation.MdpConfig;
import com.sankuai.avatar.common.utils.DateUtils;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.dao.es.FlowSearchEsRepository;
import com.sankuai.avatar.dao.es.entity.FlowSearchEntity;
import com.sankuai.avatar.dao.workflow.repository.FlowRepository;
import com.sankuai.avatar.dao.workflow.repository.entity.FlowEntity;
import com.sankuai.avatar.workflow.server.dto.flow.FlowDTO;
import com.sankuai.avatar.workflow.server.exception.EsException;
import com.sankuai.avatar.workflow.server.request.FlowPageRequest;
import com.sankuai.avatar.workflow.server.service.FlowService;
import com.sankuai.avatar.workflow.server.transfer.FlowTransfer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;

/**
 * 流程数据管理接口实现
 *
 * @author zhaozhifan02
 */
@Slf4j
@Service
public class FlowServiceImpl implements FlowService {

    /**
     * 流程查询是否走 db 的开关
     */
    @MdpConfig("FLOW_DB_SOURCE_SWITCH:false")
    private Boolean flowDbSource;

    private final FlowRepository flowRepository;

    private final FlowSearchEsRepository esRepository;

    @Autowired
    public FlowServiceImpl(FlowRepository flowRepository,
                           FlowSearchEsRepository esRepository) {
        this.flowRepository = flowRepository;
        this.esRepository = esRepository;
    }

    @Override
    public FlowDTO getFlowByUuid(String uuid) {
        FlowEntity flowEntity = flowRepository.getFlowEntityByUuid(uuid);
        return FlowTransfer.INSTANCE.toDTO(flowEntity);
    }

    @Override
    public PageResponse<FlowDTO> getPageFlow(FlowPageRequest pageRequest) {
        // 指定从 db 查询
        if (Boolean.TRUE.equals(pageRequest.getDbSource()) || Boolean.TRUE.equals(flowDbSource)) {
            return getPageFlowByDb(pageRequest);
        }
        PageResponse<FlowDTO> response = null;
        try {
            response = getPageFlowByEs(pageRequest);
        } catch (EsException ignored) {}
        if (Objects.nonNull(response)) {
            return response;
        }
        // es 异常，则去 db 查
        return getPageFlowByDb(pageRequest);
    }

    @Override
    public PageResponse<FlowDTO> getPageFlowByDb(FlowPageRequest pageRequest) {
        // 流程走db查询，默认返回一个月内的流程数据
        pageRequest.setStartTimeGt(DateUtils.localDateToDate(LocalDate.now().plusDays(-30)));
        PageResponse<FlowEntity> entityPage = flowRepository.queryPage(FlowTransfer.INSTANCE.toRequest(pageRequest));
        PageResponse<FlowDTO> pageResponse = new PageResponse<>();
        pageResponse.setPage(pageRequest.getPage());
        pageResponse.setPageSize(pageRequest.getPageSize());
        pageResponse.setTotalPage(entityPage.getTotalPage());
        pageResponse.setTotalCount(entityPage.getTotalCount());
        pageResponse.setItems(FlowTransfer.INSTANCE.toDTOList(entityPage.getItems()));
        return pageResponse;
    }

    @Override
    public PageResponse<FlowDTO> getPageFlowByEs(FlowPageRequest pageRequest) throws EsException{
        int page = pageRequest.getPage();
        int pageSize = pageRequest.getPageSize();
        PageResponse<FlowSearchEntity> doPage = esRepository.pageQuery(FlowTransfer.INSTANCE.toEsRequest(pageRequest), page, pageSize);
        if (Objects.isNull(doPage)) {
            return null;
        }
        PageResponse<FlowDTO> pageResponse = new PageResponse<>();
        pageResponse.setPage(pageRequest.getPage());
        pageResponse.setPageSize(pageRequest.getPageSize());
        pageResponse.setTotalPage(doPage.getTotalPage());
        pageResponse.setTotalCount(doPage.getTotalCount());
        pageResponse.setItems(FlowTransfer.INSTANCE.toFlowDTOList(doPage.getItems()));
        return pageResponse;
    }

}
