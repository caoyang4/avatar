package com.sankuai.avatar.workflow.core.display.model;

import lombok.Builder;
import lombok.Data;

/**
 * 申请信息表单展示
 *
 * @author Jie.li.sh
 * @create 2022-11-10
 **/
@Data
@Builder
public class InputDisplay  {
    /**
     * field
     */
    String fieldName;
    /**
     * 名称
     */
    String key;
    /**
     * 值
     */
    String value;
}
