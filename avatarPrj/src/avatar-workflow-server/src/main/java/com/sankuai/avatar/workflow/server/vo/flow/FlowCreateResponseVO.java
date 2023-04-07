package com.sankuai.avatar.workflow.server.vo.flow;

import lombok.Data;

import java.util.List;

/**
 * 流程提交展示结果
 * @author Jie.li.sh
 * @create 2023-02-28
 **/
@Data
public class FlowCreateResponseVO {
    /**
     * 主键ID
     */
    private Integer id;

    /**
     * uuid
     */
    private String uuid;

    /**
     * 流程状态
     */
    private Integer state;

    /**
     * 流程状态
     */
    private String stateName;

    /**
     * 预检检查结果
     */
    private List<PreCheckItemVO> checkList;
}
