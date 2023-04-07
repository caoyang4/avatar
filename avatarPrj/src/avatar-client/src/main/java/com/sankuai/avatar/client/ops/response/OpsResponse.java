package com.sankuai.avatar.client.ops.response;

import lombok.Data;

/**
 * @author qinwei
 **/
@Data
public class OpsResponse {

    /**
     * 错误
     */
    private OpsError error;

    /**
     * 是否成功
     *
     * @return boolean
     */
    public boolean isSuccess() {
        return error == null;
    }
}
