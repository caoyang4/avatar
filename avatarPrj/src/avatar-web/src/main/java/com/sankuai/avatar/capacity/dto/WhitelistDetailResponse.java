package com.sankuai.avatar.capacity.dto;

import lombok.Data;

import java.util.List;

/**
 * @author chenxinli
 */
@Data
public class WhitelistDetailResponse {
    private Integer totalCount;
    private Integer page;
    private Integer totalPage;
    private Integer pageSize;
    private List<Whitelist> items;
}
