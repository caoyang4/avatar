package com.sankuai.avatar.capacity.dto;

import lombok.Data;

import java.util.Map;

/**
 * Created by shujian on 2020/2/18.
 *
 */
@Data
public class NestResponse {
    private Integer code;
    private String state;
    private Map<String, Double> data;
}
