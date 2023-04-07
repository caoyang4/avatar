package com.sankuai.avatar.web.vo.capacity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author caoyang
 * @create 2022-11-04 14:53
 */
@Data
public class CapacityWhiteVO {
    /**
     * 白名单类型
     */
    private String app;

    /**
     * 加白原因
     */
    private String reason;

    /**
     * 白名单中文名称
     */
    private String cname;

    /**
     * 白名单生效截止时间
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
}
