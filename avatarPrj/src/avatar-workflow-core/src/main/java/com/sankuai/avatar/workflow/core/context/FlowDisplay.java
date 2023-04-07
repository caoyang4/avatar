package com.sankuai.avatar.workflow.core.context;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sankuai.avatar.workflow.core.display.model.DiffDisplay;
import com.sankuai.avatar.workflow.core.display.model.InputDisplay;
import com.sankuai.avatar.workflow.core.display.model.OutputDisplay;
import com.sankuai.avatar.workflow.core.display.model.TextDisplay;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * flow的展示信息：包括申请表单、表格、提示信息等
 *
 * @author Jie.li.sh
 * @create 2022-11-10
 **/
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class FlowDisplay {
    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 流程ID
     */
    private Integer flowId;

    /**
     * 申请信息
     */
    private List<InputDisplay> input;

    /**
     * 风险提示信息
     */
    private List<TextDisplay> text;

    /**
     * diff
     */
    private DiffDisplay diff;

    /**
     * 输出
     */
    private OutputDisplay output;
}
