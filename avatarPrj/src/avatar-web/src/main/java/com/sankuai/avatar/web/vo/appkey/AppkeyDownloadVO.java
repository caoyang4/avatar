package com.sankuai.avatar.web.vo.appkey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Appkey下载格式
 *
 * @author qinwei05
 * @create 2022-12-24 14:15
 * @date 2023/01/06
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AppkeyDownloadVO {

    /**
     * appkey名称
     */
    private String appkey;

    /**
     * 描述信息
     */
    private String comment;

    /**
     * 容灾等级
     */
    private Integer capacity;

    /**
     * 主机数
     */
    private Integer hostCount;

    /**
     * 服务等级
     */
    private String rank;

    /**
     * 是否有状态
     */
    private String stateful;

    /**
     * 是否存在Liteset机器
     */
    private String isLiteset;

    /**
     * 是否存在SET机器
     */
    private String isSet;

    /**
     * 上周资源利用率
     */
    private String utilization;

    /**
     * 研发负责人
     */
    private String rdAdmin;

    /**
     * 测试负责人
     */
    private String epAdmin;

    /**
     * 运维负责人
     */
    private String opAdmin;

    /**
     * 创建时间
     */
    private String createAt;
}
