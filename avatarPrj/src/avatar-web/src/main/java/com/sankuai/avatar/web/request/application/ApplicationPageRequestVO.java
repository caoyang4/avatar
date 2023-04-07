package com.sankuai.avatar.web.request.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sankuai.avatar.common.vo.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 应用分页请求VO对象
 *
 * @author zhangxiaoning07
 * @create 2022/11/24
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class ApplicationPageRequestVO extends PageRequest {

    /**
     * 用户MIS
     */
    private String mis;

    /**
     * 应用名/中文名
     */
    private String query;

    /**
     * 开发团队
     */
    private String orgIds;

    /**
     * 域名
     */
    private String domain;

    /**
     * 是否是公共服务
     */
    private Boolean isPublic;

    /**
     * 负责人:mis号／中文名
     */
    private String admin;

    /**
     * 成员:mis号／中文名; 还未支持
     */
    private String member;

    /**
     * 搜索创建时间>startTime
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    /**
     * 搜索创建时间<endTime
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;
}
