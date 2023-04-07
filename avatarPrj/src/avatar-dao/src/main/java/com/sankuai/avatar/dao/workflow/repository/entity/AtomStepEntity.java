package com.sankuai.avatar.dao.workflow.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhaozhifan02
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AtomStepEntity {
    /**
     * 主键 id
     */
    private Integer id;

    /**
     * UUID
     */
    private String uuid;

    /**
     * Atom 名称
     */
    private String name;

    /**
     * 中文名
     */
    private String cnName;

    /**
     * 重试次数
     */
    private Integer retryTimes;

    /**
     * 超时时间
     */
    private Integer timeout;

}
