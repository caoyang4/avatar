package com.sankuai.avatar.web.vo.application;

import lombok.Data;

/**
 * @author caoyang
 * @create 2023-03-03 15:27
 */
@Data
public class ApplicationWhiteVO {

    /**
     * 应用
     */
    private String name;

    /**
     * 是否加白
     */
    private Boolean isWhite;

    /**
     * 描述
     */
    private String description;

}
