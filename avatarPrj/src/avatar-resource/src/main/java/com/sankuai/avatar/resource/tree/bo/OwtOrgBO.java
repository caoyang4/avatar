package com.sankuai.avatar.resource.tree.bo;

import lombok.Data;

/**
 * @author caoyang
 * @create 2023-01-09 19:32
 */
@Data
public class OwtOrgBO {

    /**
     * id
     */
    private Integer id;

    /**
     * org id
     */
    private Integer orgID;

    /**
     * 组织架构路径，从"公司/美团"开始
     */
    private String path;

}
