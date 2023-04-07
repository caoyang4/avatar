package com.sankuai.avatar.workflow.server.controller;

import com.sankuai.avatar.workflow.server.dto.request.flow.FlowUpdateRequest;
import com.sankuai.avatar.workflow.server.service.FlowDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhaozhifan02
 */
@Slf4j
@RequestMapping("/api/v2/avatar/workflow/flowData")
@RestController
public class FlowDataController {

    private final FlowDataService flowDataService;

    @Autowired
    public FlowDataController(FlowDataService flowDataService) {
        this.flowDataService = flowDataService;
    }

    /**
     * 流程更新接口
     *
     * @param request 更新参数
     * @return boolean
     */
    @PostMapping("")
    public boolean saveFlow(@Validated @RequestBody FlowUpdateRequest request) {
        return flowDataService.asyncUpdate(request);
    }
}

