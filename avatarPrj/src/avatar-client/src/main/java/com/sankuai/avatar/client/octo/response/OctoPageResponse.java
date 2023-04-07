package com.sankuai.avatar.client.octo.response;

import lombok.Data;

/**
 * @author qinwei05
 */
@Data
public class OctoPageResponse {

    /**
     * 页码
     */
    private Integer pageNo;

    /**
     * 页面大小
     */
    private Integer pageSize;

    /**
     * 总数
     */
    private Integer totalCount;

    /**
     * 总页数
     */
    private Integer totalPageCount;

}
