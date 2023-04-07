package com.sankuai.avatar.capacity.dto;

import lombok.Data;

/**
 * @author chenxinli
 */
@Data
public class WhitelistResponse {
    private Integer code;
    private String message;
    private WhitelistDetailResponse data;
}
