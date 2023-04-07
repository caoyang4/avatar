package com.sankuai.avatar.capacity.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.List;

/**
 * Created by shujian on 2020/2/12.
 * @author chenxinli
 */
@Data
public class BladeResponse {
    private int code;
    private List<HashMap<String, ?>> data;
}



