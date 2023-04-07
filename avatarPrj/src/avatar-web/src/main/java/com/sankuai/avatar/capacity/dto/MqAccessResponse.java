package com.sankuai.avatar.capacity.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Created by shujian on 2020/2/19.
 */
@Data
public class MqAccessResponse {
    private Integer code;
    private List<Map<String, ?>> data;
}
