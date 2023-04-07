package com.sankuai.avatar.web.vo.capacity;

import lombok.Data;

/**
 * 白名单申请信息
 * @author caoyang
 * @create 2022-11-04 14:53
 */
@Data
public class WhiteApplyVO {
    /**
     * 白名单类型
     */
    private String app;
    /**
     * 白名单名称
     */
    private String cname;
    /**
     * 白名单描述
     */
    private String desc;
}
