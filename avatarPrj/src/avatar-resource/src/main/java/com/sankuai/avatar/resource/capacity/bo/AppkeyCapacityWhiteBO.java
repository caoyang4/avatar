package com.sankuai.avatar.resource.capacity.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sankuai.avatar.resource.whitelist.constant.WhiteType;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 白名单信息
 * @author caoyang
 * @create 2022-11-03 15:50
 */
@Data
public class AppkeyCapacityWhiteBO {
    /**
     * 白名单类型
     */
    private WhiteType whiteApp;
    /**
     * 中文名称
     */
    @JsonProperty(value="cName")
    private String cName;
    /**
     * 加白截止时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    /**
     * 加白原因
     */
    private String reason;
}
