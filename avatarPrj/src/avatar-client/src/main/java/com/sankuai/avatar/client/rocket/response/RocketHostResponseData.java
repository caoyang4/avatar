package com.sankuai.avatar.client.rocket.response;


import lombok.Data;

import java.util.List;

/**
 * @author qinwei05
 */
@Data
public class RocketHostResponseData<T> {

    /**
     * 总计
     */
    private Integer total;

    /**
     * 偏移量
     */
    private Integer offset;

    /**
     * 返回的条数
     */
    private Integer limit;

    /**
     * 数据
     */
    private List<T> data;
}
