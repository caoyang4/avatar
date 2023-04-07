package com.sankuai.avatar.common.constant;

import lombok.Getter;

/**
 * @author zhaozhifan02
 */
@Getter
public enum ApiStatus {
    /**
     * 操作成功
     */
    OK(0, "操作成功"),

    /**
     * 未知异常
     */
    SUPPORT_ERROR(-2, "请求失败"),

    /**
     * 重复插入数据，唯一性冲突出错
     */
    DUPLICATE_KEY_ERROR(-3, "请勿重复添加相同数据"),

    /**
     * 未知异常
     */
    UNKNOWN_ERROR(500, "服务器出错啦"),

    /**
     *
     */
    BAD_REQUEST(400, "错误的请求"),

    /**
     * 已创建
     */
    CREATED(201, "已存在");

    /**
     * 状态码
     */
    private Integer code;
    /**
     * 内容
     */
    private String message;

    ApiStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}

