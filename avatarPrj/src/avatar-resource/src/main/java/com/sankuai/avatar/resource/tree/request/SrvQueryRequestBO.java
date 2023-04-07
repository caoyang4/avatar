package com.sankuai.avatar.resource.tree.request;

import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author qinwei05
 * @date 2022/12/30 13:41
 */
@Data
@Builder
public class SrvQueryRequestBO {

    /**
     * type
     */
    private String type;

    /**
     * 用户MIS
     */
    private String user;

    /**
     * 页码
     */
    private Integer page;

    /**
     * 每页数量
     */
    private Integer pageSize;

    /**
     * 检索关键字
     */
    private String query;

    /**
     * 服务等级
     */
    private String rank;

    /**
     * 服务状态
     */
    private Boolean stateful;

    /**
     * 服务容灾等级:支持多个以逗号分隔
     */
    private String capacity;

    /**
     * 技术团队
     */
    private String bg;

    /**
     * 服务树：部门
     */
    private String owt;

    /**
     * 服务树：产品线
     */
    private String pdl;

    /**
     * 上线时间: 开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /**
     * 上线时间: 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
}
