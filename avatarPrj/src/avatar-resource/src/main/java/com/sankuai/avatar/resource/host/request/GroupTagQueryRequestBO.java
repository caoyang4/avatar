package com.sankuai.avatar.resource.host.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dayu业务分组查询对象
 *
 * @author qinwei05
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupTagQueryRequestBO {

    /**
     * 分页页码（不填，默认1）
     */
    private Integer pageNum;

    /**
     * 分页每页大小（不填，默认10）
     */
    private Integer pageSize;

    /**
     * 需要搜索的内容
     */
    private String keyword;

    /**
     * 服务树owt
     */
    private String owt;

    /**
     * 是否隐藏（默认false）
     */
    private Boolean isHidden;
}
