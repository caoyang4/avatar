package com.sankuai.avatar.sdk.util;

import lombok.Builder;
import lombok.Data;

/**
 * @author Jie.li.sh
 * @create 2020-03-18
 **/
@Data
public class BaseQueryParams {
    @Builder.Default
    private Integer page = 1;
    @Builder.Default
    private Integer pageSize = 25;
    private String query;
    private String sortBy;
}
