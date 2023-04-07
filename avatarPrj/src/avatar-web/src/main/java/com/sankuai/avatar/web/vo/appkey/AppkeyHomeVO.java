package com.sankuai.avatar.web.vo.appkey;

import lombok.Data;

/**
 * Avatar首页-Appkey对象
 *
 * @author qinwei05
 * @create 2022-12-24 14:15
 * @date 2023/01/06
 */
@Data
public class AppkeyHomeVO {

    /**
     * appkey名称
     */
    private String appkey;

    /**
     * 描述信息
     */
    private String comment;

    /**
     * 服务名字
     */
    private String name;

    /**
     * 主机数
     */
    private Integer hostCount;

    /**
     * 是否被置顶
     */
    private Boolean top;
}
