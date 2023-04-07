package com.sankuai.avatar.resource.plus.bo;


import lombok.Data;

/**
 * 发布项对象
 *
 * @author qinwei05
 * @date 2022/10/19
 */
@Data
public class PlusReleaseBO {
    /**
     * release id
     */
    Long id;

    /**
     * 发布项名称
     */
    String name;

    /**
     * 仓库地址
     */
    String repository;

    /**
     * 发布项类型; 通用general，其他 maven/1-maven/crane/nest/plugin
     */
    String releaseType;
}
