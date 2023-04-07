package com.sankuai.avatar.client.ops.response;

import lombok.Data;

/**
 * @author qinwei
 **/
@Data
public class OpsError {

    /**
     * 业务错误码
     */
    private int code;

    /**
     * 业务错误信息
     */
    private String message;
}
