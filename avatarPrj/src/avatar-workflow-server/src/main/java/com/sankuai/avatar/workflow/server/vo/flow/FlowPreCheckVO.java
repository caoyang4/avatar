package com.sankuai.avatar.workflow.server.vo.flow;

import lombok.Data;

import java.util.List;

/**
 * @author zhaozhifan02
 */
@Data
public class FlowPreCheckVO {
    /**
     * 预检项结果
     */
    List<PreCheckItemVO> items;
}
