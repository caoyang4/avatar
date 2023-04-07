package com.sankuai.avatar.web.request;

import com.sankuai.avatar.common.vo.PageRequest;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author caoyang
 * @create 2022-12-28 17:15
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class SearchPageRequest extends PageRequest {
    private String mis;
    private String query;
}
