package com.sankuai.avatar.web.dto.capacity;

import com.sankuai.avatar.resource.whitelist.constant.WhiteType;
import lombok.Data;

import java.util.Date;

/**
 * 容灾相关的加白信息
 * @author caoyang
 * @create 2022-11-04 17:46
 */
@Data
public class AppkeyCapacityWhiteDTO {
    /**
     * 白名单类型
     */
    private WhiteType whiteApp;
    /**
     * 中文名称
     */
    private String cName;
    /**
     * 加白截止时间
     */
    private Date endTime;
    /**
     * 加白原因
     */
    private String reason;
}
