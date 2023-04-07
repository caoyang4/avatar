package com.sankuai.avatar.client.plus.response;


import lombok.Data;

import java.util.List;

/**
 * @author qinwei05 (ว ˙o˙)ง
 * @date 2022/10/7 16:32
 * @version 1.0
 */
@Data
public class PlusResponseData<T> {
    /**
     * code == 0 是成功
     */
    private int code;
    /**
     * 信息提示：成功 或 相关错误信息
     */
    private String message;
    /**
     * 业务数据
     */
    private List<T> data;

    public boolean isSuccess() {
        return code == 0;
    }
}
