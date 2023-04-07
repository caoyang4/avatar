package com.sankuai.avatar.web.vo.capacity;

import lombok.Data;

/**
 * appkey容灾加白信息
 * @author caoyang
 * @create 2022-11-04 14:48
 */
@Data
public class AppkeyCapacityWhiteVO {
    /**
     * 白名单类型
     */
    private String app;

    /**
     * 是否加白
     */
    private Boolean isWhite;

    /**
     * 是否可申请
     */
    private Boolean canApply;

    /**
     * 白名单申请信息
     */
    private CapacityWhiteVO white;

    /**
     * 白名单类型信息
     */
    private WhiteApplyVO apply;
}
