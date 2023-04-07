package com.sankuai.avatar.capacity.dto;

import lombok.Data;

/**
 * Created by shujian on 2020/2/27.
 * @author chenxinli
 */
@Data
public class OctoProviderDetailResponse {
    private String name;
    private String ip;
    private String cell;
    private Integer status;
    private String protocol;
}
