package com.sankuai.avatar.client.jumper.reponse;

import lombok.Data;

/**
 * @author zhaozhifan02
 */
@Data
public class JumperResponse<T> {
    /**
     * code == 200 是成功
     */
    private Integer code;
    /**
     * 信息提示：成功 或 相关错误信息
     */
    private String msg;
    /**
     * 业务数据
     */
    private T result;

    public boolean isSuccess() {
        return code == 200;
    }
}
