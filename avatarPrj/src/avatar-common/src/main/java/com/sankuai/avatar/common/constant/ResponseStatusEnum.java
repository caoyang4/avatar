package com.sankuai.avatar.common.constant;

import lombok.Getter;

/**
 * 业务自定义状态码
 * @author dpop
 */
@Getter
public enum ResponseStatusEnum {
    /**
     * 操作成功
     */
    OK(10000, "操作成功"),

    SYSTEM_ERROR(10001, "系统内部错误"),

    SERVICE_NOT_AVAILABLE(10002, "服务暂停"),

    IP_NOT_ALLOWED(10003, "IP限制不能请求该资源"),

    PARAMETER_ILLEGAL(10004, "参数错误"),

    REQUEST_LIMIT(10005, "任务过多，后端限流"),

    TIMEOUT(10006, "任务超时"),

    BAD_REQUEST(10007, "非法请求"),

    USER_NOT_ALLOWED(10008, "不合法的用户"),

    UNAUTHORIZED(10009, "没有权限"),

    REQUEST_LENGTH_EXCEEDED(10010, "请求长度超过限制"),

    INTERFACE_NOT_FOUND(10011, "接口不存在"),

    METHOD_NOT_ALLOWED(10012, "请求的HTTP METHOD不支持，请检查是否选择了正确的POST/GET方式"),

    IP_REQUEST_EXCEEDED(10013, "IP请求频次超过上限"),

    USER_REQUEST_EXCEEDED(10014, "用户请求频次超过上限"),

    SDK_CALL_ERROR(10015, "请求第三方系统异常"),

    SDK_BUSINESS_ERROR(10016, "请求第三方系统业务逻辑异常"),

    /**
     * 未知异常
     */
    UNKNOWN_ERROR(20000, "业务逻辑出错啦");

    /**
     * 状态码
     */
    private final Integer code;
    /**
     * 内容
     */
    private final String message;

    ResponseStatusEnum(Integer code, String message) {
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
