package com.sankuai.avatar.workflow.core.display.impl;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.sankuai.avatar.common.utils.DateUtils;
import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.context.FlowDisplay;
import com.sankuai.avatar.workflow.core.display.AbstractDisplayGenerator;
import com.sankuai.avatar.workflow.core.display.model.InputDisplay;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 高峰期解禁展示信息生成器
 *
 * @author zhaozhifan02
 */
@Slf4j
@Component("unlock_deploy")
public class UnlockDeployGenerator extends AbstractDisplayGenerator {

    @Override
    protected FlowDisplay doGenerate(FlowContext flowContext) {
        return FlowDisplay.builder().input(getInputDisplay(flowContext)).build();
    }

    @Override
    public List<InputDisplay> getInputDisplay(FlowContext flowContext) {
        List<InputDisplay> inputDisplays = getDefaultInputDisplay(flowContext);
        inputDisplays.addAll(getCustomInputDisplay());
        return inputDisplays;
    }

    private List<InputDisplay> getCustomInputDisplay() {
        //TODO(zhaozhifan02): 更通用的方法，兼容长期解禁
        List<InputDisplay> inputDisplayList = new ArrayList<>();
        Instant now = Instant.now();
        Instant twoHoursLater = now.plus(Duration.ofHours(2));
        String timeRange = String.format("%s ~ %s", DateUtils.dateToStr(Date.from(now)),
                DateUtils.dateToStr(Date.from(twoHoursLater)));

        Map<String, String> customFieldValue = ImmutableMap
                .of("类型", "高峰期临时解禁", "变更类型", "新增", "时间范围", timeRange);
        Map<String, String> customMap = Maps.newHashMap(customFieldValue);
        customMap.forEach((k, v) -> inputDisplayList.add(InputDisplay.builder().key(k).value(v).build()));
        return inputDisplayList;
    }
}
