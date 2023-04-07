package com.sankuai.avatar.capacity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Whitelist {
    private Integer id;
    private String app;
    private String cname;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date add_time;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date end_time;
    private String input_user;
    private String appkey;
    private String reason;
}