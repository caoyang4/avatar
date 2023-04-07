package com.sankuai.avatar.client.rocket.response;


import com.sankuai.avatar.client.rocket.model.RocketError;
import lombok.Data;

/**
 * @author qinwei05
 */
@Data
public class RocketHostResponse<T> {

    /**
     * 数据
     */
    private T data;

    /**
     * 错误
     */
    private RocketError error;
}
