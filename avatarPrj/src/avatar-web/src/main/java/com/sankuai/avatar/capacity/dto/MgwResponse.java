package com.sankuai.avatar.capacity.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Created by shujian on 2020/2/19.
 *
 */
@Data
public class MgwResponse {
    private String status;
    private List<Map<String, ?>> data;
}
