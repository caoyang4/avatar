package com.sankuai.avatar.capacity.node;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * 资源利用率
 *
 * @author Jie.li.sh
 **/
@Builder
@AllArgsConstructor
@Data
public class ResourceUtil {
    Double value;
    Double lastWeekValue;
    Double yearPeekValue;
    Double lastWeekValueWithoutSet;
}
