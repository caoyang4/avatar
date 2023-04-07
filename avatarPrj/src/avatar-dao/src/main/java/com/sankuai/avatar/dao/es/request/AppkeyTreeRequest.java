package com.sankuai.avatar.dao.es.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sankuai.avatar.common.vo.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author caoyang
 * @create 2023-02-03 16:00
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AppkeyTreeRequest extends PageRequest {

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

    private String appkey;

    /**
     * 上线-起始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    /**
     * 上线-截止时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    /**
     * 服务状态：有状态、无状态
     */
    private Boolean stateful;

    /**
     * 服务等级
     */
    private String rank;

    /**
     * 服务容灾等级:支持多个以逗号分隔
     */
    private String capacity;

}
