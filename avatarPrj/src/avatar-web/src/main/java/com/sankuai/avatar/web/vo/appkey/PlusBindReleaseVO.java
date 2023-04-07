package com.sankuai.avatar.web.vo.appkey;

import lombok.Data;

/**
 * @author qinwei05
 * @date 2022/3/28 11:12
 * @version 1.0
 */
@Data
public class PlusBindReleaseVO {
    /**
     * release id
     */
    private Long id;

    /**
     * 发布项名称
     */
    private String name;

    /**
     * 仓库地址
     */
    private String repository;

    /**
     * CODE跳转链接
     */
    private String codeUrl;

    /**
     * Plus跳转链接
     */
    private String plusUrl;

    /**
     * Plus配置编辑跳转链接
     */
    private String plusEditUrl;

    /**
     * 发布项类型; 通用general，其他 maven/1-maven/crane/nest/plugin
     */
    private String releaseType;

    /**
     * 是否可以删除
     */
    private Boolean isDel;

    /**
     * 是否可以编辑
     */
    private Boolean isEdit;
}
