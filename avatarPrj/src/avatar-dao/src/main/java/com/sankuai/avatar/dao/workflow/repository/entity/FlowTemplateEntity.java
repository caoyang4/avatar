package com.sankuai.avatar.dao.workflow.repository.entity;

import com.sankuai.avatar.dao.workflow.repository.model.FlowTemplateTask;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author zhaozhifan02
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class FlowTemplateEntity {
    /**
     * 主键 id
     */
    private Integer id;

    /**
     * 模板名称
     */
    private String templateName;

    /**
     * 模板中文名
     */
    private String cnName;

    /**
     * 类型名称
     */
    private String typeName;

    /**
     * 配置信息
     */
    private Object config;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * atom 任务列表，兼容V1
     */
    private List<FlowTemplateTask> tasks;
}
