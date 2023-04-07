package com.sankuai.avatar.sdk.entity.servicecatalog;

import lombok.Data;

/**
 * @author Jie.li.sh
 *
 * @param <T>*/
@Data
public class Result<T> {
    /**
     * 业务状态码：0为成功；404 对象不存在
     */
    private long code;
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

    public boolean isEmpty() {
        return code == 404;
    }
}
