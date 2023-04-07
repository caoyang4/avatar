package com.sankuai.avatar.client.dayu.response;


import lombok.Data;

/**
 * @author qinwei05
 */
@Data
public class DayuResponseData<T> {
    /**
     * 业务码
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
}
