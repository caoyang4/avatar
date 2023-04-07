package com.sankuai.avatar.web.dto.tree;

import lombok.Data;

import java.util.List;

/**
 * @author qinwei05
 * @create 2022-12-29 18:06
 */
@Data
public class AppkeyTreeSrvDetailResponseDTO {

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
    private List<AppkeyTreeSrvDTO> srvs;
}
