package com.sankuai.avatar.workflow.core.mcm.response;

import com.sankuai.mcm.client.sdk.dto.common.PreCheckItem;
import com.sankuai.mcm.client.sdk.enums.Decision;
import lombok.Data;

import java.util.List;

/**
 * 预检结果
 *
 * @author zhaozhifan02
 */
@Data
public class McmPreCheckResponse {
    /**
     * 决策结果
     */
    private Decision decision;

    /**
     * 是否可忽略
     */
    private boolean shouldIgnore;

    /**
     * 预检项
     */
    private List<PreCheckItem> items;
}
