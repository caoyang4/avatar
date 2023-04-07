package com.sankuai.avatar.workflow.core.execute.atom.impl;

import com.sankuai.avatar.resource.mcm.ComponentResource;
import com.sankuai.avatar.resource.mcm.bo.PeriodConfigBO;
import com.sankuai.avatar.resource.mcm.request.CreateBusyPeriodRequestBO;
import com.sankuai.avatar.workflow.core.context.FlowResource;
import com.sankuai.avatar.workflow.core.execute.atom.AbstractAtom;
import com.sankuai.avatar.workflow.core.execute.atom.AtomStatus;
import com.sankuai.avatar.workflow.core.input.solution.UnlockDeployInput;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

/**
 * 高峰期解禁操作
 *
 * @author zhaozhifan02
 */
@Component("BusyPeriodRelease")
@Scope("prototype")
public class BusyPeriodRelease extends AbstractAtom {
    /**
     * 长期解禁类型
     */
    private static final String LONG_TIME_RELEASE_TYPE = "alteration";

    /**
     * 临时解禁默认时长
     */
    private static final Integer TEMPORARY_RELEASE_TIME_PERIOD = 2;

    /**
     * 记录类型：解禁
     */
    private static final String BUSY_PERIOD_RELEASE_ACTION = "OPEN";

    /**
     * 解禁记录，默认生效
     */
    private static final String BUSY_PERIOD_RELEASE_DEFAULT_STATUS = "ONLINE";

    /**
     * 高峰期解禁维度，默认 APPKEY 服务维度
     */
    private static final String BUSY_PERIOD_RELEASE_DEFAULT_CONFIG_TYPE = "APPKEY";


    private final ComponentResource componentResource;

    @Autowired
    public BusyPeriodRelease(ComponentResource componentResource) {
        this.componentResource = componentResource;
    }

    @Override
    protected AtomStatus doProcess() {
        FlowResource flowResource = getFlowResource();
        String appKey = flowResource.getAppkey();
        if (StringUtils.isEmpty(appKey)) {
            return AtomStatus.SUCCESS;
        }
        UnlockDeployInput input = this.getInput(UnlockDeployInput.class);
        String configType = StringUtils.isEmpty(input.getConfigType()) ?
                BUSY_PERIOD_RELEASE_DEFAULT_CONFIG_TYPE : input.getConfigType();

        //TODO:自定义解禁日期 endTime 为 String 格式、litesetList 解禁
        CreateBusyPeriodRequestBO requestBO = CreateBusyPeriodRequestBO.builder()
                .configKey(appKey)
                .configType(configType)
                .actionType(BUSY_PERIOD_RELEASE_ACTION)
                .covered(true)
                .periodConfig(getPeriodConfigBO(input))
                .status(BUSY_PERIOD_RELEASE_DEFAULT_STATUS)
                .createUser(getCreateUser())
                .description(getDescription(input))
                .build();
        boolean status = componentResource.createBusyPeriod(requestBO);
        return Boolean.TRUE.equals(status) ? AtomStatus.SUCCESS : AtomStatus.FAIL;
    }

    /**
     * 获取描述信息
     *
     * @param input 入参
     * @return String
     */
    private String getDescription(UnlockDeployInput input) {
        String app = input.getApp();
        String reason = input.getReason();
        String comment = input.getComment();
        if (StringUtils.isEmpty(app) || LONG_TIME_RELEASE_TYPE.equals(app)) {
            return reason;
        }
        return reason + ": " + comment;
    }

    /**
     * 获取高峰期时间配置
     *
     * @param input 入参
     * @return {@link PeriodConfigBO}
     */
    private PeriodConfigBO getPeriodConfigBO(UnlockDeployInput input) {
        String app = input.getApp();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String startDate = now.format(formatter);
        String endDate;

        // 长期解禁
        if (LONG_TIME_RELEASE_TYPE.equals(app) && input.getEndTime() != null) {
            LocalDateTime endDateTime = now.plusDays(input.getEndTime());
            endDate = endDateTime.format(formatter);
        } else {
            // 临时解禁2小时
            LocalDateTime endDateTime = now.plusHours(TEMPORARY_RELEASE_TIME_PERIOD);
            endDate = endDateTime.format(formatter);
        }
        PeriodConfigBO periodConfigBO = new PeriodConfigBO();
        periodConfigBO.setStartDate(startDate);
        periodConfigBO.setEndDate(endDate);
        periodConfigBO.setWeekTime(Collections.emptyMap());
        return periodConfigBO;
    }
}
