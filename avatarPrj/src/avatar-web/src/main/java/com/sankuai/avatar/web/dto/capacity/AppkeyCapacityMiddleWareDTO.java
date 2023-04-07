package com.sankuai.avatar.web.dto.capacity;

import com.sankuai.avatar.resource.capacity.constant.MiddleWareType;
import lombok.Data;

/**
 * @author caoyang
 * @create 2022-11-04 16:16
 */
@Data
public class AppkeyCapacityMiddleWareDTO {
    /**
     * 中间件类型
     */
    private MiddleWareType middleWare;

    /**
     * 是否使用
     */
    private Boolean used;
}
