package com.sankuai.avatar.capacity.node;

import com.sankuai.avatar.capacity.constant.MiddleWareName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jie.li.sh
 * @create 2020-04-05
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MiddleWare {
    /**
     * 中间间名称
     */
    MiddleWareName middleWareName;
    /**
     * 是否使用
     */
    Boolean used;
}
