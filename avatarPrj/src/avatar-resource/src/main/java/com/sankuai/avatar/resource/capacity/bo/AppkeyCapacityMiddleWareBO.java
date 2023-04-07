package com.sankuai.avatar.resource.capacity.bo;

import com.sankuai.avatar.resource.capacity.constant.MiddleWareType;
import lombok.Data;

/**
 * 中间件接入信息
 * @author caoyang
 * @create 2022-11-03 15:53
 */
@Data
public class AppkeyCapacityMiddleWareBO {

    /**
     * 中间件类型
     */
    private MiddleWareType middleWareName;

    /**
     * 是否使用
     */
    private Boolean used;
}
