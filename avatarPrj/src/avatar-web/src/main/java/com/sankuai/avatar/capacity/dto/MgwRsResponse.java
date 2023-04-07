package com.sankuai.avatar.capacity.dto;

import lombok.Data;

import java.util.Map;

/**
 * Created by shujian on 2020/2/19.
 *
 */
@Data
public class MgwRsResponse {
    private String status;
    private Map<String, ?> data;
}
