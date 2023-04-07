package com.sankuai.avatar.client.soa.response;


import lombok.Data;

/**
 * @author qinwei05
 * @date 2022/11/7 16:32
 */
@Data
public class ScV2ResponseData<T> {
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
    private T data;
}
