package com.sankuai.avatar.client.octo.response;

import lombok.Data;

/**
 * @author caoyang
 * @create 2022-12-13 14:57
 */
@Data
public class OctoResponse<T> {

    private Boolean success;

    private Integer code;

    private String msg;

    private T data;

}
