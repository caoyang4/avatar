package com.sankuai.avatar.resource.tree.bo;

import lombok.Data;

import java.util.List;

/**
 * @author qinwei05
 * @create 2022-12-29 18:06
 */
@Data
public class AppkeyTreeSrvDetailResponseBO {

    /**
     * 总计
     */
    private Integer total;

    /**
     * 每页数目
     */
    private Integer count;

    /**
     * 服务树节点
     */
    private List<AppkeyTreeSrvDetailBO> srvs;
}
