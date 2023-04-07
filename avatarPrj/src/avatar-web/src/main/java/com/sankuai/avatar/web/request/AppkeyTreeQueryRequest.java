package com.sankuai.avatar.web.request;

import com.sankuai.avatar.common.vo.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 服务树查询对象：全部服务、我的服务、我的关注
 *
 * @author caoyang
 * @create 2022-12-14 14:31
 */
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class AppkeyTreeQueryRequest extends PageRequest {

    /**
     * 用户MIS
     */
    private String user;

    /**
     * 查询类型
     */
    private String type;

    /**
     * 检索关键字
     */
    private String query;

    /**
     * 服务状态：有状态、无状态
     */
    private Boolean stateful;

    /**
     * 服务等级
     */
    private String rank;

    /**
     * SET化（Set、LiteSet）
     */
    private String cell;

    /**
     * paas服务
     */
    private Boolean paas;

    /**
     * 服务容灾等级:支持多个以逗号分隔
     */
    private String capacity;

    /**
     * 服务RD
     */
    private String rdAdmin;

    /**
     * 服务EP
     */
    private String epAdmin;

    /**
     * 服务SRE
     */
    private String opAdmin;

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
