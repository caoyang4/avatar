package com.sankuai.avatar.web.vo.whitelist;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author chenxinli
 */
@Data
public class WhitelistVo {
    /**
     * 字段: id
     */
    private Integer id;

    /**
     * 字段: app
     */
    private String app;

    /**
     * 字段: cname
     */
    private String cname;

    /**
     * 字段: add_time
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date add_time;

    /**
     * 字段: end_time
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date end_time;

    /**
     * 字段: input_user
     */
    private String input_user;

    /**
     * 字段: appkey
     */
    private String appkey;

    /**
     * 字段: reason
     */
    private String reason;

    private String application;

    private String orgIds;

    private String setName;
}
