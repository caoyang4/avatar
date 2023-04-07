package com.sankuai.avatar.client.rocket.model;

import lombok.Data;

/**
 * @author qinwei
 **/
@Data
public class RocketError {

    /**
     * 业务错误码
     */
    private Integer code;

    /**
     * 业务错误信息
     */
    private String message;
}
