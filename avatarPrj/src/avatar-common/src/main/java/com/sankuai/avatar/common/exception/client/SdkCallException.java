package com.sankuai.avatar.common.exception.client;

import com.sankuai.avatar.common.constant.State;

/**
 * SdkCallException：HTTP请求异常（非业务逻辑异常）
 *
 * @author qinwei05
 */
public class SdkCallException extends RuntimeException {

    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8223484833265657324L;

    /**
     * HTTP请求状态：State枚举值
     */
    private State state;

    private Integer code;

	public SdkCallException(String detailMessage) {
        super(detailMessage);
    }

    public SdkCallException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public SdkCallException(State state, String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
        this.state = state;
    }

    public SdkCallException(Integer code, State state, String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
        this.state = state;
        this.code = code;
    }

    public SdkCallException(Integer code, String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
        this.code = code;
    }

    public SdkCallException(State state, String detailMessage) {
        super(detailMessage);
        this.state = state;
    }
    
    public State getState() {
        return state;
    }

    public Integer getCode() {
        return code;
    }
}
