package com.sankuai.avatar.dao.workflow.repository.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 流程数据
 *
 * @author zhaozhifan02
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "flow_data")
public class FlowDataDO {
    /**
     * 主键 id
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 流程ID
     */
    private Integer flowId;

    /**
     * 流程申请信息
     */
    private String input;

    /**
     * 流程操作的资源对象
     */
    private String resource;

    /**
     * 预检结果
     */
    private String checkerResult;

    /**
     * 公共数据
     */
    private String publicData;
}
