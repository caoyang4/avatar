package com.sankuai.avatar.client.soa.response;


import lombok.Data;

import java.util.List;

/**
 * sc数据
 * 注意：cn > 0 and sn > 0 and sn <= 100
 *
 * @author qinwei05
 * @date 2022/11/7 16:32
 */
@Data
public class ScData {
    /**
     * 总条数
     */
    private int tn;

    /**
     * 当前页
     */
    private int cn;

    /**
     * 总分页数
     */
    private int pn;

    /**
     * 每页包含的个数
     */
    private int sn;

    /**
     * 数据列表
     */
    private List<String> items;
}
