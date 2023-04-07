package com.sankuai.avatar.capacity.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Created by shujian on 2020/2/18.
 *
 */
@Data
public class CraneResponse {
    private Integer status;
    private String message;
    private Map<String, List<Integer>> data;
}
