package com.sankuai.avatar.sdk.entity.servicecatalog;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

/**
 * @author Jie.li.sh
 * @create 2020-03-17
 **/
@Data
public class ResponseResult<T> {
    private long code;
    /**
     * 信息提示：成功 或 相关错误信息
     */
    private String message;
    /**
     * 业务数据
     */
    private JsonNode data;

    public boolean isSuccess() {
        return code == 0;
    }

    public boolean isEmpty() {
        return code == 404;
    }

}
