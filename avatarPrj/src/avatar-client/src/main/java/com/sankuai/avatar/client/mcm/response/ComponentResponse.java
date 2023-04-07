package com.sankuai.avatar.client.mcm.response;

import lombok.Data;

/**
 * @author zhaozhifan02
 */
@Data
public class ComponentResponse<T> {
    /**
     * code == 0 是成功
     */
    private Integer code;
    /**
     * 信息提示：成功 或 相关错误信息
     */
    private String message;
    /**
     * 业务数据
     */
    private T data;

    public boolean isSuccess() {
        return code == 0;
    }
}
